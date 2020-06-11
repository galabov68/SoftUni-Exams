package app.ccb.services;

import app.ccb.domain.dtos.xmls.card.CardImportRootDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Card;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.CardRepository;
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
public class CardServiceImpl implements CardService {

    private final String XML_FILE_PATH = "C:\\Users\\galab\\Downloads\\ColonialCouncilBank\\ColonialCouncilBank\\src\\main\\resources\\files\\xml\\cards.xml";
    private final CardRepository cardRepository;
    private final BankAccountRepository bankAccountRepository;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public CardServiceImpl(CardRepository cardRepository, BankAccountRepository bankAccountRepository, ValidationUtil validationUtil, FileUtil fileUtil, XmlParser xmlParser, Gson gson, ModelMapper modelMapper) {
        this.cardRepository = cardRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean cardsAreImported() {
        return this.cardRepository.count() != 0;
    }

    @Override
    public String readCardsXmlFile() throws IOException {
        return fileUtil.readFile(XML_FILE_PATH);
    }

    @Override
    public String importCards() throws JAXBException {
        StringBuilder sb = new StringBuilder();
        CardImportRootDto cardImportRootDto = xmlParser.parseXml(CardImportRootDto.class, XML_FILE_PATH);
        cardImportRootDto.getCardImportDtos()
                .forEach(cardImportDto -> {
                    Card card = modelMapper.map(cardImportDto, Card.class);
                    BankAccount bankAccount = bankAccountRepository.findByAccountNumber(cardImportDto.getAccountNumber());

                    if (!validationUtil.isValid(card) || bankAccount == null) {
                        sb.append(ERROR_INCORRECT_MESSAGE).append(System.lineSeparator());
                        return;
                    }

                    card.setBankAccount(bankAccount);
                    cardRepository.saveAndFlush(card);
                    sb.append(String.format(SUCCESSFULLY_IMPORTED_MESSAGE, card.getClass().getSimpleName(), card.getCardNumber()));
                });

        return sb.toString();
    }
}
