package com.husqvarna.assignment.todoapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.husqvarna.assignment.todoapp.dao.ITodoDAO;

@SpringBootTest
class TodoappApplicationTests {

	@Autowired
	ITodoDAO todoDAO;

	@Test
	void contextLoads() {
	}

}
