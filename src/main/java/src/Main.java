package src;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class Main {

    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("vehicles"); //db
        MongoCollection mongoCollection = mongoDatabase.getCollection("cars");  //collection - table

     //   save(mongoCollection);
//        read(mongoCollection);
//        readByParam(mongoCollection, "Mark", "Audi");
//        readAllByParam(mongoCollection, "Mark", "Mercedes");
//        delete(mongoCollection, "Mark", "Audi");
//        deleteAllByParam(mongoCollection, "Mark", "Mercedes");
        update(mongoCollection, "Mark", "Audi", "Model", "a8");
    }

    private static void update(MongoCollection mongoCollection, String param1, Object value2, String param2, Object newValue2) {

        Bson find = Filters.eq(param1, value2);
        Bson update = combine(set(param2, newValue2));

        mongoCollection.updateOne(find, update);
    }

    private static void deleteAllByParam(MongoCollection mongoCollection, String param, Object value) {
        Document document = new Document();
        document.put(param, value);
        mongoCollection.deleteMany(document);
    }

    private static void delete(MongoCollection mongoCollection, String param, Object value) {
        Document document = new Document();
        document.put(param, value);
        mongoCollection.deleteOne(document);
    }

    private static void readByParam(MongoCollection mongoCollection, String param, Object value) {
        Document document = new Document();
        document.put(param, value);
        Document first = (Document) mongoCollection.find(document).first();
        System.out.println(first.toJson());

    }

    private static void readAllByParam(MongoCollection mongoCollection, String param, Object value) {
        Document document = new Document();
        document.put(param, value);
        MongoCursor iterator = mongoCollection.find(document).iterator();
        while (iterator.hasNext()){
            Document next =(Document) iterator.next();
            System.out.println(next.toJson());
        }


    }

    private static void read(MongoCollection mongoCollection) {

        Document first = (Document) mongoCollection.find().first();
        System.out.println(first.toJson());
    }

    private static void save(MongoCollection mongoCollection) {
        Document document = new Document();
        document.put("Mark", "Mercedes");
        document.put("Model", "C");

        Document document1 = new Document();
        document1.put("Mark", "Mercedes");
        document1.put("Model", "S");

        Document document2 = new Document();
        document2.put("Mark", "Audi");
        document2.put("Model", "a6");
        document2.put("Power", "178");
        document2.put("Color", Arrays.asList("red", "black", "gray"));

        mongoCollection.insertMany(Arrays.asList(document, document1, document2));
    }
}
