package at.ac.fhcampuswien.fhmdb.models;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Movie {
    private final String title;
    private final String description;
    private final List<Genre> genres;
    private final int releaseYear;
    private final double rating;
    private List<String> mainCast;
    private String director;

    public Movie(String title, String description, List<Genre> genres, int releaseYear, double rating) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.director = director;

    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Movie other)) {
            return false;
        }
        return this.title.equals(other.title) && this.description.equals(other.description) && this.genres.equals(other.genres);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }
    public int getReleaseYear() {return releaseYear; }
    public double getRating() {return rating; }
    public List<String> getMainCast() {return mainCast; }
    public String getDirector() {return director; }



}