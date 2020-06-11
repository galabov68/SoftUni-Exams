package mostwanted.util;

import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;

public class XmlParserImpl implements XmlParser {
    private final FileUtil fileUtil;

    @Autowired
    public XmlParserImpl(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public <O> O parseXml(Class<O> objectClass, String filePath) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(objectClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (O) unmarshaller.unmarshal(new StringReader(fileUtil.readFile(filePath)));
    }
}
