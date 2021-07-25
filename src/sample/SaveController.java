package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SaveController {
    String name;

    private Stage stage;
    private Scene scene;
    private Parent root;
    String username;

    public void pass_name(String name1) {
        name = name1;

    }


    public void sendUsername(String name1) {
        username = name1;

    }

    public void not_save(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/history.fxml"));
        root = loader.load();
        HistoryController controller = loader.getController();
        controller.sendUsername(username);
        controller.start();
        controller.setTitle("Game Didn't Save");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void yes_save(ActionEvent event) {
        //pass
    }
}
