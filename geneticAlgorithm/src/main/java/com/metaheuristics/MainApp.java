package com.metaheuristics;


import com.metaheuristics.simulation.GeneticAlgorithm;
import com.metaheuristics.simulation.GeneticAlgorithmImpl;

public class MainApp {

    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithmImpl();
        geneticAlgorithm.startSimulation();
//        System.out.println(geneticAlgorithm.getGeneration());
    }
}
