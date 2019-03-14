package snakeGUI.Controllers;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import GameCore.Worker;
import Supports.DataBase;
import Supports.Settings;
import Timers.AppleSpawnTimer;
import Timers.MovementTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameFieldController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label scoreLabel;
    @FXML
    private Button exitButton;
    @FXML
    private Canvas gameFieldCanvas;

    private Settings settings;
    private Worker worker;
    private GraphicsContext gc;
    private MovementTimer movementTimer;
    private AppleSpawnTimer appleSpawnTimer;
    private Stage stage;

    public void setFirstSettings(String nickName) {
        worker.setNickName(nickName);
    }

    @FXML
    void initialize() {
        gc = gameFieldCanvas.getGraphicsContext2D();
        settings = new Settings(gameFieldCanvas.getWidth(), gameFieldCanvas.getHeight());

        worker = Worker.getWorkerInstance("default", settings);
        worker.initSquareMatrix();
        drawField(gc);

        movementTimer = new MovementTimer(settings.getGameDifficulty(), this);
        appleSpawnTimer = new AppleSpawnTimer(settings.getGameDifficulty(), this);

        if (!worker.isGameIsActive()){
            movementTimer.setActive(false);
            appleSpawnTimer.setActive(false);
            drawPause();
        }

        exitButton.setOnKeyPressed(event -> {
            List<Point> changes;
            if (worker.isGameIsActive()) {
                if (event.getCode().equals(KeyCode.UP)) {
                    changes = worker.moveSnakeUp();
                    drawChangedSquares(changes);
                }
                if (event.getCode().equals(KeyCode.DOWN)) {
                    changes = worker.moveSnakeDown();
                    drawChangedSquares(changes);
                }
                if (event.getCode().equals(KeyCode.RIGHT)) {
                    changes = worker.moveSnakeRight();
                    drawChangedSquares(changes);
                }
                if (event.getCode().equals(KeyCode.LEFT)) {
                    changes = worker.moveSnakeLeft();
                    drawChangedSquares(changes);
                }
                if (event.getCode().equals(KeyCode.P)) {
                    movementTimer.setActive(false);
                    appleSpawnTimer.setActive(false);
                    worker.setGameIsActive(false);
                }
            }
            else {
                if (event.getCode().equals(KeyCode.P)) {
                    movementTimer = new MovementTimer(settings.getGameDifficulty(), this);
                    appleSpawnTimer = new AppleSpawnTimer(settings.getGameDifficulty(), this);
                    worker.setGameIsActive(true);
                    drawField(gc);
                }
            }
        });
        exitButton.setOnAction(event -> {
            movementTimer.setActive(false);
            appleSpawnTimer.setActive(false);
            stage = (Stage) exitButton.getScene().getWindow();
            worker.setGameIsActive(false);
            if (worker.isGameEnded()){
                endThisGame();
            }
            stage.close();
        });
    }

    private void endThisGame() {
        gc = null;
        worker.endThisGame();
        settings = null;
    }


    // Timer Methods ========================================================
    public void timerTurn(){
        List<Point> changes = worker.timerTurn();
        drawChangedSquares(changes);
    }

    public void spawnApple(){
        List<Point> changes = worker.spawnApple();
        drawChangedSquares(changes);
    }


    // Draw Methods ========================================================
    public void drawPause(){
        if (!worker.isGameEnded()) {
            gc.setFill(Color.BLACK);
            gc.setFont(new javafx.scene.text.Font(Font.MONOSPACED, settings.getCellWidth()));
            gc.fillText("Paused", settings.getCanvasWidth() / 2 - settings.getCellWidth(),
                    settings.getCanvasHeight() / 2 - settings.getCellWidth());
        }
    }

    private void drawGameOver() {
        gc.setFill(Color.RED);
        gc.setFont(new javafx.scene.text.Font(Font.MONOSPACED, 2*settings.getCellWidth()));
        gc.fillText("Game Over, You lost!", settings.getCanvasWidth() / 2 - 10 * settings.getCellWidth(),
                settings.getCanvasHeight() / 2 - 1 * settings.getCellWidth());
    }

    private synchronized void drawField(GraphicsContext gc) {
        for (int i = 0; i < worker.getSquareMatrix().length; i++) {
            for (int j = 0; j < worker.getSquareMatrix()[i].length; j++) {
                gc.setFill(worker.getSquareFromMatrix(i, j).getColorEnum(worker.getSquareFromMatrix(i, j)));
                gc.fillRect(i * settings.getCellWidth(), j * settings.getCellWidth(),
                        settings.getCellWidth(), settings.getCellWidth());
            }
        }
    }

    private synchronized void drawChangedSquares(List<Point> changes){
        if (!worker.isGameEnded()) {
            for (Point point : changes) {
                gc.setFill(worker.getSquareFromMatrix(point.x, point.y).getColorEnum(worker.getSquareFromMatrix(point.x, point.y)));
                gc.fillRect(point.x * settings.getCellWidth(), point.y * settings.getCellWidth(),
                        settings.getCellWidth(), settings.getCellWidth());
            }

//        Узнать почему так???
            Platform.runLater(() -> scoreLabel.setText(String.valueOf(worker.getScorePoints())));
        }
        else {
            movementTimer.setActive(false);
            appleSpawnTimer.setActive(false);
            worker.setGameIsActive(false);
            drawGameOver();
        }
    }
}
