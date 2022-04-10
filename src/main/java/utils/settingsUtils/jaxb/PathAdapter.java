package utils.settingsUtils.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathAdapter extends XmlAdapter<String, Path> {
    @Override
    public Path unmarshal(String v) {
        return Paths.get(v);
    }

    @Override
    public String marshal(Path v) {
        return v.toString();
    }
}
