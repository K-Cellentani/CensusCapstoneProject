package website.JBDC;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import website.CensusController.Chart;
import website.CensusController.CensusControllerApplication;
import website.CensusController.DataForm;

import javax.sql.DataSource;

@Component
public class NationalAverages {
    private final JdbcTemplate jdbcTemplate;
    private final Dictionary<String, String> dictionary;
    public CensusControllerApplication censusControllerApplication;

    public NationalAverages(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dictionary = new Hashtable<>();
        dictionary.put("establishments", "number_of_establishments");
        dictionary.put("employment", "employment");
        dictionary.put("annual_payroll", "annual_payroll_in_thousands");
    }

    public void setCensusControllerApplication(CensusControllerApplication censusControllerApplication) {
        this.censusControllerApplication = censusControllerApplication;
    }

    public void getCensusData(Chart chart) {
        String data = chart.getData();

        String type = dictionary.get(data);

        String sql = "SELECT year, AVG(" + type + ") AS " + type +
                " FROM census_table " +
                "GROUP BY year " +
                "ORDER BY year";


        List<Map<String, Object>> chartResult = jdbcTemplate.queryForList(sql);

        try (FileWriter csvWriter = new FileWriter("chartResult.csv")) {
            csvWriter.append("year,");
            csvWriter.append(type);
            csvWriter.append("\n");
            for (Map<String, Object> row : chartResult) {
                csvWriter.append(row.get("year").toString());
                csvWriter.append(",");
                csvWriter.append(row.get(type).toString());
                csvWriter.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Starting python script");
        long timestamp = censusControllerApplication.getTimestamp();
        runPythonScript("C:\\Users\\kmcel\\Documents\\Capstone_two\\CensusController\\src\\main\\java\\website\\JBDC\\pychart.py", Long.toString(timestamp));
    }

    private void runPythonScript(String scriptPath, String timestamp) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath, timestamp);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Python script executed successfully. Image Number is " + timestamp);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
