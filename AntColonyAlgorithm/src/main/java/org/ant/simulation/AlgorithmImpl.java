package org.ant.simulation;

import org.ant.config.Config;
import org.ant.config.Properties;
import org.ant.factory.Factory;
import org.ant.model.Ant;
import org.ant.model.Location;
import org.ant.readers.LocationReader;

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
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfIterations; i++) {
            if(i % display == 0) {
                logger.info("Round of simulation number: " + i);
                logger.info("Current best distance: " + theBestAnt.getDistance());
//                dataSets.add(new DataSet(i,generation));
            }

        }
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

}
