package ru.zakharova.elena.market.entities.dtos.mappers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ru.zakharova.elena.market.entities.Category;
import ru.zakharova.elena.market.entities.dtos.CategoryDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-24T00:28:11+0300",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11.0.8 (JetBrains s.r.o.)"
)
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toCategory(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( categoryDTO.getId() );
        category.setTitle( categoryDTO.getTitle() );
        category.setDiscount( categoryDTO.getDiscount() );

        return category;
    }

    @Override
    public CategoryDTO fromCategory(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId( category.getId() );
        categoryDTO.setTitle( category.getTitle() );
        categoryDTO.setDiscount( category.getDiscount() );

        return categoryDTO;
    }

    @Override
    public List<Category> toCategoryList(List<CategoryDTO> categoryDTOS) {
        if ( categoryDTOS == null ) {
            return null;
        }

        List<Category> list = new ArrayList<Category>( categoryDTOS.size() );
        for ( CategoryDTO categoryDTO : categoryDTOS ) {
            list.add( toCategory( categoryDTO ) );
        }

        return list;
    }

    @Override
    public List<CategoryDTO> fromCategoryList(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        List<CategoryDTO> list = new ArrayList<CategoryDTO>( categories.size() );
        for ( Category category : categories ) {
            list.add( fromCategory( category ) );
        }

        return list;
    }
}