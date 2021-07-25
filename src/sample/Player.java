package sample;


import javafx.scene.control.Label;

public class Player {
    private FishPlayer fishPlayer;
    private Profile profile;
    private float high_score;
    private String name;
    private float score;
    Label score_label = new Label();
    Player(){
        fishPlayer = new FishPlayer();
        score_label.setText("0");
        score_label.setLayoutX(650);
        score_label.setLayoutY(20);
    }

    public Profile getProfile() {
        return profile;
    }

    public void setScore(float score) {

        this.score = score;
        score_label.setText(String.valueOf(score));
    }

    public int getX(){
        return (int) fishPlayer.getX();
    }

    public int getY(){
        return (int) fishPlayer.getY();
    }
    public Label getScore_label() {
        return score_label;
    }

    public float getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public FishPlayer getFishPlayer() {
        return fishPlayer;
    }


}
