package org.ual.aas.tasklists.models;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
	private int id;
	private String name;
	private List<Task> tasks;
	
	public TaskList() {
		super();
	}

	public TaskList(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.tasks = new ArrayList<Task>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	
}
