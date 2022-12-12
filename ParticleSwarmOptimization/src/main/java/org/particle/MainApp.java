package org.particle;

import org.particle.config.Config;
import org.particle.simulation.AlgorithmImpl;

public class MainApp {

    private static final int startTimes = Config.getProperties().getStartTimes();

    public static void main(String[] args) {
        for (int i = 0; i < startTimes; i++) {
            new Thread(new AlgorithmImpl()).start();
        }
    }
}
