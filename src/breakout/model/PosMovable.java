package breakout.model;


public abstract class PosMovable implements IPositionable{

    private double dx;
    private double dy;
    private double x;
    private double y;
    private double width;
    private double height;

    public PosMovable(double dx, double dy, double x, double y, double width, double height) {
        this.dx = dx;
        this.dy = dy;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    public void move() {
        setX(getX() + this.dx);
        setY(getY() + this.dy);
    }

    double getDx(){
        return this.dx;
    }
    double getDy(){
        return this.dy;
    }
    public void setX(double x){
        this.x=x;
    }
    public void setY(double y){
        this.y=y;
    }
    public void setDx(double dx){
        this.dx=dx;
    }
    public void setDy(double dy){
        this.dy=dy;
    }
}
