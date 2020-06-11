package mostwanted.service;

import mostwanted.domain.dtos.races.RaceImportRootDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static mostwanted.common.Constants.INCORRECT_DATA_MESSAGE;
import static mostwanted.common.Constants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
@Transactional
public class RaceServiceImpl implements RaceService {

    private final static String RACES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/races.xml";
    private final RaceRepository raceRepository;
    private final DistrictRepository districtRepository;
    private final RaceEntryRepository raceEntryRepository;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public RaceServiceImpl(RaceRepository raceRepository, DistrictRepository districtRepository, RaceEntryRepository raceEntryRepository, ValidationUtil validationUtil, FileUtil fileUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.raceRepository = raceRepository;
        this.districtRepository = districtRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public Boolean racesAreImported() {
        return raceRepository.count() > 0;
    }

    @Override
    public String readRacesXmlFile() throws IOException, JAXBException {
        return fileUtil.readFile(RACES_XML_FILE_PATH);
    }

    @Override
    public String importRaces() throws JAXBException, IOException {
        StringBuilder sb = new StringBuilder();
        RaceImportRootDto raceImportRootDto = xmlParser.parseXml(RaceImportRootDto.class, RACES_XML_FILE_PATH);
        raceImportRootDto.getRaceImportDto()
                .forEach(raceImportDto -> {
                    Race race = modelMapper.map(raceImportDto, Race.class);
                    District district = districtRepository.findByName(raceImportDto.getDistrictName());
                    Set<RaceEntry> raceEntries = raceImportDto
                            .getEntryImportRootDto()
                            .getEntryImportDtos()
                            .stream()
                            .map(entryImportDto -> {
                                RaceEntry raceEntry = raceEntryRepository
                                        .findById(Integer.parseInt(entryImportDto.getId())).orElse(null);
                                if (raceEntry == null) {
                                    sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                                }

                                return raceEntry;
                            }).filter(Objects::nonNull)
                            .collect(Collectors.toSet());

                    if (!validationUtil.isValid(race) || district == null) {
                        sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                        return;
                    }

                    race.setDistrict(district);
                    race.setRaceEntries(null);
                    raceRepository.saveAndFlush(race);
                    raceEntries.forEach(raceEntry -> {
                        raceEntry.setRace(race);
                    });
                    sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, race.getClass().getSimpleName(), race.getId()))
                            .append(System.lineSeparator());
                });

        return sb.toString();
    }
}