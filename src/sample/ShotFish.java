package sample;

import javafx.scene.image.Image;

public class ShotFish extends Fish {
    public Bullet shot;

    @Override
    public Bullet getshot() {
        return shot;
    }

    public ShotFish(Image image) {
        super(image);
        shot = new Bullet(this.direction, this.imageView);

    }


}
