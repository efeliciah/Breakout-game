package breakout.model;


import breakout.event.EventBus;
import breakout.event.ModelEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 *  Overall all logic for the Breakout Game
 *  Model class representing the full game
 *  This class should use other objects delegate work.
 *
 *  NOTE: Nothing visual here
 *
 */
public class Breakout {

    public static final double GAME_WIDTH = 400;
    public static final double GAME_HEIGHT = 400;
    public static final double BALL_SPEED_FACTOR = 1.05; // Increase ball speed
    public static final long SEC = 1_000_000_000;  // Nano seconds used by JavaFX

    private int nBalls = 4;
    int playerPoints;

    // TODO Constructor that accepts all objects needed for the model
    private Ball ball;
    private final Paddle paddle;
    private final List<Wall> walls;
    private final List<Brick> bricks;
    private final List<Brick> toRemove = new ArrayList<>();


    public Breakout(List<Brick> bricks, List<Wall> walls) {
        this.walls = walls;
        this.bricks = bricks;
        this.paddle = new Paddle((GAME_WIDTH - Paddle.PADDLE_WIDTH) / 2, GAME_HEIGHT - 3 * Paddle.PADDLE_HEIGHT);
        Random rand = new Random();
        double dx = ((((double) rand.nextInt(101)) - 50) / 100) * 4;
        double dy = Math.sqrt(4 - Math.pow(dx, 2));
        this.ball = new Ball(dx, dy, 200, 200);
    }

    // --------  Game Logic -------------

    private long timeForLastHit;         // To avoid multiple collisions

    public void update(long now) {
        // TODO  Main game loop, start functional decomposition from here
        if (0 <= paddle.getX() + paddle.getDx() && paddle.getX() + paddle.getDx() <= GAME_WIDTH - paddle.getWidth()) {
            paddle.move();
        }
        ball.move();
        ballHitBrick();
        ballHitPaddle(now);
        ballHitWall();
        ballFallsOut();
    }


    // ----- Helper methods--------------

    // Used for functional decomposition
    private void ballHitPaddle(long now) {
        if (ball.intersects(paddle) && now - timeForLastHit > SEC / 10) { //bounce off paddle and speed up
            ball.setDy(-ball.getDy() * BALL_SPEED_FACTOR);
            ball.setDx(ball.getDx() * BALL_SPEED_FACTOR + paddle.getDx() / 5); // friction
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_PADDLE));
            timeForLastHit=now;
        }
    }

    private void ballHitBrick() {
        for (Brick brick : bricks) { //ball touches bricks
            if (ball.intersects(brick)) {
                toRemove.add(brick);
                playerPoints += brick.getPoints();
                ball.setDy(-ball.getDy());
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_BRICK));
            }
        }
        bricks.removeAll(toRemove);
        toRemove.clear();
    }

    private void ballHitWall() {
        for (Wall wall : walls) { //ball bounces on wall
            if (ball.intersects(wall)) {
                if (wall.getDir().equals(Wall.Dir.VERTICAL)) {
                    ball.setDx(-ball.getDx());
                } else {
                    ball.setDy(-ball.getDy());
                }
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_WALL));
            }
        }
    }

    private void ballFallsOut() {
        if (ball.getY() > GAME_HEIGHT && nBalls > 0) { //Ball falls out, create new ball
            Random rand = new Random();
            double dx = ((((double) rand.nextInt(101)) - 50) / 100) * 4;
            double dy = Math.sqrt(4 - Math.pow(dx, 2));
            ball = new Ball(dx, dy, 200, 200);
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.NEW_BALL));
            nBalls -= 1;
        }
    }

    // --- Used by GUI  ------------------------

    // TODO return all objects to be rendered by GUI

    private List<IPositionable> createPositionables(List<? extends IPositionable> bricks) {
        List<IPositionable> toPos = new ArrayList<>();
        toPos.addAll(bricks);
        return toPos;
    }


    public List<IPositionable> getPositionables() {
        List<IPositionable> pos = createPositionables(bricks);
        pos.add(ball);
        pos.add(paddle);
        return pos;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public int getnBalls() {
        return nBalls;
    }

    public void setPaddleSpeed(double speed) {
        this.paddle.setDx(speed);
    }

}


