import java.util.ArrayList;
import java.util.Random;

public class Simulation {
    private ArrayList<Predator> predators;
    private ArrayList<Prey> prey;
    private Random random;

    public Simulation(int initialPredators, int initialPrey, int boardWidth, int boardHeight) {
        predators = new ArrayList<>();
        prey = new ArrayList<>();
        random = new Random();

        for (int i = 0; i < initialPredators; i++) {
            predators.add(new Predator(random.nextInt(boardWidth - 25), random.nextInt(boardHeight - 25)));
        }

        for (int i = 0; i < initialPrey; i++) {
            prey.add(new Prey(random.nextInt(boardWidth - 25), random.nextInt(boardHeight - 25)));
        }
    }

    public ArrayList<Predator> getPredators() {
        return predators;
    }

    public ArrayList<Prey> getPrey() {
        return prey;
    }

}
