package solarsystem;

import java.util.HashSet;

import javax.swing.event.MouseInputListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import display.Canvas;


public class SolarSystem extends Canvas implements ComponentListener, MouseWheelListener, MouseInputListener {
    private AffineTransform at = new AffineTransform();

    private final float gravConst = 0.001f;  
    private HashSet<Planet> planets;

    public SolarSystem(int width, int height) {
        super(width, height, Color.BLACK);

        planets = new HashSet<Planet>();

        initTransform();

        this.addMouseWheelListener(this);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addComponentListener(this);

    }

    private void initTransform() {
        at.translate(this.getWidth() / 2, this.getHeight() / 2);
        at.scale(1.0f, 1.0f);
    }

    public AffineTransform getTransform() {
        return at;
    }

    public void translateTo(float x, float y) {
        float scale = (float) at.getScaleX();

        x = (float) (x - at.getTranslateX()) / scale;
        y = (float) (y - at.getTranslateY()) / scale;

        at.translate(x, y);
    }

    private void translateToCenter() {
        at.translate(this.getWidth() / 2 - at.getTranslateX(), this.getHeight() / 2 - at.getTranslateY());
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setTransform(at);

        this.drawCoordSystem(g2, Color.GRAY, 150, false);

        for (Planet planet : planets) {
            planet.draw(g2);
        }
        
        g2.dispose();
    }

    @Override
    protected void update() {
        for (Planet attractor : planets) {
            for (Planet mover : planets) {
                if (mover.getName() == attractor.getName())
                    continue;

                attractor.attract(mover, gravConst);
                mover.update(maxFps / updatesPerSecond);
            }
        }
    }

    public void addPlanet(int x, int y, int size, float density, float xVel, float yVel, Color color, String name) {
        Planet planet = new Planet(x, y, size, density, xVel, yVel, color, name, this);
        planets.add(planet);

        this.addMouseListener(planet);
        this.addMouseMotionListener(planet);
    }

    public Planet getPlanet(String name) {
        for (Planet planet : planets) {
            if (planet.getName() == name)
                return planet;
        }

        return null;
    }


    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();

        float scale = (float) at.getScaleX();

        at.scale(1 / at.getScaleX(), 1 / at.getScaleY());

        if (rotation < 0 && scale <= 3.0f) {
            scale += 0.1f;
        } else if (rotation > 0 && scale >= 0.2f) {
            scale -= 0.1f;
        }

        at.scale(scale, scale);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    int prevX, prevY;

    @Override
    public void mousePressed(MouseEvent e) {
        prevX = e.getX();
        prevY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {    
        int x = e.getX();
        int y = e.getY();

        int dx = prevX - x;
        int dy = prevY - y;

        at.translate(-dx / at.getScaleX(), -dy / at.getScaleY());

        prevX = x;
        prevY = y;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void componentResized(ComponentEvent e) {
        translateToCenter();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
