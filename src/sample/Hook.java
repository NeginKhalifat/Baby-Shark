
package sample;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Hook extends ScreenObject {

    private int x;
    private int y;
    public ImageView imageView;
    private Timer timer = new Timer();
    Line line;
    Boolean has_fish = false;


    public Hook() throws FileNotFoundException {
        super(40, 40, null, Speed.SLOW, 400, 0, 'r');//todo
        imageView = new ImageView(new Image(getClass().getResourceAsStream("\\Images\\hook2.png")));
        super.imageView.setY(0);
        super.imageView.setX(400);
        Random random = new Random();
        if (random.nextBoolean()) direction = 'r';
        else direction = 'l';

        line = new Line();
        line.setStartX(400);
        line.setStartY(0);


        int space_width = (int) getImageView().getBoundsInParent().getWidth();

        imageView.setX(400);
        imageView.setY(0);
        line.setEndX(400 + space_width / 2 + 1);
        line.setEndY(0);

        speed = Speed.FAST;
    }


    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void stop() {
        timeline.stop();
    }


    @Override
    public void move(int dx) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                startMoving();


            }
        }, 0, 10 * 1000);


    }

    public void gotoXY(double x, double y) {
        timeline.setCycleCount(10);
        KeyFrame kf = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                while (imageView.getX() != x) {
                    if (imageView.getX() > x) {
                        imageView.setX(imageView.getX() - 1);
                    } else
                        imageView.setX(imageView.getX() + 1);
                }
                while (imageView.getY() != y) {
                    if (imageView.getY() > y) {
                        imageView.setY(imageView.getY() - 1);
                    } else
                        imageView.setY(imageView.getY() + 1);
                }
            }
        });
        timeline.getKeyFrames().add(kf);
        timeline.play();

    }


    public void setY(int y) {
        this.y = y;
    }


    /********************** MOVING **********************/


    public void startMoving() {
        timeline.setCycleCount(10);
        KeyFrame kf = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (has_fish) timeline.stop();
                float random = (float) Math.random();
                int space_height = (int) getImageView().getBoundsInParent().getHeight();
                int space_width = (int) getImageView().getBoundsInParent().getWidth();

                if (random < .25) {
                    double newX = imageView.getX() + speed.getNum() * 20;
                    if (newX > 800 - space_width) {
                        newX = (int) imageView.getX() - speed.getNum() * 20;
                    }

                    imageView.setX(newX);
                    line.setEndX(newX + space_width / 2 + 1);

                } else if (random < .5) {
                    double newX = imageView.getX() - speed.getNum() * 20;
                    if (newX < space_width) {
                        newX = (int) imageView.getX() + speed.getNum() * 20;
                    }

                    imageView.setX(newX);
                    line.setEndX(newX + space_width / 2 + 1);

                } else if (random < .75) {
                    double newY = imageView.getY() + speed.getNum() * 20;
                    if (newY > 600 - space_height) {
                        newY = (int) imageView.getY() - speed.getNum() * 20;
                    }

                    imageView.setY(newY);
                    line.setEndY(newY);

                } else {
                    double newY = (imageView.getY() - speed.getNum() * 20);
                    if (newY < space_height) {
                        newY = (int) (imageView.getY() + speed.getNum() * 20);
                    }

                    imageView.setY(newY);
                    line.setEndY(newY);

                }
            }

        });
        timeline.getKeyFrames().add(kf);
        timeline.play();

    }


}


