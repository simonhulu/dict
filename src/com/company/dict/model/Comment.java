package com.company.dict.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="comment")
public class Comment {
@Id
private String id;
private int uuid;
private String content;
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
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getTibetcontent() {
	return tibetcontent;
}
public void setTibetcontent(String tibetcontent) {
	this.tibetcontent = tibetcontent;
}
private String tibetcontent;
}
