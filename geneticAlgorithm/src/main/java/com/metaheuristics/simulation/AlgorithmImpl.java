package com.metaheuristics.simulation;

import com.metaheuristics.readers.json.JsonReader;
import com.metaheuristics.readers.json.SelectionType;
import com.metaheuristics.simulation.algorithm.Genetic;
import com.metaheuristics.simulation.algorithm.GeneticImpl;
import com.metaheuristics.simulation.factory.SpecimenFactory;
import com.metaheuristics.simulation.model.Specimen;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AlgorithmImpl implements Algorithm {

    private final static Genetic genetic = new GeneticImpl();
    private final static SpecimenFactory factory = new SpecimenFactory();
    private final static SelectionType selectionType = JsonReader.getSelectionType();
    private final static int numberOfIterations = JsonReader.getNumberOfIterations();
    private final static int numberOfParents = JsonReader.getNumberOfParents();
    private static final int populationSize = JsonReader.getPopulationSize();
    private static final int display = JsonReader.getDisplay();
    private List<Specimen> generation;
    private final Logger logger = Logger.getLogger(Algorithm.class.getSimpleName());

    /**
     * Constructor
     * Generating initial population
     * calculate adaptation for a whole generation
     */
    public AlgorithmImpl() {
        this.generation = new ArrayList<>(factory.getSpecimens());
        genetic.adaptationFunction(generation);
    }

    @Override
    public void startSimulation() {
        logger.info("The simulation has started");
        for (int i = 0; i < numberOfIterations; i++) {
            if(i % display == 0) {
                logger.info("Round of simulation number: " + i);
            }
            //parents' choice
            List<Specimen> parents = selectionType == SelectionType.ROULETTE ?
                    genetic.rouletteSelection(generation, numberOfParents)
                    : genetic.tournamentSelection(generation, numberOfParents);
            //crossing genes
            this.generation = genetic.crossGenes(parents, populationSize);
            //calculate adaptation for a whole generation
            genetic.adaptationFunction(generation);
        }

        logger.info("The simulation is over");
    }

    @Override
    public List<Specimen> getGeneration() {
        return generation;
    }
}
