package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.client.Listener;
import org.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LinkedInApplication extends Application {
//    @Override
//    public void start(Stage stage) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(LinkedInApplication.class.getResource("login.fxml"));
//            Scene scene = new Scene(fxmlLoader.load(), 520, 400);
//            stage.setTitle("LinkedIn");
//            stage.setScene(scene);
//            stage.show();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    //    public static void main(String[] args) {
//        launch();
//    }
    private static Scene backScene = null;
    private static User thisuser = null;
    private static Socket socket = null;
    private static ObjectOutputStream writer = null;
    private static ObjectInputStream reader = null;
    private static Stage stage = null;

    @Override
    public void start(Stage stage) {
        try {
            this.stage = stage;
            socket = new Socket("127.0.0.1", 8080);
            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            System.out.println("Couldn't connect to server");
        }
        showSignInPage(null);
    }

    public static void showSignInPage(String jwt) {
        FXMLLoader fxmlLoader = new FXMLLoader(LinkedInApplication.class.getResource("login.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the fxml file");
        }
        SignInController controller = fxmlLoader.getController();
        controller.setSocket(socket);
        controller.setJwt(jwt);
        controller.setReader(reader);
        controller.setWriter(writer);
        stage.setTitle("LinkedIn");
        stage.setScene(scene);
        stage.show();
    }

    public static HomePageController showHomePage(String jwt) {
        HomePageController controller2 = changeScene(stage, "homePage.fxml", "LinkedIn");
        controller2.setSocket(socket);
        controller2.setJwt(jwt);
        controller2.setWriter(writer);
        return controller2;
    }

    public static SignUpController showSignUpPage (Stage stage, Socket socket, ObjectOutputStream writer, String jwt){
        SignUpController controller2 = changeScene(stage, "signUpPage.fxml", "LinkedIn");
        controller2.setSocket(socket);
        controller2.setJwt(jwt);
        controller2.setWriter(writer);
        return controller2;
    }

    public static Scene getBackScene() {
        return backScene;
    }
    public static void setBackScene(Scene backScene) {
        LinkedInApplication.backScene = backScene;
    }
    public static User getThisuser() {
        return thisuser;
    }
    public static void setThisUser(User thisuser) {
        LinkedInApplication.thisuser = thisuser;
    }
    public static <T> T changeScene(Stage stage, String fxmlFile, String title) {
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader(LinkedInApplication.class.getResource(fxmlFile));
        try {
            root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle(title);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fxmlLoader.getController();
    }
}