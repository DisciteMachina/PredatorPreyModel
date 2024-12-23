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
    private final int preySize;
    private final Random random;

    public Prey(int startX, int startY) {
        random = new Random();
        this.position = new Point(startX, startY);
        this.velocity = new Point2D.Double(0, 0);

        this.health = random.nextDouble() * 50 + 25;
        this.hunger = 100;
        this.speed = random.nextDouble() * 2;
        this.preySize = 8;
    }

    public void eat() {
        hunger = Math.min(100, hunger + 10);
    }

    public void move(ArrayList<Predator> predatorList, int boardWidth, int boardHeight) {
        if (isDead()) {
            return;
        }

        if (isStarving()) {
            updateHealth();
        }

        if (!predatorList.isEmpty()) {
            flee(predatorList);
        } else {
            randomMove();
        }
        position.x += (int) velocity.getX();
        position.y += (int) velocity.getY();

        // Ensure prey stays within bounds
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
        // Reduce hunger over time
        hunger = Math.max(0, hunger - 0.05);
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

            double fleeDirectionX = dx / distance;
            double fleeDirectionY = dy / distance;

            double randomFactor = random.nextDouble() * 0.5 - 0.25;
            fleeDirectionX += randomFactor;
            fleeDirectionY += randomFactor;

            double fleeDirectionMagnitude = Math.sqrt(fleeDirectionX * fleeDirectionX + fleeDirectionY * fleeDirectionY);
            fleeDirectionX /= fleeDirectionMagnitude;
            fleeDirectionY /= fleeDirectionMagnitude;

            double randomSpeed = speed + (random.nextDouble() * 0.5 - 0.25);
            velocity.setLocation(fleeDirectionX * randomSpeed, fleeDirectionY * randomSpeed);
        }
    }

    private void randomMove() {
        do {
            velocity.setLocation(random.nextInt(3) - 1, random.nextInt(3) - 1);
        } while (velocity.getX() == 0 && velocity.getY() == 0);
        velocity.setLocation(velocity.getX() * speed, velocity.getY() * speed);
    }


    public boolean isStarving() {
        return hunger <= 0;
    }

    public void updateHealth() {
        this.health -= 0.05;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(position.x, position.y, 5, 5);
    }

    public Point getPosition() {
        return position;
    }

    public double getHealth() {
        return health;
    }

    public int getPreySize() {
        return preySize;
    }
}
