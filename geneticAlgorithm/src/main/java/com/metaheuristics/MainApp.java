package com.metaheuristics;

import com.metaheuristics.simulation.Algorithm;
import com.metaheuristics.simulation.AlgorithmImpl;

public class MainApp {

    public static void main(String[] args) {
        Algorithm algorithm = new AlgorithmImpl();
        algorithm.startSimulation();
    }

}
