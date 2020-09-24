package ru.zakharova.elena.market.entities.dtos.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.zakharova.elena.market.entities.Category;
import ru.zakharova.elena.market.entities.dtos.CategoryDTO;

import java.util.List;

@Mapper()
public interface CategoryMapper {
    CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CategoryDTO categoryDTO);

    @InheritInverseConfiguration
    CategoryDTO fromCategory(Category category);

    List<Category> toCategoryList(List<CategoryDTO> categoryDTOS);

    List<CategoryDTO> fromCategoryList(List<Category> categories);

}
