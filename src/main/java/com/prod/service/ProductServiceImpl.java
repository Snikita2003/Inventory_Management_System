package com.prod.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prod.exception.NotFounException;
import com.prod.model.Product;
import com.prod.repository.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Override
	public Product createProduct(Product product) {
		if (product.getStockQuantity() < 0)
			throw new NotFounException("Stock Quantity should not less than 0");
		return productRepo.save(product);

	}

	@Override
	public List<Product> getAllProduct() {
		List<Product> allProduct = productRepo.findAll();
		return allProduct;
	}

	@Override
	public void deleteProduct(int id) {

		Product product = this.productRepo.findById(id)
				.orElseThrow(() -> new NotFounException("Product not found with Id"));
		this.productRepo.delete(product);
	}

	@Override
	public Product updateProduct(int ProdId, Product updatedProduct) {
		Product product = this.productRepo.findById(ProdId)
				.orElseThrow(() -> new NotFounException("Product not found"));
		product.setProdName(updatedProduct.getProdName());
		product.setDescription(updatedProduct.getDescription());
		product.setStockQuantity(Math.max(updatedProduct.getStockQuantity(), 0));
		return this.productRepo.save(product);
	}

	@Override
	public Product increaseStock(int prodId, int quantity) {

		Product actualProd = this.productRepo.findById(prodId)
				.orElseThrow(() -> new NotFounException("Not Found Product by ProductId"));
		actualProd.setStockQuantity(actualProd.getStockQuantity() + quantity);
		return this.productRepo.save(actualProd);
	}

	@Override
	public Product decreaseStock(int prodId, int quantity) {
		Product actualProd = this.productRepo.findById(prodId)
				.orElseThrow(() -> new NotFounException("Not Found Product by ProductId"));

		if (quantity < 0) {
			throw new NotFounException("Stock Quanttiy cannot be negative");
		}
		if (actualProd.getStockQuantity() > 0 && actualProd.getStockQuantity() >= quantity) {
			actualProd.setStockQuantity(actualProd.getStockQuantity() - quantity);
			return this.productRepo.save(actualProd);
		}
		return null;
	}

	@Override
	public List<Product> getLowStockAllProducts(int qua) {

		return this.productRepo.findByStockQuantityLessThan( qua );

	}

}
