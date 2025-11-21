import DAO.MoviesMovieDAO;
import Model.Genre;
import Model.Movie;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        String uri =
                "mongodb+srv://christofferbirkegard_db_user:gxC9NRAErP9xXnth@cluster0.71hgrbl.mongodb.net/?appName=Cluster0";
        String databaseName = "Student";
        String collectionName = "Movies";

        try (MongoClient client = MongoClients.create(uri)) {
            //Härinne har vi en anslutning till atlas db (Klustret)
            MongoDatabase database = client.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);
            System.out.println("Ansluten till databasen.");

            MoviesMovieDAO dao = new MoviesMovieDAO(collection);
            List<Genre> genreList = new ArrayList<>();
            Genre action = new Genre("Action", "Krigsfilm");
            Genre comedy = new Genre("Komedi", "Rolig film");
            Genre drama = new Genre("Drama", "Kärlek");
            Genre thriller = new Genre("Thriller", "Spännande");
            genreList.add(action);
            genreList.add(comedy);
            genreList.add(drama);
            genreList.add(thriller);

            Movie lordOfTheRingsTwoTowers = new Movie("Sagan om två tornen", 2003, genreList);
            Movie lordOfTheRingsReturnOfTheKing = new Movie("Sagan om Konungens Återkomst", 2005,
                    Collections.singletonList(genreList.set(0, action)));

            dao.insert("Sagan om ringen", 2001);
            //dao.deleteAllMoviesByTitle("Sagan om Konungens Återkomst");
            dao.insert(lordOfTheRingsReturnOfTheKing);
            //System.out.println(dao.findAll());
            dao.updateYearByTitle("Sagan om ringen", 2002);
            dao.findAllFiltered();
            dao.moviesSorted(2000, 2020);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
