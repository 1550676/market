package ru.zakharova.elena.market.controllers.ws;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.zakharova.elena.market.entities.Product;
import ru.zakharova.elena.market.entities.dtos.mappers.ProductMapper;
import ru.zakharova.elena.market.repositories.ProductsRepository;
import ru.zakharova.elena.market.repositories.specifications.ProductSpecifications;
import ru.zakharova.elena.market.utils.ws.GetProductRequest;
import ru.zakharova.elena.market.utils.ws.GetProductResponse;

@Endpoint
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.zakharova-elena.com/spring/ws/products";

    private ProductsRepository productsRepository;
    private GetProductResponse response;
    private GetProductRequest request;

    public ProductEndpoint(ProductsRepository productsRepository, GetProductResponse response, GetProductRequest request) {
        this.productsRepository = productsRepository;
        this.response = response;
        this.request = request;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProductsDTOs(@RequestPayload GetProductRequest request) {
        response.getProductDTOs().clear();
        Specification<Product> spec = Specification.where(null);
        spec = spec.and(ProductSpecifications.titleByPart("%" + request.getTitlePart() + "%"));
        response.getProductDTOs().addAll(ProductMapper.PRODUCT_MAPPER.fromProductList(productsRepository.findAll(spec)));
        return response;
    }
}
