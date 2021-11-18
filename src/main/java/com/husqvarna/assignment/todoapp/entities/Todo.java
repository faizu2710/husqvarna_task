package com.husqvarna.assignment.todoapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.lang.NonNull;

@Entity
public class Todo implements ITodo {

	@Id
	@GeneratedValue
	private int id;

	@Column(length = 50, nullable = false)
	private String title;

	@Column(nullable = false)
	private Boolean completed = false;

	public Todo() {
	}

	public Todo(String title) {
		super();
		this.title = title;
		this.completed = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	
}
