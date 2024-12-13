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
    private final int predatorSize;
    private final Random random;

    private boolean chasing;
    private int chaseTime;

    public Predator(int startX, int startY) {
        random = new Random();
        this.position = new Point(startX, startY);
        this.velocity = new Point2D.Double(0, 0);

        this.health = random.nextDouble() * 50 + 25;
        this.hunger = 100;
        this.speed = random.nextDouble() * 2;
        this.predatorSize = 8;

        this.chasing = false;
        this.chaseTime = 0;
    }

    public void eat() {
        hunger = Math.min(100, hunger + 20);
    }

    public void move(ArrayList<Prey> preyList, int boardWidth, int boardHeight) {
        if (isDead()) {
            return;
        }

        if (isStarving()) {
            updateHealth();
        }

        if (!preyList.isEmpty()) {
            Prey closestPrey = findPrey(preyList);

            // Only chase if prey is close enough or the predator is hungry
            if (position.distance(closestPrey.getPosition()) < 100 || hunger < 50) {
                chasing = true;
                chasePrey(closestPrey);
                chaseTime = 30;
            } else {
                chasing = false;
            }
        } else {
            chasing = false;
        }

        // If not chasing, move randomly
        if (!chasing) {
            randomMove();
        }

        position.x += (int) velocity.getX();
        position.y += (int) velocity.getY();

        if (chaseTime > 0) chaseTime--;

        // Ensure predator stays within bounds of the board
        if (position.x < 0) {
            position.x = 0;
            velocity.setLocation(-velocity.getX(), velocity.getY());
        }
        if (position.x > boardWidth - predatorSize) {
            position.x = boardWidth - predatorSize;
            velocity.setLocation(-velocity.getX(), velocity.getY());
        }
        if (position.y < 0) {
            position.y = 0;
            velocity.setLocation(velocity.getX(), -velocity.getY());
        }
        if (position.y > boardHeight - predatorSize) {
            position.y = boardHeight - predatorSize;
            velocity.setLocation(velocity.getX(), -velocity.getY());
        }

        // Decrease hunger over time
        hunger = Math.max(0, hunger - 0.1);
    }

    private Prey findPrey(ArrayList<Prey> preyList) {
        Prey closestPrey = null;
        double closestDistance = Double.MAX_VALUE;

        for (Prey p : preyList) {
            double dist = position.distance(p.getPosition());
            if (dist < closestDistance) {
                closestDistance = dist;
                closestPrey = p;
            }
        }
        return closestPrey;
    }

    private void chasePrey(Prey prey) {
        double dx = prey.getPosition().x - position.x;
        double dy = prey.getPosition().y - position.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            velocity.setLocation((dx / distance) * speed, (dy / distance) * speed);
        }
    }

    private void randomMove() {
        do {
            velocity.setLocation(random.nextInt(3) - 1, random.nextInt(3) - 1);
        } while (velocity.getX() == 0 && velocity.getY() == 0);
        velocity.setLocation(velocity.getX() * speed, velocity.getY() * speed);
    }

    public boolean collidesWith(Prey prey) {
        int preySize = prey.getPreySize();

        return Math.abs(position.x - prey.getPosition().x) < (predatorSize / 2 + preySize / 2) &&
                Math.abs(position.y - prey.getPosition().y) < (predatorSize / 2 + preySize / 2);
    }

    public boolean isStarving() {
        return hunger <= 0;
    }

    public void updateHealth() {
        this.health -= 0.5;
    }

    public boolean isDead() {
        return health <= 0;
    }

    private Color getColorBasedOnHunger() {
        int red = 255;
        int green = (int) (hunger * 2.55);
        int blue = 0;
        return new Color(red, green, blue);
    }

    public Point getPosition() {
        return position;
    }

    public double getHealth() {
        return health;
    }

    public void draw(Graphics g) {
        g.setColor(getColorBasedOnHunger());
        g.fillRect(position.x, position.y, 5, 5);
    }
}
