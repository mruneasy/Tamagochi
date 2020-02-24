package impl;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


import java.util.Timer;
import java.util.TimerTask;
import iface.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MoveToSonic implements MoveToo{

    private Timeline timeline = new Timeline();
    @FXML
    private javafx.scene.image.ImageView img1;

    public MoveToSonic(){

    }

   // @Override
    public void moveToX(ImageView imageView) {

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000),imageView);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();

        imageView.xProperty().set(160);
        KeyValue valueX = new KeyValue(imageView.xProperty(),-160);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(6000),valueX);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().addAll(keyFrame);
        timeline.play();

    }

    public void pauseMoveToX(){
        timeline.pause();
    }

    public void continueMoveToX(){
        timeline.play();
    }

}
