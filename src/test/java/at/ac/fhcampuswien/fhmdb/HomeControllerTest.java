package at.ac.fhcampuswien.fhmdb;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

class HomeControllerTest {



    @Test
    void testGetMostPopularActor() throws IOException {
        MovieAPI movieAPI = new MovieAPI();
        List<Movie> movies = movieAPI.getAllMovies();
        HomeController controller = new HomeController();
        assertEquals("Actor A", controller.getMostPopularActor(movies));
    }

    @Test
    void testGetLongestMovieTitle() throws IOException {
        MovieAPI movieAPI = new MovieAPI();
        List<Movie> movies = movieAPI.getAllMovies();
        HomeController controller = new HomeController();
        assertEquals(25, controller.getLongestMovieTitle(movies));
    }

    @Test
    void testCountMoviesFrom() throws IOException {
        MovieAPI movieAPI = new MovieAPI();
        List<Movie> movies = movieAPI.getAllMovies();
        HomeController controller = new HomeController();
        assertEquals(1, controller.countMoviesFrom(movies, "Director X"));
    }

    @Test
    void testGetMoviesBetweenYears() throws IOException {
        MovieAPI movieAPI = new MovieAPI();
        List<Movie> movies = movieAPI.getAllMovies();
        HomeController controller = new HomeController();
        assertEquals(4, controller.getMoviesBetweenYears(movies, 2000, 2005).size());
    }
}