package Utilities;

import java.util.List;
import java.util.Map;

public class ScenarioContext {
    private List<Map<String, String>> testData;

    public List<Map<String, String>> getTestData() {
        return testData;
    }

    public void setTestData(List<Map<String, String>> testData) {
        this.testData = testData;
    }
}

