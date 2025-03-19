import java.awt.*;
import javax.swing.*;

public class BlueSquare {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 500));

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.setVisible(true);
    }

    private static class GamePanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.blue);
            g.fillRect(100, 200, 50, 50);
        }

    }

}
