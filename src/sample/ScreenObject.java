package sample;


import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class ScreenObject extends ImageView {
    Speed speed;
    char direction;
    int width;
    int height;
    Boolean visible = true;
    ImageView imageView;
    Timeline timeline = new Timeline();

    public ScreenObject(int width1, int height1, Image image, Speed speed1, int X, int Y, char direction1) {
        super(image);
        setX(X);
        setY(Y);
        width = width1;
        height = height1;
        setFitWidth(width1);
        setFitHeight(height1);
        speed = speed1;
        direction = direction1;
        imageView = new ImageView(image);
        setImageView(imageView);

    }

    protected ScreenObject() {
    }

    public void setHeight(int height) {
        this.height = height;
        setFitHeight(height);
    }

    public void setWidth(int width) {
        this.width = width;
        setFitWidth(width);
    }

    public abstract void move(int dx);

    public void add2pane(AnchorPane pane) {

        pane.getChildren().add(this.imageView);
    }

    public void removeFromPane(AnchorPane pane) {

        pane.getChildren().remove(this.imageView);

    }


    public boolean outOfScreen(AnchorPane pane) {
        if (imageView.getX() > 700 || imageView.getX() < 0) {
            removeFromPane(pane);
            return false;
        }
        return true;
    }

    /*************** Getter & Setter ****************/

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView1) {
        this.imageView = imageView1;

    }


    public void setDirection(char direction) {
        this.direction = direction;
    }

    public Boolean is_collide(ScreenObject obj) {
        return this.imageView.getBoundsInParent().intersects(obj.imageView.getBoundsInParent());

    }

    /********************** Moving **********************/
    public void flipLeft() {
        setDirection('l');
    }

    public void flipRight() {
        setDirection('r');
    }

    public abstract void stop();
}
