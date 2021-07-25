package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.io.IOException;

public class LoseController {
    @FXML
    Button play_again_, menu_;

    private Stage stage;
    private Scene scene;
    private Parent root;
    String username;


    public void PassUsername(String name) {
        username = name;

    }
    public void Switch2History(javafx.event.ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/history.fxml"));
        root = loader.load();
        HistoryController controller = loader.getController();
        controller.sendUsername(username);
        controller.setTitle("lose!");

        controller.start();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void Switch2Game(javafx.event.ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/sample.fxml"));
        root = loader.load();
        Controller controller = loader.getController();
        controller.sendUsername(username);
        controller.start();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


}
