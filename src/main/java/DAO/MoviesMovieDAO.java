package DAO;

import Model.Movie;
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
import java.util.Objects;

public class MoviesMovieDAO implements MovieDAO<Movie> {

    private final MongoCollection<Document> collection;

    public MoviesMovieDAO(MongoCollection<Document> collection) {
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
    public void insert(Movie movie) {
        Document document = movie.toDocument();

        InsertOneResult result = collection.insertOne(document);
    }

    @Override
    public List<Movie> findAll() {
        List<Document> movies = collection.find().into(new ArrayList<>());

        return movies.stream()
                .map(Movie::fromDocument)
                .toList();
    }

    @Override
    public Movie findByTitle(String title) {
        Bson filter = Filters.eq("title", title);
        Document document = collection.find(filter).first();

        return Movie.fromDocument(document);
    }

    @Override
    public void updateYearByTitle(String title, int year) {
        Movie result = Movie.fromDocument(
                Objects.requireNonNull(collection.findOneAndUpdate(
                        Filters.eq("title", title),
                        Updates.set("year", year)
                ))
        );
        System.out.println("Uppdaterad film: " + result.getTitle() + " \nNytt årtal: " + year);
    }

    @Override
    public void deleteMovieByTitle(String title) {
        DeleteResult result = collection.deleteOne(
                Filters.eq("title", title)
        );
        if (result.getDeletedCount() > 0) {
            System.out.println("Raderade filmen: " + title);
        } else {
            System.out.println("Filmen fanns inte eller gick inte att ta bort.");
        }
    }
}

