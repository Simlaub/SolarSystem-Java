import java.awt.BorderLayout;
import java.awt.Color;

import display.Frame;
import solarsystem.SolarSystem;

public class App {
    public static void main(String[] args) {
        Frame frame = new Frame("Solar System", true);
        SolarSystem solarSystem = new SolarSystem(900, 900);

        frame.add(solarSystem, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);

        
        solarSystem.addPlanet(0, 0, 100, 20.0f, 0.0f,  0.0f, Color.YELLOW, "Sun");

        /*
        solarSystem.addPlanet(800, 0, 25, 2.0f, 0.0f, 1.0f, Color.BLUE, "Earth");
        solarSystem.addPlanet(-800, 0, 25, 2.0f, 0.0f, -1.0f, Color.BLUE, "Earth1");
        */

        solarSystem.addPlanet(800, 0, 20, 2.5f,  0.0f,  1.0f, Color.BLUE, "Earth");
        solarSystem.addPlanet(740, 0,  5, 2.0f,  0.0f,  1.2f, Color.RED, "Moon");

        /*
        solarSystem.addPlanet( 500, 0, 100, 20.0f, 0.0f, -1.0f, Color.BLUE, "Star");
        solarSystem.addPlanet(0, 0, 10, 1000.0f, 0, 0, Color.DARK_GRAY, "BlackHole");
        */

        solarSystem.start();
    }
}
