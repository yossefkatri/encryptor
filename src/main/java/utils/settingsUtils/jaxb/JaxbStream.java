package utils.settingsUtils.jaxb;

import utils.FileStream;
import utils.settingsUtils.SettingsInfo;
import utils.settingsUtils.SettingsStream;

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
    public File XsdFile;

    public JaxbStream(String jaxbFileName) throws IOException {
        Path dir = Paths.get("src\\main\\java\\settings\\jaxb");
        if (!Files.exists(dir))
        {
            FileStream.createDirectory(dir);
        }
        this.jaxbFile = new File(Paths.get(dir.toString(),jaxbFileName).toString());
        this.XsdFile = new File(Paths.get(dir.toString(),"settings.xsd").toString());
    }

    @Override
    public SettingsInfo<T> readData() throws Exception {
        XSDValidator.validateXMLSchema(XsdFile.toPath(),jaxbFile.toPath());
        JAXBContext context = JAXBContext.newInstance(SettingsInfo.class);
        return (SettingsInfo<T>) context.createUnmarshaller().unmarshal(jaxbFile);
    }

    @Override
    public void writeData(SettingsInfo<T> settingsInfo) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(SettingsInfo.class);
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(settingsInfo, jaxbFile);
    }
}
