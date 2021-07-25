package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;

public class Shell extends ScreenObject{
    int score=100;
    Shell(int x,int y) throws FileNotFoundException {
        super(10, 10,null, Speed.MED, x, y, 'r');
        imageView = new ImageView(new Image(getClass().getResourceAsStream("\\Images\\shell.png")));


        imageView.setY(y);
        imageView.setX(x);

    }
    @Override
    public void move(int dx) {

    }

    @Override
    public void stop() {

    }
}
