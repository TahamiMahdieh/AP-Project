package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LinkedInApplication extends Application {
    private static Scene backScene = null;
    private static String thisUserEmail = null;
    private static Socket socket = null;
    private static ObjectOutputStream writer = null;
    private static ObjectInputStream reader = null;
    private static Stage stage = null;

    @Override
    public void start(Stage stage) {
        try {
            this.stage = stage;
            socket = new Socket("127.0.0.1", 8000);
            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            System.out.println("Couldn't connect to server");
        }
        showSignInPage(null);
    }

    public static void showPostingPage(){
        PostingPageController controller = changeScene(stage, "postingPage.fxml", "LinkedIn");
        controller.setEmail(thisUserEmail);
        controller.setSocket(socket);
        controller.setWriter(writer);
        controller.setReader(reader);
    }
    public static void showProfilePage(){
        ProfileController controller = changeScene(stage, "profilePage.fxml", "LinkedIn");
        controller.setEmail(thisUserEmail);
        controller.setSocket(socket);
        controller.setWriter(writer);
        controller.setReader(reader);
        controller.postInitialization();
    }
    public static void showEditInfoPage() {
        EditInfoController controller = changeScene(stage, "editInfo.fxml", "LinkedIn");
        controller.setEmail(thisUserEmail);
//        controller.setSocket(socket);
//        controller.setWriter(writer);
//        controller.setReader(reader);
        controller.postInitialization();
    }
    public static void showEditPicturesPage() {
        EditPicturesController controller = changeScene(stage, "editPictures.fxml", "LinkedIn");
        controller.setEmail(thisUserEmail);
    }
    public static void showMyNetworkPage (){
        MyNetworkPageController controller = changeScene(stage, "myNetworkPage.fxml", "LinkedIn");
        controller.setReader(reader);
        controller.setEmail(thisUserEmail);
        controller.setSocket(socket);
        controller.setWriter(writer);
        controller.postInitialize();
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
        stage.setResizable(false);
        stage.show();
    }

    public static void showHomePage(String jwt) {
        HomePageController controller = changeScene(stage, "homePage.fxml", "LinkedIn");
        controller.setEmail(thisUserEmail);
        controller.setSocket(socket);
        controller.setJwt(jwt);
        controller.setReader(reader);
        controller.setWriter(writer);
        controller.postInitialization();
    }

    public static void showSignUpPage (Stage stage, Socket socket, ObjectOutputStream writer, String jwt){
        SignUpController controller = changeScene(stage, "signUpPage.fxml", "LinkedIn");
        controller.setSocket(socket);
        controller.setJwt(jwt);
        controller.setReader(reader);
        controller.setWriter(writer);
    }

    public static Scene getBackScene() {
        return backScene;
    }
    public static void setBackScene(Scene backScene) {
        LinkedInApplication.backScene = backScene;
    }
    public static String getThisUserEmail() {
        return thisUserEmail;
    }
    public static void setThisUserEmail(String thisUserEmail) {
        LinkedInApplication.thisUserEmail = thisUserEmail;
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