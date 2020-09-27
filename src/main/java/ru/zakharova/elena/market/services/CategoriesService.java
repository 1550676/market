package ru.zakharova.elena.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zakharova.elena.market.entities.Category;
import ru.zakharova.elena.market.entities.dtos.CategoryDTO;
import ru.zakharova.elena.market.utils.mappers.CategoryMapper;
import ru.zakharova.elena.market.repositories.CategoryRepository;
import java.util.List;

@Service
public class CategoriesService {
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public List<CategoryDTO> getAllDTOs() {
        return CategoryMapper.CATEGORY_MAPPER.fromCategoriesList(getAll());
    }

    public List<Category> getCategoriesByIds(List<Long> ids) {
        return categoryRepository.findAllById(ids);
    }

}
