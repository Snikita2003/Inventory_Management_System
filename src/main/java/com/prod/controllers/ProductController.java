package com.prod.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prod.helper.Responce;
import com.prod.model.Product;
import com.prod.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public Responce getAllProducts() {
		Responce responce = new Responce();

		List<Product> data = this.productService.getAllProduct();
		try {
			responce.setMsg("Fetched all products successfully");
			responce.setStatus("Success");
			responce.setHttpStatusCode(HttpStatus.OK.value());
			responce.setData(data);
		} catch (Exception e) {
			responce.setMsg("Error for fetching all products");
			responce.setStatus("Failed");
			responce.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
		}
		return responce;
	}

	@PostMapping
	public ResponseEntity<Responce> createProduct(@RequestBody Product product) {
		Responce responce = new Responce();
		try {
			if (product.getStockQuantity() >= 0) {
				Product savedProduct = this.productService.createProduct(product);
				responce.setMsg("Product saved successfully");
				responce.setStatus("Success");
				responce.setHttpStatusCode(HttpStatus.CREATED.value());
				responce.setSingleProduct(savedProduct);

				return ResponseEntity.status(HttpStatus.CREATED).body(responce);
			} else {
				responce.setMsg("Stock quantity cannot be negative");
				responce.setStatus("Failed");
				responce.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responce);
			}
		} catch (Exception e) {
			responce.setMsg("Error with saving product");
			responce.setStatus("Failed");
			responce.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responce);
		}
	}

	@DeleteMapping("/{prodId}")
	public Responce deleteProduct(@PathVariable int prodId) {
		Responce responce = new Responce();
		try {
			this.productService.deleteProduct(prodId);
			responce.setMsg("product deleted successfully");
			responce.setStatus("Success");
			responce.setHttpStatusCode(200);

		} catch (Exception e) {
			responce.setMsg("Error with Deleting product");
			responce.setStatus("Failed");
			responce.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
		}

		return responce;

	}
	@PutMapping(value = "/{prodId}")
	public Responce updateProduct(@PathVariable int prodId, @RequestBody Product updatedProduct) {
	    Responce responce = new Responce();
	    try {
	        Product pr = this.productService.updateProduct(prodId, updatedProduct);
	        responce.setMsg("Product Updated successfully");
	        responce.setStatus("Success");
	        responce.setHttpStatusCode(HttpStatus.OK.value());
	        responce.setSingleProduct(pr);
	    }  catch (Exception e) {
	        responce.setMsg("Error with updating product");
	        responce.setStatus("Failed");
	        responce.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
	    }
	    return responce;
	}


	@PutMapping("/{prodId}/increase")
	public ResponseEntity<Responce> increaseStock(@PathVariable int prodId,
			@RequestParam(defaultValue = "0") int quantity) {

		Responce responce = new Responce();

		if (quantity < 0) {
			responce.setMsg("Error with Stock Quantity, Stock cannot be negative");
			responce.setStatus("Failed");
			responce.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responce);
		}

		Product updated = productService.increaseStock(prodId, quantity);
		responce.setMsg("Stock increased successfully");
		responce.setStatus("Success");
		responce.setHttpStatusCode(HttpStatus.OK.value());
		responce.setSingleProduct(updated);

		return ResponseEntity.ok(responce);
	}

	@PutMapping("/{prodId}/decrease")
	public ResponseEntity<Responce> decreaseStock(@PathVariable int prodId,
			@RequestParam(defaultValue = "0") int quantity) {

		Responce responce = new Responce();

		if (quantity < 0) {
			responce.setMsg("Error with Stock Quantity, Stock cannot be negative");
			responce.setStatus("Failed");
			responce.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responce);
		}

		Product updated = productService.decreaseStock(prodId, quantity);
		responce.setMsg("Stock decreased successfully");
		responce.setStatus("Success");
		responce.setHttpStatusCode(HttpStatus.OK.value());
		responce.setSingleProduct(updated);

		return ResponseEntity.ok(responce);
	}
	
	@GetMapping("/getLowStocks")
	public Responce getLowStockAllProducts() {
		
		List<Product> allProduct=	this.productService.getLowStockAllProducts( 20 );
		Responce responce = new Responce();
		responce.setMsg("fetched all Low Stocks Products");
		responce.setStatus("Success");
		responce.setHttpStatusCode(HttpStatus.OK.value());
		responce.setData(allProduct);
		return responce;
	}

}