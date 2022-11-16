package org.ant;

import org.ant.simulation.algorithm.Algorithm;
import org.ant.simulation.algorithm.AlgorithmImpl;

public class MainApp {
    public static void main(String[] args) {
        Algorithm algorithm = new AlgorithmImpl();
        algorithm.run();
    }
}
