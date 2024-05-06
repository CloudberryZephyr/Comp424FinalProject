import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraphicsWindow extends JPanel {

    private float cameraX = 30;
    private float cameraY = 0;
    private boolean forward, backward, left, right;
    private static final float CAMERA_MOVE_SPEED = 5f;
    private float scale = 1;

    private long lastRepaint = System.currentTimeMillis();
    public static void main(String[] args) {
        GraphicsWindow w = new GraphicsWindow();
        while (true){
            w.repaint();
        }
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

        addKeyListener(new KeyInput());
        addMouseWheelListener(new MouseWheelInput());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        long currentTime = System.currentTimeMillis();
        float deltaTime = (currentTime-lastRepaint)*0.01f;
        lastRepaint = currentTime;

        if (forward) cameraY -= CAMERA_MOVE_SPEED*deltaTime/scale;
        if (backward) cameraY += CAMERA_MOVE_SPEED*deltaTime/scale;
        if (left) cameraX -= CAMERA_MOVE_SPEED*deltaTime/scale;
        if (right) cameraX += CAMERA_MOVE_SPEED*deltaTime/scale;


        //TODO get info from objects
        float scaledX = 0*scale;
        float scaledY = 0*scale;
        float scaledR = 50*scale;

        // Converts graphics into more powerful graphics
        Graphics2D g2 = (Graphics2D) g;

        // Translates the graphics space so that 0,0 is in the center of the screen
        g2.translate( getWidth()/2-(cameraX*scale), getHeight()/2-(cameraY*scale) );

        // Draw Objects
        g2.setColor(Color.YELLOW);

        g2.translate( scaledX-scaledR , scaledY-scaledR );

        g2.fillOval(
                (int)(0),
                (int)(0),
                (int)(2*scaledR),
                (int)(2*scaledR)
        );

        g2.translate( scaledR-scaledX, scaledR-scaledY );

        g2.setColor(Color.BLACK);
        drawTextCentered(g2,"The Sun",0,0);

    }

    /**
     * Draws text centered on (x,y)
     * Adapted from https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
     * @param g the graphics context to draw on
     * @param text the string to render
     * @param x center x coordinate
     * @param y center y coordinate
     */
    private void drawTextCentered(Graphics2D g, String text, int x, int y){

        //g.getFont().getSize();

        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(g.getFont());

        // Determine the X coordinate for the text
        int centerX = x - (metrics.stringWidth(text)) / 2;

        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int centerY = y - (metrics.getHeight() / 2) + metrics.getAscent();

        // Draw the String
        g.drawString(text, centerX, centerY);
    }

    private class KeyInput extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_W:
                    forward = true;
                    break;
                case KeyEvent.VK_S:
                    backward = true;
                    break;
                case KeyEvent.VK_A:
                    left = true;
                    break;
                case KeyEvent.VK_D:
                    right = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_W:
                    forward = false;
                    break;
                case KeyEvent.VK_S:
                    backward = false;
                    break;
                case KeyEvent.VK_A:
                    left = false;
                    break;
                case KeyEvent.VK_D:
                    right = false;
            }
        }
    }


    private class MouseWheelInput implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            scale += (float)(e.getPreciseWheelRotation());
            scale = Math.max(scale,0.25f);
        }
    }
}
