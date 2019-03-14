package Supports;


import javafx.scene.paint.Color;

public enum SquaresType {
    Apple{
//        public Color getColor(){return new Settings().getAppleColor();}
    },
    SnakeBody{
//        public Color getColor(){return new Settings().getSnakeBodyColor();}
    },
    SnakeHead{

    },
    Field{
//        public Color getColor(){return new Settings().getFieldBackGroundColor();}
    };

    public Color getColorEnum(SquaresType type){
        if (type.equals(SquaresType.Field)){
            return new Settings(1,1).getFieldBackGroundColor();
        }

        if (type.equals(SquaresType.SnakeBody)){
            return new Settings(1,1).getSnakeBodyColor();
        }

        if (type.equals(SquaresType.Apple)){
            return new Settings(1,1).getAppleColor();
        }

        if (type.equals(SquaresType.SnakeHead)){
            return new Settings(1,1).getSnakeHeadColor();
        }
        return Color.RED;
    }

}
