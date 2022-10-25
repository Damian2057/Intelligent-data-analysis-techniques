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
     * @return list of selected parents
     */
    List<Specimen> rouletteSelection(List<Specimen> generation);

    /**
     * Tournament Selection
     * @param generation whole generation
     * @return list of selected parents
     */
    List<Specimen> tournamentSelection(List<Specimen> generation);

    /**
     * @param parents selected representatives of the species
     */
    List<Specimen> crossGenes(List<Specimen> parents);

    /**
     * @param generation whole generation
     * @return the best Specimen among the whole species
     */
    Specimen getTheBestSpecimen(List<Specimen> generation);

    /**
     * @param chromosome a list showing whether the item is present or not
     * @return verbal interpretation of the items
     */
    String interpretThings(List<Integer> chromosome);

    /**
     * @param generation actual generation
     * @param parents parents
     * @param kids kids
     * @return new generation
     */
    List<Specimen> createNewGeneration(List<Specimen> generation, List<Specimen> parents, List<Specimen> kids);
}
