import impl.MoveToSonic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import iface.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class SecondController extends SonicController {

    private MoveToSonic moveToSonic = new MoveToSonic();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadFile();

    }


    @Override
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

    @Override
    public void loadFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person1.txt"))) {
            feeling.setProgress((Double) ois.readDouble());
            Date date1 = new Date();
            long exitTime = ois.readLong();

            if ((feeling.getProgress() / 0.01) * 1000 - (date1.getTime() - exitTime) < -160000) {
                deathPic.setVisible(false);
                birth.setDisable(false);
                feeling.setStyle("-fx-accent: green");
                deathProgress.setVisible(false);
                deathProgress.setProgress(1);
                feeling.setProgress(1);
                return;
            }

            if (((feeling.getProgress() / 0.01) * 1000) - (date1.getTime() - exitTime) > 0) {
                feeling.setProgress((((feeling.getProgress() / 0.01) * 1000) - (date1.getTime() - exitTime)) / 1000 * 0.01);
                feelingKill();
                moveToSonic.moveToX(img1);
                birth.setDisable(true);
                button.setDisable(false);
            } else if ((feeling.getProgress() / 0.01) * 1000 - (date1.getTime() - exitTime) <= 0) {
                if ((feeling.getProgress() / 0.01) * 1000 - (date1.getTime() - exitTime) < -100000) {
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
                    deathProgress.setProgress(((100000.0 - (date1.getTime() - exitTime)) / 1000.0) * 0.01);
                    death();
                }
            }

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void newGame() {
        Timer timerNewGame = new Timer();
        timerNewGame.schedule(new TimerTask() {
            @Override
            public void run() {
                deathTime++;
                if (deathTime > 120) {
                    loadFile();
                    System.out.println("go");
                    timerNewGame.cancel();
                }
            }
        }, 0, 1000);
    }


}
