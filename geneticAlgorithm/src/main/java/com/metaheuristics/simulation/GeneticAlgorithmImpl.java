package com.metaheuristics.simulation;

import com.metaheuristics.readers.Const;
import com.metaheuristics.readers.json.CrossOver;
import com.metaheuristics.readers.json.JsonReader;
import com.metaheuristics.readers.json.SelectionType;
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
    private final static SelectionType selectionType = JsonReader.getSelectionType();
    private final static CrossOver crossOver = JsonReader.getCrossOverProperties();
    private final static int numberOfIterations = JsonReader.getNumberOfIterations();
    private final static int numberOfParents = JsonReader.getNumberOfParents();

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
            //parents' choice
            List<Specimen> parents = selectionType == SelectionType.ROULETTE ?
                    genetic.rouletteSelection(generation, numberOfParents)
                    : genetic.rankingSelection(generation, numberOfParents);

        }
    }

    @Override
    public List<Specimen> getGeneration() {
        return generation;
    }
}
