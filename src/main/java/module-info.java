module org.main.javafx {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires jjwt.api;
    requires javafx.media;


    opens controller to javafx.fxml;
    exports controller;
}