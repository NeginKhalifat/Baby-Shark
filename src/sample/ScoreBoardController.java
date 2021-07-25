package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ScoreBoardController {
    @FXML
    Button menu_;

    @FXML
    javafx.scene.control.TableView<MaxRecord> table_;
    @FXML
    TableColumn<MaxRecord, String> score_, username_;
    @FXML
    ArrayList<MaxRecord> histories = new ArrayList<>();
    String name;

    private Stage stage;
    private Scene scene;
    private Parent root;
    String username;


    public void Switch2History(javafx.event.ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/history.fxml"));
        root = loader.load();
        HistoryController controller = loader.getController();
        controller.sendUsername(username);
        controller.start();
        controller.DisplayName(name);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void pass_name(String name1) {
        name = name1;

    }


    public void sendUsername(String name1) {
        username = name1;

    }


    public void start() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("game_info.txt"), "Cp1252"));
            String line;
            Hashtable<String, Float> array = new Hashtable<>();

            while ((line = br.readLine()) != null) {
                ArrayList<String> arr = new ArrayList<>(Arrays.asList(line.split(",")));

                String user = arr.get(5);
                user = user.replace("\u0000", ""); // removes NUL chars
                user = user.replace("\\u0000", ""); // removes backslash+u0000

                String score = arr.get(4);
                score = score.replace("\u0000", ""); // removes NUL chars
                score = score.replace("\\u0000", ""); // removes backslash+u0000
                float user_score = Float.parseFloat(score);
                if (array.containsKey(user)) {
                    if (array.get(user) < user_score) {
                        array.put(user, user_score);
                    }
                } else array.put(user, user_score);

            }
            Enumeration<String> e = array.keys();
            while (e.hasMoreElements()) {
                String key = e.nextElement();
                histories.add(new MaxRecord(key, array.get(key)));

            }
            Collections.reverse(histories);
            Collections.sort(histories);
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        ObservableList<MaxRecord> list = FXCollections.observableArrayList(


                histories
        );
        score_.setCellValueFactory(new PropertyValueFactory<MaxRecord, String>("score"));
        username_.setCellValueFactory(new PropertyValueFactory<MaxRecord, String>("username"));
        table_.setItems(list);


    }


}

