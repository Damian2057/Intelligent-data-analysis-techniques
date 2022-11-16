package org.ant;

import org.ant.simulation.Algorithm;
import org.ant.simulation.AlgorithmImpl;

public class MainApp {
    public static void main(String[] args) {
        Algorithm algorithm = new AlgorithmImpl();
        algorithm.run();

    }
}
