package org.ant;

import org.ant.simulation.AlgorithmImpl;

public class MainApp {
    public static void main(String[] args) {
        new Thread(new AlgorithmImpl()).start();
    }
}
