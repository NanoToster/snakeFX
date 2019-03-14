package Units;

import java.awt.*;
import java.util.Random;

public class Apple {
    private Point point;
    private int growPoints;

    public Apple(int borderX, int borderY){
        Random random = new Random();
        growPoints = random.nextInt(3) + 1;
        point = new Point();
        point.x = random.nextInt(borderX);
        point.y = random.nextInt(borderY);
    }

    public Point getPoint() {
        return point;
    }

    public int getGrowPoints() {
        return growPoints;
    }
}
