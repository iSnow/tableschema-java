/*
 *
 */
package io.frictionlessdata.tableschema.datasources;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface for a source of tabular data.
 */
public interface DataSource {  
    public Iterator<String[]> iterator() throws Exception;
    public String[] getHeaders() throws Exception;
    public List<String[]> data() throws Exception;

    /**
     * Write to native format
     * @param outputFile the File to write to
     * @throws Exception thrown if write operation fails
     */
    public void write(File outputFile) throws Exception;

    /**
     * Write as RFC 4180 CSV file
     * @param outputFile the File to write to
     * @throws Exception thrown if write operation fails
     */
    void writeCsv(File outputFile) throws Exception;

    /**
     * Factory method to instantiate either a JsonArrayDataSource or a
     * CsvDataSource based on input format
     * @return DataSource created from input String
     */
    public static DataSource createDataSource(String input) {
        try {
            JSONArray arr = new JSONArray(input);
            return new JsonArrayDataSource(arr);
        } catch (JSONException ex) {
            // JSON parsing failed, treat it as a CSV
            return new CsvDataSource(input);
        }
    }

    /**
     * Factory method to instantiate either a JsonArrayDataSource or a
     * CsvDataSource based on input format
     * @return DataSource created from input String
     */
    public static DataSource createDataSource(File input) throws IOException {
        // The path value can either be a relative path or a full path.
        // If it's a relative path then build the full path by using the working directory.
        if(!input.exists()) {
            input = new File(System.getProperty("user.dir") + "/" + input.getAbsolutePath());
        }

        try (InputStream is = new FileInputStream(input)) { // Read the file.
            return createDataSource(is);
        }
    }

    /**
     * Factory method to instantiate either a JsonArrayDataSource or a
     * CsvDataSource based on input format
     * @return DataSource created from input String
     */
    public static DataSource createDataSource(InputStream input) throws IOException {
        String content = null;

        // Read the file.
        try (Reader fr = new InputStreamReader(input)) {
            try (BufferedReader rdr = new BufferedReader(fr)) {
                content = rdr.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException ex) {
            throw ex;
        }

        return createDataSource(content);
    }
}
