package ru.zakharova.elena.market.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zakharova.elena.market.entities.Category;
import ru.zakharova.elena.market.entities.Product;
import ru.zakharova.elena.market.entities.dtos.ProductDTO;
import ru.zakharova.elena.market.entities.dtos.mappers.ProductMapper;
import ru.zakharova.elena.market.exceptions.MarketError;
import ru.zakharova.elena.market.exceptions.ResourceNotFoundException;
import ru.zakharova.elena.market.services.CategoriesService;
import ru.zakharova.elena.market.services.ProductsService;
import ru.zakharova.elena.market.utils.ProductFilter;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/products")
@Api("Set of endpoints for CRUD operations for products")
public class RestProductsController {
    private ProductsService productsService;
    private CategoriesService categoriesService;

    @Autowired
    public RestProductsController(ProductsService productsService, CategoriesService categoriesService) {
        this.productsService = productsService;
        this.categoriesService = categoriesService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Returns list of all products")
    public List<ProductDTO> getAllProducts(@RequestParam Map<String, String> requestParams,
                                        @RequestParam(value = "category" , required = false) List<Long> chosenCategoriesId) {
        List<Category> chosenCategoriesList = null;
        if (chosenCategoriesId != null) {
            chosenCategoriesList = categoriesService.getCategoriesByIds(chosenCategoriesId);
        }
        ProductFilter productFilter = new ProductFilter(requestParams, chosenCategoriesList);
        return ProductMapper.PRODUCT_MAPPER.fromProductList(productsService.findAll(productFilter.getSpec()));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Returns one product by id")
    public ResponseEntity<?> getOneProduct(@PathVariable @ApiParam("Id of the product to be requested. Cannot be empty") Long id) {
        if (!productsService.existsById(id)) {
            return new ResponseEntity<>(new MarketError(HttpStatus.NOT_FOUND.value(), "Product not found, id: " + id), HttpStatus.NOT_FOUND);
        }
        Product product = productsService.findById(id).get();
        return new ResponseEntity<>(ProductMapper.PRODUCT_MAPPER.fromProduct(product), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation("Removes all products")
    public void deleteAllProducts() {
        productsService.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Removes one product by id")
    public ResponseEntity<?> deleteOneProducts(@PathVariable Long id) {
        if (!productsService.existsById(id)) {
            return new ResponseEntity<>(new MarketError(HttpStatus.NOT_FOUND.value(), "Product not found, id: " + id), HttpStatus.NOT_FOUND);
        }
        productsService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new product")
    public Product saveNewProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            product.setId(null);
        }
        return productsService.saveOrUpdate(product);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Modifies an existing product")
    public ResponseEntity<?> modifyProduct(@RequestBody Product product) {
        if (product.getId() == null || !productsService.existsById(product.getId())) {
            throw new ResourceNotFoundException("Product not found, id: " + product.getId());
        }
        if (product.getPrice().doubleValue() < 0.0) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Product's price can not be negative"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(productsService.saveOrUpdate(product), HttpStatus.OK);
    }


}