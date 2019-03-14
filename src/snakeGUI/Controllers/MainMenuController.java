package snakeGUI.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Supports.DataBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button settingsButton;

    @FXML
    private Button startButton;

    @FXML
    private Button quitButton;

    @FXML
    private TextField nickNameField;

    @FXML
    private Button aboutMeButton;

    @FXML
    private Button recordsButton;

    @FXML
    void initialize(){
        aboutMeButton.setOnAction(event -> {
            showNewWindow("About Ivan llc.", "/snakeGUI/Fxml/AboutMe.fxml");
        });
        startButton.setOnAction(event -> {
            showGameField("Enjoy the game!","/snakeGUI/Fxml/GameField.fxml");
        });
        quitButton.setOnAction(event -> {
            Stage stage = (Stage) quitButton.getScene().getWindow();
            stage.close();
        });
        recordsButton.setOnAction(event -> {
            showNewWindow("High scores", "/snakeGUI/Fxml/Records.fxml");
        });
    }

    private void showNewWindow(String title, String fxmlPath){
        quitButton.getScene().getWindow().hide(); // скрываем текущее окно

        FXMLLoader loader = new FXMLLoader(); // подгружаем файл окна, которое хотим открыть
        loader.setLocation(getClass().getResource(fxmlPath));

        try {
            loader.load(); // загружаем его
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.showAndWait(); // Код внизу выполняется после закрытия

        stage.setScene(aboutMeButton.getScene()); // одно и то же
        stage.show();
    }

    private void showGameField(String title, String fxmlPath){
        quitButton.getScene().getWindow().hide(); // скрываем текущее окно

        FXMLLoader loader = new FXMLLoader(); // подгружаем файл окна, которое хотим открыть
        loader.setLocation(getClass().getResource(fxmlPath));
        try {
            loader.load(); // загружаем его
        } catch (IOException e) {
            e.printStackTrace();
        }
        GameFieldController gameFieldController = loader.getController();
        gameFieldController.setFirstSettings(nickNameField.getText());

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.showAndWait();
        // Код внизу выполняется после закрытия окна с игровым полем
        stage.setScene(aboutMeButton.getScene());
        stage.show();
    }
}
