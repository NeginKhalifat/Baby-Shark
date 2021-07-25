package sample;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

import static javafx.scene.paint.Color.BLUE;

public class FishPlayer extends Fish {
    private int numOfEatFishes;
    Bullet bullet;
    Boolean has_Shield = false;
    Boolean hasBullet = false;
    Circle shield;
    int lives = 3;



    public FishPlayer() {
        super(50, 50, null, Speed.SLOW, 300, 200, 'r');
        fishType = FishType.PLAYER;

        setImageView(new ImageView(new Image(getClass().getResourceAsStream("\\Images\\fish0.png"))));


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                imageView.requestFocus();

                //if (has_Shield) bullet.imageView.requestFocus();
            }
        });
        has_Shield = false;

        imageView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.LEFT) {

                    if (direction == 'r') {
                        flipLeft();
                        imageView.setScaleX(-1);

                    }
                    imageView.setX(imageView.getX() - speed.getNum());
                    if (has_Shield)
                        shield.setCenterX((imageView.getBoundsInLocal().getMinX() + imageView.getBoundsInLocal().getMaxX()) / 2);

                }

                if (event.getCode() == KeyCode.RIGHT) {
                    if (direction == 'l') {
                        flipRight();

                        imageView.setScaleX(1);
                    }

                    imageView.setX(imageView.getX() + speed.getNum());

                    if (has_Shield) {shield.setCenterX(imageView.getX() + (imageView.getBoundsInLocal().getMaxX()-imageView.getBoundsInLocal().getMinX()) / 2);
                        }


                }

                if (event.getCode() == KeyCode.UP) {
                    imageView.setY(imageView.getY() - speed.getNum());
                    if (has_Shield) shield.setCenterY(imageView.getY() + (imageView.getBoundsInLocal().getMaxY()-imageView.getBoundsInLocal().getMinY())/ 2);


                }
                if (event.getCode() == KeyCode.DOWN) {
                    imageView.setY(imageView.getY() + speed.getNum());
                    if (has_Shield)
                        shield.setCenterY((imageView.getBoundsInLocal().getMinY() + imageView.getBoundsInLocal().getMaxY()) / 2);


                }
//                if (has_Shield) {
//                    shield.setCenterY((imageView.getBoundsInLocal().getMinY() + imageView.getBoundsInLocal().getMaxY()) / 2);
//                    shield.setCenterX((imageView.getBoundsInLocal().getMinX() + imageView.getBoundsInLocal().getMaxX()) / 2);
//                }
            }
        });
        Random random = new Random();
        Boolean up = random.nextBoolean();


    }

    private void shot(int dx, Boolean up) {
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


    /***/ //masahat
    public int getNumOfEatFishes() {
        return numOfEatFishes;
    }

    public void UpdateImage(int x, int y) {
    }


    /***/
    public void increment_numOfEatFishes() {
        numOfEatFishes++;
    }


    /********************** Moving **********************/


    /********************** EATING **********************/

    public Boolean canEatFish(Fish enemyFish) {

        return (enemyFish.getSize() < this.getSize());   /***/
    }


    public Boolean Is_winner() {
        return 4900 < this.getSize();
    }

    public void CreateShield(Pane pane) {
        shield = new Circle((imageView.getBoundsInLocal().getMaxX() - imageView.getBoundsInLocal().getMinX()) / 2 + imageView.getBoundsInLocal().getMinX(), (imageView.getBoundsInLocal().getMaxY() - imageView.getBoundsInLocal().getMinY()) / 2 + imageView.getBoundsInLocal().getMinY(), Math.max(imageView.getBoundsInLocal().getWidth() / 2, imageView.getBoundsInLocal().getHeight() / 2), Color.WHITE); //hah
        shield.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        has_Shield=true;
        pane.getChildren().add(shield);
        Timeline timeline = new Timeline();

        timeline.setCycleCount(10);

        KeyFrame kf = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {


                has_Shield = true;
                imageView.setId("imageView");
                pane.getChildren().remove(imageView);

                pane.getChildren().add(imageView);


            }
        });


        timeline.getKeyFrames().add(kf);
        timeline.play();
        timeline.setOnFinished(event -> {
            timeline.stop();
            pane.getChildren().remove(shield);
            has_Shield = false;
        });

    }


    @Override
    public Bullet getshot() {
        return bullet;

    }
}