package com.prod.service;

import java.util.List;

import com.prod.model.Product;

public interface ProductService {

	Product createProduct(Product product);
	List<Product> getAllProduct();
	void  deleteProduct(int id);
	Product updateProduct(int ProdId , Product updatedProduct);
	Product increaseStock(int prodId , int quantity);
	Product decreaseStock(int prodId , int quantity);
	List<Product> getLowStockAllProducts(int qua);
	
	}
	
	