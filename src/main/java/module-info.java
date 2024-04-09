module at.ac.fhcampuswien.fhmdb {
    opens at.ac.fhcampuswien.fhmdb.models to com.google.gson;
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires okhttp3;
    requires com.google.gson;
    requires org.junit.jupiter.api;

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb.models;
    exports at.ac.fhcampuswien.fhmdb;
}