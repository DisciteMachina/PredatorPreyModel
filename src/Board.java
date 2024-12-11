import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    private final int width = 700;
    private final int height = 700;
    private final Color boardColor;

    private final Simulation simulation;
    private final ArrayList<Predator> predators;
    private final ArrayList<Prey> prey;

    public Board(int initialPredators, int initialPrey) {
        this.setPreferredSize(new Dimension(width, height));
        this.boardColor = new Color(135, 177, 115);

        simulation = new Simulation(initialPredators, initialPrey, 700, 700);
        predators = simulation.getPredators();
        prey = simulation.getPrey();
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
        for (Predator p : predators) p.move(prey, width, height);
        for (Prey p : prey) p.move(predators, width, height);

        checkCollisions();
        repaint();
    }

    private void checkCollisions() {
        for (Predator predator : predators) {
            for (Prey prey : prey) {
                if (predator.collidesWith(prey)) {
                    prey.die();
                    predator.eat();
                }
            }
        }
    }
}
