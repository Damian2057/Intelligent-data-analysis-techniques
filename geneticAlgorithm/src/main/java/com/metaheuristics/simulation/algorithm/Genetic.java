package com.metaheuristics.simulation.algorithm;

import com.metaheuristics.simulation.model.Specimen;

import java.util.List;

public interface Genetic {

    /**
     * @param specimen
     * @return the value of an individual's adaptation
     */
    double adaptationFunction(Specimen specimen);

    /**
     * calculate adaptation for a whole generation
     * @param generation
     */
    void adaptationFunction(List<Specimen> generation);

    /**
     *
     * @param generation
     * @param numberOfParents
     * @return
     */
    List<Specimen> rouletteSelection(List<Specimen> generation, int numberOfParents);

    /**
     *
     * @param generation
     * @param numberOfParents
     * @return
     */
    List<Specimen> rankingSelection(List<Specimen> generation, int numberOfParents);
}
