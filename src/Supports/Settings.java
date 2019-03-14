package Supports;


import javafx.scene.paint.Color;

public class Settings {
    private double canvasHeight;
    private double canvasWidth;
    private Color fieldBackGroundColor;
    private Color snakeBodyColor;
    private Color appleColor;
    private Color snakeHeadColor;
    private int cellWidth;
    private int snakeEyeRadius;
    private int gameDifficulty;
    private int maximumApplesOnField;
    private int scoreMultiplier;
    private boolean volumeOn;


    public Settings(double canvasWidth, double canvasHeight){
        fieldBackGroundColor = Color.WHITE;
        snakeBodyColor = Color.BLACK;
        snakeHeadColor = Color.GRAY;
        appleColor = Color.GREEN;
        gameDifficulty = 200;
        volumeOn = true;
        cellWidth = 25;
        snakeEyeRadius = 10;
        maximumApplesOnField = 1;
        scoreMultiplier = 100;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
    }

    public double getCanvasHeight() {
        return canvasHeight;
    }

    public double getCanvasWidth() {
        return canvasWidth;
    }

    public Color getFieldBackGroundColor() {
        return fieldBackGroundColor;
    }

    public Color getSnakeBodyColor() {
        return snakeBodyColor;
    }

    public int getGameDifficulty() {
        return gameDifficulty;
    }

    public boolean isVolumeOn() {
        return volumeOn;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getSnakeEyeRadius() {
        return snakeEyeRadius;
    }

    public int getMaximumApplesOnField() {
        return maximumApplesOnField;
    }

    public int getScoreMultiplier() {
        return scoreMultiplier;
    }

    public Color getAppleColor() {
        return appleColor;
    }

    public Color getSnakeHeadColor() {
        return snakeHeadColor;
    }
}
