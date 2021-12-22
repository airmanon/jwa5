package com.training.pms.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.training.pms.model.Product;
import com.training.pms.service.ProductService;

@RestController
@RequestMapping("product") // localhost:9090/product
public class ProductController {

	@Autowired
	ProductService productService;

	@PostMapping
	public ResponseEntity<String> saveProduct(@RequestBody Product product) {
		ResponseEntity<String> responseEntity = null;
		String message = null;
		System.out.println("Product data to be saved: " + product);
		if (productService.isProductExists(product.getProductId())) {
			message = "Product with product id : " + product.getProductId() + " already exists";
			responseEntity = new ResponseEntity<>(message, HttpStatus.CONFLICT);
		} else {
			message = productService.saveProduct(product);
			if (message.equals("Product not saved successfully (Reason - Price or QOH is negative )")) {
				responseEntity = new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
			} else {
				responseEntity = new ResponseEntity<>(message, HttpStatus.CREATED);
			}
		}
		return responseEntity;
	}

	// to get all products
	@GetMapping
	public ResponseEntity<List<Product>> getproducts() {
		System.out.println("Fetching all the records");
		// code to fetch
		List<Product> products = productService.getProducts();
		ResponseEntity<List<Product>> responseEntity = null;
		if(products.isEmpty())
		{
			responseEntity = new ResponseEntity<>(products, HttpStatus.NO_CONTENT);
		}
		else
		{
			responseEntity = new ResponseEntity<>(products, HttpStatus.OK);
		}
		return responseEntity;
	}

	// to update a product
	@PutMapping
	public ResponseEntity<String> updateProduct(@RequestBody Product product) {
		ResponseEntity<String> responseEntity = null;
		String message = null;
		System.out.println("Product data to be updated: " + product);
		if (!productService.isProductExists(product.getProductId())) {
			message = "Product with product id : " + product.getProductId() + " does not exist";
			responseEntity = new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
		} else {
			message = productService.updateProduct(product);
			if (message.equals("Product not updated successfully (Reason - Price or QOH is negative )")) {
				responseEntity = new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
			} else {
				responseEntity = new ResponseEntity<>(message, HttpStatus.OK);
			}
		}
		return responseEntity;
	}

	// to get a single product
	@GetMapping("{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable("productId") int productId) {
		System.out.println("Product get to be using product id: " + productId);
		// code to get a single product using product id
		Product result = productService.getProduct(productId);
		ResponseEntity<Product> responseEntity = null;
		if(result == null)
		{
			responseEntity = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		else
		{
			responseEntity = new ResponseEntity<>(result, HttpStatus.FOUND);
		}
		return responseEntity;
		// return "Product fetched successfully " + productId;
	}

	// to delete a single product
	@DeleteMapping("{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable("productId") int productId) {
		System.out.println("Product delete to be using product id: " + productId);
		// code to delete a single product using product id
		String result = productService.deleteProduct(productId);
		ResponseEntity<String> responseEntity = null;
		if(!result.equals("Product deleted successfully"))
		{
			responseEntity = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		else
		{
			responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return responseEntity;
	}

	@GetMapping("getProductByName/{productName}")
	public ResponseEntity<List<Product>> getProductByName(@PathVariable("productName") String productName) {
		System.out.println("Searching for product named " + productName);
		List<Product> products = productService.getProductByName(productName);
		ResponseEntity<List<Product>> responseEntity = null;
		if(products.isEmpty())
		{
			responseEntity = new ResponseEntity<>(products, HttpStatus.NO_CONTENT);
		}
		else
		{
			responseEntity = new ResponseEntity<>(products, HttpStatus.FOUND);
		}
		return responseEntity;
	}

	@GetMapping("isProductExists/{productId}")
	public ResponseEntity<Boolean> isProductExists(@PathVariable("productId") int productId) {
		Boolean exists = productService.isProductExists(productId);
		ResponseEntity<Boolean> responseEntity = null;
		if(exists)
		{
			responseEntity = new ResponseEntity<>(exists, HttpStatus.FOUND);
		}
		else
		{
			responseEntity = new ResponseEntity<>(exists, HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@GetMapping("getProductByPrice/{price}")
	public ResponseEntity<List<Product>> getProductByPrice(@PathVariable("price") int price) {
		List<Product> products = productService.getProductByPrice(price);
		ResponseEntity<List<Product>> responseEntity = null;
		if(products.isEmpty())
		{
			responseEntity = new ResponseEntity<>(products, HttpStatus.NOT_FOUND);
		}
		else
		{
			responseEntity = new ResponseEntity<>(products, HttpStatus.FOUND);
		}
		return responseEntity;
	}

	// Hands-on
	// localhost:9090/product/getProductByPrice/78000/to/94000
	// localhost:9090/product/getProductByPrice/18000/to/20000
	// Getting product in the range of 18000 and 20000
	@GetMapping("getProductByPrice/{minPrice}/to/{maxPrice}")
	public ResponseEntity<List<Product>> getProductByPrice(@PathVariable("minPrice") int minPrice,
			@PathVariable("maxPrice") int maxPrice) {
		System.out.println("Product price range = " + minPrice + " to " + maxPrice);
		// code to retrieve
		List<Product> products = productService.getProductByPrice(minPrice, maxPrice);
		ResponseEntity<List<Product>> responseEntity = null;
		if(products.isEmpty())
		{
			responseEntity = new ResponseEntity<>(products, HttpStatus.NOT_FOUND);
		}
		else
		{
			responseEntity = new ResponseEntity<>(products, HttpStatus.FOUND);
		}
		return responseEntity;
	}
}
