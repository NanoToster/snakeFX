package Units;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;

public class Snake {
    private static Snake instance;

    //    Голова - конец очереди
    //    хвост - начало очереди
    private Deque<Point> body;
    private String compass;


    private Snake() {
        body = new ArrayDeque<>();
        body.addFirst(new Point(5, 5));
        body.addLast(new Point(6, 5));
        body.addLast(new Point(7, 5));
        body.addLast(new Point(8, 5));
        body.addLast(new Point(9, 5));
        compass = "Right";
    }

    public static Snake getSnakeInstance() {
        if (instance == null)
            instance = new Snake();
        return instance;
    }

    public void cleanThisSnake(){
        instance = null;
    }

    public Queue<Point> getBody() {
        return body;
    }

    public String getCompass() {
        return compass;
    }

    public Point getHead() {
        return body.peekLast();
    }

    public ArrayList<Point> goRight(int timesToGrow) {
        ArrayList<Point> changes = new ArrayList<>();
        changes.add(body.peekLast());
        body.addLast(new Point(body.peekLast().x + 1, body.peekLast().y));
        changes.add(body.peekLast());
        if (timesToGrow==0){
            changes.add(body.peekFirst());
            body.removeFirst();
        }
        compass = "Right";
        return changes;
    }

    public ArrayList<Point> goDown(int timesToGrow) {
        ArrayList<Point> changes = new ArrayList<>();
        changes.add(body.peekLast());
        body.addLast(new Point(body.peekLast().x, body.peekLast().y + 1));
        changes.add(body.peekLast());
        if (timesToGrow==0){
            changes.add(body.peekFirst());
            body.removeFirst();
        }
        compass = "Down";
        return changes;
    }

    public ArrayList<Point> goLeft(int timesToGrow) {
        ArrayList<Point> changes = new ArrayList<>();
        changes.add(body.peekLast());
        body.addLast(new Point(body.peekLast().x - 1, body.peekLast().y));
        changes.add(body.peekLast());
        if (timesToGrow==0){
            changes.add(body.peekFirst());
            body.removeFirst();
        }
        compass = "Left";
        return changes;
    }

    public ArrayList<Point> goUp(int timesToGrow) {
        ArrayList<Point> changes = new ArrayList<>();
        changes.add(body.peekLast());
        body.addLast(new Point(body.peekLast().x, body.peekLast().y - 1));
        changes.add(body.peekLast());
        if (timesToGrow==0){
            changes.add(body.peekFirst());
            body.removeFirst();
        }
        compass = "Up";
        return changes;
    }

    public synchronized ArrayList<Point> goCompass(int timesToGrow) {
        ArrayList<Point> changes = new ArrayList<>();
        if (compass == "Right") {
            changes = goRight(timesToGrow);
        } else if (compass == "Left") {
            changes = goLeft(timesToGrow);
        } else if (compass == "Down") {
            changes = goDown(timesToGrow);
        } else if (compass == "Up") {
            changes = goUp(timesToGrow);
        }
        return changes;
    }
}
