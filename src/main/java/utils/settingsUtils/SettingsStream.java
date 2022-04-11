package utils.settingsUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface SettingsStream<T> {
    SettingsInfo<T> readData() throws Exception;
    void writeData(SettingsInfo<T> settingsInfo) throws Exception;
}
