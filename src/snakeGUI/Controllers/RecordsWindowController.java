package snakeGUI.Controllers;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import Supports.DataBase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class RecordsWindowController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea highScoresArea;

    @FXML
    private Button backButton;

    @FXML
    void initialize() {
        backButton.setOnAction(event -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        });

        showRecords();
    }

    private void showRecords() {
        try {
            DataBase dataBase = DataBase.getInstance();
            ResultSet allRecords = dataBase.getAllRecords();
            while (allRecords.next()){
                highScoresArea.insertText(highScoresArea.getLength(),
                        allRecords.getString(1) +
                        "\t" + allRecords.getString(2) +
                        "\t" + allRecords.getString(3) + "\n");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
