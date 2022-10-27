package com.metaheuristics.simulation;

import com.metaheuristics.simulation.model.Specimen;

import java.util.List;

public interface Algorithm extends Runnable{

    /**
     * @return current generation
     */
    List<Specimen> getGeneration();
}
