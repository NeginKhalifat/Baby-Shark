package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class MyTimer {
    int seconds = 0;
    int min = 0;
    Timeline timeline = new Timeline();

    Label text;

    MyTimer() {
        String text_ = min + " : " + seconds;
        text = new Label(text_);
        text.setLayoutX(650);
    }

    public void stop() {
        timeline.stop();
    }

    public String getTime() {
        return min + " : " + seconds;
    }


    public void startTimer() {

        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                seconds += 1;
                if (seconds == 60) {
                    seconds = 0;
                    min += 1;
                }

                String text_ = min + " : " + seconds;
                text.setText(text_);

            }
        });
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }


}
