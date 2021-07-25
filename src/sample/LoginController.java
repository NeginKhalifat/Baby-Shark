package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class LoginController {

    @FXML
    Label username, password, error;
    @FXML
    TextField username_field;
    @FXML
    PasswordField password_field;
    @FXML
    Button login;
    @FXML
    Hyperlink signup;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public String getPassword_field() {
        return password_field.getText();
    }

    public String getUsername_field() {
        return username_field.getText();
    }

    public void initialize() {
    }


    public void Switch2History(javafx.event.ActionEvent event) throws IOException {
        Profile user = login();
        if (user != null) {
            error.setText("");
            String name = user.getFirst_name();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/history.fxml"));
            root = loader.load();
            HistoryController historyController = loader.getController();
            historyController.DisplayName(name);
            historyController.Passname(name);
            historyController.sendUsername(user.getUser_name());
            historyController.start();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            error.setText("Not Correct");
        }
    }

    public void Switch2Signup(javafx.event.ActionEvent event) throws IOException {
        Parent signupParent = FXMLLoader.load(getClass().getResource("fxml/signup.fxml"));
        Scene signupScene = new Scene(signupParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(signupScene);
        window.show();
    }


    public Profile login() {
        error.setText("");
        String userName = username_field.getText();
        String pass = password_field.getText();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("users.txt"), "Cp1252"));
            String line;

            while ((line = br.readLine()) != null) {
                ArrayList<String> arr = new ArrayList<>(Arrays.asList(line.split(",")));

                String users = arr.get(5);

                users = users.replace("\u0000", ""); // removes NUL chars
                users = users.replace("\\u0000", ""); // removes backslash+u0000
                String p = arr.get(6);
                p = p.replace("\u0000", ""); // removes NUL chars
                p = p.replace("\\u0000", ""); // removes backslash+u0000
                String g = arr.get(3);
                g = g.replace("\u0000", ""); // removes NUL chars
                g = g.replace("\\u0000", ""); // removes backslash+u0000
                String age = arr.get(4);
                age = age.replace("\u0000", ""); // removes NUL chars
                age = age.replace("\\u0000", ""); // removes backslash+u0000

                String name = arr.get(0);

                name = name.replace("\u0000", ""); // removes NUL chars
                name = name.replace("\\u0000", ""); // removes backslash+u0000
                name=name.substring(5);
                Gender gender = Gender.MALE;
                if (g.equals("Female")) gender = Gender.FEMALE;
                if (users.equals(userName)) {
                    if (pass.equals(p))
                        return new Profile(arr.get(2), Integer.parseInt(age), name, arr.get(1), gender, userName, arr.get(6));
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
