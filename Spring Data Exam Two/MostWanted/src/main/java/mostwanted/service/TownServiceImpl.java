package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.TownImportDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;

import static mostwanted.common.Constants.*;

@Service
@Transactional
public class TownServiceImpl implements TownService {

    private final static String TOWNS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/towns.json";
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean townsAreImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return fileUtil.readFile(TOWNS_JSON_FILE_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) {
        StringBuilder sb = new StringBuilder();
        TownImportDto[] townImportDtos = gson.fromJson(townsFileContent, TownImportDto[].class);
        Arrays.stream(townImportDtos)
                .forEach(townImportDto -> {
                    Town town = modelMapper.map(townImportDto, Town.class);
                    if (!validationUtil.isValid(town)) {
                        sb.append(INCORRECT_DATA_MESSAGE);
                        return;
                    }

                    if (townRepository.findByName(town.getName()) != null) {
                        sb.append(DUPLICATE_DATA_MESSAGE);
                        return;
                    }

                    townRepository.saveAndFlush(town);
                    sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, town.getClass().getSimpleName(), town.getName()));
                });

        return sb.toString();
    }

    @Override
    public String exportRacingTowns() {
        StringBuilder sb = new StringBuilder();
        townRepository.racingTowns()
                .forEach(town -> {
                    sb.append(String.format("Name: %s%n", town.getName()));
                    sb.append(String.format("Racers: %d%n%n", town.getRacers().size()));
                });

        return sb.toString();
    }
}
