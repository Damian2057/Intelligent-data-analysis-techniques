package com.metaheuristics.simulation.algorithm;

import com.metaheuristics.simulation.model.Specimen;

import java.util.List;

public interface Genetic {

    /**
     * @param specimen single specimen
     * @return the value of an individual's adaptation
     */
    double adaptationFunction(Specimen specimen);

    /**
     * calculate adaptation for a whole generation
     * @param generation whole generation
     */
    void adaptationFunction(List<Specimen> generation);

    /**
     * Roulette Selection
     * @param generation whole generation
     * @param numberOfParents number Of Parents
     * @return list of selected parents
     */
    List<Specimen> rouletteSelection(List<Specimen> generation, int numberOfParents);

    /**
     * Tournament Selection
     * @param generation whole generation
     * @param numberOfParents number Of Parents
     * @return list of selected parents
     */
    List<Specimen> tournamentSelection(List<Specimen> generation, int numberOfParents);

    /**
     * @param parents selected representatives of the species
     * @param populationSize number of Specimen to be produced
     */
    List<Specimen> crossOver(List<Specimen> parents, int populationSize);
}
