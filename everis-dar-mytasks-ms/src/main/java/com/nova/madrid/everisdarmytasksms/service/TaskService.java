package com.nova.madrid.everisdarmytasksms.service;



import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nova.madrid.everisdarmytasksms.controller.TaskRepository;
import com.nova.madrid.everisdarmytasksms.models.Task;




@Service //Para indicar que contiene metodos que se utilizan en los Controllers.
public class TaskService {

   @Autowired
    TaskRepository repository;
   
    
    public boolean checkTaskExists(long id) {
        Optional<Task> lib = repository.findById(id);
        
        return lib.isPresent();
    }
    
    public Task getTaskById(Long id)
	{
		return repository.findById(id).get();
	}
}
