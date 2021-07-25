package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Bullet extends ScreenObject {
    private Boolean visible = true;

    Random random = new Random();
    boolean up;
    int dx;

    Bullet(FishPlayer fishPlayer) throws FileNotFoundException {
        super(10, 10, null, Speed.FAST, (int) fishPlayer.getImageView().getX(), (int) fishPlayer.getImageView().getY(), 'r');

        imageView = new ImageView(new Image(getClass().getResourceAsStream("\\Images\\bullet1.png")));
        imageView.setFitWidth(10);
        imageView.setFitHeight(10);
        speed = Speed.FAST;
        imageView.setY(fishPlayer.getImageView().getY());
        imageView.setX(fishPlayer.getImageView().getX());


    }

    Bullet(char direction1, ImageView fish_image) {
        super(10, 10, null, Speed.FAST, 0, 0, 'r');

        imageView = new ImageView(new Image(getClass().getResourceAsStream("\\Images\\bullet1.png")));
        imageView.setFitWidth(10);
        imageView.setFitHeight(10);
        speed = Speed.FAST;
        direction = direction1;


        dx = 1 + random.nextInt(5);


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (direction == 'r') {
                    imageView.setX(fish_image.getX() + 1);
                } else {
                    imageView.setX(fish_image.getX() - 1);

                }
                imageView.setY(fish_image.getY());
                up = random.nextBoolean();
                move(dx);

            }
        }, 0, 5 * 1000);

    }


    @Override
    public void move(int dx) {
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Duration.millis(310 - this.speed.getNum() * 100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (direction == 'r') {

                    imageView.setX(imageView.getX() + dx);

                } else {

                    imageView.setX(imageView.getX() - dx);
                }
                if (up) {
                    imageView.setY(imageView.getY() + 1);
                } else {
                    imageView.setY(imageView.getY() - 1);
                }
                if (imageView.getX() > 700 || imageView.getY() < 0) {
                    visible = false;
                }
            }

        });
        timeline.getKeyFrames().add(kf);
        timeline.play();


    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    @Override
    public void stop() {
        timeline.stop();
    }

}
