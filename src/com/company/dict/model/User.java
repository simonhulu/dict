package com.company.dict.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "users")
public class User {
	@Id
	private String id;
	String username;
	String passwd;
	public User(){}
	public User(String username,String passwd)
	{
		this.username = username;
		this.passwd = passwd ;
	}
	/**
	 * @return the id
	 */
	public String getId(){
		return id ;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id ;
	}
	/**
	 * @return the username
	 */
	public String getUserName(){
		return username;
	}
	public void setUserName(String username){
		this.username = username ;
	}
	
	public String getPasswd()
	{
		return passwd;
	}
	
	public void setPasswd(String passwd)
	{
		this.passwd = passwd ;
	}
	
}
