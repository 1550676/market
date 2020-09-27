package ru.zakharova.elena.market.utils.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.zakharova.elena.market.entities.Product;
import ru.zakharova.elena.market.entities.dtos.ProductDTO;
import java.util.List;

@Mapper(uses = {CategoryMapper.class })
public interface ProductMapper {
    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "categoriesDTOs", target = "categories")
    Product toProduct(ProductDTO productDTO);

    List<Product> toProductsList(List<ProductDTO> productsDTOs);

    @InheritInverseConfiguration
    ProductDTO fromProduct(Product product);

    List<ProductDTO> fromProductsList(List<Product> products);
}

