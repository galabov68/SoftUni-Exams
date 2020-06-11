package mostwanted.util;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface XmlParser {

    <O> O parseXml(Class<O> objectClass, String filePath) throws JAXBException, IOException;
}
