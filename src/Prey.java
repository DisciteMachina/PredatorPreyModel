import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Prey {
    private final Point position;
    private final Point2D velocity;
    private double health;
    private double hunger;
    private final double speed;
    private final Random random;

    public Prey(int startX, int startY) {
        this.position = new Point(startX, startY);
        this.velocity = new Point2D.Double(0, 0);
        random = new Random();

        this.health = random.nextDouble() * 50 + 25;
        this.hunger = 100;
        this.speed = random.nextDouble() * 15 + 5;
    }

    public void eat() {

    }

    public void die() {

    }

    public void move(ArrayList<Predator> predatorList) {
        /*
        Prey randomly moves (maybe force it to stop every so
        often to "eat", if it spots a predator (50?) pxs, run away
        If it gets away, go back to random movement
         */

        if (!predatorList.isEmpty()) {
            Predator closestPredator = findPredator(predatorList);
            flee(predatorList);
        } else {
            randomMove();
        }
        position.x += (int) velocity.getX();
        position.y += (int) velocity.getY();
    }

    private Predator findPredator(ArrayList<Predator> predatorList) {
        Predator closestPredator = null;
        return closestPredator;
    }

    private void flee(ArrayList<Predator> predatorList) {

    }

    private void randomMove() {

    }


    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(position.x, position.y, 10, 10);
    }
}
