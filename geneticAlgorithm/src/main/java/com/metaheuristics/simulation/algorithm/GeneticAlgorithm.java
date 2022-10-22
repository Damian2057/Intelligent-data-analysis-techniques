package com.metaheuristics.simulation.algorithm;

import com.metaheuristics.simulation.model.Specimen;

import java.util.List;

public interface GeneticAlgorithm {

    void startSimulation();
    List<Specimen> getGeneration();
}
