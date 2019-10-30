package io.frictionlessdata.tableschema;

import io.frictionlessdata.tableschema.datasources.CsvDataSource;
import io.frictionlessdata.tableschema.datasources.DataSource;
import io.frictionlessdata.tableschema.datasources.JsonArrayDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class DataSourceTest {
    private String jsonString1 = "[" +
            "{" +
            "\"city\": \"london\"," +
            "\"year\": 2017," +
            "\"population\": 8780000" +
            "}," +
            "{" +
            "\"city\": \"paris\"," +
            "\"year\": 2017," +
            "\"population\": 2240000" +
            "}," +
            "{" +
            "\"city\": \"rome\"," +
            "\"year\": 2017," +
            "\"population\": 2860000" +
            "}" +
            "]";

    @Test
    public void testCreateJsonArrayDataSource() throws Exception{
        DataSource ds = DataSource.createDataSource(jsonString1);
        Assert.assertTrue(ds instanceof JsonArrayDataSource);
    }

    @Test
    public void testJsonArrayDataSourceHeaders() throws Exception{
        DataSource ds = DataSource.createDataSource(jsonString1);
        String[] headers = ds.getHeaders();
        System.out.println(headers);
    }

    @Test
    public void createCsvDataSource() throws Exception{
        String dates = this.getDatesCsvData();
        DataSource ds = DataSource.createDataSource(dates);
        Assert.assertTrue(ds instanceof CsvDataSource);
    }

    @Test
    public void writeCsvToCsv() throws Exception{
        String dates = this.getDatesCsvData();
        String content = null;

        DataSource ds = DataSource.createDataSource(dates);
        File tempFile = Files.createTempFile("tableschema-", ".csv").toFile();
        ds.writeCsv(tempFile);
        try (FileReader fr = new FileReader(tempFile)) {
            try (BufferedReader rdr = new BufferedReader(fr)) {
                content = rdr.lines().collect(Collectors.joining("\n"));
            }
        }
        // evade the CRLF mess by nuking all CR chars
        Assert.assertEquals(dates.replaceAll("\\r", ""), content.replaceAll("\\r", ""));
    }

    private String getDatesCsvData() {
        return getFileContents("/fixtures/dates_data.csv");
    }

    private static String getFileContents(String fileName) {
        try {
            // Create file-URL of source file:
            URL sourceFileUrl = DataSourceTest.class.getResource(fileName);
            // Get path of URL
            Path path = Paths.get(sourceFileUrl.toURI());
            return new String(Files.readAllBytes(path));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
