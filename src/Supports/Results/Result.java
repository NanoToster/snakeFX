package Supports.Results;

public class Result {
    private String nickName;
    private int score;


    public Result(String nickName, int score){
        this.nickName = nickName;
        this.score = score;
    }

    public String getNickName() {
        return nickName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString(){
        return nickName + " ------- \t" + score;
    }

}

