import javax.swing.*;
import java.awt.*;

public class MazePanel extends JPanel {
    static int mazeHeight = MazeGenerator.mazeHeight, mazeWidth = MazeGenerator.mazeWidth;
    static int CELL_SIZE = MazeGenerator.CELL_SIZE;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, mazeWidth * CELL_SIZE, mazeHeight * CELL_SIZE); // Fill the entire maze with black

        for (int i = 0; i < mazeHeight; i++) {
            for (int j = 0; j < mazeWidth; j++) {
                if (MazeGenerator.maze[i][j] != 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(j * CELL_SIZE + 1, i * CELL_SIZE + 1, CELL_SIZE-2, CELL_SIZE-2); // Fill in path with white
                    g.setColor(Color.BLACK);
                    if (i == MazeGenerator.currentY && j == MazeGenerator.currentX) {
                        g.setColor(Color.RED);
                        g.fillOval(j * CELL_SIZE + 10, i * CELL_SIZE + 10, CELL_SIZE-20, CELL_SIZE-20); // Example: draw a red circle
                        g.setColor(Color.BLACK);
                    }
                    if(MazeGenerator.maze[i][j] == MazeCell.TOP && i != 0){
                        g.setColor(Color.WHITE);
                        g.fillRect((j * CELL_SIZE)+1, (i * CELL_SIZE) -2, CELL_SIZE-2, 3);
                    }
                    if(MazeGenerator.maze[i][j] == MazeCell.RIGHT){
                        g.setColor(Color.WHITE);
                        g.fillRect((j * CELL_SIZE)+CELL_SIZE-1, (i * CELL_SIZE)+1 , 3, CELL_SIZE-2);
                    }
                    if(MazeGenerator.maze[i][j] == MazeCell.BOTTOM){
                        g.setColor(Color.WHITE);
                        g.fillRect((j * CELL_SIZE)+1, (i * CELL_SIZE) +CELL_SIZE-1, CELL_SIZE-2, 3);
                    }
                    if(MazeGenerator.maze[i][j] == MazeCell.LEFT){
                        g.setColor(Color.WHITE);
                        g.fillRect((j * CELL_SIZE)-1, (i * CELL_SIZE) +1, 2, CELL_SIZE-2);
                    }
                }
            }
        }
    }
}
