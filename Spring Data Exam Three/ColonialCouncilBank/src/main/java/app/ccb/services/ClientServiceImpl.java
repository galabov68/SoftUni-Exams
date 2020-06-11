package app.ccb.services;

import app.ccb.domain.dtos.jsons.ClientImportDto;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.ClientRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static app.ccb.constants.Constants.ERROR_INCORRECT_MESSAGE;
import static app.ccb.constants.Constants.SUCCESSFULLY_IMPORTED_MESSAGE;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final String FILE_JSON_PATH = "C:\\Users\\galab\\Downloads\\ColonialCouncilBank\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\clients.json";
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public ClientServiceImpl(ClientRepository clientRepository, EmployeeRepository employeeRepository, FileUtil fileUtil, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean clientsAreImported() {
        return this.clientRepository.count() != 0;
    }

    @Override
    public String readClientsJsonFile() throws IOException {
        return fileUtil.readFile(FILE_JSON_PATH);
    }

    @Override
    public String importClients(String clients) {
        StringBuilder sb = new StringBuilder();
        ClientImportDto[] clientImportDtos = gson.fromJson(clients, ClientImportDto[].class);
        Arrays.stream(clientImportDtos)
                .forEach(clientImportDto -> {
                    Client client = modelMapper.map(clientImportDto, Client.class);
                    String fullName = clientImportDto.getFirstName() + " " + clientImportDto.getLastName();
                    client.setFullName(fullName);
                    String[] splittedEmployeeName = clientImportDto.getAppointedEmployee().split(" ");
                    Employee employee = employeeRepository.findByFirstNameAndLastName(splittedEmployeeName[0], splittedEmployeeName[1]);

                    if (!validationUtil.isValid(client) || employee == null) {
                        sb.append(ERROR_INCORRECT_MESSAGE).append(System.lineSeparator());
                        return;
                    }

                    Client findClient = clientRepository.findByFullName(fullName);
                    if (findClient != null) {
                        employee.getClients().add(findClient);
                        return;
                    }

                    clientRepository.saveAndFlush(client);
                    sb.append(String.format(SUCCESSFULLY_IMPORTED_MESSAGE, client.getClass().getSimpleName(), client.getFullName()));
                    employee.getClients().add(client);
                });

        return sb.toString();
    }

    @Override
    public String exportFamilyGuy() {
        StringBuilder sb = new StringBuilder();
        List<Client> clients = clientRepository.clientByMostCards();
        Client client = clients.get(0);
        sb.append(client.getFullName()).append(System.lineSeparator());
        sb.append(client.getAge()).append(System.lineSeparator());
        sb.append(client.getBankAccount().getAccountNumber()).append(System.lineSeparator());
        sb.append("Cards:").append(System.lineSeparator());
        client.getBankAccount().getCards()
                .forEach(card -> {
                    sb.append("     ").append(card.getCardNumber()).append(" ").append(card.getCardStatus()).append(System.lineSeparator());
                });

        return sb.toString();
    }
}
