package com.examplecategory.category.controller;

import com.examplecategory.category.entity.Category;
import com.examplecategory.category.entity.Product;
import com.examplecategory.category.exception.CategoryNotFoundException;
import com.examplecategory.category.repository.CategoryRepository;
import com.examplecategory.category.repository.ProductRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
@RestController
public class Controller {

private CategoryRepository categoryRepository;
private ProductRepository productRepository;

    public Controller(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/cat")
    public List<Category> getMainCategory(@RequestBody Category category )
    {

        return categoryRepository.findAll();
    }

    @GetMapping("/cat/{id}")
    public EntityModel<Category> getCategory(@PathVariable int id) throws CategoryNotFoundException {
        Optional<Category> category =categoryRepository.findById(id);
        if(category.isEmpty())
            throw new CategoryNotFoundException("id"+id);
        //  Category category1 =category.get();
        EntityModel<Category> CategoryEntityModel =EntityModel.of(category.get());
        return CategoryEntityModel;
    }

    @PostMapping("/cat")
    public void getCategory(@RequestBody Category category)
    {
        categoryRepository.save(category);
    }


    @PutMapping("/cat/{id}")
    public Category updateMain(@PathVariable int id,   @RequestBody Category category) {
        Optional<Category> main = categoryRepository.findById(id);
        if(main.isEmpty())
            throw new CategoryNotFoundException("MainCategory id not found " + id);
        categoryRepository.deleteById(id);
        Category update=categoryRepository.save(category);
        return update;
    }

    @DeleteMapping("/cat/{id}")
    public void deleteId(@PathVariable int id)
    {
        categoryRepository.deleteById(id);
    }





    @PostMapping("/cat/{id}/prod")
    public ResponseEntity<Object> createPostForPro(@PathVariable int id, @RequestBody Product product) {
        Optional<Category> user = categoryRepository.findById(id);

        if(user.isEmpty())
            throw new CategoryNotFoundException("id:"+id);

        product.setCategory(user.get());

        Product savedPost = productRepository.save(product);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getP_id())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/pro")
    public List<Product> getProduct(@RequestBody Product product )
    {
        return productRepository.findAll();
    }

    @GetMapping("/cat/{id}/pro")
    public List<Product>  retrieveSubProduct(@PathVariable int id)
    {

        Optional<Category> Category=categoryRepository.findById(id);
        if(Category.isEmpty())
            throw new CategoryNotFoundException("id not found "+ id);
        return Category.get().getProduct();
    }

    @DeleteMapping("/pro/{id}")
    public void deleteProductId(@PathVariable int id)
    {
        productRepository.deleteById(id);
    }

}
