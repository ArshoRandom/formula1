package Formula1;

public class GameComponents {

    protected int xP;
    protected int yP;

    public GameComponents(int xP, int yP) {
        this.xP = xP;
        this.yP = yP;
    }

    public void movePoint(int pointSpeed){
        this.yP += pointSpeed;
    }



}
