package com.husqvarna.assignment.todoapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.husqvarna.assignment.todoapp.entities.Todo;

public interface ITodoDAO extends JpaRepository<Todo, Integer>{

	
}
