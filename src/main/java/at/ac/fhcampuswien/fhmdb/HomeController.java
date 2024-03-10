package at.ac.fhcampuswien.fhmdb;

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
import java.util.*;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public JFXButton deleteBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    private ObservableList<Movie> sortByGenre = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list
        movieListView.getItems().addAll(allMovies);


        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        // TODO add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");

        genreComboBox.getItems().addAll(Arrays.asList(Movie.Genre.values()));


        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            //  CREATE A STRING, WHICH WILL BE THE WORD IN THE SEARCH-FIELD
            String searchQuery = searchField.getText().toLowerCase();
            Movie.Genre selectedGenre = (Movie.Genre) genreComboBox.getValue();
            List<Movie> filteredMovies = new ArrayList<>();

            List<Movie> searchResult = searchMoviesByLetter(searchQuery);
            List<Movie> genreResult = searchByGenre(selectedGenre);


            filteredMovies.addAll(mergeMovies(searchResult, genreResult));

            observableMovies.clear();
            observableMovies.addAll(filteredMovies);

            });
        // Functionality of Sort Button
        sortBtn.setOnAction(actionEvent -> {
            sortMovies(observableMovies, sortBtn.getText());
            if (Objects.equals(sortBtn.getText(), "Sort (asc)")) {
                sortBtn.setText("Sort (desc)");
            } else {
                sortBtn.setText("Sort (asc)");
            }
        });

        /**
        Functionality of Delete Button
         */
        deleteBtn.setOnAction(actionEvent -> clearState());

    }
    public ObservableList<Movie> sortMovies(ObservableList<Movie> observableMovies, String sortBtnText) {

        if (Objects.equals(sortBtnText, "Sort (asc)")) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
        } else {
            observableMovies.sort(Comparator.comparing(Movie::getDescription).reversed());
        }
        return observableMovies;
    }

    public List<Movie> searchMoviesByLetter(String searchQuery) {

        List<Movie> filteredMovies = new ArrayList<>();

        for (Movie movie : allMovies) {
            if (movie.getTitle().toLowerCase().contains(searchQuery) || movie.getDescription().contains(searchQuery)) {
                filteredMovies.add(movie);
            }
            //observableMovies.setAll(filteredMovies);
        }
        return filteredMovies;
    }
    public List<Movie> searchByGenre(Movie.Genre selectedGenre) {

        List<Movie> filteredMovies = new ArrayList<>();

        for (Movie movie : allMovies) {
            if (movie.getGenres().contains(selectedGenre) || selectedGenre == null) {
                filteredMovies.add(movie);
            }
            observableMovies.setAll(filteredMovies);
        }
        return filteredMovies;
    }



    public List<Movie> mergeMovies(List<Movie> list1, List<Movie> list2) {

        List<Movie> mergedMovies = new ArrayList<>();

        for (Movie merge : list1) {
            if (list2.contains(merge))
                mergedMovies.add(merge);
        }
        return mergedMovies;
    }

    public void clearState() {

        searchField.setText("");
        genreComboBox.setValue(null);

        observableMovies.clear();
        observableMovies.setAll(allMovies);
    }

}