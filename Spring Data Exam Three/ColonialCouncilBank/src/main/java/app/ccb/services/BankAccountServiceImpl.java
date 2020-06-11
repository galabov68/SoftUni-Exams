package app.ccb.services;

import app.ccb.domain.dtos.xmls.bank_account.BankAccountImportRootDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Client;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.ClientRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import app.ccb.util.XmlParser;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;

import static app.ccb.constants.Constants.ERROR_INCORRECT_MESSAGE;
import static app.ccb.constants.Constants.SUCCESSFULLY_IMPORTED_MESSAGE;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {
    private final String XML_FILE_PATH = "C:\\Users\\galab\\Downloads\\ColonialCouncilBank\\ColonialCouncilBank\\src\\main\\resources\\files\\xml\\bank-accounts.xml";
    private final BankAccountRepository bankAccountRepository;
    private final ClientRepository clientRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, ClientRepository clientRepository, FileUtil fileUtil, ValidationUtil validationUtil, XmlParser xmlParser, Gson gson, ModelMapper modelMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean bankAccountsAreImported() {
        return this.bankAccountRepository.count() != 0;
    }

    @Override
    public String readBankAccountsXmlFile() throws IOException {
        return fileUtil.readFile(XML_FILE_PATH);
    }

    @Override
    public String importBankAccounts() throws JAXBException {
        StringBuilder sb = new StringBuilder();
        BankAccountImportRootDto bankAccountImportRootDto = xmlParser.parseXml(BankAccountImportRootDto.class, XML_FILE_PATH);
        bankAccountImportRootDto.getBankAccountImportDtos()
                .forEach(bankAccountImportDto -> {
                    BankAccount bankAccount = modelMapper.map(bankAccountImportDto, BankAccount.class);
                    bankAccount.setClient(null);
                    Client client = clientRepository.findByFullName(bankAccountImportDto.getClient());

                    if (!validationUtil.isValid(bankAccount) || client == null) {
                        sb.append(ERROR_INCORRECT_MESSAGE).append(System.lineSeparator());
                        return;
                    }

                    bankAccountRepository.saveAndFlush(bankAccount);
                    sb.append(String.format(SUCCESSFULLY_IMPORTED_MESSAGE, bankAccount.getClass().getSimpleName(), bankAccount.getAccountNumber()));
                    client.setBankAccount(bankAccount);
                });

        return sb.toString();
    }
}
