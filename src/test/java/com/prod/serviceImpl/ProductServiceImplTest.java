package com.prod.serviceImpl ;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.prod.exception.NotFounException;
import com.prod.model.Product;
import com.prod.repository.ProductRepo;
import com.prod.service.ProductServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setProdId(1);
        product.setProdName("Laptop");
        product.setDescription("High-end Laptop");
        product.setStockQuantity(10);
    }

    // =================== createProduct ===================
    @Test
    void testCreateProduct_Success() {
        when(productRepo.save(product)).thenReturn(product);
        Product created = productService.createProduct(product);
        assertEquals("Laptop", created.getProdName());
        assertEquals(10, created.getStockQuantity());
    }

    @Test
    void testCreateProduct_NegativeStock() {
        product.setStockQuantity(-5);
        NotFounException exception = assertThrows(NotFounException.class,
                () -> productService.createProduct(product));
        assertEquals("Stock Quantity should not less than 0", exception.getMessage());
    }

    // =================== getAllProduct ===================
    @Test
    void testGetAllProduct() {
        List<Product> products = Arrays.asList(product);
        when(productRepo.findAll()).thenReturn(products);
        List<Product> result = productService.getAllProduct();
        assertEquals(1, result.size());
    }

    // =================== deleteProduct ===================
    @Test
    void testDeleteProduct_Success() {
        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        doNothing().when(productRepo).delete(product);

        assertDoesNotThrow(() -> productService.deleteProduct(1));
        verify(productRepo, times(1)).delete(product);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productRepo.findById(1)).thenReturn(Optional.empty());
        NotFounException exception = assertThrows(NotFounException.class,
                () -> productService.deleteProduct(1));
        assertEquals("Product not found with Id", exception.getMessage());
    }

    // =================== updateProduct ===================
    @Test
    void testUpdateProduct_Success() {
        Product updated = new Product();
        updated.setProdName("Laptop Pro");
        updated.setDescription("Updated Laptop");
        updated.setStockQuantity(20);

        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(productRepo.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        Product result = productService.updateProduct(1, updated);
        assertEquals("Laptop Pro", result.getProdName());
        assertEquals(20, result.getStockQuantity());
    }

    @Test
    void testUpdateProduct_NegativeStock() {
        Product updated = new Product();
        updated.setStockQuantity(-10);

        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(productRepo.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        Product result = productService.updateProduct(1, updated);
        assertEquals(0, result.getStockQuantity()); // stock cannot be negative
    }

    // =================== increaseStock ===================
    @Test
    void testIncreaseStock_Success() {
        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(productRepo.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        Product result = productService.increaseStock(1, 5);
        assertEquals(15, result.getStockQuantity());
    }

    @Test
    void testIncreaseStock_ProductNotFound() {
        when(productRepo.findById(1)).thenReturn(Optional.empty());
        NotFounException exception = assertThrows(NotFounException.class,
                () -> productService.increaseStock(1, 5));
        assertEquals("Not Found Product by ProductId", exception.getMessage());
    }

    // =================== decreaseStock ===================
    @Test
    void testDecreaseStock_Success() {
        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(productRepo.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        Product result = productService.decreaseStock(1, 5);
        assertEquals(5, result.getStockQuantity());
    }

    @Test
    void testDecreaseStock_NegativeQuantity() {
        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        NotFounException exception = assertThrows(NotFounException.class,
                () -> productService.decreaseStock(1, -5));
        assertEquals("Stock Quanttiy cannot be negative", exception.getMessage());
    }

    @Test
    void testDecreaseStock_InsufficientStock() {
        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        Product result = productService.decreaseStock(1, 20);
        assertNull(result); // as per your implementation, returns null if insufficient
    }

    @Test
    void testDecreaseStock_ProductNotFound() {
        when(productRepo.findById(1)).thenReturn(Optional.empty());
        NotFounException exception = assertThrows(NotFounException.class,
                () -> productService.decreaseStock(1, 5));
        assertEquals("Not Found Product by ProductId", exception.getMessage());
    }

    // =================== getLowStockAllProducts ===================
    @Test
    void testGetLowStockAllProducts() {
        Product lowStock = new Product();
        lowStock.setProdId(2);
        lowStock.setProdName("Mouse");
        lowStock.setStockQuantity(2);

        when(productRepo.findByStockQuantityLessThan(5)).thenReturn(Arrays.asList(lowStock));

        List<Product> result = productService.getLowStockAllProducts(5);
        assertEquals(1, result.size());
        assertEquals("Mouse", result.get(0).getProdName());
    }
}

