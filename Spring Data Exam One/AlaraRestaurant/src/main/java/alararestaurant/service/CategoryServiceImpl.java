package alararestaurant.service;

import alararestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        StringBuilder sb = new StringBuilder();
        categoryRepository.findAllByOrderByItemsDesc()
                .forEach(category -> {
                    sb.append(String.format("Category: %s%n", category.getName()));
                    category.getItems()
                            .forEach(item -> {
                                sb.append(String.format("---Item Name: %s%n", item.getName()));
                                sb.append(String.format("---Item Price: %s%n%n", item.getPrice()));
                            });
                });
        return sb.toString();
    }
}
