package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {
    private String title;
    private String description;
    // TODO add more properties here

    private List<Genre> genres;


    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    /*ENUM WITH ALL GENRES TO ASSIGN TO MOVIES*/
    public enum Genre {
        ACTION,
        ADVENTURE,
        ANIMATION,
        BIOGRAPHY,
        COMEDY,
        CRIME,
        DRAMA,
        DOCUMENTARY,
        FAMILY,
        FANTASY,
        HISTORY,
        HORROR,
        MUSICAL,
        MYSTERY,
        ROMANCE,
        SCIENCE_FICTION,
        SPORT,
        THRILLER,
        WAR,
        WESTERN,
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        // TODO add some dummy data here
        movies.add(new Movie("Scream VI",
                "Following the latest Ghostface killings, the four survivors leave Woodsboro behind and start a fresh chapter.",
                Arrays.asList(Genre.HORROR, Genre.MYSTERY, Genre.THRILLER)));

        movies.add(new Movie("Missing",
                "When her mother disappears while on vacation in Colombia with her new boyfriend, June’s search for answers is hindered by international red tape.",
                Arrays.asList(Genre.MYSTERY, Genre.THRILLER, Genre.DRAMA, Genre.HORROR)));

        movies.add(new Movie("Marlowe",
                "As poor business and loneliness tolls on private detective Philip Marlowe, a beautiful blonde arrives and asks him to find her ex-lover, which proves to be just a small part in a bigger mystery.",
                Arrays.asList(Genre.MYSTERY, Genre.THRILLER, Genre.CRIME)));

        movies.add(new Movie("Bandit",
                "After escaping a Michigan prison, a charming career criminal assumes a new identity in Canada and goes on to rob a record 59 banks and jewellery stores while being hunted by a rogue task force. Based on the story of The Flying Bandit.",
                Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.THRILLER)));

        movies.add(new Movie("John Wick: Chapter 2",
                "John Wick is forced out of retirement by a former associate looking to seize control of a shadowy international assassins’ guild. Bound by a blood oath to aid him, Wick travels to Rome and does battle against some of the world’s most dangerous killers.",
                Arrays.asList(Genre.ACTION, Genre.THRILLER, Genre.CRIME)));

        movies.add(new Movie("Girl at the Window",
                "A troubled teenage girl who’s struggling to cope with the accidental death of her father suspects that the mysterious killer stalking her hometown is not only her neighbour but her mother’s new romantic interest.",
                Arrays.asList(Genre.THRILLER, Genre.HORROR)));

        movies.add(new Movie("Lord of the Streets",
                "When Jason Dyson refuses to make his prized fighter throw an MMA match, a notorious gangster collects his debt by killing the fighter and kidnapping Jason's daughter. Now he must train a prisoner to endure five consecutive underground fights to save her.",
                Arrays.asList(Genre.ACTION)));

        movies.add(new Movie("AAAAAA",
                "AAAAAAAAA",
                Arrays.asList(Genre.COMEDY)));

        return movies;
    }
}
