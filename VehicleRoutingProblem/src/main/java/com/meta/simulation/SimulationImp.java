package com.meta.simulation;

import com.meta.chart.ChartGenerator;
import com.meta.config.Config;
import com.meta.config.LocationReader;
import com.meta.factory.Factory;
import com.meta.logic.Algorithm;
import com.meta.model.Ant;
import com.meta.model.Location;
import com.meta.model.Properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class SimulationImp implements Simulation {

    private static final Properties properties = Config.getProperties();
    private static final List<Location> locations = LocationReader.getLocations(properties.getSchemaName());
    private final Logger logger = Logger.getLogger(Simulation.class.getSimpleName());
    private final Factory factory = new Factory();
    private final double[][] distanceMatrix;
    private final double[][] pheromoneMatrix;
    private List<Ant> colony;
    private Ant theBestAnt;
    private double time;

    public SimulationImp() {
        logger.info("Parameter initialization");
        this.distanceMatrix = Algorithm.calculateDistances(locations);
        this.pheromoneMatrix = Algorithm.initializePheromone(locations, properties.getInitPheromone());
        colony = factory.createColony(properties.getNumberOfAnts());
        theBestAnt = colony.get(0);
    }

    @Override
    public void run() {
        logger.info("The simulation has started");

        for (int i = 0; i < properties.getNumberOfIteration(); i++) {
            colony.forEach(ant -> ant.addVisitedLocation(locations.get(0)));

            for (int j = 0; j < locations.size() - 1; j++) {
                for (Ant ant : colony) {
                    move(ant);
                }
            }
            colony.forEach(ant -> ant.getDistance(distanceMatrix));
            updatePheromones();
            this.theBestAnt = Algorithm.findTheBest(theBestAnt, colony);

            if(i % properties.getDisplay() == 0) {
                logger.info("Round of simulation number: " + i);
                logger.info("Current best distance: " + theBestAnt.getDistance(distanceMatrix));
            }
            this.colony = factory.createColony(properties.getNumberOfAnts());
        }

        logger.info("Solution: " + theBestAnt.getDistance(distanceMatrix) + theBestAnt.getVisitedLocations());
//        ChartGenerator chartGenerator = new ChartGenerator(dataSets, maxDataSets, String.valueOf(theBestAnt.getDistance(distanceMatrix)));
//        chartGenerator.pack();
//        chartGenerator.setVisible(true);
//
//        ChartGenerator chartGenerator2 = new ChartGenerator(dataSets);
//        chartGenerator2.pack();
//        chartGenerator2.setVisible(true);
//        RoadGenerator.generateRoad(theBestAnt);
    }

    private void move(Ant ant) {
        Random random = new Random();
        double randomValue = 0 + random.nextDouble();
        if(randomValue < properties.getProbabilityOfRandomAttraction()) {
            randomLocation(ant);
        } else {
            roulette(ant);
        }
    }

    private void randomLocation(Ant ant) {

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


    private void updatePheromones() {

    }

}
