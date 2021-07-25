package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.Random;

public class ShellGenerator {
    Timeline timeline = new Timeline();

    public void generateShell(AnchorPane pane, ScreenManager sn) {

        timeline.setCycleCount(Timeline.INDEFINITE);
        Random random = new Random();
        KeyFrame kf = new KeyFrame(Duration.millis(10000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //create shell:
                int y = random.nextInt(500) + 1;
                int x = random.nextInt(700) + 1;
                Shell shell_1 = null;
                try {
                    shell_1 = new Shell(x, y);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                pane.getChildren().add(shell_1.imageView);
                sn.addShell(shell_1);
                int y1 = random.nextInt(500) + 1;
                int x1 = random.nextInt(700) + 1;
                Shell shell_2 = null;
                try {
                    shell_2 = new Shell(x1, y1);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                sn.addShell(shell_2);
                assert shell_2 != null;
                pane.getChildren().add(shell_2.imageView);


            }
        });
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }
}

