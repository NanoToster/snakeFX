package Timers;


import snakeGUI.Controllers.GameFieldController;

public class MovementTimer implements Runnable {
    private int difficultLevel;
    private GameFieldController gameField;
    private boolean isActive;
    private Thread thread;

    public MovementTimer(int difficultLevel, GameFieldController gameFieldController){
        this.difficultLevel = difficultLevel;
        this.gameField = gameFieldController;
        this.isActive = true;
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run(){
        while (isActive){
            try{
                Thread.sleep(100000 / difficultLevel);
            } catch (InterruptedException e){ }
            gameField.timerTurn();
        }
        gameField.drawPause();
    }

    public void setActive(boolean isActive){
        this.isActive = isActive;
    }
}
