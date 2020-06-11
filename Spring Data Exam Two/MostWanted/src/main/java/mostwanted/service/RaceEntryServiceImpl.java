package mostwanted.service;

import mostwanted.domain.dtos.raceentries.RaceEntryImportRootDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;

import static mostwanted.common.Constants.INCORRECT_DATA_MESSAGE;
import static mostwanted.common.Constants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
@Transactional
public class RaceEntryServiceImpl implements RaceEntryService {

    private final static String RACE_ENTRIES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/race-entries.xml";
    private final RaceEntryRepository raceEntryRepository;
    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, CarRepository carRepository, RacerRepository racerRepository, ValidationUtil validationUtil, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper) {
        this.raceEntryRepository = raceEntryRepository;
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean raceEntriesAreImported() {
        return raceEntryRepository.count() > 0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws JAXBException, IOException {
        return fileUtil.readFile(RACE_ENTRIES_XML_FILE_PATH);
    }

    @Override
    public String importRaceEntries() throws JAXBException, IOException {
        StringBuilder sb = new StringBuilder();
        RaceEntryImportRootDto raceEntryImportRootDto = xmlParser.parseXml(RaceEntryImportRootDto.class, RACE_ENTRIES_XML_FILE_PATH);
        raceEntryImportRootDto.getRaceEntryImportDto()
                .forEach(raceEntryImportDto -> {
                    RaceEntry raceEntry = modelMapper.map(raceEntryImportDto, RaceEntry.class);
                    raceEntry.setRace(null);
                    raceEntry.setId(null);
                    Car car = carRepository.findById(raceEntryImportDto.getCarId()).orElse(null);
                    Racer racer = racerRepository.findByName(raceEntryImportDto.getRacer());

                    if (!validationUtil.isValid(raceEntry) || car == null || racer == null) {
                        sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                        return;
                    }

                    raceEntry.setCar(car);
                    raceEntry.setRacer(racer);
                    raceEntryRepository.saveAndFlush(raceEntry);
                    sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, raceEntry.getClass().getSimpleName(), raceEntry.getId()))
                            .append(System.lineSeparator());
                });

        return sb.toString();
    }
}
