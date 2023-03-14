package com.domo.productservice;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.domo.productservice.dto.ProductRequest;
import com.domo.productservice.model.Product;
import com.domo.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.4");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String procuctRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(post("/api/product")
				.contentType(APPLICATION_JSON)
				.content(procuctRequestString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.count());
	}

	private static ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Product 1")
				.description("Product 1 description")
				.price(BigDecimal.valueOf(12000))
				.build();
	}

	@Test
	void shouldSelectProduct() throws Exception {
		Product product = Product.builder()
				.name("Product 2")
				.description("Product 2 description")
				.price(BigDecimal.valueOf(16000))
				.build();
		productRepository.save(product);
		mockMvc.perform(get("/api/product"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Product 2"))
				.andExpect(jsonPath("$[0].description").value("Product 2 description"))
				.andExpect(jsonPath("$[0].price").value("16000"))
				.andDo(print());
	}

}
