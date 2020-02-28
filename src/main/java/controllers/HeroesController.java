package controllers;

import impl.MoveToSonic;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Age;
import models.Model;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public abstract class HeroesController implements Initializable {

    private Model modelSonic;

    private MoveToSonic moveToSonic = new MoveToSonic();

    private Date date = new Date();

    private Timer timerFeelingKill = new Timer();

    private Timer timerDeath = new Timer();

    private Timer timerNewGame = new Timer();
    private Timer ageTime = new Timer();

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

    @FXML
    void feed(MouseEvent event) {
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
        modelSonic.getAge().setStartDay(date.getDate());
        modelSonic.getAge().setStartHour(date.getHours());
        modelSonic.getAge().setStartMinut(date.getMinutes());

        moveToSonic.moveToX(img1);
        birth.setDisable(true);
        birthPic.setVisible(false);
        img1.setVisible(true);
        button.setDisable(false);
        age();
        feelingKill();



    }

    @FXML
    void toMenu(MouseEvent event) throws IOException {
        timerFeelingKill.cancel();
        timerDeath.cancel();
        ageTime.cancel();
        Stage stage = (Stage) menu.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("home.fxml"));

        feeling.getScene().getWindow().hide();
        loader.load();

        Parent root = loader.getRoot();
        stage = new Stage();
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


    public void setModel(Model model) {
        this.modelSonic = model;
    }

    public Model getModel() {
        return modelSonic;
    }


    public void rehab() {
        if (deathProgress.getProgress() < 1) {
            age();
        }
        modelSonic.setDeathProgressBar(1);
        deathProgress.setProgress(1);
        moveToSonic.pauseMoveToX();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (feeling.getProgress() < 1) {
                    feeling.setProgress(modelSonic.rehab(feeling.getProgress()));
                    modelSonic.saveFile(birth.isDisable());
                } else {
                    img1.setVisible(true);
                    img.setVisible(false);
                    img1.setX(img.getX());
                    moveToSonic.continueMoveToX();
                    feeling.setProgress(1);
                    modelSonic.setLevelFeeling(1);
                    modelSonic.setDeathProgressBar(1);
                    deathProgress.setProgress(1);
                    modelSonic.saveFile(birth.isDisable());
                    timer.cancel();

                }

            }
        }, 100, 1000);
    }


    public void feelingKill() {
        timerFeelingKill.schedule(new TimerTask() {
            @Override
            public void run() {
                if (feeling.getProgress() > 0.012) {
                    feeling.setProgress(modelSonic.getLevelFeeling() - 0.01);
                    modelSonic.setLevelFeeling(feeling.getProgress());

                    modelSonic.saveFile(birth.isDisable());
                } else if (feeling.getProgress() <= 0.012) {
                    feeling.setProgress(0);
                    modelSonic.setLevelFeeling(0);
                    deathProgress.setVisible(true);
                    modelSonic.saveFile(birth.isDisable());
                    death();
                    timerFeelingKill.cancel();
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
            if (!img.isVisible()) {
                img1.setVisible(true);
            }
            else img1.setVisible(false);
        } else if (feeling.getProgress() > 0.3 && feeling.getProgress() <= 0.6) {
            feeling.setStyle("-fx-accent: yellow");
            carefully.setX(0);
            if (!img.isVisible()) {
                carefully.setVisible(true);
            }
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

    public void age() {
        ageTime.schedule(new TimerTask() {
            @Override
            public void run() {

                if (deathProgress.getProgress() < 0.07) {ageTime.cancel();}


                age.setText(String.valueOf(modelSonic.getAge().getAge()));

            }
        }, 0, 60000);

    }


    public void death() {
        timerDeath.schedule(new TimerTask() {
            @Override
            public void run() {
                if (deathProgress.getProgress() < 0.011) {
                    deathPic.setVisible(true);
                    cry.setVisible(false);
                    button.setDisable(true);
                    deathProgress.setProgress(0);
                    modelSonic.setDeathProgressBar(0);
                    newGame();
                    modelSonic.saveFile(birth.isDisable());
                    timerDeath.cancel();
                    return;
                }

                if (feeling.getProgress() > 0.07) {

                    deathProgress.setProgress(1);
                    deathProgress.setVisible(false);
                    modelSonic.saveFile(birth.isDisable());
                    moveToSonic.moveToX(img1);
                    feelingKill();
                    timerDeath.cancel();
                    return;
                }

                deathProgress.setProgress(modelSonic.getDeathProgressBar() - 0.01);
                modelSonic.setDeathProgressBar(deathProgress.getProgress());

                modelSonic.saveFile(birth.isDisable());

            }
        }, 0, 18000);

    }

    public void loadFile() {

        //if (birth.isDisable()) return;

        if (modelSonic.loadFile()){
            if (modelSonic.getTime()>0){
                feeling.setProgress(modelSonic.getLevelFeeling());
                feelingKill();
                moveToSonic.moveToX(img1);
                birth.setDisable(true);
                button.setDisable(false);
                modelSonic.saveFile(birth.isDisable());
                age();
            }
        }
        else {
            if (modelSonic.getTime()>0){
                cry.setVisible(true);
                birth.setDisable(true);
                button.setDisable(false);
                deathProgress.setVisible(true);
                deathProgress.setProgress(modelSonic.getDeathProgressBar());
                feeling.setProgress(0);
                modelSonic.setLevelFeeling(0);
                modelSonic.saveFile(birth.isDisable());
                death();
                age();
            }
            else if (modelSonic.getTime()<0 && modelSonic.getTime()>-30){
                feeling.setProgress(0);
                modelSonic.setLevelFeeling(0);
                deathProgress.setProgress(0);
                modelSonic.setDeathProgressBar(0);
                deathPic.setVisible(true);
                birth.setDisable(true);
                modelSonic.saveFile(birth.isDisable());
                newGame();

            }
            else {
                deathPic.setVisible(false);
                birth.setDisable(false);
                feeling.setStyle("-fx-accent: green");
                deathProgress.setVisible(false);
                deathProgress.setProgress(1);
                modelSonic.setLevelFeeling(1);
                modelSonic.setDeathProgressBar(1);
                feeling.setProgress(1);
                modelSonic.setAge(new Age());
                modelSonic.saveFile(birth.isDisable());
            }
        }

    }


    public void newGame() {
        timerNewGame.schedule(new TimerTask() {
            @Override
            public void run() {
                modelSonic.setTime(modelSonic.getTime()-1);
                if (modelSonic.getTime() < -31) {
                    loadFile();
                    timerNewGame.cancel();
                }
            }
        }, 60000, 60000);
    }


}
