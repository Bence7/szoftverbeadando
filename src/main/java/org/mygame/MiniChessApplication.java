package org.mygame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;

public class MiniChessApplication extends Application {

    private String gamename="MiniChessGame";
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mygame.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle(gamename);
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}

