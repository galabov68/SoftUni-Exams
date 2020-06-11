package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.CarImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;

import static mostwanted.common.Constants.INCORRECT_DATA_MESSAGE;
import static mostwanted.common.Constants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final static String CARS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/cars.json";
    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, RacerRepository racerRepository, FileUtil fileUtil, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean carsAreImported() {
        return carRepository.count() > 0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return fileUtil.readFile(CARS_JSON_FILE_PATH);
    }

    @Override
    public String importCars(String carsFileContent) {
        StringBuilder sb = new StringBuilder();
        CarImportDto[] carImportDtos = gson.fromJson(carsFileContent, CarImportDto[].class);
        Arrays.stream(carImportDtos)
                .forEach(carImportDto -> {
                    Car car = modelMapper.map(carImportDto, Car.class);
                    Racer racer = racerRepository.findByName(carImportDto.getRacerName());

                    if (!validationUtil.isValid(car) || racer == null) {
                        sb.append(INCORRECT_DATA_MESSAGE);
                        return;
                    }

                    car.setRacer(racer);
                    carRepository.saveAndFlush(car);
                    sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, racer.getClass().getSimpleName(), car.getModel()));
                });

        return sb.toString();
    }
}
