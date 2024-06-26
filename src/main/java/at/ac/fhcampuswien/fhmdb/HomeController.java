package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
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
    private TextField yearTextField;

    @FXML
    private ComboBox ratingComboBox;

    @FXML
    public JFXListView<Movie> movieListView;

    @FXML
    public JFXComboBox<Genre> genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies;
    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
    public SortedState sortedState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeState();
        initializeLayout();
        addListeners();
    }

    public void Delete(ActionEvent actionEvent) {
        clearState();
    }

    public void clearState() {
        searchField.setText("");
        genreComboBox.setValue(null);
        yearTextField.setText("");
        ratingComboBox.setValue(null);

        observableMovies.clear();
        observableMovies.addAll(allMovies);
        sortMovies(sortedState);
    }

    public void initializeState() {
        sortedState = SortedState.NONE;
        try {
            MovieAPI movieAPI = new MovieAPI();
            allMovies = movieAPI.getAllMovies();
            observableMovies.addAll(allMovies);
            sortMovies(sortedState);
        } catch (IOException e) {
            System.err.println("Fehler beim Laden der Filme: " + e.getMessage());
        }
    }

    private void initializeLayout() {
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());
        ratingComboBox.setPromptText("Filter by Rating");
        String[] rating = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        ratingComboBox.getItems().add("No Rating");
        ratingComboBox.getItems().addAll(rating);
        yearTextField.setPromptText("Filter by Release Year");
    }

    private void addListeners() {
        yearTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                yearTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void searchBtnClicked(ActionEvent actionEvent) {
        String searchQuery = searchField.getText().toLowerCase();
        Genre selectedGenre = genreComboBox.getValue();
        Object selectedRating = ratingComboBox.getValue();
        int selectedYear = yearTextField.getText().isEmpty() ? 0 : Integer.parseInt(yearTextField.getText());

        List<Movie> filteredMovies = allMovies.stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(searchQuery)
                        || movie.getDescription().toLowerCase().contains(searchQuery))
                .filter(movie -> selectedGenre == null || movie.getGenres().contains(selectedGenre))
                .filter(movie -> yearTextField.getText().isEmpty() || (selectedYear != 0 && movie.getReleaseYear() == selectedYear))
                .filter(movie -> selectedRating == null ||
                        ((selectedRating instanceof String && ((String) selectedRating).equals("No Rating")) ||
                                (selectedRating instanceof String && Double.parseDouble((String) selectedRating) <= movie.getRating())))
                .collect(Collectors.toList());

        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
        sortMovies(sortedState);
    }

    public void sortBtnClicked(ActionEvent actionEvent) {
        sortMovies();
    }

    public void sortMovies() {
        if (sortedState == SortedState.NONE || sortedState == SortedState.ASCENDING) {
            sortMovies(SortedState.DESCENDING);
        } else if (sortedState == SortedState.DESCENDING) {
            sortMovies(SortedState.ASCENDING);
        }
    }

    private void sortMovies(SortedState sortDirection) {
        if (sortDirection == SortedState.DESCENDING) {
            observableMovies.sort((movie1, movie2) -> movie2.getTitle().compareToIgnoreCase(movie1.getTitle()));
            sortedState = SortedState.DESCENDING;
        } else {
            observableMovies.sort((movie1, movie2) -> movie1.getTitle().compareToIgnoreCase(movie2.getTitle()));
            sortedState = SortedState.ASCENDING;
        }
    }

    public String getMostPopularActor(List<Movie> movies) {
        return movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream()
                .mapToInt(movie -> movie.getTitle().length())
                .max()
                .orElse(0);
    }

    public long countMoviesFrom(List<Movie> movies, String director) {
        if (movies == null) {
            return 0;
        }
        return movies.stream()
                .filter(movie -> {
                    String movieDirector = movie.getDirector();
                    return movieDirector != null && movieDirector.equals(director);
                })
                .count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }
    public List<Movie> filterByQuery(List<Movie> movies, String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return movies;
        }
        if(movies == null){
            throw new IllegalArgumentException("not null");
        }

        String lowercaseSearchQuery = searchQuery.toLowerCase();

        return movies.stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(lowercaseSearchQuery) ||
                        movie.getDescription().toLowerCase().contains(lowercaseSearchQuery))
                .collect(Collectors.toList());
    }
    public List<Movie> filterByGenre(List<Movie> movies, Genre genre) {
        if (genre == null) {
            return movies;
        }

        return movies.stream()
                .filter(movie -> movie.getGenres().contains(genre))
                .collect(Collectors.toList());
    }
}