
package sample;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class ScreenManager {
    Label lives;
    private AnchorPane pane;
    private ArrayList<Fish> fishes = new ArrayList<>();
    private Hook hook;
    private ArrayList<Shell> shells = new ArrayList<Shell>();
    MyTimer myTimer = new MyTimer();
    String username;
    Game game;
    Boolean stop = false;


    public ScreenManager(AnchorPane pane, String username, FishGenerator generator, ShellGenerator shellGenerator) throws IOException {
        this.pane = pane;

        this.username = username;


        hook = new Hook();
        pane.getChildren().add(hook.getImageView());

        pane.getChildren().add(hook.line);
        lives = new Label();

        lives.setLayoutX(650);
        lives.setLayoutY(50);
        lives.setText("lives: 3");
        pane.getChildren().add(myTimer.text);

        pane.getChildren().add(lives);
        Label exit = new Label("exit");
        exit.setLayoutX(648);
        exit.setLayoutY(470);
        exit.setTextFill(Paint.valueOf("#040f17"));

        exit.setPrefHeight(28);
        exit.setPrefWidth(51);
        exit.setOnMouseClicked(event -> {

            Stage stage;
            Scene scene;
            Parent root = null;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/saveOrnot.fxml"));
            try {
                root = loader.load();
                stop = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            SaveController controller = loader.getController();
            controller.sendUsername(username);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        });


        pane.getChildren().add(exit);


        myTimer.startTimer();

        Player player = new Player();
        pane.getChildren().add(player.getFishPlayer().getImageView());
        pane.getChildren().add(player.getScore_label());
        game = new Game(player, this, generator, shellGenerator);

    }

    public AnchorPane getPane() {
        return pane;
    }

    public MyTimer getMyTimer() {
        return myTimer;
    }

    public Hook getHook() {
        return hook;
    }

    public ArrayList<Fish> getFishes() {
        return fishes;
    }

    public void start() {


    }


    public void addShell(Shell shell) {

        shells.add(shell);
    }

    public ArrayList<Shell> getShells() {
        return shells;
    }

    public void addFish(Fish fish) {
        fishes.add(fish);
    }

    public void removeShell(ScreenObject shell) {

        shells.remove((Shell) shell);
        pane.getChildren().remove(shell.imageView);

    }


    public void removeFish(ScreenObject fish) {

        fishes.remove((Fish) fish);
        pane.getChildren().remove(fish.imageView);

    }

    public void setHook(Hook hook) {
        this.hook = hook;
    }

    public void createShield(FishPlayer fishPlayer) {
    }

    public void addObject(Circle shield) {
        pane.getChildren().add(shield);
    }
}
