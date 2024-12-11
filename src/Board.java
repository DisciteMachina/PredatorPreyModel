import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

/*
TODO:
 Add spawning food for prey to collect.
 Make health go down in increments rather than at once
 Predators shouldn't be going after prey if their hunger is >70
 Breeding
 Aging
 If hunger level is high, predator should chase for longer
 Improve random move, i.e:
 move towards areas with more prey, or
 move in places that it hasn't been to
 stamina
 stop prey from bunching in corner
 natural healing over time if hunger > 80 for both
 differentiate between male and female pred and prey (?)
 different types of predators?
 predators that attack predators?
 fights?
 im getting way ahead of myself
 */


public class Board extends JPanel {
    private final int width = 700;
    private final int height = 700;
    private final Color boardColor;

    private Simulation simulation;
    private ArrayList<Predator> predators;
    private ArrayList<Prey> prey;

    public Board(int initialPredators, int initialPrey) {
        this.setPreferredSize(new Dimension(710, 710));
        this.boardColor = new Color(135, 177, 115);
        this.setBackground(Color.BLACK);

        // Initialize simulation
        initialize(initialPredators, initialPrey);

        // Key listener for restarting the simulation
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {  // 'R' key to restart simulation
                    initialize(initialPredators, initialPrey);
                }
            }
        });
        this.setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawEnvironment(g);
        drawGrid(g);

        for (Predator p : predators) p.draw(g);
        for (Prey p : prey) p.draw(g);
    }

    private void drawEnvironment(Graphics g) {
        g.setColor(boardColor);
        g.fillRect(0, 0, width, height);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.GRAY);
        int cellSize = 50;

        for (int i = 0; i < width; i += cellSize) {
            g.drawLine(i, 0, i, height);
        }

        for (int i = 0; i < height; i += cellSize) {
            g.drawLine(0, i, width, i);
        }
    }

    public void start() {
        Timer timer = new Timer(20, e -> update());
        timer.start();
    }

    private void update() {
        // Temporary lists to hold predators and prey to remove
        ArrayList<Predator> predatorsToRemove = new ArrayList<>();
        ArrayList<Prey> preyToRemove = new ArrayList<>();

        for (Predator p : predators) {
            p.move(prey, width, height);
            if (p.getHealth() <= 0) {
                predatorsToRemove.add(p);
            }
        }

        for (Prey p : prey) {
            p.move(predators, width, height);
            if (p.getHealth() <= 0) {
                preyToRemove.add(p);
            }
        }

        // Remove predators and prey after loops
        predators.removeAll(predatorsToRemove);
        prey.removeAll(preyToRemove);

        checkCollisions();
        repaint();
    }

    private void checkCollisions() {
        for (Predator predator : predators) {
            Iterator<Prey> preyIterator = prey.iterator();
            while (preyIterator.hasNext()) {
                Prey p = preyIterator.next();
                if (predator.collidesWith(p)) {
                    predator.eat();
                    preyIterator.remove();
                }
            }
        }
    }

    // Initialize
    private void initialize(int initialPredators, int initialPrey) {
        simulation = new Simulation(initialPredators, initialPrey, width, height);
        predators = simulation.getPredators();
        prey = simulation.getPrey();
        repaint();
    }
}
