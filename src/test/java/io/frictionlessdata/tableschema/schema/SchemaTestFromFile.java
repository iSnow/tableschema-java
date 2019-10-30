package io.frictionlessdata.tableschema.schema;

import io.frictionlessdata.tableschema.Field;
import io.frictionlessdata.tableschema.Schema;
import io.frictionlessdata.tableschema.exceptions.InvalidCastException;
import io.frictionlessdata.tableschema.exceptions.PrimaryKeyException;
import io.frictionlessdata.tableschema.fk.ForeignKey;
import io.frictionlessdata.tableschema.fk.Reference;
import org.everit.json.schema.ValidationException;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;


/**
 *
 * 
 */
public class SchemaTestFromFile {
    
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    

    @Test
    public void testCreateSchemaFromFileWithValidPrimaryKey() throws Exception{
        String sourceFileAbsPath = SchemaTestFromFile.class.getResource("/fixtures/primarykey/simple_schema_with_valid_pk.json").getPath();
        Schema schemaWithValidPK = new Schema(new File(sourceFileAbsPath), true);
        
        Assert.assertEquals("id", schemaWithValidPK.getPrimaryKey());
    }
    
    @Test
    public void testCreateSchemaFromFileWithInvalidPrimaryKey() throws Exception{
        String sourceFileAbsPath = SchemaTestFromFile.class.getResource("/fixtures/primarykey/simple_schema_with_invalid_pk.json").getPath();
        
        exception.expect(PrimaryKeyException.class);
        new Schema(new File(sourceFileAbsPath), true);
    }
    
    @Test
    public void testCreateSchemaFromFileWithValidCompositeKey() throws Exception{
        String sourceFileAbsPath = SchemaTestFromFile.class.getResource("/fixtures/primarykey/simple_schema_with_valid_ck.json").getPath();
        Schema schemaWithValidCK = new Schema(new File(sourceFileAbsPath), false); //FIXME: Why does this primary key fail validation when strict=true
        
        String[] compositePrimaryKey = schemaWithValidCK.getPrimaryKey();
        Assert.assertEquals("name", compositePrimaryKey[0]);
        Assert.assertEquals("surname", compositePrimaryKey[1]);
        
    }
    
    @Test
    public void testCreateSchemaFromFileWithInvalidCompositeKey() throws Exception{
        String sourceFileAbsPath = SchemaTestFromFile.class.getResource("/fixtures/primarykey/simple_schema_with_invalid_ck.json").getPath();
        
        exception.expect(PrimaryKeyException.class);
        new Schema(new File(sourceFileAbsPath), true);
    }

    @Test
    public void testInvalidForeignKeyArray() throws Exception{
        String sourceFileAbsPath = SchemaTestFromFile.class.getResource("/fixtures/foreignkeys/schema_invalid_fk_array.json").getPath();
        
        exception.expectMessage("The reference's fields property must be an array if the outer fields is an array.");
        new Schema(new File(sourceFileAbsPath), true);
    }
    
    @Test
    public void testInvalidForeignKeyArrayString() throws Exception{
        String sourceFileAbsPath = SchemaTestFromFile.class.getResource("/fixtures/foreignkeys/schema_invalid_fk_array_string.json").getPath();
 
        exception.expectMessage("The reference's fields property must be a string if the outer fields is a string.");
        new Schema(new File(sourceFileAbsPath), true);
    }
    
    @Test
    public void testInvalidForeignKeyArrayStringRef() throws Exception{
        String sourceFileAbsPath = SchemaTestFromFile.class.getResource("/fixtures/foreignkeys/schema_invalid_fk_array_string_ref.json").getPath();
        
        exception.expectMessage("The reference's fields property must be an array if the outer fields is an array.");
        new Schema(new File(sourceFileAbsPath), true);
    }
    
    @Test
    public void testInvalidForeignKeyArrayWrongNumber() throws Exception{
        String sourceFileAbsPath = SchemaTestFromFile.class.getResource("/fixtures/foreignkeys/schema_invalid_fk_array_wrong_number.json").getPath();
        
        exception.expectMessage("The reference's fields property must be an array of the same length as that of the outer fields' array.");
        new Schema(new File(sourceFileAbsPath), true);
    }
    
    @Test
    public void testInvalidForeignKeyNoReference() throws Exception{
        String sourceFileAbsPath = SchemaTestFromFile.class.getResource("/fixtures/foreignkeys/schema_invalid_fk_no_reference.json").getPath();
        
        exception.expectMessage("A foreign key must have the fields and reference properties.");
        new Schema(new File(sourceFileAbsPath), true);
    }
    
    @Test
    public void testInvalidForeignKeyString() throws Exception{
        String sourceFileAbsPath = SchemaTestFromFile.class.getResource("/fixtures/foreignkeys/schema_invalid_fk_string.json").getPath();
        
        exception.expect(ValidationException.class);
        new Schema(new File(sourceFileAbsPath), true);
    }
    
    @Test
    public void testInvalidForeignKeyStringArrayRef() throws Exception{
        String sourceFileAbsPath = SchemaTestFromFile.class.getResource("/fixtures/foreignkeys/schema_invalid_fk_string_array_ref.json").getPath();
        
        exception.expectMessage("The reference's fields property must be a string if the outer fields is a string.");
        new Schema(new File(sourceFileAbsPath), true);
    }
    
    @Test
    public void testValidForeignKeyArray() throws Exception{
        String sourceFileAbsPath = SchemaTestFromFile.class.getResource("/fixtures/foreignkeys/schema_valid_fk_array.json").getPath();
        Schema schema = new Schema(new File(sourceFileAbsPath), true);

        JSONArray parsedFields = schema.getForeignKeys().get(0).getFields();
        Assert.assertEquals("id", parsedFields.getString(0));
        Assert.assertEquals("title", parsedFields.getString(1));
        
        JSONArray refFields = schema.getForeignKeys().get(0).getReference().getFields();  
        Assert.assertEquals("fk_id", refFields.getString(0));
        Assert.assertEquals("title_id", refFields.getString(1));
    }
    
    @Test
    public void testValidForeignKeyString() throws Exception{
        String sourceFileAbsPath = SchemaTestFromFile.class.getResource("/fixtures/foreignkeys/schema_valid_fk_string.json").getPath();
        Schema schema = new Schema(new File(sourceFileAbsPath), true);
        
        Assert.assertEquals("position_title", schema.getForeignKeys().get(0).getFields());
        Assert.assertEquals("positions", schema.getForeignKeys().get(0).getReference().getResource());
        Assert.assertEquals("name", schema.getForeignKeys().get(0).getReference().getFields());
    }
}
