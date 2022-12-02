package io.frictionlessdata.tableschema.schema;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.frictionlessdata.tableschema.Table;
import io.frictionlessdata.tableschema.TestHelper;
import io.frictionlessdata.tableschema.beans.ExplicitNamingBean;
import io.frictionlessdata.tableschema.field.ReflectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import static io.frictionlessdata.tableschema.TestHelper.getResourceFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SchemaInferralTests {

    @Test
    @DisplayName("Infer a Schema and test MapValueComparator")
    void inferASchema() throws Exception{
        File basePath = getResourceFile("/fixtures/data/");
        File source = getResourceFile("employee_data.csv");
        Table table = Table.fromSource(source, basePath);

        Schema schema = table.inferSchema();

        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonObject = objectMapper.readValue(schema.getJson(), Object.class);
        String expectedString = TestHelper.getResourceFileContent(
                "/fixtures/schema/employee_schema.json");
        assertEquals(objectMapper.readValue(expectedString, Object.class), jsonObject);
    }

    @Test
    @DisplayName("Infer a Bean Schema")
    void inferExplicitNamingBeanSchema() throws Exception{
        Schema schema = BeanSchema.infer(ExplicitNamingBean.class);
        String expectedSchemaString = TestHelper.getResourceFileContent(
                "/fixtures/beans/explicitnamingbean-schema.json");
        Schema expectedSchema = Schema.fromJson(expectedSchemaString, true);

        Assertions.assertEquals(expectedSchema, schema);
        // validate equals() method implementation on Schema and BeanSchema
        // don't simplify this to assertEquals()
        Assertions.assertTrue(schema.equals(expectedSchema));
        Assertions.assertTrue(expectedSchema.equals(schema));
    }

    @Test
    @DisplayName("Infer a Bean Schema, fix for https://github.com/frictionlessdata/tableschema-java/issues/72")
    void inferSchemaWithEmptyColumns() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        File basePath = getResourceFile("/fixtures/data/");

        // first, a CSV with empty cols
        File source = getResourceFile("AW_229_000001_000002.csv");
        Table table = Table.fromSource(source, basePath);

        Schema schema = table.inferSchema();

        Object jsonObject = objectMapper.readValue(schema.getJson(), Object.class);
        String expectedString = TestHelper.getResourceFileContent(
                "/fixtures/schema/issue-72.json");
        assertEquals(objectMapper.readValue(expectedString, Object.class), jsonObject);


        // then, a CSV with quoted empty cols
        File source2 = getResourceFile("AW_229_000001_000002_1Row.csv");
        Table table2 = Table.fromSource(source2, basePath);

        Schema schema2 = table2.inferSchema();

        jsonObject = objectMapper.readValue(schema2.getJson(), Object.class);
        assertEquals(objectMapper.readValue(expectedString, Object.class), jsonObject);
    }
}
