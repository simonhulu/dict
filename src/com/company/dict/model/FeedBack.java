package com.company.dict.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="feedback")
public class FeedBack {
	@Id
	private String id;
	private int uuid;
	private String uid;
	private String content;
	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getId() {
		return id;
	}


	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
