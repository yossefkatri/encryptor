package utils.settingsUtils.jaxb;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class XSDValidator {
    public static void validateXMLSchema(Path xsdPath, Path xmlPath) throws SAXException, IOException {
        SchemaFactory factory =
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new File(xsdPath.toString()));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new File(xmlPath.toString())));
    }
}
