package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.common.Bridge;

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
            socket = new Socket("127.0.0.1", 8080);
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
    public static void showHashtagPage(String hashtagWord) {
        HashtagPageController controller = changeScene(stage, "hashtagPage.fxml", "LinkedIn");
        controller.setEmail(thisUserEmail);
        controller.setSocket(socket);
        controller.setReader(reader);
        controller.setWriter(writer);
        controller.postInitialization(hashtagWord);
    }
    public static void showSearchPostPage(String word) {
        SearchPostsPageController controller = changeScene(stage, "searchPostsPage.fxml", "LinkedIn");
        controller.setEmail(thisUserEmail);
        controller.setSocket(socket);
        controller.setReader(reader);
        controller.setWriter(writer);
        controller.postInitialization(word);
    }
    public static void shoCommentsListPage(String post_id, Bridge bridge){
        CommentsListController controller = changeScene(stage, "commentsListPage.fxml", "LinkedIn");
        controller.setEmail(thisUserEmail);
        controller.setSocket(socket);
        controller.setWriter(writer);
        controller.setReader(reader);
        controller.setPostId(post_id);
        controller.postInitialize(bridge);
    }
    public static void showCommentPage (String post_id){
        AddCommentController controller = changeScene(stage, "addCommentPage.fxml", "LinkedIn");
        controller.setEmail(thisUserEmail);
        controller.setSocket(socket);
        controller.setWriter(writer);
        controller.setReader(reader);
        controller.setPostId(post_id);
    }
    public static void showProfilePage(){
        ProfileController controller = changeScene(stage, "profilePage.fxml", "LinkedIn");
        controller.setEmail(thisUserEmail);
        controller.setSocket(socket);
        controller.setWriter(writer);
        controller.setReader(reader);
        controller.postInitialization();
    }
    public static void showOthersProfilePage(String othersEmail) {
        OthersProfilePageController controller = changeScene(stage, "othersProfilePage.fxml", "LinkedIn");
        controller.setThisUsersEmail(thisUserEmail);
        controller.setOtherUsersEmail(othersEmail);
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
    public static void showAddEducationPage() {
        AddNewEducationController controller = changeScene(stage, "addNewEducation.fxml", "LinkedIn");
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
    public static void showEditContactInfoPage() {
        EditContactInfoController controller = changeScene(stage, "editContactInfo.fxml", "LinkedIn");
        controller.setEmail(thisUserEmail);
        controller.postInitialization();
    }

    public static void showContactInfoPage(String email) {
        ContactInfoPageController controller = changeScene(stage, "contactInfoPage.fxml", "LinkedIn");
        controller.setReader(reader);
        controller.setOthersEmail(email);
        controller.setThisUsersEmail(thisUserEmail);
        controller.setSocket(socket);
        controller.setWriter(writer);
        if (email.equals(thisUserEmail)) {
            controller.setThisUser(true);
        }
        controller.postInitialization();
    }

    public static void showLikeListPage (Bridge bridge){
        LikeListPageController controller = changeScene(stage, "likeListPage.fxml", "LinkedIn");
        controller.setEmail(thisUserEmail);
        controller.setWriter(writer);
        controller.setReader(reader);
        controller.setSocket(socket);
        controller.postInitialize(bridge);
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