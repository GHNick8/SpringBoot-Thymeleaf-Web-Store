package com.example.store.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.store.domain.product.Category;
import com.example.store.domain.product.Product;
import com.example.store.repo.CategoryRepo;
import com.example.store.repo.ProductRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public void run(String... args) {
        if (productRepo.count() == 0) {
            // Create categories
            Category books = new Category();
            books.setName("Books");
            categoryRepo.save(books);

            Category electronics = new Category();
            electronics.setName("Electronics");
            categoryRepo.save(electronics);

            // Products
            Product mug = new Product();
            mug.setName("Coffee Mug");
            mug.setDescription("A ceramic mug for your morning coffee.");
            mug.setPrice(BigDecimal.valueOf(9.99));
            mug.setImageUrl("/images/mug.jpg");
            mug.setCategory(books);
            mug.setOnSale(false);
            productRepo.save(mug);

            Product mouse = new Product();
            mouse.setName("Gaming Mouse");
            mouse.setDescription("High DPI RGB gaming mouse.");
            mouse.setPrice(BigDecimal.valueOf(49.99));
            mouse.setImageUrl("/images/mouse.jpg");
            mouse.setCategory(electronics);
            mouse.setOnSale(true);
            productRepo.save(mouse);

            Product notebook = new Product();
            notebook.setName("Notebook");
            notebook.setDescription("A ruled notebook for your ideas.");
            notebook.setPrice(BigDecimal.valueOf(4.49));
            notebook.setImageUrl("/images/notebook.jpg");
            notebook.setCategory(books);
            notebook.setOnSale(false);
            productRepo.save(notebook);
        }
    }
}
