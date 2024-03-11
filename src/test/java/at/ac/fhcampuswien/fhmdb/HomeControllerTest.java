package at.ac.fhcampuswien.fhmdb;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Collectors;
import java.util.List;

class HomeTest {

    @Test
    public void movieHasTitle() {
        // Arrange
        String expectedTitle = "Test Movie";
        Movie movie = new Movie(expectedTitle, "This is a test movie.",
                List.of(Genre.ACTION, Genre.COMEDY));

        // Act
        String actualTitle = movie.getTitle();

        // Assert
        assertEquals(expectedTitle, actualTitle, "The movie should have the correct title.");
    }

    @Test
    public void movieHasGenre() {
        // Arrange
        List<Genre> expectedGenres = List.of(Genre.ACTION, Genre.COMEDY);
        Movie movie = new Movie("Test Movie", "This is a test movie.", expectedGenres);

        // Act
        List<Genre> actualGenres = movie.getGenres();

        // Assert
        assertEquals(expectedGenres, actualGenres, "The movie should have the correct genres.");
    }

    @Test
    public void movieHasDescription() {
        // Arrange
        String expectedDescription = "This is a test movie.";
        Movie movie = new Movie("Test Movie", expectedDescription, List.of(Genre.ACTION, Genre.COMEDY));

        // Act
        String actualDescription = movie.getDescription();

        // Assert
        assertEquals(expectedDescription, actualDescription, "The movie should have the correct description.");
    }
}