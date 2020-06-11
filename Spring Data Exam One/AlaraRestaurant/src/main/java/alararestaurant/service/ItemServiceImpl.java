package alararestaurant.service;

import alararestaurant.domain.dtos.json.ItemDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
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
public class ItemServiceImpl implements ItemService {
    private final String ITEM_JSON_FILE_PATH = "C:\\Users\\galab\\Downloads\\AlaraRestaurant\\src\\main\\resources\\files\\items.json";

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository, FileUtil fileUtil, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        return fileUtil.readFile(ITEM_JSON_FILE_PATH);
    }

    @Override
    public String importItems(String items) {
        StringBuilder sb = new StringBuilder();

        ItemDto[] itemDtos = gson.fromJson(items, ItemDto[].class);
        Arrays.stream(itemDtos)
                .forEach(itemDto -> {
                    Item item = modelMapper.map(itemDto, Item.class);
                    if (!validationUtil.isValid(item) || itemRepository.findByName(itemDto.getName()) != null) {
                        sb.append("Invalid data format.").append(System.lineSeparator());
                        return;
                    }

                    Category category = categoryRepository.findByName(itemDto.getCategory());
                    if (category == null) {
                        category = new Category();
                        category.setName(itemDto.getCategory());
                        if (!validationUtil.isValid(category)) {
                            sb.append("Invalid data format.").append(System.lineSeparator());
                            return;
                        }
                        categoryRepository.saveAndFlush(category);
                    }

                    item.setCategory(category);
                    itemRepository.saveAndFlush(item);

                    sb.append(String.format("Record %s successfully imported.%n", itemDto.getName()));
                });

        return sb.toString();
    }
}
