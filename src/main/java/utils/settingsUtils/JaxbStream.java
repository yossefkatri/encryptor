package utils.settingsUtils;

import utils.FileStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JaxbStream<T> implements SettingsStream<T> {

    public File jaxbFile;

    public JaxbStream(String jaxbFileName) throws IOException {
        Path dir = Paths.get("src\\main\\java\\settings");
        if (!Files.exists(dir))
        {
            FileStream.createDirectory(dir);
        }
        this.jaxbFile = new File(Paths.get(dir.toString(),jaxbFileName).toString());
    }

    @Override
    public SettingsInfo<T> readData() throws IOException {
        return null;
    }

    @Override
    public void writeData(SettingsInfo<T> settingsInfo) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(SettingsInfo.class);
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(settingsInfo, jaxbFile);
    }
}
