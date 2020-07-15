package Formula1;

public abstract  class Car {
    protected int x;
    protected int y;
    protected double directSpeed;

    public Car(int x, int y,int speed) {
        this.x = x;
        this.y = y;
        this.directSpeed = speed;
    }

    public void move (double dX,double dY){
        this.x += dX;
        this.y += dY;
    }
}
