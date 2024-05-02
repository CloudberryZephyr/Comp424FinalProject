import javax.swing.*;
import java.awt.*;

public class GraphicsWindow extends JPanel {

    public static void main(String[] args) {
        new GraphicsWindow();
    }
    public GraphicsWindow(){
        JFrame frame = new JFrame("N-body simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setMinimumSize(new Dimension(900,600));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocus();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //Draw stuff here
        g2.translate(getWidth()/2,getHeight()/2);

        g2.setColor(Color.YELLOW);
        g2.fillOval(-25,-25,50,50);

        g2.setColor(Color.BLACK);
        drawTextCentered(g2,"The Sun",0,0);

    }

    private void drawTextCentered(Graphics2D g, String text, int x, int y){
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(g.getFont());

        // Determine the X coordinate for the text
        int centerX = x - (metrics.stringWidth(text)) / 2;

        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int centerY = y - (metrics.getHeight() / 2) + metrics.getAscent();

        // Draw the String
        g.drawString(text, centerX, centerY);
    }
}
