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
    private int preySize;
    private final Random random;

    public Prey(int startX, int startY) {
        random = new Random();
        this.position = new Point(startX, startY);
        this.velocity = new Point2D.Double(0, 0);

        this.health = random.nextDouble() * 50 + 25;
        this.hunger = 100;
        this.speed = random.nextDouble() * 3 + 1;
        this.preySize = 10;
    }

    public void eat(Predator predator, ArrayList<Prey> preyList) {
    }

    public void die() {
    }

    public void move(ArrayList<Predator> predatorList, int boardWidth, int boardHeight) {
        if (!predatorList.isEmpty()) {
            flee(predatorList);
        } else {
            randomMove();
        }
        position.x += (int) velocity.getX();
        position.y += (int) velocity.getY();

        // Ensure predator stays within bounds
        if (position.x < 0) {
            position.x = 0;
            velocity.setLocation(-velocity.getX(), velocity.getY());
        }
        if (position.x > boardWidth - preySize) {
            position.x = boardWidth - preySize;
            velocity.setLocation(-velocity.getX(), velocity.getY());
        }
        if (position.y < 0) {
            position.y = 0;
            velocity.setLocation(velocity.getX(), -velocity.getY());
        }
        if (position.y > boardHeight - preySize) {
            position.y = boardHeight - preySize;
            velocity.setLocation(velocity.getX(), -velocity.getY());
        }
    }

    private Predator findPredator(ArrayList<Predator> predatorList) {
        Predator closestPredator = null;
        double closestDistance = Double.MAX_VALUE;

        for (Predator p : predatorList) {
            double dist = position.distance(p.getPosition());
            if (dist < closestDistance) {
                closestDistance = dist;
                closestPredator = p;
            }
        }
        return closestPredator;
    }

    private void flee(ArrayList<Predator> predatorList) {
        Predator closestPredator = findPredator(predatorList);
        if (closestPredator != null) {
            double dx = position.x - closestPredator.getPosition().x;
            double dy = position.y - closestPredator.getPosition().y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            velocity.setLocation((dx / distance) * speed, (dy / distance) * speed);
        }
    }

    private void randomMove() {
        do {
            velocity.setLocation(random.nextInt(3) - 1, random.nextInt(3) - 1);
        } while (velocity.getX() == 0 && velocity.getY() == 0);
        velocity.setLocation(velocity.getX() * speed, velocity.getY() * speed);
    }

    public boolean collidesWith(Predator predator) {
        int predatorSize = 10;
        int preySize = 10;

        return Math.abs(position.x - predator.getPosition().x) < (predatorSize / 2 + preySize / 2) &&
                Math.abs(position.y - predator.getPosition().y) < (predatorSize / 2 + preySize / 2);
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(position.x, position.y, 10, 10);
    }

    public Point getPosition() {
        return position;
    }

    public void setHealth() {
        health -= 10;
        if (health == 0){
            die();
        }
    }
}
