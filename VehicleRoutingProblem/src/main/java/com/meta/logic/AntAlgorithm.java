package com.meta.logic;

import com.meta.model.Ant;
import com.meta.model.Location;

import java.util.List;

public class AntAlgorithm {
    public static double[][] calculateDistances(List<Location> locations) {
        double[][] distanceMatrix = new double[locations.size()][locations.size()];
        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < locations.size(); j++) {
                distanceMatrix[i][j] = Math.sqrt((locations.get(i).getX() - locations.get(j).getX()) *
                        (locations.get(i).getX() - locations.get(j).getX()) +
                        (locations.get(i).getY() - locations.get(j).getY()) *
                                (locations.get(i).getY() - locations.get(j).getY()));
            }
        }
        return distanceMatrix;
    }

    public static double[][] initializePheromone(List<Location> locations, double value) {
        double[][] pheromoneMatrix = new double[locations.size()][locations.size()];
        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < locations.size(); j++) {
                pheromoneMatrix[i][j] = value;
            }
        }
        return pheromoneMatrix;
    }


    public static Ant findTheBest(Ant currentBest, List<Ant> colony) {
        for (Ant ant : colony) {
            if (ant.getDistance() < currentBest.getDistance()) {
                currentBest = ant;
            }
        }
        return currentBest;
    }
}
