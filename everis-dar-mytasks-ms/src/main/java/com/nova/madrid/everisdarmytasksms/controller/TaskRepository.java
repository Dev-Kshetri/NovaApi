package com.nova.madrid.everisdarmytasksms.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nova.madrid.everisdarmytasksms.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
}