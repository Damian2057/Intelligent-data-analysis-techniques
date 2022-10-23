package com.metaheuristics.simulation;

import com.metaheuristics.readers.json.JsonReader;
import com.metaheuristics.readers.json.SelectionType;
import com.metaheuristics.simulation.algorithm.Genetic;
import com.metaheuristics.simulation.algorithm.GeneticImpl;
import com.metaheuristics.simulation.factory.SpecimenFactory;
import com.metaheuristics.simulation.model.Specimen;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

public class AlgorithmImpl implements Algorithm {

    private final static Genetic genetic = new GeneticImpl();
    private final static SpecimenFactory factory = new SpecimenFactory();
    private final static SelectionType selectionType = JsonReader.getSelectionType();
    private final static int numberOfIterations = JsonReader.getNumberOfIterations();
    private final static int numberOfParents = JsonReader.getNumberOfParents();
    private static final int populationSize = JsonReader.getPopulationSize();
    private static final int display = JsonReader.getDisplay();
    private static final DecimalFormat decimalFormat = new DecimalFormat("########.#");
    private List<Specimen> generation;
    private Specimen bestSample;
    private final Logger logger = Logger.getLogger(Algorithm.class.getSimpleName());
    private final Function<Specimen, Specimen> fineTheBest = best -> best.getAdaptation() > bestSample.getAdaptation() ? best : bestSample;


    /**
     * Constructor
     * Generating initial population
     * calculate adaptation for a whole generation
     */
    public AlgorithmImpl() {
        this.generation = new ArrayList<>(factory.getSpecimens());
        genetic.adaptationFunction(generation);
        bestSample = genetic.getTheBestSpecimen(generation);
    }

    @Override
    public void startSimulation() {
        logger.info("The simulation has started");
        for (int i = 0; i < numberOfIterations; i++) {
            if(i % display == 0) {
                logger.info("Round of simulation number: " + i);
                logger.info("Current the best adaptation: " + decimalFormat.format(bestSample.getAdaptation()));
            }
            //parents' choice
            List<Specimen> parents = selectionType == SelectionType.ROULETTE ?
                    genetic.rouletteSelection(generation, numberOfParents)
                    : genetic.tournamentSelection(generation, numberOfParents);
            //crossing genes
            this.generation = genetic.crossGenes(parents, populationSize);
            //calculate adaptation for a whole generation
            genetic.adaptationFunction(generation);
            bestSample = fineTheBest.apply(genetic.getTheBestSpecimen(generation));
        }

        logger.info("The simulation is over, the result is a backpack:\n"
                + bestSample.toString()
                + "\nContents: \n"
                + genetic.interpretThings(bestSample.getGens()));
    }

    @Override
    public List<Specimen> getGeneration() {
        return generation;
    }

}
