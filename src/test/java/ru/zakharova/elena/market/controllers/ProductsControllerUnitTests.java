package ru.zakharova.elena.market.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.zakharova.elena.market.controllers.rest.RestProductsController;
import ru.zakharova.elena.market.entities.Product;
import ru.zakharova.elena.market.services.CategoriesService;
import ru.zakharova.elena.market.services.ProductsService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RestProductsController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class ProductsControllerUnitTests {
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductsService productsService;
    @MockBean
    private CategoriesService categoriesService;
    @Autowired
    private MockMvc mockMvc;

//    @AfterEach
//    public void resetDb() {
//        repository.deleteAll();
//    }

    // @Test
    public void givenProduct_whenAdd_thenStatus201andProductReturned() throws Exception {
        Product product = new Product(1L, "Cheese", new BigDecimal(320), new ArrayList<>());
        Mockito.when(productsService.saveOrUpdate((Product) any())).thenReturn(product);
        mockMvc.perform(
                post("/api/v1/products")
                        .content(objectMapper.writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Cheese"))
                .andExpect(jsonPath("$.price").value(320.00))
                .andExpect(jsonPath("$.categories").isArray())
                .andExpect(jsonPath("$.categories").isEmpty());
    }

   // @Test
    public void givenId_whenGetExistingProduct_thenStatus200andProductReturned() throws Exception {
        Product product = new Product(1L, "Cheese", new BigDecimal(320), new ArrayList<>());
        Mockito.when(productsService.findById(any()).get()).thenReturn(product);
        Mockito.when(productsService.existsById(any())).thenReturn(true);
        mockMvc.perform(
                get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Cheese"))
                .andExpect(jsonPath("$.price").value(320.00))
                .andExpect(jsonPath("$.categories").isArray())
                .andExpect(jsonPath("$.categories").isEmpty());
    }


    @Test
    public void givenId_whenGetNotExistingProduct_thenStatus404anExceptionThrown() throws Exception {
        Mockito.when(productsService.existsById(any())).thenReturn(false);
        mockMvc.perform(
                get("/api/v1/products/0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(content().string(org.hamcrest.core.StringContains.containsString("not found")))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    public void giveProduct_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {
        Product product = new Product(1L, "Cheese", new BigDecimal(320), new ArrayList<>());
        Mockito.when(productsService.saveOrUpdate((Product) any())).thenReturn(product);
        Mockito.when(productsService.existsById(any())).thenReturn(true);
        mockMvc.perform(
                put("/api/v1/products")
                        .content(objectMapper.writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Cheese"))
                .andExpect(jsonPath("$.price").value(320.00))
                .andExpect(jsonPath("$.categories").isArray())
                .andExpect(jsonPath("$.categories").isEmpty());
    }

    @Test
    public void givenId_whenDeleteProduct_thenStatus200() throws Exception {
        Mockito.when(productsService.existsById(any())).thenReturn(true);
        mockMvc.perform(
                delete("/api/v1/products/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNothing_whenGetAllProducts_thenStatus200() throws Exception {
        Product p1 = new Product(1L, "Cheese", new BigDecimal(320), new ArrayList<>());
        Product p2 = new Product(2L, "Milk", new BigDecimal(80), new ArrayList<>());
        List<Product> productList = Arrays.asList(p1, p2);
        Mockito.when(productsService.findAll(any())).thenReturn(productList);
        mockMvc.perform(
                get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productList)));
    }


}
