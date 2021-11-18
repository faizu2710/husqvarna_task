package com.husqvarna.assignment.todoapp;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.husqvarna.assignment.todoapp.dao.ITodoDAO;
import com.husqvarna.assignment.todoapp.entities.Todo;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class TodoRepositoryTest {

	@Autowired
	private ITodoDAO todoDAO;

	private List<Todo> allTodos;

	@BeforeAll
	void init() {
		allTodos = new ArrayList<Todo>();
		allTodos.add(new Todo("first Todo"));
		allTodos.add(new Todo("second Todo"));
		allTodos.add(new Todo("third Todo"));

		todoDAO.saveAll(allTodos);
	}

	@AfterAll
	void destory() {
		todoDAO.deleteAll(allTodos);
	}

	@Test
	@Order(3)
	public void addTodoTest() {
		Todo t = new Todo("sample test");
		todoDAO.save(t);
		allTodos.add(t); // this also needs to be removed after the test completes.Hence added in
							// allTodos
		
		assertEquals("sample test", t.getTitle());
	}

	@Test
	@Order(1)
	public void getAllTodosTest() {

		List<Todo> todos = todoDAO.findAll();

		assertThat(todos.size()).isGreaterThanOrEqualTo(3);
	}

	@Test
	@Order(2)
	public void getTodoTest() {

		Optional<Todo> todo = todoDAO.findById(allTodos.get(0).getId());

		assertNotNull(todo.get().getTitle());
	}

	@Test
	@Order(4)
	public void editTodoTest() {

		Todo existingTodo = todoDAO.findById(allTodos.get(0).getId()).get();
		existingTodo.setTitle("edited todo");

		todoDAO.save(existingTodo);

		assertEquals( "edited todo", existingTodo.getTitle());
	}

	@Test
	@Order(5)
	public void deleteTodoTest() {

		int id = allTodos.get(0).getId();
		todoDAO.deleteById(id);

		assertThat(todoDAO.findById(id).isPresent()).isIn(false);
	}

}
