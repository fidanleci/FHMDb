package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;
    @FXML
    public JFXButton deleteBtn;
    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    public final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    public HomeController(List<Movie> allMovies) {

    }

    public HomeController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll("ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
                "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY", "HORROR",
                "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION", "SPORT", "THRILLER", "WAR",
                "WESTERN");

        searchBtn.setOnAction(actionEvent -> applyFilters());
        deleteBtn.setOnAction(actionEvent -> Delete());

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                FXCollections.sort(observableMovies, Comparator.comparing(Movie::getTitle));
                // TODO sort observableMovies ascending
                sortBtn.setText("Sort (desc)");
            } else {
                FXCollections.sort(observableMovies, (movie1, movie2) -> movie2.getTitle().compareTo(movie1.getTitle()));
                // TODO sort observableMovies descending
                sortBtn.setText("Sort (asc)");
            }
        });
    }

    public void applyFilters() {
        String query = searchField.getText().toLowerCase();
        String selectedGenre = genreComboBox.getValue() != null ? genreComboBox.getValue().toString() : "";

        List<Movie> filteredMovies = allMovies.stream()
                .filter(movie -> isTitleOrDescriptionContainsQuery(movie, query))
                .filter(movie -> isGenreMatches(movie, selectedGenre))
                .collect(Collectors.toList());
        Delete();
        movieListView.setItems(FXCollections.observableArrayList(filteredMovies));
        movieListView.setCellFactory(movieListView -> new MovieCell());
        System.out.println("Query: " + query);
        System.out.println("Selected Genre: " + selectedGenre);
    }

    private boolean isTitleOrDescriptionContainsQuery(Movie movie, String query) {
        return movie.getTitle().toLowerCase().contains(query) ||
                (movie.getDescription() != null && movie.getDescription().toLowerCase().contains(query));
    }

    public void Delete() {
        genreComboBox.setValue(null);
        movieListView.setItems(FXCollections.observableArrayList(allMovies));
        movieListView.setCellFactory(movieListView -> new MovieCell());
    }




    private boolean isGenreMatches(Movie movie, String selectedGenre) {
        if (selectedGenre == null || selectedGenre.isEmpty()) {
            return true; // Wenn kein Genre ausgew채hlt wurde, wird der Filter 체bersprungen

        }

        try {
            Genre genre = Genre.valueOf(selectedGenre.toUpperCase());
            return movie.getGenres().contains(genre);
        } catch (IllegalArgumentException e) {
            // Wenn der Genre-Wert kein g체ltiger Enum-Wert ist, wird der Filter 체bersprungen
            return false;
        }
    }
}