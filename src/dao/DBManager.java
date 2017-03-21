package dao;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import main.Utils;
import model.BaseModel;

public class DBManager {

	public static MongoCollection<Document> getDBCollection(String mongodb, int dbPort, String collectionName) {
		try {
			MongoClient mongoClient = new MongoClient(mongodb, dbPort);
			MongoDatabase mongoDatabase = mongoClient.getDatabase("monitor");
			MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
			return collection;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return null;
	}

	public static void insert(MongoCollection<Document> collection, BaseModel baseModel) {
		Document document = new Document();
		String[] filedName = Utils.getFiledName(baseModel);
		for(int i = 0; i < filedName.length; i++) {
			String attribute = filedName[i];
			Object value = Utils.getFieldValueByName(attribute, baseModel);
			document.append(attribute, value);
		}
		List<Document> documents = new ArrayList<Document>();
		documents.add(document);
		collection.insertMany(documents);
	}
	
}