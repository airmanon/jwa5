package com.training.pms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.training.pms.model.Product;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest extends AbstractTest {

	int productId = 99999; 
	String uri = "/product";
	int productId2 = 100;

	@Override
	@BeforeEach
	protected void setUp() {
		super.setUp();
	}
	
	@Test
	@DisplayName(value = "Testing save product")
	@Order(1)
	public void testSaveProduct() throws Exception {
		Product product = new Product(productId, "DummyProduct", 100, 1006);
		//json
		String productJSON = super.mapToJson(product);
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(productJSON)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Product saved successfully");
	}
	
	@Test
	@DisplayName(value = "Testing update product")
	@Order(2)
	public void testUpdateProduct() throws Exception {

		Product product = new Product(productId, "UpdateDummyProduct", 99, 187);
		//json
		String productJSON = super.mapToJson(product);
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(productJSON)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Product updated successfully");
	}
	
	@Test
	@DisplayName(value = "Testing get all products")
	@Order(3)
	public void testGetProducts() throws Exception {

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Product []productList = super.mapFromJson(content, Product[].class);
		assertTrue(productList.length > 0);
	}
	
	//test get a single record by productId	- Hands -0n
	
	@Test
	@DisplayName(value = "Testing delete product")
	@Order(4)
	public void testDeleteProduct() throws Exception {

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri+"/"+productId)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		
		assertEquals(content, "Product deleted successfully");
	}
	
	@Test
	@DisplayName(value = "Testing save product")
	@Order(5)
	public void testSaveProduct2() throws Exception {
		Product product = new Product(productId2, "MoonPlushie", 99, 9);
		//json
		String productJSON = super.mapToJson(product);
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(productJSON)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Product saved successfully");
	}
	
	@Test
	@DisplayName(value = "Testing update product")
	@Order(6)
	public void testUpdateProduct2() throws Exception {

		Product product = new Product(productId2, "UpdateMoonPlushie", 1000, 9);
		//json
		String productJSON = super.mapToJson(product);
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(productJSON)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Product updated successfully");
	}
	
	//test get a single record by productId	- Hands -0n
	@Test
	@DisplayName(value = "Testing get a single product")
	@Order(7)
	public void testGetProduct() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+"/"+productId2)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(302, status);
		String content = mvcResult.getResponse().getContentAsString();
		Product productResult = super.mapFromJson(content, Product.class);
		assertTrue(productResult.getProductId() == productId2);
	}
	
	@Test
	@DisplayName(value = "Testing delete product")
	@Order(8)
	public void testDeleteProduct2() throws Exception {

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri+"/"+productId2)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		
		assertEquals(content, "Product deleted successfully");
	}
}