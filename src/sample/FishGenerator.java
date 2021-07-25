
package sample;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class FishGenerator {
    ArrayList<Image> Images = new ArrayList<>();
    Timeline timeline = new Timeline();
    Random random = new Random();

    public void generateFish(AnchorPane pane, ScreenManager sn) {
        String path = new File("src\\sample\\Images")
                .getAbsolutePath();
        File folder = new File(path);

        for (File imageF : folder.listFiles()) {
            try {
                Images.add(new Image(new FileInputStream(imageF)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int rand_type = random.nextInt(7);

                if (rand_type == 6) {
                    ShotFish shotfish = new ShotFish(Images.get(rand_type));
                    shotfish.add2pane(pane);

                    sn.addFish(shotfish);
                    shotfish.move(2);
                    pane.getChildren().add(shotfish.shot.imageView);


                } else {
                    Fish normalfish;
                    normalfish = new EnemyFish(rand_type, Images.get(rand_type));


                    normalfish.move(2);

                    sn.addFish(normalfish);
                    normalfish.add2pane(pane);


                }


            }
        });
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }
}
