package utils.settingsUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import utils.FileStream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonStream<T> implements SettingsStream<T>{

    public File jsonFile;
    public ObjectMapper mapper = new ObjectMapper();

    public JsonStream(String jsonFileName) throws IOException {
        Path dir = Paths.get("src\\main\\java\\settings");
        if (!Files.exists(dir))
        {
                FileStream.createDirectory(dir);
        }
        this.jsonFile = new File(Paths.get(dir.toString(),jsonFileName).toString());
    }

    public SettingsInfo<T> readData() throws IOException {
        return mapper.readValue(jsonFile,SettingsInfo.class);
    }
    public void writeData(SettingsInfo<T> settingsInfo) throws IOException {
        mapper.writeValue(jsonFile,settingsInfo);
    }
}
