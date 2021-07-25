
package sample;

import javafx.scene.image.Image;


public class EnemyFish extends Fish {
    private Boolean visible = true;


    public EnemyFish(int type, Image image) {
        super(type, image);
        if (type < 3) speed = Speed.SLOW;
        else if (type < 5) speed = Speed.MED;
        else if (type < 7) speed = Speed.FAST;
        fish_value = 5 * type + getSize() / 100;

    }


    public int getFish_value() {
        return fish_value;
    }

    @Override
    public Bullet getshot() {
        return null;
    }


    /********************** Eating **********************/
    public Boolean isCollide(FishPlayer fishPlayer) {
        return imageView.getBoundsInParent().intersects(fishPlayer.getImageView().getBoundsInParent());
    }
}
