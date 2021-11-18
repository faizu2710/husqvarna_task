package com.husqvarna.assignment.todoapp;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.husqvarna.assignment.todoapp.controllers.TodoController;
import com.husqvarna.assignment.todoapp.entities.Todo;
import com.husqvarna.assignment.todoapp.services.TodoService;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	TodoService todoService;

	Todo t1 = new Todo("firstTodo");
	Todo t2 = new Todo("secondTodo");
	Todo t3 = new Todo("thirdTodo");

	@Test
	public void getTodosTest() throws Exception {
		List<Todo> todos = new ArrayList<Todo>();
		todos.add(t1);
		todos.add(t2);
		todos.add(t3);

		Mockito.when(todoService.getAllTodos()).thenReturn(todos);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/todos")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[1].title", is("secondTodo")))
				.andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void getTodoTest() throws Exception {

		Mockito.when(todoService.getTodo(0)).thenReturn(t1);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/todos/0")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.title", is("firstTodo")))
				.andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void addTodoTest() throws Exception {

		Mockito.when(todoService.addTodo(Mockito.any(Todo.class))).thenReturn(t2);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(t2));

		mockMvc.perform(mockRequest)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title", is("secondTodo")))
				.andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void editTodoTest() throws Exception {

		Mockito.when(todoService.editTodo(Mockito.any(Todo.class), Mockito.anyInt())).thenReturn(t2);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.patch("/todos/2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(t2));

		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is(t2.getTitle())))
				.andDo(MockMvcResultHandlers.print());
	}
}
