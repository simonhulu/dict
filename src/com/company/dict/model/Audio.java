package com.company.dict.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="audio")
public class Audio {
@Id
private String id;
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
public String getAudiopath() {
	return audiopath;
}
public void setAudiopath(String audiopath) {
	this.audiopath = audiopath;
}
public String getTibetaudiopath() {
	return tibetaudiopath;
}
public void setTibetaudiopath(String tibetaudiopath) {
	this.tibetaudiopath = tibetaudiopath;
}
private int uuid;
private String audiopath;
private String tibetaudiopath;
}
