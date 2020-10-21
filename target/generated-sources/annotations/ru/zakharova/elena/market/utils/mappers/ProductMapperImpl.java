package ru.zakharova.elena.market.utils.mappers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.mapstruct.factory.Mappers;
import ru.zakharova.elena.market.entities.Product;
import ru.zakharova.elena.market.entities.dtos.CategoryDTO;
import ru.zakharova.elena.market.entities.dtos.ProductDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-10T16:52:39+0300",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11.0.8 (JetBrains s.r.o.)"
)
public class ProductMapperImpl implements ProductMapper {

    private final CategoryMapper categoryMapper = Mappers.getMapper( CategoryMapper.class );

    @Override
    public Product toProduct(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setCategories( categoryMapper.toCategoriesList( productDTO.getCategoriesDTOs() ) );
        product.setId( productDTO.getId() );
        product.setTitle( productDTO.getTitle() );
        product.setPrice( productDTO.getPrice() );

        return product;
    }

    @Override
    public List<Product> toProductsList(List<ProductDTO> productsDTOs) {
        if ( productsDTOs == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( productsDTOs.size() );
        for ( ProductDTO productDTO : productsDTOs ) {
            list.add( toProduct( productDTO ) );
        }

        return list;
    }

    @Override
    public ProductDTO fromProduct(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        if ( productDTO.getCategoriesDTOs() != null ) {
            List<CategoryDTO> list = categoryMapper.fromCategoriesList( product.getCategories() );
            if ( list != null ) {
                productDTO.getCategoriesDTOs().addAll( list );
            }
        }
        productDTO.setId( product.getId() );
        productDTO.setTitle( product.getTitle() );
        productDTO.setPrice( product.getPrice() );

        return productDTO;
    }

    @Override
    public List<ProductDTO> fromProductsList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( products.size() );
        for ( Product product : products ) {
            list.add( fromProduct( product ) );
        }

        return list;
    }
}
