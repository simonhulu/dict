package com.company.dict;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("config")
public class Config {
	@Value("${mediaPath}") 
    private String mediaPath;  
  
	@Value("${imgPath}") 
    private String imgPath;  

	@Value("${audioPath}") 
    private String audioPath; 
    
	@Value("${videoPath}")  
    private String videoPath; 
    
	@Value("$[staticServer]")
	private String staticServer;
    public String getStaticServer() {
		return staticServer;
	}

	public void setStaticServer(String staticServer) {
		this.staticServer = staticServer;
	}

	public String getMediaPath() {  
        return mediaPath;  
    }  
  
    public String getImgPath() {  
        return imgPath;  
    }  
    
    public String getAudioPath() {  
        return audioPath;  
    }  
    
    public String getVideoPath() {  
        return videoPath;  
    }  
}
