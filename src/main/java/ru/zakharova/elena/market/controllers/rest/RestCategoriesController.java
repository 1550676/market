package ru.zakharova.elena.market.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.zakharova.elena.market.entities.dtos.CategoryDTO;
import ru.zakharova.elena.market.services.CategoriesService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/categories")
public class RestCategoriesController {
    private CategoriesService categoriesService;

    @Autowired
    public RestCategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Returns list of all categories")
    public List<CategoryDTO> getAllCategories() {
        return categoriesService.getAllDTOs();
    }


}