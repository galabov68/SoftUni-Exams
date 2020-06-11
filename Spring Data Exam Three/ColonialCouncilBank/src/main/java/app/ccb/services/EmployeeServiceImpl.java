package app.ccb.services;

import app.ccb.domain.dtos.jsons.EmployeeImportDto;
import app.ccb.domain.entities.Branch;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.BranchRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

import static app.ccb.constants.Constants.ERROR_INCORRECT_MESSAGE;
import static app.ccb.constants.Constants.SUCCESSFULLY_IMPORTED_MESSAGE;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final String JSON_FILE_PATH = "C:\\Users\\galab\\Downloads\\ColonialCouncilBank\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\employees.json";
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BranchRepository branchRepository, ValidationUtil validationUtil, FileUtil fileUtil, Gson gson, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() != 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return fileUtil.readFile(JSON_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder sb = new StringBuilder();
        EmployeeImportDto[] employeeImportDtos = gson.fromJson(employees, EmployeeImportDto[].class);
        Arrays.stream(employeeImportDtos)
                .forEach(employeeImportDto -> {
                    Employee employee = modelMapper.map(employeeImportDto, Employee.class);
                    Branch branch = branchRepository.findByName(employeeImportDto.getBranchName());
                    String[] splittedName = employeeImportDto.getFullName().split(" ");
                    employee.setFirstName(splittedName[0]);
                    employee.setLastName(splittedName[1]);
                    int[] splittedDate = Arrays.stream(employeeImportDto.getStartedOn().split("-")).mapToInt(Integer::parseInt).toArray();
                    LocalDate localDate = LocalDate.of(splittedDate[0], splittedDate[1], splittedDate[2]);
                    employee.setStartedOn(localDate);

                    if (!validationUtil.isValid(employee) || branch == null) {
                        sb.append(ERROR_INCORRECT_MESSAGE).append(System.lineSeparator());
                        return;
                    }

                    employee.setBranch(branch);
                    employeeRepository.saveAndFlush(employee);
                    sb.append(String.format(SUCCESSFULLY_IMPORTED_MESSAGE, employee.getClass().getSimpleName(), employeeImportDto.getFullName()));
                });

        return sb.toString();
    }

    @Override
    public String exportTopEmployees() {
        StringBuilder sb = new StringBuilder();
        employeeRepository.employeesWithAnyClients()
                .forEach(employee -> {
                    sb.append(employee.getFirstName()).append(" ").append(employee.getLastName()).append(System.lineSeparator());
                    employee.getClients()
                            .forEach(client -> {
                                sb.append("     ").append(client.getFullName()).append(System.lineSeparator());
                            });
                });

        return sb.toString();
    }
}
