package alararestaurant.service;

import alararestaurant.domain.dtos.json.EmployeeDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final String EMPLOYEE_JSON_PATH = "C:\\Users\\galab\\Downloads\\AlaraRestaurant\\src\\main\\resources\\files\\employees.json";

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository, FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return fileUtil.readFile(EMPLOYEE_JSON_PATH);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder sb = new StringBuilder();

        EmployeeDto[] employeeDtos = gson.fromJson(employees, EmployeeDto[].class);
        Arrays.stream(employeeDtos)
                .forEach(employeeDto -> {
                    Employee employee = modelMapper.map(employeeDto, Employee.class);
                    if (!validationUtil.isValid(employee)) {
                        sb.append("Invalid data format.").append(System.lineSeparator());
                        return;
                    }

                    Position position = positionRepository.findAllByNameLike(employeeDto.getPosition());
                    if (position == null) {
                        position = new Position();
                        position.setName(employeeDto.getPosition());
                        if (!validationUtil.isValid(position)) {
                            sb.append("Invalid data format.").append(System.lineSeparator());
                            return;
                        }
                        positionRepository.saveAndFlush(position);
                    }
                    employee.setPosition(position);

                    employeeRepository.saveAndFlush(employee);
                    sb.append(String.format("Record %s successfully imported.%n", employeeDto.getName()));
                });

        return sb.toString();
    }
}
