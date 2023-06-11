package Utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVUtils {
    public static List<Map<String, String>> readCSV(String filePath) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);

            if (lines.isEmpty()) {
                return data;
            }

            String[] headers = lines.get(0).split(",");
            for (int i = 1; i < lines.size(); i++) {
                String[] values = lines.get(i).split(",");
                Map<String, String> rowData = new HashMap<>();

                for (int j = 0; j < headers.length; j++) {
                    String header = headers[j];
                    String value = (j < values.length) ? values[j] : "";
                    rowData.put(header, value);
                }

                data.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void writeCSV(String filePath, List<Map<String, String>> data) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = new ArrayList<>();

            if (!data.isEmpty()) {
                List<String> headers = new ArrayList<>(data.get(0).keySet());
                lines.add(String.join(",", headers));

                for (Map<String, String> rowData : data) {
                    List<String> values = new ArrayList<>();
                    for (String header : headers) {
                        String value = rowData.getOrDefault(header, "");
                        values.add(value);
                    }
                    lines.add(String.join(",", values));
                }
            }

            Files.write(path, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}