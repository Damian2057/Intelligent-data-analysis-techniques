package org.particle;

import org.particle.simulation.AlgorithmImpl;

public class MainApp {
    public static void main(String[] args) {
        new Thread(new AlgorithmImpl()).start();
    }
}
