package org.ant.simulation;

import org.ant.chart.ChartGenerator;
import org.ant.chart.DataSet;
import org.ant.config.Config;
import org.ant.model.Properties;
import org.ant.factory.Factory;
import org.ant.model.Ant;
import org.ant.model.Location;
import org.ant.config.LocationReader;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

public class AlgorithmImpl implements Algorithm {

    private static final Properties properties = Config.getProperties();
    private final static int numberOfIterations = Config.getProperties().getNumberOfIteration();
    private static final int display = Config.getProperties().getDisplay();
    private static final List<Location> locations = LocationReader.getBagPackItems(properties.getSchemaName());
    private final Logger logger = Logger.getLogger(Algorithm.class.getSimpleName());
    private final Factory factory = new Factory();
    private List<Ant> colony;
    private Ant theBestAnt;
    private final Function<Ant, Ant> findTheBest = best -> best.getDistance() < theBestAnt.getDistance() ? best : theBestAnt;
    private double[][] distanceMatrix = new double[locations.size()][locations.size()];
    private double[][] pheromoneMatrix = new double[locations.size()][locations.size()];

    public AlgorithmImpl() {
        logger.info("The simulation has started");
        colony = factory.createColony(properties.getNumberOfAnts());
        calculateDistances();
        initializePheromone();
        theBestAnt = colony.get(0);
    }

    @Override
    public void run() {
        List<DataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < numberOfIterations; i++) {

            for (int j = 0; j < locations.size(); j++) {
                for (Ant ant : colony) {
                    ant.move();
                }
            }

            if(i % display == 0) {
                logger.info("Round of simulation number: " + i);
                logger.info("Current best distance: " + theBestAnt.getDistance(distanceMatrix));
                dataSets.add(new DataSet(i, colony));
            }

        }

//        ChartGenerator chartGenerator = new ChartGenerator(dataSets, String.valueOf(theBestAnt.getDistance(distanceMatrix)));
//        chartGenerator.pack();
//        chartGenerator.setVisible(true);
    }



    private void calculateDistances() {
        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < locations.size(); j++) {
                this.distanceMatrix[i][j] = Math.sqrt((locations.get(i).getX() - locations.get(j).getX()) *
                        (locations.get(i).getX() - locations.get(j).getX()) +
                        (locations.get(i).getY() - locations.get(j).getY()) *
                        (locations.get(i).getY() - locations.get(j).getY()));
            }
        }
    }

    private void initializePheromone() {
        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < locations.size(); j++) {
                this.pheromoneMatrix[i][j] = 1;
            }
        }
    }

    private void updatePheromones() {
        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < locations.size(); j++) {
                this.pheromoneMatrix[i][j] *= properties.getPheromoneEvaporation();
            }
        }
        for (Ant ant : colony) {
            for (int i = 0; i < ant.getVisitedLocations().size() - 1; i++) {
                this.pheromoneMatrix[ant.getVisitedLocations().get(i).getId() - 1][ant.getVisitedLocations().get(i + 1).getId() - 1]
                        += 1 / ant.getDistance(distanceMatrix);
            }
        }
    }

}
