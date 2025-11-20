import DAO.MovieDAO;
import DAO.MovieDaoMongo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Main {
    public static void main(String[] args) {
        String uri =
                "mongodb+srv://christofferbirkegard_db_user:gxC9NRAErP9xXnth@cluster0.71hgrbl.mongodb.net/?appName=Cluster0";
        //Anslutning till databasen
        try (MongoClient client = MongoClients.create(uri)) {
            MongoDatabase database = client.getDatabase("Student");
            MongoCollection<Document> collection = database.getCollection("Movies");
            System.out.println("Ansluten till databasen.");

            MovieDAO movieDAO = new MovieDaoMongo(collection);

            movieDAO.insert("Frankenstein", 2025);
            movieDAO.findAll();
            movieDAO.findByTitle("Miracle");
            movieDAO.updateYearByTitle("Miracle", 1987);
            movieDAO.updateYearByTitle("Pulp fiction", 1996);
            movieDAO.deleteMovieByTitle("Frankenstein");
            movieDAO.findAll();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
