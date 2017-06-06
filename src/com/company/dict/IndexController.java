package com.company.dict;
import org.slf4j.Logger;  

import java.sql.*;

import org.sqlite.JDBC;

import com.company.dict.model.SequenceId;

import org.slf4j.LoggerFactory; 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.*;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.company.dict.model.Audio;
import com.company.dict.model.Category;
import com.company.dict.model.Collection;
import com.company.dict.model.Comment;
import com.company.dict.model.FeedBack;
import com.company.dict.model.Imgs;
import com.company.dict.model.Phrase;
import com.company.dict.model.PhraseModel;
import com.company.dict.model.User;
import com.company.dict.model.Video;
import com.company.dict.repositories.*;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.sun.xml.internal.ws.api.pipe.NextAction;
@Controller
@RequestMapping(value = "/") 
public class IndexController extends DictController {
	
//搜索翻译的内容type 是6
//电子词典对应的type是5	
	
//	@Autowired UsersRepository repository;
	@Autowired  
	private Config configInfo; 
	
	private Integer countInPage = 20 ;
	
	@RequestMapping(value="/")
	public String index(Model model)
	{
		 ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		    MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		 List<User> list =   mongoOperation.findAll(User.class);
		 User user = (User)list.get(0);
		 System.out.println(user.getUserName());
		 return "login";
	}
	
	@RequestMapping(value="/login")
	public String login(Model model)
	{
		 ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		    MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		 List<User> list =   mongoOperation.findAll(User.class);
		 User user = (User)list.get(0);
		 System.out.println(user.getUserName());
		    return "login";
	}
	@RequestMapping(value="/userLogin")	
	public String userLogin(HttpServletRequest request,Model model)
	{
		   String username = request.getParameter("username");
		   System.out.println(username);
		   String passwd = request.getParameter("passwd");		   
		   ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		   MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		   Query query = new Query(where("username").is(username));
		   User user = mongoOperation.findOne(query,User.class);
			if(user == null)
			{
				return "login";
			}
			if(!user.getPasswd().equals(passwd))
			{
				return "login";
			}
			model.addAttribute("page",0);
		return "main";
	}
	
	@RequestMapping(value="/doRegist")	
	@ResponseBody
	public String doRegist(HttpServletRequest request) throws UnsupportedEncodingException
	{
		String username = request.getParameter("uid");
		username = username.toLowerCase();
		String passwd = request.getParameter("upass");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		Query query = new Query(where("username").is(username));
		User user = mongoOperation.findOne(query,User.class);
		Result res = new Result();
		Gson gson = new Gson();
		String result ="";
		if (user != null)
		{
			res.setSuccess(false);
			res.setMsg("用户名已存在");
		
		}else{
			User nuser = new User(username,passwd);
			mongoOperation.save(nuser);
			res.setSuccess(true);
			res.setData(nuser);
		}
		result = gson.toJson(res);
		result = new String(result.getBytes("utf-8"),"utf-8");
		return  result;
	}
	
	@RequestMapping(value="/doLogin")	
	@ResponseBody
	public String doLogin(HttpServletRequest request)
	{
		String username = request.getParameter("uid");
		username = username.toLowerCase();
		String passwd = request.getParameter("upass");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		Query query = new Query(where("username").is(username));
		User user = mongoOperation.findOne(query,User.class);
		Result res = new Result();
		Gson gson = new Gson();
		String result ="";
		if (user != null)
		{
			if (user.getPasswd().equalsIgnoreCase(passwd))
			{
				res.setSuccess(true);
				res.setData( user);
			}else{
				res.setSuccess(false);
				res.setMsg("用户名或密码错误");
			}
		}else{
			res.setSuccess(false);
			res.setMsg("用户名或密码错误"); 
		}
		result = gson.toJson(res);
		return result;
	}
	
	@RequestMapping(value="/uLogin")	
	@ResponseBody
	public String uLogin(HttpServletRequest request,Model model)
	{

		   String username = request.getParameter("username");
		   System.out.println(username);
		   String passwd = request.getParameter("passwd");		   
		   ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		   MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
			 Query query = new Query(where("username").is(username));
			User user = mongoOperation.findOne(query,User.class);
			if(user == null)
			{
				return "login";
			}
			if(!user.getPasswd().equals(passwd))
			{
				return "login";
			}
			model.addAttribute("page",0);
		return "main";
	}
	
	
	

	
	
	@RequestMapping(value="/main")
	public String mainPage(HttpServletRequest request,Model model)
	{
		String str =  request.getParameter("page");
		String pageNumStr =  request.getParameter("pn");
		int pageNum = 0 ;
		int pageIndex = 0;
		if(str!=null)
		{
			pageIndex = Integer.parseInt(str);
		}
		
		if(pageNumStr != null)
		{
			pageNum = Integer.parseInt(pageNumStr)-1;
			if(pageNum < 0)
			{
				pageNum = 0 ;
			}			
		}else{
			pageNum = 0 ;
		}
		
		if(pageIndex == 1 || pageIndex == 2 || pageIndex == 3 ||pageIndex == 4)
		{
			ArrayList<PhraseModel> arrayList = null;
			Query query = null;
			switch (pageIndex) {
			case 1:
				query	=new Query(Criteria.where("type").is(1));
				query.limit(countInPage).skip(pageNum*countInPage);
				break;
			case 2:
				query = new Query(Criteria.where("type").is(6));
				query.limit(countInPage).skip(pageNum*countInPage);
				break;
			case 3:
				query = new Query(Criteria.where("type").is(5));
				query.limit(countInPage).skip(pageNum*countInPage);
				break;
			case 4:
			query = new Query(Criteria.where("type").is(4));
			query.limit(countInPage).skip(pageNum*countInPage);
			break;
			default:
				query	= new Query();
				break;
			}
			arrayList = this.getPhrase(query);
			List<Category> categories = getAllCategory();
			model.addAttribute("phrases",arrayList);
			model.addAttribute("categories",categories);
		}else if(pageIndex == 5)
		{
			List<FeedBack> feedbacks = getAllFeedback();
			model.addAttribute("feedbacks",feedbacks);
		}else if(pageIndex == 6)
		{
			List<User> feedbacks = getUsers();
			model.addAttribute("users",feedbacks);
		}
		model.addAttribute("page",pageIndex);
		ConfigInstance.getInstance();
		return "main";
	}
	
	@RequestMapping(value="/spokenPage")
	public String spokenPage(HttpServletRequest request,Model model)
	{
		String str =  request.getParameter("page");
		String pageNumStr =  request.getParameter("pn");
		int pageIndex = 0;
		int pageNum = 0 ;
		if(str!=null)
		{
			pageIndex = Integer.parseInt(str);
			if(pageIndex < 0)
			{
				pageIndex = 0 ;
			}
		}
		
		if(pageNumStr != null)
		{
			pageNum = Integer.parseInt(pageNumStr)-1;
			if(pageNum < 0)
			{
				pageNum = 0 ;
			}			
		}
		if(pageIndex == 1 || pageIndex == 2 || pageIndex == 3 ||pageIndex == 4)
		{
			ArrayList<PhraseModel> arrayList = null;
			Query query = null;
			switch (pageIndex) {
			case 1:
				query	=new Query(Criteria.where("type").is(1));
				query.limit(20).skip(pageNum*countInPage);
				break;
			case 2:
				query = new Query(Criteria.where("type").is(6));
				query.limit(countInPage);
				break;
			case 3:
				query = new Query(Criteria.where("type").is(5));
				query.limit(countInPage);
				break;
			case 4:
				query = new Query(Criteria.where("type").is(4));
				query.limit(countInPage);
				break;
			default:
				query	= new Query();
				break;
			}
			arrayList = this.getPhrase(query);
			List<Category> categories = getAllCategory();
			model.addAttribute("phrases",arrayList);
			model.addAttribute("categories",categories);
		}
		model.addAttribute("page",pageIndex);
		ConfigInstance.getInstance();
		return "main";
	}	
	
	
	
	
	public ArrayList<PhraseModel> getPhrase(Query query)
	{
		ArrayList<PhraseModel> arrayList = new ArrayList<PhraseModel>();
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		 MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		 List<Phrase> phrases = mongoOperation.find(query, Phrase.class);
		 for(int i = phrases.size()-1; i >=0; i--)
			{
				Phrase ph =  phrases.get(i);
				PhraseModel phmodel = new PhraseModel();
				phmodel.setPhrase(ph);
				arrayList.add(phmodel);
				System.out.println(ph);
			}
		return arrayList;
	}	
	
	 public static int getNextSequence(String name)
	 {
		 ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		 Query query = new Query(Criteria.where("_id").is(name));

	        Update update = new Update();
		update.inc("seq", 1);

		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);

		SequenceId seqId = 
	            mongoOperation.findAndModify(query, update, options, SequenceId.class);

		return seqId.getSeq();

	 }
	

	@RequestMapping(value="/uploadlanuchimage")
	@ResponseBody
	public String uploadlanuchimage(HttpServletRequest request,Model model)
	{
		try {
	        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
	        for (FileItem item : items) {
	            if (item.isFormField()) {
	                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
	                String fieldName = item.getFieldName();
	                String fieldValue = item.getString();
	                // ... (do your job here)
	                
	            } else {
	                // Process form file field (input type="file").
	                String fieldName = item.getFieldName();
	                String fileName = FilenameUtils.getName(item.getName());
	                String launchPath = "";
	                try {
						InputStream fileContent = item.getInputStream();
						OutputStream outputStream = null;
						System.out.println(configInfo.getImgPath());
						
						launchPath = configInfo.getImgPath() +"/"+System.currentTimeMillis()+ ".png";
						
						outputStream = 
			                    new FileOutputStream(new File(launchPath));

					int read = 0;
					byte[] bytes = new byte[1024];

					while ((read = fileContent.read(bytes)) != -1) {
						outputStream.write(bytes, 0, read);
					}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                Imgs imgs = new Imgs();
	                imgs.setPath(launchPath);
	                ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
	       		 MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
	       		mongoOperation.save(imgs);
	                // ... (do your job here)
	            }
	        }
	    } catch (FileUploadException e) {
	        try {
				throw new ServletException("Cannot parse multipart request.", e);
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
		return "main";
	}
	
	@RequestMapping(value="/editSpoken")	
	public String editSpoken(HttpServletRequest request,Model model)
	{
		int phraseId = Integer.parseInt(request.getParameter("phraseId"));
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		 MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		 Query phraseQuery =  new Query(where("uuid").is(phraseId));
		Phrase ph =  mongoOperation.findOne(phraseQuery, Phrase.class);
		if(ph != null)
		{
			PhraseModel phm = new PhraseModel();
			phm.setPhrase(ph);
			model.addAttribute("phrase",phm);
		}

		return "editStudySpoken";
	}
	
	@RequestMapping(value="/getAdimg")	
	@ResponseBody	
	public String getAdimg(HttpServletRequest request,Model model)
	{
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		 MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		 Query phraseQuery =  new Query();
		 phraseQuery.with(new Sort(Sort.Direction.DESC, "_id"));
		Imgs img =  mongoOperation.findOne(phraseQuery, Imgs.class);
		
		String path = ConfigInstance.getInstance().getStaticServer();
		if (img !=null)
		{
			path = path + "/"+ img.getPath();
			img.setPath(path);
		}
		String result ="";
		try {
			result = objectMapper.writeValueAsString(img) ;
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/addStudy",method = RequestMethod.POST)	
	@ResponseBody	
	public String addStudy(HttpServletRequest request,Model model)
	{ 
		try{
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			upload.setHeaderEncoding("UTF-8");
	        List<FileItem> items = upload.parseRequest(request);
	        Phrase phrase = new Phrase();
			phrase.setUuid(getNextSequence("phrase"));
			phrase.setType(4);
			 Audio audio = null;
			 Video video = null;
			 Comment comment = null;
	        for (FileItem item : items) {
	            if (item.isFormField()) {
	                String fieldName = item.getFieldName();
	                String fieldValue = item.getString();
	                if(fieldName.equals("content"))
	                {
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							phrase.setContent(ss);
	                	} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 

	                }
	                if(fieldName.equals("tibetcontent"))
	                {
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							phrase.setTibetcontent(ss);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                
	                if(fieldName.equals("comment"))
	                {
	                	if(comment == null)
	                	{
	                		comment = new Comment();
	                	}
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							comment.setContent(ss);
							comment.setUuid(getNextSequence("comment"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                
	                if(fieldName.equals("tibetcomment"))
	                {
	                	if(comment == null)
	                	{
	                		comment = new Comment();
	                	}
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							comment.setTibetcontent(ss);
							comment.setUuid(getNextSequence("comment"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                
	                
	                if(fieldName.equals("cateid"))
	                {
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							phrase.setCategory(Integer.parseInt(ss));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }

	                

	                
	                // ... (do your job here)
	            } else {
	                // Process form file field (input type="file").
	                String fieldName = item.getFieldName();
	                String fileName = FilenameUtils.getName(item.getName());
	                System.out.println("fieldName="+fieldName);
	                System.out.println("fileName="+fileName);
	                String filePath = "";
	                if(fieldName.equals("tibetaudiopath"))
	                {
	                	filePath =  configInfo.getAudioPath() +"/"+ fieldName+fileName+".mp3";
	                	audio = new Audio();
	                	 audio.setUuid(getNextSequence("audio"));
	 	                // ... (do your job here)
	 	                audio.setTibetaudiopath(fieldName+fileName+".mp3");
	                }else if (fieldName.equals("videopath"))
	                {
	                	filePath = configInfo.getVideoPath() +"/"+ fieldName+fileName+".mp4";
	                	video = new Video();
	                	video.setUuid(getNextSequence("audio"));
	                	video.setVideopath(fieldName+fileName+".mp4");
	                }
	                if(!filePath.equals(""))
	                {
		                try {
							InputStream fileContent = item.getInputStream();
							OutputStream outputStream = null;
							outputStream = 
				                    new FileOutputStream(new File(filePath));
							int read = 0;
							byte[] bytes = new byte[1024];
							while ((read = fileContent.read(bytes)) != -1) {
								outputStream.write(bytes, 0, read);
							}

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	            }
	        }
	        if(audio != null)
	        {
	            saveAudio(audio);
		        phrase.setAudio(audio.getUuid());
	        }
	        if(video != null)
	        {
	            saveVideo(video);
	            phrase.setVideo(video.getUuid());
	        }
            if(comment != null)
            {
            	saveComment(comment);
            }
	        if(comment != null)
	        {
	        	phrase.setComment(comment.getUuid());
	        }
	        savePh(phrase);
		}catch (FileUploadException e) {
	        try {
				throw new ServletException("Cannot parse multipart request.", e);
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }

		Result res = new Result();
		res.setSuccess(true);
		Gson gson = new Gson();
		String json = gson.toJson(res);  
		return json;
	}
	@RequestMapping(value="/addDic",method = RequestMethod.POST)	
	@ResponseBody	
	public String addDic(HttpServletRequest request,Model model)
	{ 
		try{
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			upload.setHeaderEncoding("UTF-8");
	        List<FileItem> items = upload.parseRequest(request);
	        Phrase phrase = new Phrase();
			phrase.setUuid(getNextSequence("phrase"));
			phrase.setType(5);
			 Audio audio = new Audio();
			 Comment comment = null;
	        for (FileItem item : items) {
	            if (item.isFormField()) {
	                String fieldName = item.getFieldName();
	                String fieldValue = item.getString();
	                if(fieldName.equals("content"))
	                {
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							phrase.setContent(ss);
	                	} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 

	                }
	                if(fieldName.equals("tibetcontent"))
	                {
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							phrase.setTibetcontent(ss);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                
	                if(fieldName.equals("comment"))
	                {
	                	if(comment == null)
	                	{
	                		comment = new Comment();
	                	}
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							comment.setContent(ss);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                
	                if(fieldName.equals("tibetcomment"))
	                {
	                	if(comment == null)
	                	{
	                		comment = new Comment();
	                	}
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							comment.setTibetcontent(ss);
							comment.setUuid(getNextSequence("comment"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                
	                
	                if(fieldName.equals("cateid"))
	                {
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							phrase.setCategory(Integer.parseInt(ss));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }

	                

	                
	                // ... (do your job here)
	            } else {
	                // Process form file field (input type="file").
	                String fieldName = item.getFieldName();
	                String fileName = FilenameUtils.getName(item.getName());
	                System.out.println("fieldName="+fieldName);
	                System.out.println("fileName="+fileName);
	                String mp3Path = configInfo.getAudioPath() +"/"+ fieldName+fileName+".mp3";
	                try {
						InputStream fileContent = item.getInputStream();
						OutputStream outputStream = null;
						
						outputStream = 
			                    new FileOutputStream(new File(mp3Path));
						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = fileContent.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	               
	                audio.setUuid(getNextSequence("audio"));
	                
	                // ... (do your job here)
	                if(fieldName.equals("hanfile"))
	                {
	                	audio.setAudiopath(fieldName+fileName+".mp3");
	                }
	                if(fieldName.equals("zangyufile"))
	                {
	                	audio.setTibetaudiopath(fieldName+fileName+".mp3");
	                }
	                
	               
	            }
	        }
	        saveAudio(audio);
            if(comment != null)
            {
            	saveComment(comment);
            }
	        phrase.setAudio(audio.getUuid());
	        if(comment != null)
	        {
	        	phrase.setComment(comment.getUuid());
	        }
	        savePh(phrase);
		}catch (FileUploadException e) {
	        try {
				throw new ServletException("Cannot parse multipart request.", e);
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }

		Result res = new Result();
		res.setSuccess(true);
		Gson gson = new Gson();
		String json = gson.toJson(res);  
		return json;
	}
	@RequestMapping(value="/addTran",method = RequestMethod.POST)	
	@ResponseBody	
	public String addTran(HttpServletRequest request,Model model)
	{ 
		try{
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			upload.setHeaderEncoding("UTF-8");
	        List<FileItem> items = upload.parseRequest(request);
	        Phrase phrase = new Phrase();
			phrase.setUuid(getNextSequence("phrase"));
			phrase.setType(6);
			phrase.setComment(-1);
			 Audio audio = new Audio();
	        for (FileItem item : items) {
	            if (item.isFormField()) {
	                String fieldName = item.getFieldName();
	                String fieldValue = item.getString();
	                if(fieldName.equals("content"))
	                {
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							phrase.setContent(ss);
	                	} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 

	                }
	                if(fieldName.equals("tibetcontent"))
	                {
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							phrase.setTibetcontent(ss);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                if(fieldName.equals("cateid"))
	                {
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							phrase.setCategory(Integer.parseInt(ss));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                // ... (do your job here)
	            } else {
	                // Process form file field (input type="file").
	                String fieldName = item.getFieldName();
	                String fileName = FilenameUtils.getName(item.getName());
	                System.out.println("fieldName="+fieldName);
	                System.out.println("fileName="+fileName);
	                String mp3Path = configInfo.getAudioPath() +"/"+ fieldName+fileName+".mp3";
	                try {
						InputStream fileContent = item.getInputStream();
						OutputStream outputStream = null;
						
						outputStream = 
			                    new FileOutputStream(new File(mp3Path));
						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = fileContent.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	               
	                audio.setUuid(getNextSequence("audio"));
	                
	                // ... (do your job here)
	                if(fieldName.equals("hanfile"))
	                {
	                	audio.setAudiopath(fieldName+fileName+".mp3");
	                }
	                if(fieldName.equals("zangyufile"))
	                {
	                	audio.setTibetaudiopath(fieldName+fileName+".mp3");
	                }
	               
	            }
	        }
	        saveAudio(audio);
	        phrase.setAudio(audio.getUuid());
	        savePh(phrase);
		}catch (FileUploadException e) {
	        try {
				throw new ServletException("Cannot parse multipart request.", e);
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }

		Result res = new Result();
		res.setSuccess(true);
		Gson gson = new Gson();
		String json = gson.toJson(res);  
		return json;
	}
	
	@RequestMapping(value="/addTrans",method = RequestMethod.POST)	
	@ResponseBody	
	public String addTransFromExcel(HttpServletRequest request,Model model)
	{ 
		try{
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			upload.setHeaderEncoding("UTF-8");
	        List<FileItem> items = upload.parseRequest(request);
	        for (FileItem item : items) {
				if (item.isFormField())
				{
				}else{
	                // Process form file field (input type="file").
	                String fieldName = item.getFieldName();
	                String fileName = FilenameUtils.getName(item.getName());
	                try {
						InputStream fileContent = item.getInputStream();
						OutputStream outputStream = null;
						String excelPath = configInfo.getMediaPath() +"/"+ fileName;
						outputStream = 
			                    new FileOutputStream(new File(excelPath));
						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = fileContent.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}
						outputStream.close();
						try {
							FileInputStream in = new FileInputStream(new File(excelPath));
						    XSSFWorkbook wb = new XSSFWorkbook(in);
						    XSSFSheet sheet = wb.getSheetAt(0);
						    XSSFRow row ;
						    Cell cell ;
						    int rows ; // No of rows
						    rows = sheet.getPhysicalNumberOfRows() ;
						    int cols = 0 ; // No of columns
						    int tmp = 0 ;
						    for(int r = 0; r < rows; r++) {
						    	if(rows >0)
						    	{
							        Phrase phrase = new Phrase();
									phrase.setUuid(getNextSequence("phrase"));
									phrase.setType(6);
									phrase.setComment(-1);
							        row = sheet.getRow(r);
							        if(row != null) {
							                cell = row.getCell((short)0);
							                if(cell != null) {
							                    // Your code here
							                	phrase.setContent(cell.getStringCellValue());
							                }
							                Cell cell2 = row.getCell(1);
							                if(cell2 != null) {
							                    // Your code here
							                	phrase.setTibetcontent(cell2.getStringCellValue());
							                }
							         savePh(phrase);
							        }						    		
						    	}
						    }
						} catch(Exception ioe) {
						    ioe.printStackTrace();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	        }
		}catch (FileUploadException e) {
	        try {
				throw new ServletException("Cannot parse multipart request.", e);
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		Result res = new Result();
		res.setSuccess(true);
		Gson gson = new Gson();
		String json = gson.toJson(res);  
		return json;
	}
	
	
	@RequestMapping(value="/addDics",method = RequestMethod.POST)	
	@ResponseBody	
	public String addDicsFromExcel(HttpServletRequest request,Model model)
	{ 
		try{
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			upload.setHeaderEncoding("UTF-8");
	        List<FileItem> items = upload.parseRequest(request);
	        for (FileItem item : items) {
				if (item.isFormField())
				{
				}else{
	                // Process form file field (input type="file").
	                String fieldName = item.getFieldName();
	                String fileName = FilenameUtils.getName(item.getName());
	                try {
						InputStream fileContent = item.getInputStream();
						OutputStream outputStream = null;
						String excelPath = configInfo.getMediaPath() +"/"+ fileName;
						outputStream = 
			                    new FileOutputStream(new File(excelPath));
						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = fileContent.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}
						outputStream.close();
						try {
							FileInputStream in = new FileInputStream(new File(excelPath));
						    XSSFWorkbook wb = new XSSFWorkbook(in);
						    XSSFSheet sheet = wb.getSheetAt(0);
						    XSSFRow row ;
						    Cell cell ;
						    int rows ; // No of rows
						    rows = sheet.getPhysicalNumberOfRows() ;
						    int cols = 0 ; // No of columns
						    int tmp = 0 ;
						    for(int r = 0; r < rows; r++) {
						    	if(rows >0)
						    	{
							        Phrase phrase = new Phrase();
									phrase.setUuid(getNextSequence("phrase"));
									phrase.setType(5);
									phrase.setComment(-1);
							        row = sheet.getRow(r);
							        if(row != null) {
							                cell = row.getCell((short)0);
							                if(cell != null) {
							                    // Your code here
							                	phrase.setContent(cell.getStringCellValue());
							                }
							                Cell cell2 = row.getCell(1);
							                if(cell2 != null) {
							                    // Your code here
							                	phrase.setTibetcontent(cell2.getStringCellValue());
							                }
							         savePh(phrase);
							        }						    		
						    	}
						    }
						} catch(Exception ioe) {
						    ioe.printStackTrace();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	        }
		}catch (FileUploadException e) {
	        try {
				throw new ServletException("Cannot parse multipart request.", e);
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		Result res = new Result();
		res.setSuccess(true);
		Gson gson = new Gson();
		String json = gson.toJson(res);  
		return json;
	}	
	
	
	@RequestMapping(value="/addSpoken",method = RequestMethod.POST)	
	@ResponseBody
	public String addSpoken(HttpServletRequest request,Model model)
	{ 	

		try{
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			upload.setHeaderEncoding("UTF-8");
	        List<FileItem> items = upload.parseRequest(request);
	        Phrase phrase = new Phrase();
			phrase.setUuid(getNextSequence("phrase"));
			phrase.setType(1);
			phrase.setComment(-1);
			 Audio audio = new Audio();
	        for (FileItem item : items) {
	            if (item.isFormField()) {
//	                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
	                String fieldName = item.getFieldName();
	                String fieldValue = item.getString();
//	                Audio audio = new Audio();
//	                audio.setUuid(getNextSequence("audio"));
	                if(fieldName.equals("content"))
	                {
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							phrase.setContent(ss);
	                	} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 

	                }
	                if(fieldName.equals("tibetcontent"))
	                {
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							phrase.setTibetcontent(ss);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                if(fieldName.equals("cateid"))
	                {
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
							phrase.setCategory(Integer.parseInt(ss));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                // ... (do your job here)
	            } else {
	                // Process form file field (input type="file").
	                String fieldName = item.getFieldName();
	                String fileName = FilenameUtils.getName(item.getName());
	                System.out.println("fieldName="+fieldName);
	                System.out.println("fileName="+fileName);
	                String mp3Path = configInfo.getAudioPath() +"/"+ fieldName+fileName+".mp3";
	                try {
						InputStream fileContent = item.getInputStream();
						OutputStream outputStream = null;
						
						outputStream = 
			                    new FileOutputStream(new File(mp3Path));
						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = fileContent.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}
						outputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	               
	                audio.setUuid(getNextSequence("audio"));
	                
	                // ... (do your job here)
	                if(fieldName.equals("hanfile"))
	                {
	                	audio.setAudiopath(fieldName+fileName+".mp3");
	                }
	                if(fieldName.equals("zangyufile"))
	                {
	                	audio.setTibetaudiopath(fieldName+fileName+".mp3");
	                }
	               
	            }
	        }
	        saveAudio(audio);
	        phrase.setAudio(audio.getUuid());
	        savePh(phrase);
		}catch (FileUploadException e) {
	        try {
				throw new ServletException("Cannot parse multipart request.", e);
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }

		Result res = new Result();
		res.setSuccess(true);
		Gson gson = new Gson();
		String json = gson.toJson(res);  
		return json;
	}
	
	public void saveAudio(Audio audio)
	{
		ConfigInstance.getInstance().getMongoOperation().save(audio);
	}
	
	public void saveVideo(Video video)
	{
		ConfigInstance.getInstance().getMongoOperation().save(video);
	}
	
	public void saveUser(User user)
	{
		ConfigInstance.getInstance().getMongoOperation().save(user);
	}
	
	public void saveComment(Comment comment)
	{
		ConfigInstance.getInstance().getMongoOperation().save(comment);	
	}
	
	public void savePh(Phrase phrase)
	{
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
//		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		ConfigInstance.getInstance().getMongoOperation().save(phrase);
	}
	
	@RequestMapping(value="/saveSpoken")	
	@ResponseBody
	public String saveSpoken(HttpServletRequest request,Model model)
	{ 
		String str =  request.getParameter("content");
		PhraseModel phmodel = new PhraseModel();
		try {
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			upload.setHeaderEncoding("UTF-8");
	        List<FileItem> items = upload.parseRequest(request);
	        for (FileItem item : items) {
	            if (item.isFormField()) {
	                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
	                String fieldName = item.getFieldName();
	                String fieldValue = item.getString();
	                if(fieldName.equals("id"))
	                {
	                	phmodel.setUuid(Integer.parseInt(fieldValue));
	                }
	                if(fieldName.equals("content"))
	                {
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
		                	phmodel.setContent(ss);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 

	                }
	                if(fieldName.equals("tibetconent"))
	                {
	                	try {
							String ss =new String(fieldValue.getBytes("ISO8859_1"), "utf-8");
		                	phmodel.setTibetContent(ss);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                
	                // ... (do your job here)
	            } else {
	                // Process form file field (input type="file").
	                String fieldName = item.getFieldName();
	                String fileName = FilenameUtils.getName(item.getName());
	                System.out.println("fieldName="+fieldName);
	                System.out.println("fileName="+fileName);
	                String mp3Path = configInfo.getAudioPath() +"/"+ fieldName+fileName+".mp3";
	                try {
						InputStream fileContent = item.getInputStream();
						OutputStream outputStream = null;
						
						outputStream = 
			                    new FileOutputStream(new File(mp3Path));
						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = fileContent.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                // ... (do your job here)
	                if(fieldName.equals("cn"))
	                {
	                	phmodel.setAudioPath(fieldName+fileName+".mp3");
	                }
	                if(fieldName.equals("ti"))
	                {
	                	phmodel.setTibetAudioPath(fieldName+fileName+".mp3");
	                }
	            }
	        }
	    } catch (FileUploadException e) {
	        try {
				throw new ServletException("Cannot parse multipart request.", e);
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		this.updateAudio(phmodel, mongoOperation);
		this.updateContent(phmodel, mongoOperation);
		return "editStudySpoken";
	}
	
	@RequestMapping(value="/ttttt",produces = "application/json;charset=UTF-8")	
	@ResponseBody
	public String tttttt(HttpServletRequest request,Model model)
	{ 
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB db = mongoClient.getDB( "dict" );
		DBCollection coll = db.getCollection("phrase");
//		BulkWriteOperation builder = coll.initializeOrderedBulkOperation();
//		BasicDBObject newDocument = new BasicDBObject();
//		newDocument.append("$set", new BasicDBObject().append("comment", -1));
//		BasicDBObject searchQuery = new BasicDBObject().append("comment", "");
	
		int rnd = 0 ;
		DBCursor cursor = coll.find();
		try{
			while(cursor.hasNext())
			{
				BasicDBObject obj = (BasicDBObject)cursor.next();
				obj.put("comment", -1);
				coll.save(obj);
			}
		}finally
		{
			cursor.close();
		}
		
		
		return	"success";
//		;
//
//		return 	builder.find(new BasicDBObject("comment","")).update(new BasicDBObject("$set", new BasicDBObject("comment", -1)));;
	}
	
	@RequestMapping(value="/searchtran",produces = "application/json;charset=UTF-8")	
	@ResponseBody
	public String searchtran(HttpServletRequest request,Model model)
	{ 
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		String content = request.getParameter("content") ;
		int page = 0 ;
		if ( request.getParameter("page") != null)
		{
			 page = Integer.parseInt( request.getParameter("page")) ;
		}
		int count = 0;
		if(request.getParameter("count") != null)
		{
			count = Integer.parseInt( request.getParameter("count")) ;
		}
		
		
		if (page<0)page=0;
		if(count<0)count = 0 ;
		Criteria criteria = new Criteria();
		List<Phrase> phs;
		if (content !=null &&  content.length()>0)
		{
			criteria.orOperator(Criteria.where("content").regex(content));
			int skipCount = (page - 1)*count;
			if (skipCount < 0)
				skipCount = 0;
			Query query = new Query(criteria).skip(skipCount);
			query.limit(count);
			phs = mongoOperation.find(query, Phrase.class);
		}else
		{
			phs = mongoOperation.findAll( Phrase.class);
		}
		ArrayList<PhraseModel> arrayList = new ArrayList<PhraseModel>();
		 for(int i = 0; i < phs.size(); i++)
			{
				Phrase ph =  phs.get(i);
				PhraseModel phmodel = new PhraseModel();
				phmodel.setPhrase(ph);
				arrayList.add(phmodel);
			}
		Gson son = new Gson();
		Result res = new Result();
		res.success = true ;
		res.data = arrayList;
		String result = "";
		result = son.toJson(res);
		return result;
	}
	
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public String delete(HttpServletRequest request,Model model)
	{
		String phraseId = request.getParameter("id");
		this.deletePh(phraseId);
		Gson son = new Gson();
		Result res = new Result();
		res.success = true ;
		String json = son.toJson(res);
		return json;
	}
	
	@RequestMapping(value="/search")	
	public String search(HttpServletRequest request,Model model)
	{ 
		return"";
	}
	
	@RequestMapping(value="/getCategories",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getCategories(HttpServletRequest request,Model model)
	{ 
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
//		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		List<Category> list = getAllCategory();
		String result ="";
		try {
			result = objectMapper.writeValueAsString(list) ;
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	public List<Category> getAllCategory()
	{
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		List<Category> list = mongoOperation.findAll(Category.class);
	
		return list;
	}
	
	public List<User> getUsers()
	{
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		List<User> list = mongoOperation.findAll(User.class);
		return list;
	}	

	public List<FeedBack> getAllFeedback()
	{
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		
		List<FeedBack> list = mongoOperation.findAll(FeedBack.class);
	
		return list;
	}	
	
	public Boolean deletePh(String phId)
	{
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		Query phraseQuery =  new Query(where("uuid").is(Integer.parseInt(phId)));
		mongoOperation.remove(phraseQuery,Phrase.class);
		return true;
	}
	
	public Phrase getPhraseById(int phId)
	{
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		Query phraseQuery =  new Query(where("uuid").is(phId));
		return mongoOperation.findOne(phraseQuery,Phrase.class);
	}
	

	
	@RequestMapping(value="/getPhraseByCategory",produces = "application/json;charset=UTF-8")
	@ResponseBody	
	public String getPhraseByCategory(HttpServletRequest request,Model model)
	{
		int categoryIndex = Integer.parseInt( request.getParameter("categoryIndex")) ;
		ArrayList<PhraseModel> arrayList = new ArrayList<PhraseModel>();
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		Query phraseQuery =  new Query(where("category").is(categoryIndex));
		List<Phrase> phrases = mongoOperation.find(phraseQuery, Phrase.class);
		 for(int i = 0; i < phrases.size(); i++)
			{
				Phrase ph =  phrases.get(i);
				PhraseModel phmodel = new PhraseModel();
				phmodel.setPhrase(ph);
				arrayList.add(phmodel);
			}
		String result = "";
		try {
			result = objectMapper.writeValueAsString(arrayList) ;
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;		
	}
	
	
	private void updateAudio(PhraseModel phmodel, MongoOperations mongoOperation)
	{
		 Query phraseQuery =  new Query(where("uuid").is(phmodel.getUuid()));
		 Phrase ph = mongoOperation.findOne(phraseQuery, Phrase.class);
		 Query audioQuery =  new Query(where("audio").is(ph.getAudio())) ;
		 if(phmodel.getAudioPath() != null)
		 {
			 mongoOperation.updateFirst(new Query(where("uuid").is(ph.getAudio())), Update.update("audiopath", phmodel.getAudioPath()), Audio.class);
		 }
		 if(phmodel.getTibetAudioPath() != null)
		 {
			 mongoOperation.updateFirst(new Query(where("uuid").is(ph.getAudio())), Update.update("tibetaudiopath", phmodel.getTibetAudioPath()), Audio.class);			 
		 }
	}
	
	private void updateContent(PhraseModel phmodel, MongoOperations mongoOperation)
	{
		 Query phraseQuery =  new Query(where("uuid").is(phmodel.getUuid()));
		 mongoOperation.updateFirst(phraseQuery, Update.update("content", phmodel.getContent()), Phrase.class);
		 mongoOperation.updateFirst(phraseQuery, Update.update("tibetcontent", phmodel.getTibetContent()), Phrase.class);
	}
	
	@RequestMapping(value="/getLearnDicById",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getLearnDicById(HttpServletRequest request)
	{
		String phraseId = request.getParameter("id");
		Phrase phrase = this.getPhraseById(Integer.parseInt( phraseId));
		PhraseModel phmodel = new PhraseModel();
		phmodel.setPhrase(phrase);
		String result = "";
		try {
			result = objectMapper.writeValueAsString(phmodel) ;
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@RequestMapping(value="/getLearnDic",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getLearnDic(HttpServletRequest request)
	{
		Query query = null;
		query = new Query(Criteria.where("type").is(4));
		ArrayList arrayList = null ;
		arrayList = this.getPhrase(query);
		List<Category> categories = getAllCategory();
//		model.addAttribute("phrases",arrayList);
		String result = "";
		try {
			result = objectMapper.writeValueAsString(arrayList) ;
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/searchInCategory",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String searchInCategory(HttpServletRequest request,Model model)
	{
		String cateIndex = request.getParameter("categoryIndex");
		String keyword=  request.getParameter("keyword");
		Result res = new Result();
		Gson gson = new Gson();
		String result ="";
		Criteria criteria = new Criteria();
		
		criteria.andOperator(Criteria.where("category").is(Integer.parseInt(cateIndex)));
		criteria.orOperator(Criteria.where("content").regex(keyword),Criteria.where("tibetcontent").regex(keyword));
		Query categoryQuery =  new Query(criteria);
		List<Phrase> phrases = ConfigInstance.getInstance().getMongoOperation().find(categoryQuery, Phrase.class);
		ArrayList<PhraseModel> list = new ArrayList();
		for (Phrase ph : phrases)
		{
			PhraseModel pm = new PhraseModel();
			pm.setPhrase(ph);
			list.add(pm);
		}
		res.data =  list;
		res.success = true ;
		result = gson.toJson(res);
		return result ;
	}
	
	@RequestMapping(value="/searchByCategotyStr",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String searchByCategotyStr(HttpServletRequest request,Model model)
	{
		String cateStr = request.getParameter("categoryStr");
		ArrayList<Category> arrayList = new ArrayList<Category>();
		Criteria criteria = new Criteria();
		criteria.orOperator(Criteria.where("name").regex(cateStr),Criteria.where("tibet").regex(cateStr));
		Query categoryQuery =  new Query(criteria);
		List<Category> categories = ConfigInstance.getInstance().getMongoOperation().find(categoryQuery, Category.class);
		System.out.println(cateStr);
		System.out.println(categories);
		for(int i = 0; i < categories.size(); i++)
			{
			 Category cate = categories.get(i);
			 arrayList.add(cate);
			}
		String result = "";
		try {
			result = objectMapper.writeValueAsString(arrayList) ;
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;		
	}
	
	public List<Video> getAllVideo()
	{
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		List<Video> list = mongoOperation.findAll(Video.class);
		return list;
	}
	
	public List<Audio> getAllAudio()
	{
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		List<Audio> list = mongoOperation.findAll(Audio.class);
		return list;
	}
	
	public List<Comment> getAllComment()
	{
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		List<Comment> list = mongoOperation.findAll(Comment.class);
		return list;
	}
	
	public List<Phrase> getAllPhrase()
	{
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		List<Phrase> list = mongoOperation.findAll(Phrase.class);
		return list;
	}
	
	
	@RequestMapping(value="/getAll",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getAll(HttpServletRequest request,Model model)
	{
		Result res = new Result();
		Gson gson = new Gson();
		String result ="";
		Criteria criteria = new Criteria();
		List<Video> videos = this.getAllVideo();
		List<Audio> audios = this.getAllAudio();
		List<Category> categories = this.getAllCategory();
		List<Comment> comments = this.getAllComment();	
		List<Phrase> phs = this.getAllPhrase();
		ArrayList arr = new ArrayList();
		arr.add(videos);
		arr.add(audios);
		arr.add(categories);
		arr.add(comments);
		arr.add(phs);
		HashMap<String, List> map = new HashMap<String, List>();
		map.put("video", videos);
		map.put("audio", audios);
		map.put("category", categories);
		map.put("comment", comments);
		map.put("phrase", phs);
		res.data = map;
		res.success = true ;
		result = gson.toJson(res);
		return result;
	}
	
	@RequestMapping(value="/addtransfromsqlite",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addtransfromsqlite(HttpServletRequest request,Model model)
	{
		Result res = new Result();
		Gson gson = new Gson();
		String result ="complete";
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/zhangshijie/Downloads/corpus.db");
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from parallel where (origin == '数学词典' OR origin == '物理学词典' OR origin == '现代汉藏词典' OR origin == '计算机词典') ");
			while(rs.next())
			{
				Phrase phrase = new Phrase();
				phrase.setUuid(getNextSequence("phrase"));
				phrase.setType(5);
				phrase.setContent(rs.getString("zh"));
				phrase.setTibetcontent(rs.getString("tb"));
				savePh(phrase);
				System.out.print("-------------------------------");
			}
			System.out.print("complete");
		}catch(Exception e)
		{
			
		}
		return result;
	}
	
	@RequestMapping(value="/addedicfromsqlite",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addedicfromsqlite(HttpServletRequest request,Model model)
	{
		Result res = new Result();
		Gson gson = new Gson();
		String result ="";
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/zhangshijie/Downloads/corpus.db");
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from parallel where (origin != '数学词典' AND origin != '物理学词典' AND origin != '现代汉藏词典' AND origin != '计算机词典') ");
			while(rs.next())
			{
				Phrase phrase = new Phrase();
				phrase.setUuid(getNextSequence("phrase"));
				phrase.setType(6);
				phrase.setContent(rs.getString("zh"));
				phrase.setTibetcontent(rs.getString("tb"));
				savePh(phrase);
			}
			System.out.print("-------------------------------");
		}catch(Exception e)
		{
			
		}
		return result;
	}

	@RequestMapping(value="/getTrans",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getTrans(HttpServletRequest request,Model model)
	{
		String pageStr = request.getParameter("page");
		Integer page = Integer.parseInt(pageStr);
		
		Result res = new Result();
		Gson gson = new Gson();
		String result ="";
		Query query = null;
		int skipCount = (page - 1)*countInPage;
		if (skipCount < 0)
			skipCount = 0;
		query = new Query(Criteria.where("type").is(6)).skip(skipCount).limit(countInPage);
		ArrayList arrayList = null ;
		arrayList = this.getPhrase(query);
		res.data = arrayList;
		res.success = true ;
		result = gson.toJson(res);
		return result;
	}
	
	

	@RequestMapping(value="/getEDic",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getEDic(HttpServletRequest request,Model model)
	{
		String pageStr = request.getParameter("page");
		Integer page = Integer.parseInt(pageStr);
		
		Result res = new Result();
		Gson gson = new Gson();
		String result ="";
		Query query = null;
		int skipCount = (page - 1)*countInPage;
		if (skipCount < 0)
			skipCount = 0;
		query = new Query(Criteria.where("type").is(5)).skip(skipCount).limit(countInPage);
		ArrayList arrayList = null ;
		arrayList = this.getPhrase(query);
		res.data = arrayList;
		res.success = true ;
		result = gson.toJson(res);
		return result;
	}
	
	private void saveCollection(Collection collection)
	{
		ConfigInstance.getInstance().getMongoOperation().save(collection);
	}
	
	private void saveFeedBack(FeedBack feedback)
	{
		ConfigInstance.getInstance().getMongoOperation().save(feedback);
	}
	
	@RequestMapping(value="/addCollect",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addCollect(HttpServletRequest request,Model model)
	{
		String uid = request.getParameter("uid");
		String pid = request.getParameter("pid");
		Result res = new Result();
		Gson gson = new Gson();
		Collection collection = new Collection();
		collection.setUuid(this.getNextSequence("collectphrases"));
		collection.setPid(pid);
		collection.setUid(uid);
		this.saveCollection(collection);
		String result ="";
		res.success = true ;
		result = gson.toJson(res);
		return result ;
	}
	
	
	@RequestMapping(value="/delFeedBack")
	@ResponseBody
	public String delFeedBack(HttpServletRequest request,Model model)
	{
		String id = request.getParameter("id");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		Query phraseQuery =  new Query(where("uuid").is(Integer.parseInt(id)));
		mongoOperation.remove(phraseQuery,FeedBack.class);
		Gson gson = new Gson();
		String result ="";
		Result res = new Result();
		res.setSuccess(true);
		result = gson.toJson(res);
		return result;
	}
	
	@RequestMapping(value="/delUser")
	@ResponseBody
	public String delUser(HttpServletRequest request,Model model)
	{
		String id = request.getParameter("uid");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		Query phraseQuery =  new Query(where("id").is(id));
		mongoOperation.remove(phraseQuery,User.class);
		Gson gson = new Gson();
		String result ="";
		Result res = new Result();
		res.setSuccess(true);
		result = gson.toJson(res);
		return result;
	}
	
	@RequestMapping(value="/modifyUser")
	@ResponseBody
	public String modifyUser(HttpServletRequest request,Model model)
	{
		String id = request.getParameter("uid");
		String passwd = request.getParameter("passwd");		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
		Query phraseQuery =  new Query(where("id").is(id));
		WriteResult wres =  mongoOperation.updateFirst(phraseQuery, Update.update("passwd", passwd), User.class);
		Gson gson = new Gson();
		System.out.println(wres.getError());
		String result ="";
		Result res = new Result();
		res.setSuccess(true);
		result = gson.toJson(res);
		return result;
	}
	
	
	@RequestMapping(value="/feedback",produces = "application/json;charset=UTF-8")
	@ResponseBody	
	public String feedback(HttpServletRequest request,Model model)
	{
		Gson gson = new Gson();
		String result ="";
		String content = request.getParameter("content");
		String uid = request.getParameter("uid");
		FeedBack feedback = new FeedBack();
		feedback.setUuid(this.getNextSequence("feedback"));
		feedback.setUid(uid);
		feedback.setContent(content);
		this.saveFeedBack(feedback);
		Result res = new Result();
		res.setSuccess(true);
		result = gson.toJson(res);
		return result;
	}
	
}


