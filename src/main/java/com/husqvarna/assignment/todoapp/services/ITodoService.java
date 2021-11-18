package com.husqvarna.assignment.todoapp.services;

import java.util.List;

import com.husqvarna.assignment.todoapp.entities.Todo;

import javassist.NotFoundException;

public interface ITodoService {

	List<Todo> getAllTodos();
	
	Todo getTodo(Integer id);
	
	Todo addTodo(Todo todo);
	
	Todo editTodo(Todo todo, Integer id) throws NotFoundException;
	
	void deleteTodo(Integer id) throws NotFoundException;
	 
}
