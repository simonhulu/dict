import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.company.dict.SpringMongoConfig;
import com.company.dict.model.Category;
import com.company.dict.model.Imgs;
import com.company.dict.model.SequenceId;
//import com.mongodb.DB;
//import com.mongodb.Mongo;
//import com.mongodb.MongoClient;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;



import com.mifmif.common.regex.Generex;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

import org.apache.poi.ss.usermodel.*;
//import org.bson.Document;
public class Main {
//	 @Test   
//	    public static void testMongodb(MongoCollection collection) throws EncryptedDocumentException, InvalidFormatException, IOException  
//	    {  
//		 	InputStream inp = new FileInputStream("/Users/zhangshijie/Downloads/dictr/v.xlsx");
//		 	Workbook wb = WorkbookFactory.create(inp);
//		 	Sheet sheet = (Sheet) wb.getSheetAt(0);
//		 	ArrayList hList = new ArrayList();
//		 	ArrayList zList = new ArrayList();
//		 	ArrayList caList = new ArrayList();		 	
//		 	for(Row row:sheet)
//		 	{
//		 		
//		 		Cell hCell =  row.getCell(1);
//		 		Cell cell = row.getCell(2);
//		 		Cell caCell = row.getCell(3);
//		 		hList.add(hCell.toString());
//		 		zList.add(cell.toString());
//		 		caList.add(caCell.toString());
//		 	}
//		 	
////		 	HashSet h  =   new  HashSet(hList); 
////		 	hList.clear(); 
////		 	hList.addAll(h);
////		 	HashSet z  =   new  HashSet(zList); 
////		 	zList.clear(); 
////		 	zList.addAll(z);
//
//
//		 	for(int i = 0 ;i<hList.size();i++)
//		 	{
//		 		String hName = hList.get(i).toString();
//		 		String zName = zList.get(i).toString();
////		 		 Document document = new Document("uuid", getNextSequence("audio")).
////				 append("audiopath", hName).
////				 append("tibetaudiopath", zName);
////		 		 Document document = new Document("uuid", getNextSequence("category")).
////		 				 append("name", hName).
////		 				 append("tibet", zName);
//		 		 Document document = new Document("uuid", getNextSequence("phrase")).
//		 				 append("content", hName).  
//		 		         append("tibetcontent", zName).  
//		 		         append("category", getCategory(caList.get(i).toString())).
//		 		         append("audio", i+1).
//		 		         append("video",-1).
//		 		         append("comment", "").
//		 		         append("type", 1);  
//		 		         List<Document> documents = new ArrayList<Document>();  
//		 		         documents.add(document);  
//		 		         collection.insertMany(documents);  
//		 		        
//		 	}
//		 	 System.out.println("文档插入成功");  
//		 	inp.close();
//	    }
//	 public static int getCategory(String cate)
//	 {
//		 int a= -1;
//		 try{
//	         // 连接到 mongodb 服务
//	         MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
//	         
//	         // 连接到数据库
//	         MongoDatabase mongoDatabase = mongoClient.getDatabase("dict");  
//	         System.out.println("Connect to database successfully");
//	         
//	         MongoCollection<Document> collection = mongoDatabase.getCollection("category");
//	         FindIterable<Document> iterable =   collection.find(new Document().append("name", cate));
//	        a = (Integer) iterable.first().get("uuid");
//		 }catch(Exception e)
//		 {
//			 
//		 }
//return a;
//	 }
//	 public static int getNextSequence(String name)
//	 {
//		 ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
//		MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
//		 Query query = new Query(Criteria.where("_id").is(name));
//
//	        Update update = new Update();
//		update.inc("seq", 1);
//
//		FindAndModifyOptions options = new FindAndModifyOptions();
//		options.returnNew(true);
//
//		SequenceId seqId = 
//	            mongoOperation.findAndModify(query, update, options, SequenceId.class);
//
//		return seqId.getSeq();
//
//	 }
	 public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		 
		 try{
			 Generex generex = new Generex("M[z-a][Z-A][z-a9-0][Z-A][Z-Az-a][Z-A][z-a9-0][Z-A][Z-Az-a][A-Zz-a][z-a9-0][Z-A][Z-Az-a]==");
			 List<String> matchedStrs = generex.getAllMatchedStrings();
	         // 连接到 mongodb 服务
//	         MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	         
//	         // 连接到数据库
//	         MongoDatabase mongoDatabase = mongoClient.getDatabase("dict");  
//	         System.out.println("Connect to database successfully");
//	         
//	         MongoCollection<Document> collection = mongoDatabase.getCollection("phrase");
//	         System.out.println("集合 category 选择成功");
//	         DB db = mongoClient.getDB( "dict" );
//	         DBCollection coll = db.getCollection("audio");
//	         mongoClient.setWriteConcern(WriteConcern.JOURNALED);
//	         DBCursor cursor = coll.find();
//	         int i = 0;
//	         try {
//	        	   while(cursor.hasNext()) {
//	        		   i++;
//	        		   DBObject obj = cursor.next();
//	        		   String audioPa = (String) obj.get("audiopath");
//	        		   String ti = (String) obj.get("tibetaudiopath");
//	        		   BasicDBObject nn = new BasicDBObject();
//	        		   nn.put("audiopath", audioPa);
//	        		   nn.put("tibetaudiopath", ti);
//	        		   nn.put("uuid", i);
//	        		   coll.update(obj, nn);
//	        	   }
//	        	} finally {
//	        	   cursor.close();
//	        	}

	         //插入文档  
	         /** 
	         * 1. 创建文档 org.bson.Document 参数为key-value的格式 
	         * 2. 创建文档集合List<Document> 
	         * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document) 
	         * */
//	         Document document = new Document("title", "MongoDB").  
//	         append("description", "database").  
//	         append("likes", 100).  
//	         append("by", "Fly");  
//	         List<Document> documents = new ArrayList<Document>();  
//	         documents.add(document);  
//	         collection.insertMany(documents);  
//	         testMongodb(collection);
	      }catch(Exception e){
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      }
	}
}


