package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.exceptions.CustomException;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    @FXML
    Label error;
    @FXML
    TextField age_field;
    @FXML
    TextField first_name_field, last_name_field, id_code_field;
    @FXML
    TextField username_field;
    @FXML
    PasswordField password_field;
    @FXML
    Button ok;
    @FXML
    ComboBox<String> gender_field;
    private Stage stage;
    private Scene scene;
    private Parent root;
    static String name;

    public void Switch2History(javafx.event.ActionEvent event) throws IOException {
        Profile user = createUser();
        if (user != null) {
            name = user.getFirst_name();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/history.fxml"));
            root = loader.load();
            HistoryController historyController = loader.getController();
            historyController.DisplayName(name);
            historyController.Passname(name);
            historyController.sendUsername(user.getUser_name());
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }

    public void Switch2Login(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public Profile createUser() {
        try {
            String fname = first_name_field.getText();
            String lname = last_name_field.getText();
            String id = id_code_field.getText();
            String age = age_field.getText();
            String gender = gender_field.getValue();
            String name = fname;
            name = name.replace("\u0000", ""); // removes NUL chars
            name = name.replace("\\u0000", ""); // removes backslash+u0000

            Gender gender1;
            if (gender.equals("Female")) {
                gender1 = Gender.FEMALE;
            } else gender1 = Gender.MALE;

            String userName = username_field.getText();
            String pass = password_field.getText();
            if (fname.equals("")) throw new CustomException("First Name is empty");
            else if (lname.equals("")) throw new CustomException("Last Name is empty");
            else if (id.length() != 10 && isNumeric(id)) throw new CustomException("ID must be 10 digits");
            else if (!isNumeric(age)) throw new CustomException("Age must be a number");
            else if (userName.equals("")) throw new CustomException("username is empty");

            else if (userName.charAt(0) <= 9) {
                if (userName.charAt(0) >= 0)
                    throw new CustomException("username must have start with character");
            } else if (sameUsername(userName)) throw new CustomException("username must be unique");

            else if (pass.length() < 8) throw new CustomException("password must have 8 characters");
            Profile user = new Profile(id, Integer.parseInt(age), name, lname, gender1, userName, pass);
            error.setText("");
            writetoFile(user);
            return user;

        } catch (CustomException ex) {
            error.setText(ex.getErrorMsg());
        }
        return null;
    }

    private Boolean sameUsername(String userName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("users.txt"), "Cp1252"));
            String line;

            while ((line = br.readLine()) != null) {
                ArrayList<String> arr = new ArrayList<>(Arrays.asList(line.split(",")));

                String users = arr.get(5);
                users = users.replace("\u0000", ""); // removes NUL chars
                users = users.replace("\\u0000", ""); // removes backslash+u0000

                if (users.equals(userName)) {
                    return true;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;


    }

    private void writetoFile(Profile user) {
        try {
            FileOutputStream f = new FileOutputStream(new File("users.txt"), true);

            ObjectOutputStream o = new ObjectOutputStream(f);
            String name = user.first_name;
            name = name.replace("\u0000", ""); // removes NUL chars
            name = name.replace("\\u0000", ""); // removes backslash+u0000

            String string = name + "," + user.last_name + "," + user.id + "," + user.gender + "," + user.age + "," + user.getUser_name() + "," + user.password + '\n';
            o.writeChars(string);
            o.close();
            f.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gender_field.getItems().add("Female");
        gender_field.getItems().add("Male");
    }

}
