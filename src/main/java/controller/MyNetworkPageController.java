package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.Database.DataBaseActions;

import org.common.*;

public class MyNetworkPageController {
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private String email;
    @FXML
    private ListView<GetConnectionReturn> connectionListView;
    @FXML
    private Button exitButton;
    @FXML
    private ListView<String> followersListView;
    @FXML
    private ListView<String> followingsListView;
    @FXML
    private Button homeButton;
    public void postInitialize (){
        Bridge bridge = new Bridge(Commands.GET_FOLLOWEE, email);
        SendMessage.send(bridge, writer);
        try {
            Bridge b = (Bridge) reader.readObject();
            if (b.getCommand() == Commands.GET_FOLLOWEE) {
                ArrayList<String> followeeArray = b.get();
                ObservableList<String> followees = FXCollections.observableArrayList();
                followees.addAll(followeeArray);
                followingsListView.setItems(followees);
                followingsListView.getSelectionModel().selectedItemProperty().
                        addListener( new ChangeListener<String>() {
                            @Override
                            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
                                //TODO: handle what's ganna happen when you click on a followee
                            }
                        });
                // set custom ListView cell factory
                followingsListView.setCellFactory(new Callback<ListView<String>, ListCell<String >>() {
                    @Override
                    public ListCell<String> call(ListView<String> listView) {
                        return new FollowingTextCell(email);
                    }
                });
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Bridge bridge2 = new Bridge(Commands.GET_FOLLOWERS, email);
        SendMessage.send(bridge2, writer);
        try {
            Bridge b = (Bridge) reader.readObject();
            if (b.getCommand() == Commands.GET_FOLLOWERS) {
                ArrayList<String> followerArray = b.get();
                ObservableList<String> followers = FXCollections.observableArrayList();
                followers.addAll(followerArray);
                followersListView.setItems(followers);
                followersListView.getSelectionModel().selectedItemProperty().
                        addListener( new ChangeListener<String>() {
                            @Override
                            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
                                //TODO: handle what's ganna happen when you click on a followee
                            }
                        });
                // set custom ListView cell factory
                followersListView.setCellFactory(new Callback<ListView<String>, ListCell<String >>() {
                    @Override
                    public ListCell<String> call(ListView<String> listView) {
                        return new FollowerTextCell(email);
                    }
                });
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Bridge bridge3 = new Bridge(Commands.GET_CONNECTION, email);
        SendMessage.send(bridge3, writer);
        try {
            Bridge b = (Bridge) reader.readObject();
            if (b.getCommand() == Commands.GET_CONNECTION) {
                ArrayList<GetConnectionReturn> connections = b.get();
                ObservableList<GetConnectionReturn> connectionObservable = FXCollections.observableArrayList();
                connectionObservable.addAll(connections);
                connectionListView.setItems(connectionObservable);
                connectionListView.getSelectionModel().selectedItemProperty().
                        addListener( new ChangeListener<GetConnectionReturn>() {
                            @Override
                            public void changed(ObservableValue<? extends GetConnectionReturn> observable, GetConnectionReturn oldValue, GetConnectionReturn newValue) {
                                //TODO: handle what's ganna happen when you click on a followee
                            }

                        });
                // set custom ListView cell factory
                connectionListView.setCellFactory(new Callback<ListView<GetConnectionReturn>, ListCell<GetConnectionReturn>>() {
                    @Override
                    public ListCell<GetConnectionReturn> call(ListView<GetConnectionReturn> param) {
                        return new ConnectionTextCell(email);
                    }
                });
            }
        } catch (IOException | ClassNotFoundException e ) {
            e.printStackTrace();
        }

    }
    @FXML
    void exitButtonPressed(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void homeButtonPressed(ActionEvent event) {
        LinkedInApplication.showHomePage(null);
    }

    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public ObjectInputStream getReader() {
        return reader;
    }
    public void setReader(ObjectInputStream reader) {
        this.reader = reader;
    }
    public ObjectOutputStream getWriter() {
        return writer;
    }
    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
