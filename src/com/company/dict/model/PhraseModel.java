package com.company.dict.model;

import static org.springframework.data.mongodb.core.query.Criteria.where;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import com.company.dict.ConfigInstance;
import com.company.dict.SpringMongoConfig;

public class PhraseModel {
	private String id;
	private int uuid;
	private String content;
	private String tibetContent ;
	private String categoryContent ;
	private String tibetCategoryContent ;
	private Integer type;
	private String videopath;
	private String audioPath;
	private String tibetAudioPath;
	private String comment;
	private String tibetComment;
	public String getVideopath() {
		return videopath;
	}
	public void setVideopath(String videopath) {
		this.videopath = videopath;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTibetCategoryContent() {
		return tibetCategoryContent;
	}
	public void setTibetCategoryContent(String tibetCategoryContent) {
		this.tibetCategoryContent = tibetCategoryContent;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTibetContent() {
		return tibetContent;
	}
	public void setTibetContent(String tibetContent) {
		this.tibetContent = tibetContent;
	}
	public String getCategoryContent() {
		return categoryContent;
	}
	public void setCategoryContent(String categoryContent) {
		this.categoryContent = categoryContent;
	}
	public String getAudioPath() {
		return audioPath;
	}
	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}
	public String getTibetAudioPath() {
		return tibetAudioPath;
	}
	public void setTibetAudioPath(String tibetAudioPath) {
		this.tibetAudioPath = tibetAudioPath;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTibetComment() {
		return tibetComment;
	}
	public void setTibetComment(String tibetComment) {
		this.tibetComment = tibetComment;
	}
	
	public void setPhrase(Phrase phrase)
	{
		this.id = phrase.getId();
		this.uuid = phrase.getUuid();
		this.content = phrase.getContent();
		this.tibetContent = phrase.getTibetcontent();
		this.type = phrase.getType();

		 MongoOperations mongoOperation = ConfigInstance.getInstance().getMongoOperation();
		 String categoryStr = null ;
		 Query query = new Query(where("uuid").is(phrase.getCategory()));
		Category cat =  mongoOperation.findOne(query, Category.class);
		if (cat != null)
		{
			this.setCategoryContent(cat.getName());
			this.setTibetCategoryContent(cat.getTibet());
		}

		Query commentQuery = new Query(where("uuid").is(phrase.getComment()));
		Comment comment =  mongoOperation.findOne(commentQuery, Comment.class);
		if(comment != null)
		{
			this.setComment(comment.getContent());
			this.setTibetComment(comment.getTibetcontent());
		}
		Query audioQuery = new Query(where("uuid").is(phrase.getAudio()));
		Audio audio =  mongoOperation.findOne(audioQuery, Audio.class);
		if(audio != null){
			this.setAudioPath(ConfigInstance.getInstance().getStaticServer() +"/audio/"+  audio.getAudiopath());
			this.setTibetAudioPath(ConfigInstance.getInstance().getStaticServer() +"/audio/"+ audio.getTibetaudiopath());
		}
		Query videoQuery = new Query(where("uuid").is(phrase.getVideo()));
		Video vide =  mongoOperation.findOne(videoQuery, Video.class);
		if (vide != null){
			this.setVideopath(ConfigInstance.getInstance().getStaticServer() +"/video/"+vide.getVideopath());
		}
	}
	
	
}
