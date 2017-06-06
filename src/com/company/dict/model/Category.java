package com.company.dict.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="category")
public class Category {
	@Id
	private String id;
	private int uuid;
	private String name;
	private String tibet;
	public String getTibet() {
		return tibet;
	}
	public void setTibet(String tibet) {
		this.tibet = tibet;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getUuid() {
		return uuid;
	}
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
