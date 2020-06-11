package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.RacerImportDto;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;

import static mostwanted.common.Constants.*;

@Service
@Transactional
public class RacerServiceImpl implements RacerService {

    private final static String RACERS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/racers.json";
    private final RacerRepository racerRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public RacerServiceImpl(RacerRepository racerRepository, TownRepository townRepository, FileUtil fileUtil, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.racerRepository = racerRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean racersAreImported() {
        return racerRepository.count() > 0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return fileUtil.readFile(RACERS_JSON_FILE_PATH);
    }

    @Override
    public String importRacers(String racersFileContent) {
        StringBuilder sb = new StringBuilder();
        RacerImportDto[] racerImportDtos = gson.fromJson(racersFileContent, RacerImportDto[].class);
        Arrays.stream(racerImportDtos)
                .forEach(racerImportDto -> {
                    Racer racer = modelMapper.map(racerImportDto, Racer.class);
                    Town town = townRepository.findByName(racerImportDto.getHomeTown());
                    if (!validationUtil.isValid(racer) || town == null) {
                        sb.append(INCORRECT_DATA_MESSAGE);
                        return;
                    }

                    if (racerRepository.findByName(racerImportDto.getName()) != null) {
                        sb.append(DUPLICATE_DATA_MESSAGE);
                        return;
                    }
                    racer.setHomeTown(town);
                    racerRepository.saveAndFlush(racer);
                    sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, racer.getClass().getSimpleName(), racer.getName()));
                });
        return sb.toString();
    }

    @Override
    public String exportRacingCars() {
        StringBuilder sb = new StringBuilder();
        racerRepository.racingCars()
                .forEach(racer -> {
                    sb.append(String.format("Name: %s%n", racer.getName()));
                    if (racer.getAge() != 0) {
                        sb.append(String.format("Age: %d%n", racer.getAge()));
                    }
                    sb.append("Cars:").append(System.lineSeparator());
                    racer.getCars()
                            .forEach(car -> {
                                sb.append(" ");
                                sb.append(car.getBrand()).append(" ");
                                sb.append(car.getModel()).append(" ");
                                sb.append(car.getYearOfProduction()).append(" ").append(System.lineSeparator());
                            });
                });

        return sb.toString();
    }
}
