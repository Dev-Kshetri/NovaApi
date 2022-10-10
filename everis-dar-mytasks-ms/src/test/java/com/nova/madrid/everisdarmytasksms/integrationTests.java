package com.nova.madrid.everisdarmytasksms;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class integrationTests {

	@Test
	@Order(1)  
	void testEmptyGetAll() {	
		System.out.println("Start of testEmptyGetAll");
		TestRestTemplate restTemplate =new TestRestTemplate();
		ResponseEntity<String>	response =restTemplate.getForEntity("http://localhost:8080/task", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		assertEquals(response.getStatusCode().toString(), "200 OK");
		assertNotNull(response.getBody());
		System.out.println(response.getBody().length());
		System.out.println("End of testEmptyGetAll \n");
	}
	
	@Test
	@Order(2)
	void testCreateTaskOk() {
		System.out.println("Start of testCreateTaskOk");		
		TestRestTemplate restTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject taskObject = new JSONObject();
	    try {
			taskObject.put("title", "title_0");
			taskObject.put("description", "description_0");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ResponseEntity<String>	response =restTemplate.postForEntity("http://localhost:8080/task", new HttpEntity<String>(taskObject.toString(), headers), String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		assertEquals(response.getStatusCode().toString(), "201 CREATED");
		
		System.out.println("End of testCreateTaskOk \n");
	}
	
	@Test
	@Order(3)  
	void testNotEmptyGetAll() {	
		System.out.println("Start of testNotEmptyGetAll");
		TestRestTemplate restTemplate =new TestRestTemplate();
		ResponseEntity<String>	response =restTemplate.getForEntity("http://localhost:8080/task", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		System.out.println(response.getBody().length());
		assertEquals(response.getStatusCode().toString(), "200 OK");
		assertNotNull(response.getBody());
		assertTrue(response.getBody().length() > 2);
		System.out.println("End of testNotEmptyGetAll \n");
	}
	
	@Test
	@Order(4)  
	void testGetAll() {	
		System.out.println("Start of testGetAll");
		
		TestRestTemplate restTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject taskObject = new JSONObject();
	    try {
			taskObject.put("title", "title_1");
			taskObject.put("description", "description_1");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ResponseEntity<String>	response =restTemplate.postForEntity("http://localhost:8080/task", new HttpEntity<String>(taskObject.toString(), headers), String.class);
		
		restTemplate =new TestRestTemplate();
		response =restTemplate.getForEntity("http://localhost:8080/task", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		System.out.println(response.getBody().length());
		assertEquals(response.getStatusCode().toString(), "200 OK");
		assertNotNull(response.getBody());
		assertTrue(response.getBody().length() > 2);
		System.out.println("End of testGetAll \n");
	}
	
	@Test
	@Order(5)
	void testCreateTaskErr() {
		System.out.println("Start of testCreateTaskErr");		

		String resp = "{\"title\":\"Titulo_2\",\"description\":\"description_2\",\"priority\":0,\"status\":\"Done\"}";
		
		TestRestTemplate restTemplate =new TestRestTemplate();
		ResponseEntity<String>	response =restTemplate.postForEntity("http://localhost:8080/task", resp, String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		assertEquals(response.getStatusCode().toString(), "415 UNSUPPORTED_MEDIA_TYPE");
		
		System.out.println("End of testCreateTaskErr \n");
	}
	
	/*
	@Test
	@Order(6)
	void testCreateTaskOversize() {
		System.out.println("Start of testCreateTaskOk");
		
		String descript = new String();
		
		for(int i = 0; i<1000 ; i++)
			descript = descript + "A";
		
		TestRestTemplate restTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject taskObject = new JSONObject();
	    try {
			taskObject.put("title", "title_0");
			taskObject.put("description", descript);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ResponseEntity<String>	response =restTemplate.postForEntity("http://localhost:8080/task", new HttpEntity<String>(taskObject.toString(), headers), String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		assertEquals(response.getStatusCode().toString(), "400 BAD_REQUEST");
		
		System.out.println("End of testCreateTaskOk \n");
	}
	*/
	
	@Test
    @Order(6)
    void testGetId() {
        System.out.println("Start of TestGetId");
        TestRestTemplate restTemplate =new TestRestTemplate();
        ResponseEntity<String>  response = restTemplate.getForEntity("http://localhost:8080/task/1", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        
        assertEquals(response.getStatusCode().toString(), "200 OK");
        
        System.out.println("End of TestGetId \n");
    }
    @Test
    @Order(7)
    void testGetIdThatDoesntExist() {
        System.out.println("Start of testGetIdThatDoesntExist");
        TestRestTemplate restTemplate =new TestRestTemplate();
        ResponseEntity<String>  response = restTemplate.getForEntity("http://localhost:8080/task/-1", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        
        assertEquals(response.getStatusCode().toString(), "404 NOT_FOUND");
        
        System.out.println("End of testGetIdThatDoesntExist \n");
    }
	
	@Test
	@Order(8)
	void testModifyTaskEsxists() {
		System.out.println("Start of testModifyTaskEsxists");
		
		TestRestTemplate restTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject taskObject = new JSONObject();
	    try {
			taskObject.put("title", "title_changed");
			taskObject.put("description", "changed");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    ResponseEntity<Void> response = restTemplate.exchange("http://localhost:8080/task/1", HttpMethod.PUT, new HttpEntity<String>(taskObject.toString(), headers), Void.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		assertEquals(response.getStatusCode().toString(), "200 OK");
		
		ResponseEntity<String> response2 = restTemplate.getForEntity("http://localhost:8080/task/1", String.class);
        System.out.println(response2.getStatusCode());
        System.out.println(response2.getBody());
		
		System.out.println("End of testModifyTaskEsxists \n");
	}
	
	@Test
	@Order(9)
	void testModifyTaskError() {
		System.out.println("Start of testModifyTaskError");
		
		TestRestTemplate restTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject taskObject = new JSONObject();
	    try {
			taskObject.put("title", "title_changed");
			taskObject.put("id", "id_changed");
			taskObject.put("description", "changed");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    ResponseEntity<Void> response = restTemplate.exchange("http://localhost:8080/task/-1", HttpMethod.PUT, new HttpEntity<String>(taskObject.toString(), headers), Void.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		assertEquals(response.getStatusCode().toString(), "400 BAD_REQUEST");
		
		System.out.println("End of testModifyTaskError \n");
	}
	
	
	@Test
	@Order(10)
	void testModifyTaskNoEsxists() {
		System.out.println("Start of testModifyTaskNoEsxists");
		
		TestRestTemplate restTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject taskObject = new JSONObject();
	    try {
			taskObject.put("title", "title_changed");
			taskObject.put("description", "changed");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    ResponseEntity<Void> response = restTemplate.exchange("http://localhost:8080/task/-1", HttpMethod.PUT, new HttpEntity<String>(taskObject.toString(), headers), Void.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		assertEquals(response.getStatusCode().toString(), "404 NOT_FOUND");
		
		System.out.println("End of testModifyTaskNoEsxists \n");
	}
	
	
    @Test
    @Order(11)
    void deleteById() {
        System.out.println("Delete by DeleteById");
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/task/1", HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        System.out.println(response.getStatusCodeValue());
        System.out.println(response.getBody());
        
        assertEquals(response.getStatusCode().toString(), "201 CREATED");
        
        System.out.println("End of DeleteById \n");
    }
    
    @Test
    @Order(12)
    void deleteByIdNoExists() {
        System.out.println("Delete by deleteByIdNoExists");
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/task/-1", HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        System.out.println(response.getStatusCodeValue());
        System.out.println(response.getBody());
        
        assertEquals(response.getStatusCode().toString(), "404 NOT_FOUND");
        
        System.out.println("End of deleteByIdNoExists \n");
    }
	
}
