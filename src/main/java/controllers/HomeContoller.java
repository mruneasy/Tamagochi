package controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Model;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeContoller implements Initializable {

    @FXML
    private ImageView sonic;

    @FXML
    private ImageView secondHero;


    @FXML
    void selectSonic(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("sonic.fxml"));
        sonic.getScene().getWindow().hide();
        loader.load();
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Tamagochi");
        stage.show();
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event)
            {

                System.exit(-1);
            }
        });


    }

    @FXML
    void selectSecond(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("crosh.fxml"));


        secondHero.getScene().getWindow().hide();
        loader.load();


        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Tamagochi");
        stage.show();
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(-1);
            }
        });
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
