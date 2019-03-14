package GameCore;

import Supports.DataBase;
import Supports.Settings;
import Supports.SquaresType;
import Units.Apple;
import Units.Snake;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Worker {
    private static Worker instance;
    private String nickName;
    private Snake snake;
    private Settings settings;
    private int timesToGrow;
    private boolean gameIsActive;
    private int scorePoints;
    private SquaresType[][] squareMatrix;
    private List<Apple> appleList;
    private boolean gameEnded;

    private Worker(String nickName, Settings settings) {
        this.nickName = nickName;
        this.snake = Snake.getSnakeInstance();
        this.settings = settings;
        this.timesToGrow = 0;
        this.gameIsActive = true;
        this.scorePoints = 0;
        this.appleList = new ArrayList<>();
        this.gameEnded = false;
    }

    public static Worker getWorkerInstance(String nickName, Settings settings) {
        if (instance == null)
            instance = new Worker(nickName, settings);
        return instance;
    }

    // Game Over===================================================
    public void endThisGame(){
        commitResultInDB();
        snake.cleanThisSnake();
        squareMatrix = null;
        instance = null;
    }

    private void commitResultInDB(){
        try {
            DataBase dataBase = DataBase.getInstance();
            dataBase.insertNewRecord(nickName, scorePoints);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // GET and SET===================================================
    public Snake getSnake() {
        return snake;
    }

    public Settings getSettings() {
        return settings;
    }

    public int getScorePoints() {
        return scorePoints;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isGameIsActive() {
        return gameIsActive;
    }

    public void setGameIsActive(boolean gameIsActive) {
        this.gameIsActive = gameIsActive;
    }

    public SquaresType getSquareFromMatrix(int x, int y) {
        return squareMatrix[x][y];
    }

    public SquaresType[][] getSquareMatrix() {
        return squareMatrix;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    // Snake MOVE =====================================

    public List<Point> moveSnakeRight() {
        List<Point> changes = new ArrayList<>();
        if (snake.getCompass() != "Left") {
            changes = snake.goRight(timesToGrow);
            applyChangesOnMatrix(changes);
        }
        return changes;
    }

    public List<Point> moveSnakeDown() {
        List<Point> changes = new ArrayList<>();
        if (snake.getCompass() != "Up") {
            changes = snake.goDown(timesToGrow);
            applyChangesOnMatrix(changes);
        }
        return changes;
    }

    public List<Point> moveSnakeLeft() {
        List<Point> changes = new ArrayList<>();
        if (snake.getCompass() != "Right") {
            changes = snake.goLeft(timesToGrow);
            applyChangesOnMatrix(changes);
        }
        return changes;
    }

    public List<Point> moveSnakeUp() {
        List<Point> changes = new ArrayList<>();
        if (snake.getCompass() != "Down") {
            changes = snake.goUp(timesToGrow);
            applyChangesOnMatrix(changes);
        }
        return changes;
    }

    public List<Point> timerTurn() {
        List<Point> changes;
        changes = snake.goCompass(timesToGrow);
        applyChangesOnMatrix(changes);
        return changes;
    }


    // Matrix WORKS =====================================

    public synchronized void applyChangesOnMatrix(List<Point> changes) {
        checkCollisionWithApple();
        if (checkLose()){
            gameEnded = true;
        }
        for (Point point : changes) {
            point = controlBorders(point);
            if (squareMatrix[point.x][point.y].equals(SquaresType.SnakeBody)) {
                squareMatrix[point.x][point.y] = SquaresType.Field;
            }
            if (squareMatrix[point.x][point.y].equals(SquaresType.SnakeHead)) {
                squareMatrix[point.x][point.y] = SquaresType.SnakeBody;
            }
            if (point.equals(snake.getHead())) {
                squareMatrix[point.x][point.y] = SquaresType.SnakeHead;
            }
        }
    }

    public void initSquareMatrix() {
        /////////////// field init
        squareMatrix = new SquaresType[(int) settings.getCanvasWidth() / settings.getCellWidth()]
                [(int) settings.getCanvasHeight() / settings.getCellWidth()];
        for (int i = 0; i < squareMatrix.length; i++) {
            for (int j = 0; j < squareMatrix[i].length; j++) {
                squareMatrix[i][j] = SquaresType.Field;
            }
        }
        /////////////// Snake body init
        for (Point point : snake.getBody()) {
            squareMatrix[point.x][point.y] = SquaresType.SnakeBody;
        }
        /////////////// Snake Head init
        squareMatrix[snake.getHead().x][snake.getHead().y] = SquaresType.SnakeHead;
    }


    // Apples + Border WORKS =====================================

    private Point controlBorders(Point point) {
        if (point.x == squareMatrix.length) {
            point.x = 0;
        } else if (point.x == -1) {
            point.x = squareMatrix.length - 1;
        }
        if (point.y == squareMatrix[point.x].length) {
            point.y = 0;
        } else if (point.y == -1) {
            point.y = squareMatrix[point.x].length - 1;
        }
        return point;
    }

    private void incrementScore(int growPoints) {
        scorePoints = scorePoints + growPoints * settings.getScoreMultiplier();
    }

    public List<Point> spawnApple() {
        List<Point> changes = new ArrayList<>();
        if (appleList.size() < settings.getMaximumApplesOnField()) {
            Apple apple = new Apple(squareMatrix.length, squareMatrix[0].length);
            appleList.add(apple);
            if (squareMatrix[apple.getPoint().x][apple.getPoint().y] != SquaresType.SnakeHead) {
                squareMatrix[apple.getPoint().x][apple.getPoint().y] = SquaresType.Apple;
                changes.add(apple.getPoint());
            } else {
                // придумать что бы сразу съедалось
            }
        } else {
            for (Apple apple : appleList) {
                squareMatrix[apple.getPoint().x][apple.getPoint().y] = SquaresType.Apple;
                changes.add(apple.getPoint());
            }
        }
        return changes;
    }

    // Collisions ================================
    public void checkCollisionWithApple() {
        if (timesToGrow != 0) {
            timesToGrow--;
        }
        for (Apple apple : appleList) {
            if ((apple.getPoint().x == snake.getHead().x) &&
                    (apple.getPoint().y == snake.getHead().y)) {
                timesToGrow = apple.getGrowPoints();
                incrementScore(timesToGrow);
                appleList.remove(apple);
                break;
            }
        }
    }

    public synchronized boolean checkLose() {
        Point head = snake.getHead();
        int count = 0;
        for (Point point : snake.getBody()) {
            if ((point.x == head.x) && (point.y == head.y)){
                count++;
            }
        }
        if (count >= 2){
            return true;
        }
        else {
            return false;
        }
    }
}
