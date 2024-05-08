import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import scala.collection.immutable.List;

public class GraphicsWindow extends JPanel {

    private float cameraX = 0;
    private float cameraY = 0;
    private boolean forward, backward, left, right;
    private static final float CAMERA_MOVE_SPEED = 5f;
    private float scale = 1;

    private long lastRepaint = System.currentTimeMillis();
    private List<Body> objects = null;

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

    public void paint(List<Body> objects){
        this.objects = objects;
        repaint();
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

        // Converts graphics into more powerful graphics
        Graphics2D g2 = (Graphics2D) g;

        // Translates the graphics space so that 0,0 is in the center of the screen
        g2.translate( getWidth()/2-(cameraX*scale), getHeight()/2-(cameraY*scale) );

        if (objects == null){ continuel }
        for (int i = 0; i < objects.length(); i++){

            Body body = objects.apply(i);
            float scaledX = body.x()*scale;
            float scaledY = body.y()*scale;
            float scaledR = body.radius()*scale;

            if (cameraX*scale - getWidth()/2 > scaledX + scaledR ||
                cameraX*scale + getWidth()/2 < scaledX - scaledR ||
                cameraY*scale - getHeight()/2 > scaledY + scaledR ||
                cameraY*scale + getHeight()/2 < scaledY - scaledR
            ) {
                continue; // Object is off screen, dont take the time to render it
            }

            // Draw The object
            g2.setColor(body.color());
            g2.translate( scaledX-scaledR , scaledY-scaledR );
            g2.fillOval(
                    (int)(0),
                    (int)(0),
                    (int)(2*scaledR),
                    (int)(2*scaledR)
            );

            drawTextCentered(g2, body.label(), (int)scaledR, -10);

            g2.translate( scaledR-scaledX, scaledR-scaledY );

        }

        g2.setColor(Color.WHITE);
        g2.drawString(String.format("(%.2f,%.2f)",cameraX,cameraY),cameraX*scale-getWidth()/2 + 10, cameraY*scale-getHeight()/2 + 15);
        g2.drawString(String.format("Zoom: %.3fx",scale),cameraX*scale-getWidth()/2 + 10, cameraY*scale-getHeight()/2 + 30);
        g2.drawString(String.format("Simulation Speed: %.1fx",1f),cameraX*scale-getWidth()/2 + 10, cameraY*scale-getHeight()/2 + 45);

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
            scale = Math.max(scale,0.001f);
        }
    }
}
