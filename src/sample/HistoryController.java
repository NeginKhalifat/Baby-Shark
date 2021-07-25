package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class HistoryController {
    @FXML
    Label label;
    @FXML
    javafx.scene.control.TableView<History> table;
    @FXML
    TableColumn<History, String> date, time, score, status;
    @FXML
    Button new_game, continue_, score_board;
    ArrayList<History> histories = new ArrayList<>();

    private Stage stage;
    private Scene scene;
    private Parent root;
    String username;
    String name;



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

    public void Switch2Board(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/score_board.fxml"));
        root = loader.load();
        ScoreBoardController controller = loader.getController();
        controller.sendUsername(username);
        controller.pass_name(name);
        controller.start();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void DisplayName(String name) {
        label.setText("Well come " + name);
    }

    public void sendUsername(String name) {
        username = name;

    }
    public void Passname(String name1) {
        name = name1;

    }

    public void setTitle(String text) {
        label.setText(text);
    }


    public void start() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("game_info.txt"), "Cp1252"));
            String line;

            while ((line = br.readLine()) != null) {
                ArrayList<String> arr = new ArrayList<>(Arrays.asList(line.split(",")));

                String users = arr.get(5);
                users = users.replace("\u0000", ""); // removes NUL chars
                users = users.replace("\\u0000", ""); // removes backslash+u0000
                String user_name = username;

                user_name = user_name.replace("\u0000", ""); // removes NUL chars
                user_name = user_name.replace("\\u0000", ""); // removes backslash+u0000


                if (users.equals(user_name)) {
                    String score = arr.get(4);
                    score = score.replace("\u0000", ""); // removes NUL chars
                    score = score.replace("\\u0000", ""); // removes backslash+u0000

                    histories.add(new History(score, arr.get(3), arr.get(2), arr.get(1)));
                }
            }
            Collections.reverse(histories);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        ObservableList<History> list = FXCollections.observableArrayList(


                histories
        );


        score.setCellValueFactory(new PropertyValueFactory<History, String>("score"));
        status.setCellValueFactory(new PropertyValueFactory<History, String>("status"));
        time.setCellValueFactory(new PropertyValueFactory<History, String>("time"));
        date.setCellValueFactory(new PropertyValueFactory<History, String>("date"));
        table.setItems(list);


    }


}
