package utils.settingsUtils.json;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonValidator {
    public static void validateJsonSchema(Path schemaPath, Path jsonPath) throws ValidationException, IOException {
        JSONObject jsonSchema = new JSONObject(new JSONTokener(Files.newInputStream(schemaPath)));
        JSONObject jsonSubject = new JSONObject(new JSONTokener(Files.newInputStream(jsonPath)));
        Schema schema = SchemaLoader.load(jsonSchema);
        schema.validate(jsonSubject);
    }
}
