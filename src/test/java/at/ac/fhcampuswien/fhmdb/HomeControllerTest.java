package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    private HomeController homeController;

    @BeforeEach
    void setUp() {
        homeController = new HomeController();
        homeController.initializeState();
    }

    @Test
    void testGetMostPopularActor() throws IOException {
        MovieAPI movieAPI = new MovieAPI();
        List<Movie> movies = movieAPI.getAllMovies();
        assertEquals("Leonardo DiCaprio", homeController.getMostPopularActor(movies));
    }

    @Test
    void testGetLongestMovieTitle() throws IOException {
        MovieAPI movieAPI = new MovieAPI();
        List<Movie> movies = movieAPI.getAllMovies();
        HomeController controller = new HomeController();

        Optional<String> longestTitle = movies.stream()
                .map(Movie::getTitle)
                .max(Comparator.comparingInt(String::length));

        assertTrue(longestTitle.isPresent());
        assertEquals(longestTitle.get().length(), controller.getLongestMovieTitle(movies));
    }

    @Test
    void testCountMoviesFrom() throws IOException {
        MovieAPI movieAPI = new MovieAPI();
        List<Movie> movies = movieAPI.getAllMovies();
        assertEquals(0, homeController.countMoviesFrom(movies, "Steven Spielberg"));
    }

    @Test
    void testGetMoviesBetweenYears() throws IOException {
        MovieAPI movieAPI = new MovieAPI();
        List<Movie> movies = movieAPI.getAllMovies();
        assertEquals(4, homeController.getMoviesBetweenYears(movies, 2000, 2005).size());
    }


    @Test
    void testSortAppliedInAscendingOrderIfNotYetSorted() {
        homeController.sortedState = SortedState.NONE;
        homeController.sortMovies();
        assertFalse(Arrays.equals(homeController.allMovies.toArray(), homeController.observableMovies.toArray()));
    }

    @Test
    void testNextSortIsDescendingIfLastSortWasAscending() {
        homeController.sortedState = SortedState.ASCENDING;
        homeController.sortMovies();
        assertEquals(SortedState.DESCENDING, homeController.sortedState);
    }

    @Test
    void testNextSortIsAscendingIfLastSortWasDescending() {
        homeController.sortedState = SortedState.DESCENDING;
        homeController.sortMovies();
        assertEquals(SortedState.ASCENDING, homeController.sortedState);
    }

    @Test
    void testQueryFilterMatchesWithLowerAndUpperCaseLetters() {
        List<Movie> actual = homeController.filterByQuery(homeController.observableMovies, "IfE");
        assertEquals(6, actual.size());
    }

    @Test
    void testQueryFilterWithNullMovieListThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> homeController.filterByQuery(null, "IfE"));
    }

    @Test
    void testQueryFilterWithNullValueReturnsUnfilteredList() {
        List<Movie> actual = homeController.filterByQuery(homeController.observableMovies, null);
        assertEquals(homeController.observableMovies, actual);
    }

    @Test
    void testGenreFilterWithNullValueReturnsUnfilteredList() {
        List<Movie> actual = homeController.filterByGenre(homeController.observableMovies, null);
        assertEquals(homeController.observableMovies, actual);
    }

    @Test
    void testGenreFilterReturnsAllMoviesContainingGivenGenre() {
        List<Movie> actual = homeController.filterByGenre(homeController.observableMovies, Genre.DRAMA);
        assertEquals(22, actual.size());
    }

}
