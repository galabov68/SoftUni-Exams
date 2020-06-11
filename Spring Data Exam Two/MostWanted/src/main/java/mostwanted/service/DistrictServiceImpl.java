package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
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
public class DistrictServiceImpl implements DistrictService {

    private final static String DISTRICT_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/districts.json";
    private final DistrictRepository districtRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository, TownRepository townRepository, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.districtRepository = districtRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean districtsAreImported() {
        return districtRepository.count() > 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return fileUtil.readFile(DISTRICT_JSON_FILE_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) {
        StringBuilder sb = new StringBuilder();
        DistrictImportDto[] districtImportDtos = gson.fromJson(districtsFileContent, DistrictImportDto[].class);

        Arrays.stream(districtImportDtos)
                .forEach(districtImportDto -> {
                    District district = modelMapper.map(districtImportDto, District.class);
                    Town town = townRepository.findByName(districtImportDto.getTownName());

                    if (!validationUtil.isValid(district) || town == null) {
                        sb.append(INCORRECT_DATA_MESSAGE);
                        return;
                    }

                    if (districtRepository.findByName(districtImportDto.getName()) != null) {
                        sb.append(DUPLICATE_DATA_MESSAGE);
                        return;
                    }

                    district.setTown(town);
                    districtRepository.saveAndFlush(district);
                    sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, district.getClass().getSimpleName(), district.getName()));
                });

        return sb.toString();
    }
}
