import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Predator {
    private final Point position;
    private final Point2D velocity;
    private double health;
    private double hunger;
    private final double speed;
    private final Random random;

    public Predator(int startX, int startY) {
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

    public void move(ArrayList<Prey> preyList) {
        /*
        Move randomly until its within distance of prey (50?) pxs
        When in distance, move directly toward prey
        If prey runs out of distance, keep predator moving for a while
        after a certain amount of time, predator goes back to randomly
        moving
         */

        if (!preyList.isEmpty()) {
            Prey closestPrey = findPrey(preyList);
            chasePrey(closestPrey);
        } else {
            randomMove();
        }
        position.x += (int) velocity.getX();
        position.y += (int) velocity.getY();
    }

    private Prey findPrey(ArrayList<Prey> preyList) {
        Prey closestPrey = null;
        return closestPrey;
    }

    private void chasePrey(Prey prey) {

    }

    private void randomMove() {

    }

    public boolean collidesWith(Prey prey) {
        return false;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(position.x, position.y, 10, 10);
    }
}
