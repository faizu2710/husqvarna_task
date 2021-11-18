package com.husqvarna.assignment.todoapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.husqvarna.assignment.todoapp.entities.Todo;
import com.husqvarna.assignment.todoapp.services.ITodoService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/todos")
public class TodoController extends BaseController {

	@Autowired
	ITodoService todoService;

	
	// Getting all the todos.
	@GetMapping
	public ResponseEntity<List<Todo>> getTodos() {
		List<Todo> allTodos = todoService.getAllTodos();
		return ResponseEntity.status(HttpStatus.OK).body(allTodos);
	}

	
	// Getting a specific todo.
	@GetMapping("/{id}")
	public ResponseEntity<Todo> getTodo(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodo(Integer.parseInt(id)));
	}

	
	// Adding a new todo with by-default completeness as false.
	@PostMapping
	public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
		return ResponseEntity.status(HttpStatus.CREATED).body(todoService.addTodo(todo));
	}

	
	// Editing an existing todo.
	@PatchMapping("/{id}")
	public ResponseEntity<?> editTodo(@RequestBody Todo todo, @PathVariable String id) {
		Todo editTodoResponse;
		try {
			editTodoResponse = todoService.editTodo(todo, Integer.parseInt(id));
			return ResponseEntity.status(HttpStatus.OK).body(editTodoResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	
	// Deleting a todo.
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTodo(@PathVariable String id) {
		try {
			todoService.deleteTodo(Integer.parseInt(id));
		} catch (NotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
