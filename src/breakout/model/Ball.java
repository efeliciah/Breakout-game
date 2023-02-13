package breakout.model;

import java.util.List;
import java.util.Random;

import static breakout.model.Breakout.GAME_HEIGHT;
import static breakout.model.Breakout.GAME_WIDTH;
import java.lang.Math;

/*
 *    A Ball for the Breakout game
 */

public class Ball extends PosMovable{

    public Ball( double dx, double dy, double x, double y) {
        super(dx,dy,x,y,12,12);

    }

    public boolean intersects(IPositionable other) {
        boolean above = other.getY() + other.getHeight() < getY();
        boolean below = other.getY() > getY() + getHeight();
        boolean left = other.getX() + other.getWidth() < getX();
        boolean right = other.getX() > getX() + getWidth();

        return !(above || below || left || right);
    }
}
