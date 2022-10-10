package com.nova.madrid.everisdarmytasksms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nova.madrid.everisdarmytasksms.controller.TaskController;
import com.nova.madrid.everisdarmytasksms.controller.TaskRepository;
import com.nova.madrid.everisdarmytasksms.models.Task;
import com.nova.madrid.everisdarmytasksms.service.TaskService;

@SpringBootTest
@AutoConfigureMockMvc
class EverisDarMytasksMsApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	TaskController tc;
	
	@MockBean
	TaskRepository repo;
	
	@MockBean
	TaskService service;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	public void getTasks() throws Exception {
		List<Task> li =new ArrayList<Task>();
		li.add(generateTask());
		li.add(generateTask());
		when(repo.findAll()).thenReturn(li);
		
		List<Task> listReturned = tc.getTasks();
		
		for(int i = 0; i<li.size(); i++) {
			assertEquals(li.get(i),listReturned.get(i));
		}
	}
	
	@Test
	public void getTaskById() throws Exception {
		
		Task t = generateTask();
		when(service.getTaskById(t.getId())).thenReturn(t);
		
		this.mockMvc.perform(get("/task/"+t.getId())).andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(t.getId()));
	}
	
	@Test
	public void addTask() throws Exception {
		Task t = generateTask();
		when(repo.save(any())).thenReturn(t);
		
		/*ObjectMapper map =new ObjectMapper();
		String jsonString = map.writeValueAsString(t);
		
		this.mockMvc.perform(post("/addBook").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)).andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").value(t.getId()));*/
		
		ResponseEntity<Task> response = tc.createTask(t);
		assertEquals(response.getStatusCode(),HttpStatus.CREATED);
	}
	
	@Test
	public void deleteTask() throws Exception {
		Task t = generateTask();
		when(service.getTaskById(any())).thenReturn(t);
		doNothing().when(repo).delete(any());
		
		this.mockMvc.perform(delete("/task/"+t.getId()))
		.andExpect(status().isCreated())
		.andExpect(content().string("Task deleted sucessfully!"));
		
	}
	
	@Test
	public void updateTask() throws Exception {
		Task t = generateTask();
		
		when(service.getTaskById(any())).thenReturn(t);
		when(repo.save(any())).thenReturn(t);
		/*ObjectMapper map =new ObjectMapper();
		String jsonString = map.writeValueAsString(t);	
		
		this.mockMvc.perform(put("/task/"+t.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value(t.getTitle()));*/
		ResponseEntity<Task> response = tc.updateTask(t.getId(),t);
		assertEquals(response.getStatusCode(),HttpStatus.OK);
	}
	
	private Task generateTask() {
		Task t = new Task();
		t.setDescription("Desc");
		t.setTitle("Titulo");
		return t;
	}

}
