package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

public abstract class Fish extends ScreenObject {
    private final int maxWidth = 700;
    private final int maxHeight = 500;
    FishType fishType;
    int fish_value = 10;
    Timeline timeline = new Timeline();

    public Fish(int width1, int height1, Image image, Speed speed1, int X, int Y, char direction1) {
        super(width1, height1, image, speed1, X, Y, direction1);

    }

    public int getFish_value() {
        return fish_value;
    }

    public abstract Bullet getshot();


    @Override
    public void move(int dx) {

        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Duration.millis(310 - (speed.getNum() * 100)), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (direction == 'r') {
                    imageView.setX(imageView.getX() + dx);
                } else {
                    imageView.setX(imageView.getX() - dx);
                }
                if (imageView.getX() > maxWidth || imageView.getX() < 0) {
                    visible = false;
                }
            }

        });
        timeline.getKeyFrames().add(kf);
        timeline.play();


    }

    public void stop() {
        timeline.stop();
    }


    protected Fish(Image img) {
        super();
        Random random = new Random();
        imageView = new ImageView(img);

        int space = (int) 60;
        int X, Y;
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);

        Y = random.nextInt(maxHeight - 2 * space) + space;
        if (random.nextBoolean()) {
            direction = 'r';
            X = 0;
        } else {
            direction = 'l';
            flipLeft();
            imageView.setScaleX(-1);
            X = (maxWidth - 60);
        }
        imageView.setX(X);
        imageView.setY(Y);
        speed = Speed.SLOW;
        fishType = FishType.SHOT;


    }

    public Fish(int type, Image img) {
        Random random = new Random();


        switch (type) {
            case 0:
                fishType = FishType.ENEMY;
                imageView = new ImageView(img);
                int space = (int) getImageView().getBoundsInParent().getHeight();


                super.imageView.setY(random.nextInt(maxHeight - 2 * space) + space);

                if (random.nextBoolean()) {
                    super.direction = 'r';
                    super.imageView.setX(0);
                } else {
                    flipLeft();
                    super.imageView.setScaleX(-1);
                    super.imageView.setX(maxWidth - getImageView().getBoundsInParent().getWidth());
                }
                int X = (int) imageView.getX();
                int Y = (int) imageView.getY();

                break;
            case 1:

                fishType = FishType.ENEMY;
                imageView = new ImageView(img);
                space = (int) getImageView().getBoundsInParent().getHeight();

                imageView.setY(random.nextInt(maxHeight - 2 * space) + space);
                if (random.nextBoolean()) {
                    direction = 'r';
                    imageView.setX(0);
                } else {
                    flipLeft();
                    imageView.setScaleX(-1);
                    imageView.setX(maxWidth - getImageView().getBoundsInParent().getWidth());
                }
                X = (int) imageView.getX();
                Y = (int) imageView.getY();

                break;
            case 2:

                fishType = FishType.ENEMY;

                imageView = new ImageView(img);
                space = (int) getImageView().getBoundsInParent().getHeight();

                imageView.setY(random.nextInt(maxHeight - 2 * space) + space);
                if (random.nextBoolean()) {
                    direction = 'r';
                    imageView.setX(0);
                } else {
                    flipLeft();
                    imageView.setScaleX(-1);
                    imageView.setX(maxWidth - getImageView().getBoundsInParent().getWidth());
                }


                break;
            case 3:

                fishType = FishType.ENEMY;
                imageView = new ImageView(img);



                space = (int) getImageView().getBoundsInParent().getHeight();

                imageView.setY(random.nextInt(maxHeight - 2 * space) + space);
                if (random.nextBoolean()) {
                    direction = 'r';
                    imageView.setX(0);
                } else {
                    flipLeft();
                    imageView.setScaleX(-1);
                    imageView.setX(maxWidth - getImageView().getBoundsInParent().getWidth());
                }

                break;
            case 4:
                imageView = new ImageView(img);
                space = (int) getImageView().getBoundsInParent().getHeight();
                                imageView.setY(random.nextInt(maxHeight - 2 * space) + space);
                if (random.nextBoolean()) {
                    direction = 'r';
                    imageView.setX(0);
                } else {
                    flipLeft();
                    imageView.setScaleX(-1);
                    imageView.setX(maxWidth - getImageView().getBoundsInParent().getWidth());
                }
                fishType = FishType.ENEMY;


                break;
            case 5:
                imageView = new ImageView(img);
                imageView.setFitWidth(70);
                imageView.setFitHeight(70);
                space = (int) getImageView().getBoundsInParent().getHeight();

                fishType = FishType.ENEMY;
                imageView.setY(random.nextInt(maxHeight - 2 * space) + space);
                if (random.nextBoolean()) {
                    direction = 'r';
                    imageView.setX(0);
                } else {
                    flipLeft();
                    imageView.setScaleX(-1);
                    imageView.setX(maxWidth - getImageView().getBoundsInParent().getWidth());
                }

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public int getSize() {
        return (int) (imageView.getBoundsInLocal().getWidth() * imageView.getBoundsInLocal().getHeight());
    }

}