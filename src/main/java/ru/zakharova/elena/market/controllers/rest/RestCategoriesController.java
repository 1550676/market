package ru.zakharova.elena.market.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.zakharova.elena.market.entities.Category;
import ru.zakharova.elena.market.services.CategoriesService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/categories")
@Api("Set of endpoints for CRUD operations for categories")
public class RestCategoriesController {
    private CategoriesService categoriesService;

    @Autowired
    public RestCategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all categories")
    public List<Category> getAllCategories() {
        return categoriesService.getAll();
    }


}