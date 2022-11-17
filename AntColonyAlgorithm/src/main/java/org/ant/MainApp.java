package org.ant;

import org.ant.config.Config;
import org.ant.simulation.AlgorithmImpl;

public class MainApp {
    public static void main(String[] args) {
        for (int i = 0; i < Config.getProperties().getStartTimes(); i++) {
            new Thread(new AlgorithmImpl()).start();
        }
    }
}
