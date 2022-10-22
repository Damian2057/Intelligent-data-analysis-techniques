package com.metaheuristics.simulation;

import com.metaheuristics.readers.json.JsonReader;
import com.metaheuristics.simulation.algorithm.Genetic;
import com.metaheuristics.simulation.algorithm.GeneticImpl;
import com.metaheuristics.simulation.factory.SpecimenFactory;
import com.metaheuristics.simulation.model.Specimen;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithmImpl implements GeneticAlgorithm {

    private final List<Specimen> generation;
    private final static Genetic genetic = new GeneticImpl();
    private final static SpecimenFactory factory = new SpecimenFactory();
    private final static int numberOfIterations = JsonReader.getNumberOfIterations();

    /**
     * Constructor
     * Generating initial population
     * calculate adaptation for a whole generation
     */
    public GeneticAlgorithmImpl() {
        this.generation = new ArrayList<>(factory.getSpecimens());
        genetic.adaptationFunction(generation);
    }

    @Override
    public void startSimulation() {
        for (int i = 0; i < numberOfIterations; i++) {
            //TODO: evolutionary loop
        }
    }

    @Override
    public List<Specimen> getGeneration() {
        return generation;
    }
}
