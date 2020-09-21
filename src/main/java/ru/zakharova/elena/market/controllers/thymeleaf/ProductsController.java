package ru.zakharova.elena.market.controllers.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zakharova.elena.market.entities.Category;
import ru.zakharova.elena.market.entities.Product;
import ru.zakharova.elena.market.services.CategoriesService;
import ru.zakharova.elena.market.services.ProductsService;
import ru.zakharova.elena.market.utils.ProductFilter;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private ProductsService productsService;
    private CategoriesService categoriesService;

    @Autowired
    public ProductsController(ProductsService productsService, CategoriesService categoriesService) {
        this.productsService = productsService;
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public String showAll(Model model,
                          @RequestParam Map<String, String> requestParams,
                          @RequestParam(value = "category" , required = false) List<Long> chosenCategoriesId)  {
        List<Category> chosenCategoriesList = null;
        if (chosenCategoriesId != null) {
            chosenCategoriesList = categoriesService.getCategoriesByIds(chosenCategoriesId);
        }
        ProductFilter productFilter = new ProductFilter(requestParams, chosenCategoriesList);
        Integer pageNumber = Integer.parseInt(requestParams.getOrDefault("p", "1"));
        Page<Product> products = productsService.findAll(productFilter.getSpec(), pageNumber);
        model.addAttribute("products", products);
        model.addAttribute("filterDef", productFilter.getFilterDefinition().toString());
        return "all_products";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "add_product_form";
    }

    @PostMapping("/add")
    public String saveNewProduct(@ModelAttribute Product product, @RequestParam(value = "category") List<Long> сategoriesId) {
        List<Category> categoriesList = categoriesService.getCategoriesByIds(сategoriesId);
        product.setCategories(categoriesList);
        productsService.saveOrUpdate(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productsService.findById(id));
        return "edit_product_form";
    }

    @PostMapping("/edit")
    public String modifyProduct(@ModelAttribute Product product,
                          @RequestParam(value = "category") List<Long> сategoriesId) {
        List<Category> categoriesList = categoriesService.getCategoriesByIds(сategoriesId);
        product.setCategories(categoriesList);
        productsService.saveOrUpdate(product);
        return "redirect:/products";
    }

}