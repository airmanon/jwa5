package com.training.pms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.training.pms.model.Product;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductappSpringAppRevatureJwaApplicationTestsProduct {

	@LocalServerPort
	private String port;
	
	private String baseURL = "http://localhost";
	
	URL url;
	
	@Autowired
	RestTemplate restTemplate;
	
	int productId;
	
	@BeforeEach
	public void setUp() throws MalformedURLException
	{
		url = new URL(baseURL+ ":" + port + "/product");
		productId = 100;
	}
	
	@Test
	@DisplayName("Testing save Product - CREATED")
	@Order(1)
	public void saveProduct()
	{
		Product product = new Product(productId, "MoonPlushie", 99, 9);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.toString(), product, String.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
	
	@Test
	@DisplayName("Testing get Products - FOUND")
	@Order(3)
	public void getProducts1()
	{
		ResponseEntity<List> responseEntity = restTemplate.getForEntity(url.toString(), List.class);
		assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
	}
}
