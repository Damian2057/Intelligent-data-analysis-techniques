package com.metaheuristics.simulation;

import com.metaheuristics.simulation.model.Specimen;

import java.util.List;

public interface Algorithm {

    /**
     * evolutionary loop
     */
    void startSimulation();

    /**
     * @return current generation
     */
    List<Specimen> getGeneration();
}
