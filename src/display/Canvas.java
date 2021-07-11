package display;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JPanel;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Canvas extends JPanel implements Runnable {
    private Thread thread;

    protected int maxFps = 75;
    protected int updatesPerSecond = 75;

    private Timer drawTimer = new Timer();
    private Timer updateTimer = new Timer();

    public Canvas(int width, int height, Color bg) {
        this.setPreferredSize(new Dimension(width, height));
        this.setSize(new Dimension(width, height));

        this.setBackground(bg);
    }


    public void drawCoordSystem(Graphics2D g2, Color color, int gridSize, boolean fullGrid) {
        AffineTransform transform = g2.getTransform();
        
        
        float xScale = (float) transform.getScaleX();
        float yScale = (float) transform.getScaleY();

        int xTrans = (int) (transform.getTranslateX() / xScale);
        int yTrans = (int) (transform.getTranslateY() / yScale);
        
        int width = (int) (this.getWidth() / xScale);
        int height = (int) (this.getHeight() / yScale);

        g2.setColor(color);
        g2.drawLine(-xTrans, 0, width - xTrans, 0);
        g2.drawLine(0, -yTrans, 0, height - yTrans);

        int gridMarkerLength = (int) (10 / (xScale * 2));

        if (fullGrid)
            gridMarkerLength = height / 2;

        for (int i = 0; i < xTrans; i++) {
            int x = -i * gridSize;
            g2.drawLine(x, -gridMarkerLength, x, gridMarkerLength);
        }

        for (int i = 0; i < width - xTrans; i++) {
            int x = i * gridSize;
            g2.drawLine(x, -gridMarkerLength, x, gridMarkerLength);
        }

        if (fullGrid)
            gridMarkerLength = width / 2;

        for (int i = 0; i < yTrans; i++) {
            int y = -i * gridSize;
            g2.drawLine(-gridMarkerLength, y, gridMarkerLength, y);
        }

        for (int i = 0; i < height - yTrans; i++) {
            int y = i * gridSize;
            g2.drawLine(-gridMarkerLength, y, gridMarkerLength, y);
        }

    }

    protected abstract void update();

    public void start() {
        thread = new Thread(this, "Renderer");
        thread.start();
    }

    public void stop() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        drawTimer.scheduleAtFixedRate(drawTask, 0, 1000 / maxFps);
        updateTimer.scheduleAtFixedRate(updateTask, 0, 1000 / updatesPerSecond);
    }

    private TimerTask drawTask = new TimerTask(){

        @Override
        public void run() {
            repaint();
        }
        
    };

    private TimerTask updateTask = new TimerTask() {

        @Override
        public void run() {
            update();
        }

    };

}
