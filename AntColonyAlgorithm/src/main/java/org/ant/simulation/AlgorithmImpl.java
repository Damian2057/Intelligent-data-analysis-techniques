package org.ant.simulation;

import org.ant.chart.ChartGenerator;
import org.ant.chart.DataSet;
import org.ant.chart.MaxDataSet;
import org.ant.chart.RoadGenerator;
import org.ant.config.Config;
import org.ant.model.Properties;
import org.ant.factory.Factory;
import org.ant.model.Ant;
import org.ant.model.Location;
import org.ant.config.LocationReader;

import java.util.*;
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
    private double[][] distanceMatrix = new double[locations.size()][locations.size()];
    private double[][] pheromoneMatrix = new double[locations.size()][locations.size()];
    private final Function<Ant, Ant> findTheBest = best -> best.getDistance(distanceMatrix) < theBestAnt.getDistance(distanceMatrix) ? best : theBestAnt;

    public AlgorithmImpl() {
        logger.info("The simulation has started");
        calculateDistances();
        initializePheromone();
        colony = factory.createColony(properties.getNumberOfAnts());
        theBestAnt = colony.get(0);
    }

    @Override
    public void run() {
        List<DataSet> dataSets = new ArrayList<>();
        List<MaxDataSet> maxDataSets = new ArrayList<>();
        for (int i = 0; i < numberOfIterations; i++) {
            for (Ant ant : colony) {
                ant.drawRandomLocation(locations);
            }
            for (int j = 0; j < locations.size() - 1; j++) {
                for (Ant ant : colony) {
                    move(ant);
                }
            }
            updatePheromones();
            theBestAnt = findTheBest.apply(getTheBestAnt());

            if(i % display == 0) {
                logger.info("Round of simulation number: " + i);
                logger.info("Current best distance: " + theBestAnt.getDistance(distanceMatrix));
                dataSets.add(new DataSet(i, colony));
                maxDataSets.add(new MaxDataSet(i, theBestAnt));
            }
            this.colony = factory.createColony(properties.getNumberOfAnts());
        }

        logger.info("Solution: " + theBestAnt.getDistance(distanceMatrix) + theBestAnt.getVisitedLocations());
        ChartGenerator chartGenerator = new ChartGenerator(dataSets, maxDataSets, String.valueOf(theBestAnt.getDistance(distanceMatrix)));
        chartGenerator.pack();
        chartGenerator.setVisible(true);

        ChartGenerator chartGenerator2 = new ChartGenerator(dataSets);
        chartGenerator2.pack();
        chartGenerator2.setVisible(true);
        RoadGenerator.generateRoad(theBestAnt);
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
                this.pheromoneMatrix[i][j] = properties.getInitPheromone();
            }
        }
    }

    private void updatePheromones() {
        for (int i = 0; i < locations.size(); i++) {
            for (int j = i; j < locations.size(); j++) {
                double index1 = pheromoneMatrix[i][j] * properties.getPheromoneEvaporation();
                this.pheromoneMatrix[i][j] = index1;
                this.pheromoneMatrix[j][i] = index1;
            }
        }
        for (Ant ant : colony) {
            for (int i = 0; i < ant.getVisitedLocations().size() - 1; i++) {
                int index1 = ant.getVisitedLocations().get(i).getId() - 1;
                int index2 = ant.getVisitedLocations().get(i + 1).getId() - 1;
                double distance = ant.getDistance(distanceMatrix);
                this.pheromoneMatrix[index1][index2]
                        += 1.0 / distance;
                this.pheromoneMatrix[index2][index1]
                        += 1.0 / distance;
            }
        }
    }

    private Ant getTheBestAnt() {
        Ant theBest = colony.get(0);
        for (Ant ant : colony) {
            if(ant.getDistance(distanceMatrix) < theBest.getDistance(distanceMatrix)) {
                theBest = ant;
            }
        }
        return theBest;
    }

    private void move(Ant ant) {
        Random random = new Random();
        double randomValue = 0 + random.nextDouble();
        if(randomValue < properties.getProbabilityOfRandomAttraction()) {
            System.out.println(randomValue);
            randomLocation(ant);
        } else {
            roulette(ant);
        }
    }

    private void roulette(Ant ant) {
        List<Location> otherLocations = new ArrayList<>(locations);
        otherLocations.removeAll(ant.getVisitedLocations());
        Location lastLocation = ant.getVisitedLocations().get(ant.getVisitedLocations().size()-1);
        double totalSum = 0.0;
        double partialSum;
        for (Location location : otherLocations) {
            partialSum = Math.pow(pheromoneMatrix[location.getId() - 1][lastLocation.getId() - 1],
                    properties.getAlfa()) *
                    Math.pow((1.0 / distanceMatrix[location.getId() - 1][lastLocation.getId() - 1]),
                            properties.getBeta());
            location.setPartialProbability(partialSum);
            totalSum += partialSum;
        }
        for (Location location : otherLocations) {
            location.setPartialProbability(location.getPartialProbability() / totalSum);
        }
        ant.addVisitedLocation(select(otherLocations));
    }

    private void randomLocation(Ant ant) {
        Random random = new Random();
        List<Location> otherLocations = new ArrayList<>(locations);
        otherLocations.removeAll(ant.getVisitedLocations());
        int randomIndex = random.nextInt(otherLocations.size());
        Location location = otherLocations.get(randomIndex);
        ant.addVisitedLocation(location);
    }

    private Location select(List<Location> wheelProbabilities) {
        Random random = new Random();
        double[] cumulative = new double[wheelProbabilities.size()];
        cumulative[0] = wheelProbabilities.get(0).getPartialProbability();
        for (int i = 1; i < wheelProbabilities.size(); i++)
        {
            double sample = wheelProbabilities.get(i).getPartialProbability();
            cumulative[i] = cumulative[i - 1] + sample;
        }
        int last = wheelProbabilities.size()-1;
        double nextDouble = random.nextDouble();
        int selected = Arrays.binarySearch(cumulative, nextDouble);
        if (selected < 0)
        {
            selected = Math.abs(selected + 1);
        }
        int i = selected;
        while (wheelProbabilities.get(i).getPartialProbability() == 0.0){
            i--;
            if (i < 0) i = last;
        }
        selected = i;
        return wheelProbabilities.get(selected);
    }

}
