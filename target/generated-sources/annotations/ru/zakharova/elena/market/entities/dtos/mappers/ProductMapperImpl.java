package ru.zakharova.elena.market.entities.dtos.mappers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.mapstruct.factory.Mappers;
import ru.zakharova.elena.market.entities.Product;
import ru.zakharova.elena.market.entities.dtos.CategoryDTO;
import ru.zakharova.elena.market.entities.dtos.ProductDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-24T00:28:11+0300",
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

        product.setCategories( categoryMapper.toCategoryList( productDTO.getCategoryDTOS() ) );
        product.setId( productDTO.getId() );
        product.setTitle( productDTO.getTitle() );
        product.setPrice( productDTO.getPrice() );

        return product;
    }

    @Override
    public List<Product> toProductList(List<ProductDTO> productDTOS) {
        if ( productDTOS == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( productDTOS.size() );
        for ( ProductDTO productDTO : productDTOS ) {
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

        if ( productDTO.getCategoryDTOS() != null ) {
            List<CategoryDTO> list = categoryMapper.fromCategoryList( product.getCategories() );
            if ( list != null ) {
                productDTO.getCategoryDTOS().addAll( list );
            }
        }
        productDTO.setId( product.getId() );
        productDTO.setTitle( product.getTitle() );
        productDTO.setPrice( product.getPrice() );

        return productDTO;
    }

    @Override
    public List<ProductDTO> fromProductList(List<Product> products) {
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
