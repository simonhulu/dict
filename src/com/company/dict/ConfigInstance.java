package com.company.dict;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

public class ConfigInstance {
private static ConfigInstance instance;

private String rootPath;
private String staticServer;
private Properties prop;
private MongoOperations mongoOperation;
public MongoOperations getMongoOperation() {
	return mongoOperation;
}

public String getRootPath() {
	return rootPath;
}

public String getStaticServer()
{
	return staticServer;
}

private ConfigInstance(){}
public static synchronized ConfigInstance getInstance(){
	if(instance == null)
	{
		
		instance = new ConfigInstance();
		instance.prop = new Properties();
		String classPath = instance.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		int index = classPath.indexOf("class");
		instance.rootPath = classPath.substring(0, index);
		try {
			FileInputStream fis = new FileInputStream(instance.rootPath+"config.properties");
			try {
				instance.prop.load(fis);
				instance.staticServer = instance.prop.getProperty("staticServer");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		instance.mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		//		String path = resource.getPath();
//		FileInputStream fis = new FileInputStream(file)
	}
	return instance;
}


}
