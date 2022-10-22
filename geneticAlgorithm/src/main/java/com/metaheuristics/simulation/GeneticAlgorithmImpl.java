package com.metaheuristics.simulation;

import com.metaheuristics.simulation.algorithm.Genetic;
import com.metaheuristics.simulation.algorithm.GeneticImpl;
import com.metaheuristics.simulation.factory.SpecimenFactory;
import com.metaheuristics.simulation.model.Specimen;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithmImpl implements GeneticAlgorithm {

    private final List<Specimen> generation;
    private final Genetic genetic;

    /**
     * Constructor
     * Generating initial population
     */
    public GeneticAlgorithmImpl() {
        this.genetic = new GeneticImpl();
        this.generation = new ArrayList<>(SpecimenFactory.getSpecimens());
        genetic.adaptationFunction(generation);
    }

    @Override
    public void startSimulation() {

    }

    @Override
    public List<Specimen> getGeneration() {
        return generation;
    }
}
