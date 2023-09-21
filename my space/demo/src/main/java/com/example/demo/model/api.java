package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "api")
public class api {

	
	@Id
	private long id;
	private String name;
	
	private String language ;
	private String framework;
	
	
	public api()
	{
		super();
	}


	public api(long id,String name,  String language, String framework) {
		super();
		this.id =id;
		this.name = name;
		this.language = language;
		this.framework = framework;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getFramework() {
		return framework;
	}


	public void setFramework(String framework) {
		this.framework = framework;
	}
	
	
}
