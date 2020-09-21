package ru.zakharova.elena.market.controllers;
//https://sysout.ru/testirovanie-kontrollerov-s-pomoshhyu-mockmvc/

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.zakharova.elena.market.entities.Product;
import ru.zakharova.elena.market.services.ProductsService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductsControllerIntegrationTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private MockMvc mockMvc;

//    @AfterEach
//    public void resetDb() {
//        repository.deleteAll();
//    }

    @Test
    @WithMockUser(username = "2", roles = "admin")
    // @WithAnonymousUser
    @Transactional
    public void givenProduct_whenAdd_thenStatus201andProductReturned() throws Exception {
        Product product = productsService.findById(1L).get();
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
                .andExpect(jsonPath("$.items").isEmpty())
                .andExpect(jsonPath("$.items").isArray());
    }

    @Test
    @WithMockUser(username = "2", roles = "admin")
    @Transactional
    public void givenId_whenGetExistingProduct_thenStatus200andProductReturned() throws Exception {
        mockMvc.perform(
                get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Cheese"))
                .andExpect(jsonPath("$.price").value(320.00))
                .andExpect(jsonPath("$.categories").isArray())
                .andExpect(jsonPath("$.items").isEmpty())
                .andExpect(jsonPath("$.items").isArray());
    }


    @Test
    @WithMockUser(username = "2", roles = "admin")
    public void givenId_whenGetNotExistingProduct_thenStatus404anExceptionThrown() throws Exception {
        mockMvc.perform(
                get("/api/v1/products/0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(content().string(org.hamcrest.core.StringContains.containsString("not found")))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @WithMockUser(username = "2", roles = "admin")
    public void giveProduct_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {
        Product product = productsService.findById(1L).get();
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
                .andExpect(jsonPath("$.items").isEmpty())
                .andExpect(jsonPath("$.items").isArray());
    }

    @Test
    @WithMockUser(username = "2", roles = "admin")
    public void givenId_whenDeleteProduct_thenStatus200() throws Exception {
        mockMvc.perform(
                delete("/api/v1/products/2"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @WithMockUser(username = "2", roles = "admin")
    public void givenNothing_whenGetAllProducts_thenStatus200() throws Exception {
                List<Product> products = productsService.findAll();
        mockMvc.perform(
                get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(products)));
    }


}
