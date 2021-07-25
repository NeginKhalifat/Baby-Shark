
package sample;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;


public class Controller {

    @FXML
    AnchorPane pane;

    String username;
    String name;


    public void pass_name(String name1) {
        name = name1;

    }


    public void sendUsername(String name1) {
        username = name1;

    }


    public void start() throws IOException {
        FishGenerator generator = new FishGenerator();
        ShellGenerator shellGenerator = new ShellGenerator();
        ScreenManager sn = new ScreenManager(pane, username, generator, shellGenerator);
        generator.generateFish(pane, sn);
        shellGenerator.generateShell(pane, sn);

        sn.start();
    }


}
