import impl.MoveToSonic;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import iface.*;
import javafx.util.Duration;
import models.ModelSonic;

import java.io.*;
import java.net.URL;
import java.util.*;

public class SonicController implements Initializable {


    private ModelSonic modelSonic = new ModelSonic();

    private MoveToSonic moveToSonic = new MoveToSonic();

    private Stage stage = new Stage();

    long deathTime = 0;

    private Date date = new Date();

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
    private ProgressBar feeling;

    @FXML
    private ProgressBar deathProgress;

    @FXML
    void h(MouseEvent event) {
        cry.setVisible(false);
        carefully.setVisible(false);
        img1.setVisible(false);
        img.setVisible(true);
        img.setX(img1.getX());

        rehab();
    }


    @FXML
    void rButtom(ActionEvent event) {
        snowPic.setVisible(false);
        rainPic.setVisible(true);

    }

    @FXML
    void withoutButtom(ActionEvent event) {
        snowPic.setVisible(false);
        rainPic.setVisible(false);
    }


    @FXML
    void sButtom(ActionEvent event) {
        rainPic.setVisible(false);
        snowPic.setVisible(true);

    }

    @FXML
    void birthButtom(MouseEvent event) {
        feelingKill();
        moveToSonic.moveToX(img1);
        birth.setDisable(true);
        birthPic.setVisible(false);
        img1.setVisible(true);
        button.setDisable(false);


    }

    @FXML
    void toMenu(MouseEvent event) throws IOException {

        modelSonic.saveFile(birth.isDisable(), feeling.getProgress(), date);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));

        feeling.getScene().getWindow().hide();
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
        loadFile();

    }


    public void rehab() {
        moveToSonic.pauseMoveToX();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (feeling.getProgress() < 1) {
                    feeling.setProgress(modelSonic.rehab(feeling.getProgress()));
                } else {
                    System.out.println("finish");
                    img1.setVisible(true);
                    img.setVisible(false);
                    img1.setX(img.getX());
                    moveToSonic.continueMoveToX();
                    timer.cancel();


                }

            }
        }, 100, 1000);
    }


    public void feelingKill() {
        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                if (feeling.getProgress() > 0.012) {
                    feeling.setProgress(feeling.getProgress() - 0.01);
                } else if (feeling.getProgress() <= 0.012) {
                    deathProgress.setVisible(true);
                    death();
                    timer2.cancel();
                    return;
                }

                changeColorProgressBar();

            }
        }, 0, 108000);

    }

    public void changeColorProgressBar() {
        if (feeling.getProgress() > 0.6) {
            feeling.setStyle("-fx-accent: green");
            carefully.setVisible(false);
            cry.setVisible(false);
            if (!img.isVisible())
                img1.setVisible(true);
            else img1.setVisible(false);
        } else if (feeling.getProgress() > 0.3 && feeling.getProgress() <= 0.6) {
            feeling.setStyle("-fx-accent: yellow");
            carefully.setX(0);
            if (!img.isVisible())
                carefully.setVisible(true);
            img1.setVisible(false);
            cry.setVisible(false);
        } else if (feeling.getProgress() <= 0.3) {
            feeling.setStyle("-fx-accent: red");
            carefully.setVisible(false);
            img1.setVisible(false);
            if (!img.isVisible())
                cry.setVisible(true);
        }
    }


    public void death() {

        Timer timer3 = new Timer();
        timer3.schedule(new TimerTask() {
            @Override
            public void run() {
                if (feeling.getProgress() > 0.07) {
                    feelingKill();
                    deathProgress.setProgress(1);
                    deathProgress.setVisible(false);
                    timer3.cancel();
                    return;
                }
                deathProgress.setProgress(deathProgress.getProgress() - 0.01);
                if (deathProgress.getProgress() < 0.011) {
                    deathPic.setVisible(true);
                    cry.setVisible(false);
                    button.setDisable(true);
                    deathProgress.setProgress(0);
                    newGame();
                    timer3.cancel();
                }
            }
        }, 0, 18000);

    }

    public void loadFile() {
        if (modelSonic.loadFile() < -60) {
            deathPic.setVisible(false);
            birth.setDisable(false);
            feeling.setStyle("-fx-accent: green");
            deathProgress.setVisible(false);
            deathProgress.setProgress(1);
            feeling.setProgress(1);
        }
        if (modelSonic.loadFile() > 0) {
            feeling.setProgress(modelSonic.getLevelFeeling());
            feelingKill();
            moveToSonic.moveToX(img1);
            birth.setDisable(true);
            button.setDisable(false);
        }

        if (modelSonic.loadFile() < 0) {
            if (modelSonic.loadFile() < -30) {
                feeling.setProgress(0);
                deathProgress.setProgress(0);
                deathPic.setVisible(true);
                //feelingKill();
                birth.setDisable(true);

            } else {
                cry.setVisible(true);
                birth.setDisable(true);
                button.setDisable(false);
                deathProgress.setVisible(true);
                deathProgress.setProgress(modelSonic.getDeathProgressBar());
                death();
            }
        }
    }


    public void newGame() {
        Timer timerNewGame = new Timer();
        timerNewGame.schedule(new TimerTask() {
            @Override
            public void run() {
                deathTime++;
                if (deathTime > 1900) {
                    loadFile();
                    System.out.println("go");
                    timerNewGame.cancel();
                }
            }
        }, 0, 1000);
    }

}



