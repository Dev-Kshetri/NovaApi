package com.nova.madrid.everisdarmytasksms.controller;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.nova.madrid.everisdarmytasksms.models.Task;
import com.nova.madrid.everisdarmytasksms.service.TaskService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
public class TaskController {

	@Autowired
	TaskRepository repo;

	@Autowired
	TaskService service;

	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@GetMapping("/task")
	public List<Task> getTasks() {
		return repo.findAll();
	}

	@GetMapping("/task/{id}")
	public Task getTasksById(@PathVariable(value = "id") long id) {
		try {
			return service.getTaskById(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/task")
	public ResponseEntity<Task> createTask(@RequestBody Task task) {
		repo.save(task);
		ResponseEntity<Task> response = new ResponseEntity<Task>(task, HttpStatus.CREATED);
		return response;
	}

	@PutMapping("/task/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable(value = "id") long id, @RequestBody Task task) {
		try {
			Task existingTask = service.getTaskById(id);
			existingTask.setTitle(task.getTitle());
			existingTask.setDescription(task.getDescription());
			existingTask.setPriority(task.getPriority());
			existingTask.setStatus(task.getStatus());
			//tareaExistente.setDate(task.getDate());
			repo.save(existingTask);

			return new ResponseEntity<Task>(existingTask, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
		}
	}

	/*@DeleteMapping("/task")
	public ResponseEntity<String> deleteTask(@RequestBody Task task) {
		try {
			Task taskToDelete = service.getTaskById(task.getId());
			repo.delete(taskToDelete);
			return new ResponseEntity<>("Task is Deleted", HttpStatus.CREATED); // To Decide
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}*/

	@DeleteMapping("/task/{id}")
	public ResponseEntity<String> deleteTaskById(@PathVariable(value = "id") long id) {
		try {
			Task taskToDelete = service.getTaskById(id);
			repo.delete(taskToDelete);
			return new ResponseEntity<>("Task deleted sucessfully!", HttpStatus.CREATED); // To Decide
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
