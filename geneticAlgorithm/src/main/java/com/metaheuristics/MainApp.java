package com.metaheuristics;


import com.metaheuristics.simulation.algorithm.GeneticAlgorithm;
import com.metaheuristics.simulation.algorithm.GeneticAlgorithmImpl;

public class MainApp {

    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithmImpl();
        geneticAlgorithm.startSimulation();
        System.out.println(geneticAlgorithm.getGeneration());
    }
}
