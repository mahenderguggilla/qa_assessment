package stepdefinitions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Utilities.CSVUtils;
import io.cucumber.java.en.Given;

public class DataLoadAndTransformationTest {
	
	
	private static final String INPUT_DIRECTORY = "app/In/";
    private static final String OUTPUT_DIRECTORY = "app/out/";

    private static final String INSTRUMENT_DETAILS_FILE = "InstrumentDetails.csv";
    private static final String POSITION_DETAILS_FILE = "PositionDetails.csv";
    private static final String OUTPUT_FILE = "PositionReport.csv";

    private static final String ID_COLUMN = "ID";
    private static final String NAME_COLUMN = "Name";
    private static final String ISIN_COLUMN = "ISIN";
    private static final String QUANTITY_COLUMN = "Quantity";
    private static final String UNIT_PRICE_COLUMN = "Unit Price";
    private static final String POSITION_ID_COLUMN = "PositionID";
    private static final String TOTAL_PRICE_COLUMN = "TotalPrice";
    
    @Given("I load data")
    public void i_load_data() {
        // Load instrument details from file 1
        String instrumentDetailsPath = INPUT_DIRECTORY + INSTRUMENT_DETAILS_FILE;
        List<Map<String, String>> instrumentDetails = CSVUtils.readCSV(instrumentDetailsPath);

        // Convert instrument details to a map
        Map<String, Instrument> instrumentMap = convertInstrumentDetailsToMap(instrumentDetails);

        // Process position details from file 2 and generate the report
        String positionDetailsPath = INPUT_DIRECTORY + POSITION_DETAILS_FILE;
        List<Map<String, String>> positionDetails = CSVUtils.readCSV(positionDetailsPath);

        List<Map<String, String>> transformedData = transformPositionDetails(positionDetails, instrumentMap);

        // Write the transformed data to the output file
        String outputPath = OUTPUT_DIRECTORY + OUTPUT_FILE;
        CSVUtils.writeCSV(outputPath, transformedData);

        System.out.println("Data load and transformation completed.");
    }

    private static Map<String, Instrument> convertInstrumentDetailsToMap(List<Map<String, String>> instrumentDetails) {
        Map<String, Instrument> instrumentMap = new HashMap<>();

        for (Map<String, String> instrument : instrumentDetails) {
            String id = instrument.get(ID_COLUMN);
            String name = instrument.get(NAME_COLUMN);
            String isin = instrument.get(ISIN_COLUMN);
            double unitPrice = Double.parseDouble(instrument.get(UNIT_PRICE_COLUMN));

            Instrument instrumentObj = new Instrument(id, name, isin, unitPrice);
            instrumentMap.put(isin, instrumentObj);
        }

        return instrumentMap;
    }

    private static List<Map<String, String>> transformPositionDetails(List<Map<String, String>> positionDetails,
                                                                     Map<String, Instrument> instrumentMap) {
        List<Map<String, String>> transformedData = new ArrayList<>();

        for (Map<String, String> position : positionDetails) {
            String id = position.get(ID_COLUMN);
            String positionId = position.get(POSITION_ID_COLUMN);
            String isin = position.get(ISIN_COLUMN);
            int quantity = Integer.parseInt(position.get(QUANTITY_COLUMN));

            Instrument instrument = instrumentMap.get(isin);
            if (instrument != null) {
                double totalPrice = instrument.getUnitPrice() * quantity;

                Map<String, String> transformedPosition = new LinkedHashMap<>();
                transformedPosition.put(ID_COLUMN, id);
                transformedPosition.put(POSITION_ID_COLUMN, positionId);
                transformedPosition.put(ISIN_COLUMN, isin);
                transformedPosition.put(QUANTITY_COLUMN, String.valueOf(quantity));
                transformedPosition.put(TOTAL_PRICE_COLUMN, String.valueOf(totalPrice));

                transformedData.add(transformedPosition);
            }
        }

        return transformedData;
    }

    private static class Instrument {
        private String id;
        private String name;
        private String isin;
        private double unitPrice;

        public Instrument(String id, String name, String isin, double unitPrice) {
            this.id = id;
            this.name = name;
            this.isin = isin;
            this.unitPrice = unitPrice;
        }

        public double getUnitPrice() {
            return unitPrice;
        }
    }    
}

