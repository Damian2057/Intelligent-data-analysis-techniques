package com.meta.simulation;

import com.meta.chart.ChartGenerator;
import com.meta.chart.DataSet;
import com.meta.chart.MaxDataSet;
import com.meta.chart.RoadGenerator;
import com.meta.config.Config;
import com.meta.config.LocationReader;
import com.meta.factory.Factory;
import com.meta.logic.AntAlgorithm;
import com.meta.logic.TwoOPT;
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
    private static final Location base = locations.get(0);
    private final Logger logger = Logger.getLogger(Simulation.class.getSimpleName());
    private final Factory factory = new Factory();
    private final double[][] distanceMatrix;
    private final double[][] pheromoneMatrix;
    private List<Ant> colony;
    private Ant theBestAnt;

    public SimulationImp() {
        logger.info("Parameter initialization");
        this.distanceMatrix = AntAlgorithm.calculateDistances(locations);
        this.pheromoneMatrix = AntAlgorithm.initializePheromone(locations, properties.getInitPheromone());
        colony = factory.createColony(properties.getNumberOfAnts());
        theBestAnt = colony.get(0);
    }

    @Override
    public void run() {
        List<DataSet> dataSets = new ArrayList<>();
        List<MaxDataSet> maxDataSets = new ArrayList<>();
        logger.info("The simulation has started");

        for (int i = 0; i < properties.getNumberOfIteration(); i++) {
            colony.forEach(ant -> ant.addVisitedLocation(locations.get(0)));
            for (Ant ant : colony) {
                move(ant);
            }

            colony.forEach(ant -> ant.getDistance(distanceMatrix));
            updatePheromones();
            this.theBestAnt = AntAlgorithm.findTheBest(theBestAnt, colony);
            this.theBestAnt = TwoOPT.improveSolution(this.theBestAnt);

            if(i % properties.getDisplay() == 0) {
                logger.info("Round of simulation number: " + i);
                logger.info("Current best distance: " + theBestAnt.getDistance(distanceMatrix));
                dataSets.add(new DataSet(i, colony));
                maxDataSets.add(new MaxDataSet(i, theBestAnt));
            }
            this.colony = factory.createColony(properties.getNumberOfAnts());
        }

        logger.info("Solution: " + theBestAnt.getDistance(distanceMatrix) + "Ant: " + theBestAnt.toString());
        ChartGenerator chartGenerator = new ChartGenerator(dataSets, maxDataSets, String.valueOf(theBestAnt.getDistance(distanceMatrix)));
        chartGenerator.pack();
        chartGenerator.setVisible(true);
        RoadGenerator.generateRoad(theBestAnt);
    }

    private void move(Ant ant) {
        double time = 0.0;
        double weight = 0.0;

        while (!isAntVisitEveryLocation(ant)) {
            List<Location> availableLocationByTime = getAvailableLocationByTime(ant, time);
            List<Location> availableLocationByWeight = new ArrayList<>();
            if (availableLocationByTime.isEmpty()) {
                time += 1;
            } else {
                availableLocationByWeight = getAvailableLocationByWeight(availableLocationByTime,
                        weight);
                if (availableLocationByWeight.isEmpty()) {
                    ant.addVisitedLocation(base);
                    weight = 0.0;
                    time = 0.0;
                } else {
                    Location location = goToLocation(ant, availableLocationByWeight);
                    weight += location.getDemand();
                    time += location.getServiceTime();
                    ant.setCurrentWeight(weight);
                    ant.setTime(time);
                }
            }
            if (time > findMax()) {
                //the truck came to the warehouse; reset day
                time = 0.0;
                weight = 0.0;
            }
        }
    }

    private double findMax() {
        double maxTime = locations.get(0).getDueDate();
        for (Location location :
                locations) {
            if (location.getDueDate() > maxTime) {
                maxTime = location.getDueDate();
            }
        }
        return maxTime;
    }

    private Location goToLocation(Ant ant, List<Location> locations) {
        Random random = new Random();
        double randomValue = 0 + random.nextDouble();
        if(randomValue < properties.getProbabilityOfRandomAttraction()) {
            return randomLocation(ant, locations);
        } else {
            return roulette(ant, locations);
        }
    }

    private List<Location> getAvailableLocationByTime(Ant ant, double time) {
        List<Location> availableLocation = new ArrayList<>();
        List<Location> locationCopy = new ArrayList<>(locations);
        locationCopy.removeAll(ant.getVisitedLocations());
        for (Location location : locationCopy) {
            if (time >= location.getReadyTime() && time <= location.getDueDate()) {
                availableLocation.add(location);
            }
        }
        return availableLocation;
    }

    private List<Location> getAvailableLocationByWeight(List<Location> locations, double weight) {
        List<Location> availableLocation = new ArrayList<>();
        for (Location location : locations) {
            if (location.getDemand() + weight <= properties.getVehicleCapacity()) {
                availableLocation.add(location);
            }
        }

        return availableLocation;
    }

    private boolean isAntVisitEveryLocation(Ant ant) {
        List<Location> visitedLocations = new ArrayList<>(locations);
        visitedLocations.removeAll(ant.getVisitedLocations());
        if (visitedLocations.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private Location randomLocation(Ant ant, List<Location> locations) {
        Random random = new Random();
        int randomIndex = random.nextInt(locations.size());
        Location location = locations.get(randomIndex);
        ant.addVisitedLocation(location);
        return location;
    }

    private Location roulette(Ant ant, List<Location> locations) {
        locations.removeAll(ant.getVisitedLocations());
        Location lastLocation = ant.getVisitedLocations().get(ant.getVisitedLocations().size()-1);
        double totalSum = 0.0;
        double partialSum;
        for (Location location : locations) {
            partialSum = Math.pow(pheromoneMatrix[location.getId() - 1][lastLocation.getId() - 1],
                    properties.getAlfa()) *
                    Math.pow((1.0 / distanceMatrix[location.getId() - 1][lastLocation.getId() - 1]),
                            properties.getBeta());
            location.setPartialProbability(partialSum);
            totalSum += partialSum;
        }
        for (Location location : locations) {
            location.setPartialProbability(location.getPartialProbability() / totalSum);
        }
        Location location = select(locations);
        ant.addVisitedLocation(location);
        return location;
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

}
