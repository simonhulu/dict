package com.company.dict.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "phrase")
public class Phrase {
	@Id
	private String id;
	private Integer uuid;
	private String content = "";
	private String tibetcontent = "" ;
	private Integer category = -1 ;
	private Integer audio = -1;
	private Integer comment = -1;
	private Integer video = -1;
	//1.口语学习，2.翻译，3.字典，4.藏文
	private Integer type;

	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getVideo() {
		return video;
	}
	public void setVideo(Integer video) {
		this.video = video;
	}
	public Integer getComment() {
		return comment;
	}
	public void setComment(Integer comment) {
		this.comment = comment;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getUuid() {
		return uuid;
	}
	public void setUuid(Integer uuid) {
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
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public Integer getAudio() {
		return audio;
	}
	public void setAudio(Integer audio) {
		this.audio = audio;
	}
	
}
