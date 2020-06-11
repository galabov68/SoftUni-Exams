package app.ccb.services;

import app.ccb.domain.dtos.jsons.BranchImportDto;
import app.ccb.domain.entities.Branch;
import app.ccb.repositories.BranchRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;

import static app.ccb.constants.Constants.ERROR_INCORRECT_MESSAGE;
import static app.ccb.constants.Constants.SUCCESSFULLY_IMPORTED_MESSAGE;

@Service
@Transactional
public class BranchServiceImpl implements BranchService {

    private final String JSON_FILE_PATH = "C:\\Users\\galab\\Downloads\\ColonialCouncilBank\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\branches.json";

    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public BranchServiceImpl(BranchRepository branchRepository, FileUtil fileUtil, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() != 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return fileUtil.readFile(JSON_FILE_PATH);
    }

    @Override
    public String importBranches(String branchesJson) {
        StringBuilder sb = new StringBuilder();
        BranchImportDto[] branchImportDtos = gson.fromJson(branchesJson, BranchImportDto[].class);
        Arrays.stream(branchImportDtos)
                .forEach(branchImportDto -> {
                    Branch branch = modelMapper.map(branchImportDto, Branch.class);

                    if (!validationUtil.isValid(branch)) {
                        sb.append(ERROR_INCORRECT_MESSAGE).append(System.lineSeparator());
                        return;
                    }

                    branchRepository.saveAndFlush(branch);
                    sb.append(String.format(SUCCESSFULLY_IMPORTED_MESSAGE, branch.getClass().getSimpleName(), branch.getName()));
                });

        return sb.toString();
    }
}
