package com.meta.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Ant {

    private double distance;
    private double currentWeight = 0.0;
    private double time = 0.0;
    private List<Location> visitedLocations = new ArrayList<>();

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
    }
}
