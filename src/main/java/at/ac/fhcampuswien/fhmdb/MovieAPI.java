package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class MovieAPI {
    private static final String BASE_URL = "https://prog2.fh-campuswien.ac.at/movies";
    private static final String USER_AGENT = "http.agent";

    private final OkHttpClient httpClient;
    private final Gson gson;

    public MovieAPI() {
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
    }

    public static List<Movie> jsonToMovies(String json) {
        Gson gson = new Gson();
        TypeToken<List<Movie>> collectionType = new TypeToken<>() {
        };

        return gson.fromJson(json, collectionType.getType());
    }

    public String requestApi(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", USER_AGENT)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
            return response.body().string();
        }
    }

    public List<Movie> getAllMovies() throws IOException {
        String json = requestApi(BASE_URL);
        return jsonToMovies(json);
    }


}