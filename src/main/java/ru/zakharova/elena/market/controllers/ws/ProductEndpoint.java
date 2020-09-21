package ru.zakharova.elena.market.controllers.ws;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.zakharova.elena.market.repositories.ProductsRepository;
import ru.zakharova.elena.market.utils.ws.GetProductRequest;
import ru.zakharova.elena.market.utils.ws.GetProductResponse;

@Endpoint
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.zakharova-elena.com/spring/ws/products";

    private ProductsRepository productsRepository;

    @Autowired
    public ProductEndpoint(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    // @Transactional
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProducts(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        response.getProduct().addAll(productsRepository.findAll());
        return response;

    }
}
