package org.particle;

import org.particle.config.Config;
import org.particle.simulation.AlgorithmImpl;

public class MainApp {

    private static final int START_TIME = Config.getProperties().getStartTimes();

    public static void main(String[] args) {
        for (int i = 0; i < START_TIME; i++) {
            new Thread(new AlgorithmImpl()).start();
        }
    }
}
