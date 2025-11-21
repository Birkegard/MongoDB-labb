package DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MovieDaoMongo implements MovieDAO<Document> {

    private final MongoCollection<Document> collection;

    public MovieDaoMongo(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    @Override
    public void insert(String title, int year) {
        collection.deleteOne(Filters.eq("title", title));

        Document doc = new Document();
        doc.append("title", title);
        doc.append("year", year);

        InsertOneResult result = collection.insertOne(doc);

        if (result.wasAcknowledged()) {
            BsonValue insertedId = result.getInsertedId();
            System.out.println("Insatt dokument med ID: " + insertedId);
        } else {
            System.out.println("Insättning misslyckades!");
        }

    }

    @Override
    public void insert(Document movie) {

    }

    @Override
    public List<Document> findAll() {
        List<Document> documents = collection.find().into(new ArrayList<>());
        System.out.println("Alla filmer: ");
        if (documents.isEmpty()) {
            System.out.println("Inga filmer fanns");
        } else {
            for (Document doc1 : documents) {
                System.out.println(doc1.toJson());
            }
        }
        return documents;
    }

    @Override
    public void findAllFiltered() {

    }

    @Override
    public Document findByTitle(String title) {
        System.out.println("Söker efter filmen...");
        Bson filter = Filters.eq("title", title);
        Document foundMovie = collection.find(filter).first();

        System.out.println(filter);

        return foundMovie;
    }

    @Override
    public void updateYearByTitle(String title, int year) {
        Bson filter = Filters.eq("title", title);
        Bson update = Updates.set("year", year);

        Document result = collection.findOneAndUpdate(filter, update);

        if (result != null) {
            System.out.println("Uppdaterad film med nytt årtal: " + result.toJson());
        } else {
            System.out.println("Uppdateringen bekräftades inte av servern.");
        }
    }

    @Override
    public void deleteMovieByTitle(String title) {
        Bson filter = Filters.eq("title", title);
        DeleteResult result = collection.deleteOne(filter);

        if (result.getDeletedCount() > 0) {
            System.out.println("Raderade filmen: " + title);
        } else {
            System.out.println("Filmen fanns inte eller gick inte att ta bort.");
        }
    }

    @Override
    public void deleteAllMoviesByTitle(String title) {

    }

    @Override
    public void moviesSorted(int year, int year1) {

    }
}
