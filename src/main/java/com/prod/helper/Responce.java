package com.prod.helper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prod.model.Product;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Responce {

	private List<Product> data;
	private String msg;
	private String status;
	private Integer httpStatusCode;
	private Product singleProduct;
	

	public Responce() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Responce(String msg, String status, Integer httpStatusCode, Product singleProduct) {
		super();
		this.msg = msg;
		this.status = status;
		this.httpStatusCode = httpStatusCode;
		this.singleProduct = singleProduct;
	}



	public Product getSingleProduct() {
		return singleProduct;
	}



	public void setSingleProduct(Product singleProduct) {
		this.singleProduct = singleProduct;
	}



	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}


	public List<Product> getData() {
		return data;
	}


	public void setData(List<Product> data) {
		this.data = data;
	}
	

}
