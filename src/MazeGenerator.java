import javax.swing.*;
import java.util.*;

public class MazeGenerator {
    static int mazeHeight, mazeWidth, CELL_SIZE;
    static int[][] maze;
    static Stack<Map<Integer, Integer>> stack = new Stack<>();
    static int visited = 1;
    static int currentX = 0, currentY = 0;

    public static void main(String[] args) {
        mazeWidth = Integer.parseInt(JOptionPane.showInputDialog("Enter maze width:"));
        mazeHeight = Integer.parseInt(JOptionPane.showInputDialog("Enter maze height:"));
        CELL_SIZE = Integer.parseInt(JOptionPane.showInputDialog("Enter cell size(>= 30):"));

        // Initialize maze array
        maze = new int[mazeHeight][mazeWidth];
        JFrame frame = new JFrame();
        MazePanel mazePanel = new MazePanel();
        frame.add(mazePanel);
        frame.setSize(mazeWidth * CELL_SIZE + 17, mazeHeight * CELL_SIZE + 40);
        frame.setTitle("Maze generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        setStartValues();
        generateMaze(mazePanel);
    }

    static void setStartValues() {
        Map<Integer, Integer> startPosition = new HashMap<>();
        startPosition.put(currentX, currentY);
        maze[currentY][currentX] = MazeCell.TOP;
        stack.push(startPosition);
    }

    static void generateMaze(MazePanel mazePanel) {
        while (visited < mazeHeight * mazeWidth) {
            Vector<Integer> neighbors = new Vector<>();
            Map<Integer, Integer> position;
            int direction = 0;
            position = stack.peek();
            currentX = position.keySet().iterator().next();
            currentY = position.get(currentX);
            if (currentX < mazeWidth - 1 && maze[currentY][currentX + 1] == 0) { // right
                neighbors.add(0);
            }
            if (currentY < mazeHeight - 1 && maze[currentY + 1][currentX] == 0) { // down
                neighbors.add(1);
            }
            if (currentX > 0 && maze[currentY][currentX - 1] == 0) { // left
                neighbors.add(2);
            }
            if (currentY > 0 && maze[currentY - 1][currentX] == 0) { // up
                neighbors.add(3);
            }
            Random rand = new Random();
            int next;
            if (!neighbors.isEmpty()) {
                next = neighbors.elementAt(rand.nextInt(neighbors.size()));
                switch (next) {
                    case 0:
                        currentX += 1;
                        direction = MazeCell.LEFT;
                        break;
                    case 1:
                        currentY += 1;
                        direction = MazeCell.TOP;
                        break;
                    case 2:
                        currentX -= 1;
                        direction = MazeCell.RIGHT;
                        break;
                    case 3:
                        currentY -= 1;
                        direction = MazeCell.BOTTOM;
                        break;
                    default:
                        break;
                }
                Map<Integer, Integer> newPosition = new HashMap<>();
                newPosition.put(currentX, currentY);
                stack.push(newPosition);
                maze[currentY][currentX] = direction;
                visited++;
            } else {
                stack.pop();
            }
            mazePanel.repaint();
            try {
                Thread.sleep(100); // Pause for 100 milliseconds for better visualization
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
