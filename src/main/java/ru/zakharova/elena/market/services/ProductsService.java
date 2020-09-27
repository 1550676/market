package ru.zakharova.elena.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.zakharova.elena.market.entities.Product;
import ru.zakharova.elena.market.entities.dtos.ProductDTO;
import ru.zakharova.elena.market.utils.mappers.ProductMapper;
import ru.zakharova.elena.market.repositories.ProductsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    private ProductsRepository productsRepository;

    @Autowired
    public void setProductRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }


    public Product saveOrUpdate(Product product) {
        return productsRepository.save(product);
    }

    public Optional<Product> findById(Long id) {
        return productsRepository.findById(id);
    }

    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    public Page<Product> findAll(Specification<Product> spec, Integer page) {
        if (page < 1L) {
            page = 1;
        }
        return productsRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }

    public List<Product> findAll(Specification<Product> spec) {
        return productsRepository.findAll(spec);
    }

    public List<ProductDTO> findAllDTO() {
        return ProductMapper.PRODUCT_MAPPER.fromProductsList(productsRepository.findAll());
    }

    public List<ProductDTO> findAllDTO(Specification<Product> spec) {
        return ProductMapper.PRODUCT_MAPPER.fromProductsList(findAll(spec));
    }

    public void deleteAll() {
        productsRepository.deleteAll();
    }

    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return productsRepository.existsById(id);
    }

}
