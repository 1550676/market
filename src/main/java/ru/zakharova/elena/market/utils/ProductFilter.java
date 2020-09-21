package ru.zakharova.elena.market.utils;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import ru.zakharova.elena.market.entities.Category;
import ru.zakharova.elena.market.entities.Product;
import ru.zakharova.elena.market.repositories.specifications.ProductSpecifications;

import java.util.List;
import java.util.Map;

@Getter
public class ProductFilter {
    private Specification<Product> spec;
    private StringBuilder filterDefinition;

    public ProductFilter(Map<String, String> map, List<Category> categories) {
        this.spec = Specification.where(null);
        this.filterDefinition = new StringBuilder();
        if (map.containsKey("min_price") && !map.get("min_price").isEmpty()) {
            int minPrice = Integer.parseInt(map.get("min_price"));
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(minPrice));
            filterDefinition.append("&min_price=").append(minPrice);
        }
        if (map.containsKey("max_price") && !map.get("max_price").isEmpty()) {
            int maxPrice = Integer.parseInt(map.get("max_price"));
            spec = spec.and(ProductSpecifications.priceLesserOrEqualsThan(maxPrice));
            filterDefinition.append("&max_price=").append(maxPrice);
        }
        if (map.containsKey("sub_title") && !map.get("sub_title").isEmpty()) {
            String titlePart = map.get("sub_title");
            spec = spec.and(ProductSpecifications.titleByPart("%" + titlePart + "%"));
            filterDefinition.append("&sub_title=").append(titlePart);
        }

        if (categories != null) {
            Specification<Product> specCategories = null;

            for (Category c : categories) {
                if (specCategories == null) {
                    specCategories = ProductSpecifications.productByCategory(c);
                } else {
                    specCategories = specCategories.or(ProductSpecifications.productByCategory(c));
                }
                filterDefinition.append("&category=").append(c.getId());
            }
            spec = spec.and(specCategories);
        }
    }

}
