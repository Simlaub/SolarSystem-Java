package solarsystem;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import javax.swing.event.MouseInputListener;

import java.awt.Color;

public class Planet implements MouseInputListener {
    private float x, y, size;
    private float mass;
    private float xVel, yVel;
    private float xAcc, yAcc;

    private Color color;
    private String name;

    private SolarSystem solarSystem;

    private boolean hover = false, selected = false;

    public Planet(int x, int y, int size, float density, float xVel, float yVel, Color color, String name, SolarSystem solarSystem) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.xVel = xVel;
        this.yVel = yVel;

        this.xAcc = 0;
        this.yAcc = 0;

        this.color = color;
        this.name = name;

        this.mass = size * density;

        this.solarSystem = solarSystem;
    }

    public void draw(Graphics2D g2) {
        if (this.hover)
            g2.setColor(color.darker());
        else
            g2.setColor(color);
        
        g2.fillOval((int) (x - size), (int) (y - size), (int) (size * 2), (int) (size * 2));
        
        if (this.selected) {
            g2.setStroke(new BasicStroke(this.size / 20));
            g2.setColor(Color.WHITE);    
            g2.drawOval((int) (x - size), (int) (y - size), (int) (size * 2), (int) (size * 2));
        }
    }

    public void update(float period) {
        if (period < 1.0f)
            period = 1.0f;
            
        this.xVel += this.xAcc / period;
        this.yVel += this.yAcc / period;

        this.x += xVel / period;
        this.y += yVel / period;

        if (this.selected)
            solarSystem.translateTo(-this.x, -this.y);
    }

    public void applyForce(float force, float xDir, float yDir) {
        xAcc = (force * xDir) / mass;
        yAcc = (force * yDir) / mass;
    }

    public void attract(Planet mover, float gravConst) {
        float distSquared = (float) (Math.pow(Math.abs(this.x - mover.x) , 2) + Math.pow(Math.abs(this.y - mover.y) , 2));
        float force = gravConst * (this.mass * mover.mass) / distSquared;


        float xDir = (float) (this.x - mover.x);
        float yDir = (float) (this.y - mover.y);

        mover.applyForce(force, xDir, yDir);
    }

    public float getX() { return x; };
    public float getY() { return y; };
    public String getName() { return this.name; }

    @Override
    public void mouseClicked(MouseEvent e) {
        AffineTransform at = solarSystem.getTransform();

        float scale = (float) at.getScaleX();

        float xTrans = (float) at.getTranslateX() / scale;
        float yTrans = (float) at.getTranslateY() / scale;

        float mouseX = e.getX() / scale - xTrans;
        float mouseY = e.getY() / scale - yTrans;

        float planetX = this.x;
        float planetY = this.y;

        float distToMouseSquared = Math
                .abs((planetX - mouseX) * (planetX - mouseX) + (planetY - mouseY) * (planetY - mouseY));

        if (distToMouseSquared <= (this.size) * (this.size))
            this.selected = true;
        else
            this.selected = false;

    }

    @Override
    public void mousePressed(MouseEvent e) {

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


    }

    @Override
    public void mouseMoved(MouseEvent e) {
        AffineTransform at = solarSystem.getTransform();

        float scale = (float) at.getScaleX();

        float xTrans = (float) at.getTranslateX() / scale;
        float yTrans = (float) at.getTranslateY() / scale;

        float mouseX = e.getX() / scale - xTrans;
        float mouseY = e.getY() / scale - yTrans;

        float planetX = this.x;
        float planetY = this.y;

        float distToMouseSquared = Math.abs((planetX - mouseX) * (planetX - mouseX) + (planetY - mouseY) * (planetY - mouseY)); 

        if (distToMouseSquared <= (this.size) * (this.size))
            this.hover = true;
        else
            this.hover = false;

    };
}
