package DAO;

import java.util.List;

public interface MovieDAO<T> {

    void insert(String title, int year);

    void insert(T movie);

    List<T> findAll();

    void findAllFiltered();

    T findByTitle(String title);

    void updateYearByTitle(String title, int year);

    void deleteMovieByTitle(String title);

    void deleteAllMoviesByTitle(String title);

    void moviesSorted(int year, int year1);
}
