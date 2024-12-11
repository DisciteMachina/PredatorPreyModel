import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Predator and Prey Model");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Board board = new Board(5, 10);
        frame.add(board);

        frame.pack();
        frame.setVisible(true);

        board.start();
    }
}
