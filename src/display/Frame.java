package display;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Frame extends JFrame {

    public Frame(String title, int width, int height, boolean resizable) {
        this.setTitle(title);
        this.setSize(new Dimension(width, height));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(resizable);
        this.setVisible(true);
    }

    public Frame(String title, boolean resizable) {
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(resizable);
        this.setVisible(true);
    }

    
}