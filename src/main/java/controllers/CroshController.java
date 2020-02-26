package controllers;

import impl.MoveToSonic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.Model;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class CroshController extends SonicController {

    private MoveToSonic moveToSonic = new MoveToSonic();

    private Model modelSonic = new Model("croshSave.txt");

    @FXML
    private AnchorPane pane;

    @FXML
    private Button button;

    @FXML
    private Button birth;

    @FXML
    private Button menu;

    @FXML
    private ImageView img;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView carefully;

    @FXML
    private ImageView cry;

    @FXML
    private ImageView rainPic;

    @FXML
    private ImageView deathPic;

    @FXML
    private ImageView snowPic;

    @FXML
    private ImageView birthPic;

    @FXML
    private Text age;

    @FXML
    private ProgressBar feeling;

    @FXML
    private ProgressBar deathProgress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setModel(modelSonic);
        loadFile();

    }
}
