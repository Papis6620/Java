import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MazeGenerator extends JPanel{
    static int mazeHeight = 10, mazeWidth = 10;
    static int CELL_SIZE = 30;
    static int[][] maze = new int[mazeHeight][mazeWidth]; // 0 -unvisited, 1-top, 2-right, 3- bottom, 4-left
    static Stack<Map<Integer, Integer>> stack = new Stack<>();
    static int visited = 1;
    static int currentX = 0, currentY = 0;

    static void setStartValues() {
        Map<Integer, Integer> startPosition = new HashMap<>();
        startPosition.put(currentX, currentY);
        maze[currentY][currentX] = 1;
        stack.push(startPosition);
    }
    static void generateMaze(MazeGenerator mazePanel) {
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
            int next = -1;
            if (!neighbors.isEmpty()) {
                next = neighbors.elementAt(rand.nextInt(neighbors.size()));
                switch (next) {
                    case 0:
                        currentX += 1;
                        direction = 4;
                        break;
                    case 1:
                        currentY += 1;
                        direction = 1;
                        break;
                    case 2:
                        currentX -= 1;
                        direction = 2;
                        break;
                    case 3:
                        currentY -= 1;
                        direction = 3;
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
                Thread.sleep(100); // Pause for 500 milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, mazeWidth * CELL_SIZE, mazeHeight * CELL_SIZE); // Fill the entire maze with black

        for (int i = 0; i < mazeHeight; i++) {
            for (int j = 0; j < mazeWidth; j++) {
                if (maze[i][j] != 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(j * CELL_SIZE + 1, i * CELL_SIZE + 1, CELL_SIZE-2, CELL_SIZE-2); // Fill in path with white
                    g.setColor(Color.BLACK);
                    if (i == currentY && j == currentX) {
                        g.setColor(Color.RED);
                        g.fillOval(j * CELL_SIZE + 10, i * CELL_SIZE + 10, CELL_SIZE-20, CELL_SIZE-20); // Example: draw a red circle
                        g.setColor(Color.BLACK);
                    }
                    if(maze[i][j] == 1 && i != 0){
                        g.setColor(Color.WHITE);
                        g.fillRect((j * CELL_SIZE)+1, (i * CELL_SIZE) -2, CELL_SIZE-2, 3);
                    }
                    if(maze[i][j] == 2){
                        g.setColor(Color.WHITE);
                        g.fillRect((j * CELL_SIZE)+CELL_SIZE-1, (i * CELL_SIZE)+1 , 3, CELL_SIZE-2);
                    }
                    if(maze[i][j] == 3){
                        g.setColor(Color.WHITE);
                        g.fillRect((j * CELL_SIZE)+1, (i * CELL_SIZE) +CELL_SIZE-1, CELL_SIZE-2, 3);
                    }
                    if(maze[i][j] == 4){
                        g.setColor(Color.WHITE);
                        g.fillRect((j * CELL_SIZE)-1, (i * CELL_SIZE) +1, 2, CELL_SIZE-2);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        MazeGenerator mazePanel = new MazeGenerator();
        frame.add(mazePanel);
        frame.setSize(mazeWidth * CELL_SIZE + 17, mazeHeight*CELL_SIZE + 40);
        frame.setTitle("Maze generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        setStartValues();
        generateMaze(mazePanel);
    }
}
