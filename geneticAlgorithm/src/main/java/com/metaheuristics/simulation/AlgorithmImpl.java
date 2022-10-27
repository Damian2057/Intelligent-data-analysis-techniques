package com.metaheuristics.simulation;

import com.metaheuristics.chart.ChartGenerator;
import com.metaheuristics.chart.DataSet;
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
    public List<Specimen> getGeneration() {
        return generation;
    }

    @Override
    public void run() {
        List<DataSet> dataSets = new ArrayList<>();
        logger.info("The simulation has started");
        for (int i = 0; i < numberOfIterations; i++) {
            if(i % display == 0) {
                logger.info("Round of simulation number: " + i);
                logger.info("Current best adaptation: " + decimalFormat.format(bestSample.getAdaptation()));
                //fileOperation.writeData(i,generation);
                dataSets.add(new DataSet(i,generation));
            }
            //parents' choice
            List<Specimen> parents = selectionType == SelectionType.ROULETTE ?
                    genetic.rouletteSelection(generation)
                    : genetic.tournamentSelection(generation);
            //crossing genes
            List<Specimen> kids = genetic.crossGenes(parents);
            this.generation = genetic.createNewGeneration(generation, parents, kids);
            //calculate adaptation for a whole generation
            genetic.adaptationFunction(generation);
            bestSample = fineTheBest.apply(genetic.getTheBestSpecimen(generation));
        }

        logger.info("The simulation is over, the result is a backpack:\n"
                + bestSample.toString()
                + "\nContents: \n"
                + genetic.interpretThings(bestSample.getGens()));
        ChartGenerator chartGenerator = new ChartGenerator(dataSets, decimalFormat.format(bestSample.getAdaptation()));
        chartGenerator.pack();
        chartGenerator.setVisible(true);

        ChartGenerator chartGenerator1 = new ChartGenerator(dataSets, decimalFormat.format(bestSample.getAdaptation()), 1);
        chartGenerator1.pack();
        chartGenerator1.setVisible(true);
    }
}
