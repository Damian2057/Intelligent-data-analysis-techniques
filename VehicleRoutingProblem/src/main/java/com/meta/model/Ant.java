package com.meta.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class Ant {

    private double distance;
    private double currentWeight = 0.0;
    private List<Location> visitedLocations = new ArrayList<>();

    public void drawRandomLocation(List<Location> locations) {
        List<Location> temp = new ArrayList<>(locations);
        Random rand = new Random();
        int randomIndex = rand.nextInt(temp.size());
        this.visitedLocations.add(temp.get(randomIndex));
        this.currentWeight += temp.get(randomIndex).getDemand();
    }

    public double getDistance(double[][] distances) {
        distance = 0.0;
        if(visitedLocations.size() < 2) {
            throw new RuntimeException("to calculate the distance you need to pass at least two locations");
        }
        for (int i = 0; i < visitedLocations.size() - 1; i++) {
            this.distance += distances[visitedLocations.get(i).getId() - 1][visitedLocations.get(i + 1).getId() - 1];
        }
        return distance;
    }

    public void addVisitedLocation(Location location) {
        visitedLocations.add(location);
        this.currentWeight += location.getDemand();
    }
}
