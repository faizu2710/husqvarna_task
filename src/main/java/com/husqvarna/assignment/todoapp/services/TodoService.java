package com.husqvarna.assignment.todoapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.husqvarna.assignment.todoapp.dao.ITodoDAO;
import com.husqvarna.assignment.todoapp.entities.Todo;

import javassist.NotFoundException;

@Service
public class TodoService implements ITodoService {

	@Autowired
	ITodoDAO todoDAO;

	private static final String NOT_FOUND_ERROR_MESSAGE = "No todo found for the given ID!";

	public List<Todo> getAllTodos() {
		return todoDAO.findAll();
	}

	@Override
	public Todo getTodo(Integer id) {

		Optional<Todo> todo = todoDAO.findById(id);
		return todo.isPresent() ? todo.get() : null;

	}

	@Override
	public Todo addTodo(Todo todo) {
		return todoDAO.save(todo);
	}

	@Override
	public Todo editTodo(Todo todo, Integer id) throws NotFoundException {

		Optional<Todo> existingTodo = todoDAO.findById(id);

		if (existingTodo.isPresent()) {
			Todo t = existingTodo.get();
			t.setTitle(todo.getTitle());
			t.setCompleted(todo.getCompleted()); // maybe user wishes to change completeness too.

			return todoDAO.save(t);
		} else {
			throw new NotFoundException(NOT_FOUND_ERROR_MESSAGE);
		}
	}

	@Override
	public void deleteTodo(Integer id) throws NotFoundException {
		boolean todoExist = todoDAO.existsById(id);

		if (todoExist) {
			todoDAO.deleteById(id);
		} else {
			throw new NotFoundException(NOT_FOUND_ERROR_MESSAGE);
		}

	}
}
