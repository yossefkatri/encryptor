package utils.settingsUtils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import utils.FileStream;
import utils.settingsUtils.SettingsInfo;
import utils.settingsUtils.SettingsStream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonStream<T> implements SettingsStream<T> {

    public File jsonFile;
    public ObjectMapper mapper = new ObjectMapper();

    public JsonStream(String jsonFileName) throws IOException {
        Path dir = Paths.get("src\\main\\java\\settings\\json");
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
