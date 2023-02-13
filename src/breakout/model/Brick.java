package breakout.model;

/*
 *   A brick for the rows of bricks
 */

public class Brick extends PosStatic {

    public static final double BRICK_WIDTH = 20;  // Default values, use in constructors, not directly
    public static final double BRICK_HEIGHT = 10;
    private int points;

    public Brick(double x, double y, int points) {
        super(x,y,BRICK_WIDTH, BRICK_HEIGHT);
        this.points=points;
    }

    public double getPoints() {
        return points;
    }


}

