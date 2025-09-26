package com.prod.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prod.controllers.ProductController;
import com.prod.helper.Responce;
import com.prod.model.Product;
import com.prod.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProdId(1);
        product.setProdName("Laptop");
        product.setDescription("lenovo Laptop");
        product.setStockQuantity(10);
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<Product> products = Arrays.asList(product);
        Mockito.when(productService.getAllProduct()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.data[0].prodName").value("Laptop"));
    }

    @Test
    void testCreateProduct() throws Exception {
        Mockito.when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.singleProduct.prodName").value("Laptop"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Mockito.doNothing().when(productService).deleteProduct(1);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.msg").value("product deleted successfully"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product updated = new Product();
        updated.setProdName("Laptop Pro");
        updated.setDescription("Updated Laptop");
        updated.setStockQuantity(15);

        Mockito.when(productService.updateProduct(anyInt(), any(Product.class))).thenReturn(updated);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.singleProduct.prodName").value("Laptop Pro"));
    }

    @Test
    void testIncreaseStock() throws Exception {
        product.setStockQuantity(15);
        Mockito.when(productService.increaseStock(1, 5)).thenReturn(product);

        mockMvc.perform(put("/api/products/1/increase?quantity=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.singleProduct.stockQuantity").value(15));
    }

    @Test
    void testIncreaseStockNegative() throws Exception {
        mockMvc.perform(put("/api/products/1/increase?quantity=-5"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Failed"));
    }

    @Test
    void testDecreaseStock() throws Exception {
        product.setStockQuantity(5);
        Mockito.when(productService.decreaseStock(1, 5)).thenReturn(product);

        mockMvc.perform(put("/api/products/1/decrease?quantity=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.singleProduct.stockQuantity").value(5));
    }

    @Test
    void testDecreaseStockNegative() throws Exception {
        mockMvc.perform(put("/api/products/1/decrease?quantity=-5"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Failed"));
    }
}
