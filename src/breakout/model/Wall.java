package breakout.model;

/*
        A wall for the ball to bounce
 */
public class Wall extends PosStatic {
    private final Dir dir;

    public Wall(double x, double y, double width, double height, Dir dir) {
        super(x,y,width,height);
        this.dir = dir;
    }

    public Dir getDir() {
        return dir;
    }

    public enum Dir {
        HORIZONTAL, VERTICAL;
    }

}
