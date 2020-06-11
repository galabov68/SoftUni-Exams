package mostwanted.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface RaceEntryService {

    Boolean raceEntriesAreImported();

    String readRaceEntriesXmlFile() throws IOException, JAXBException;

    String importRaceEntries() throws JAXBException, IOException;
}
