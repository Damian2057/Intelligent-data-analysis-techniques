package com.meta.logic;

import com.meta.model.Ant;
import com.meta.model.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwoOPT {

    public static Ant improveSolution(Ant ant, double[][] distanceMatrix) throws CloneNotSupportedException {
        for (int i = 1; i < ant.getVisitedLocations().size() - 1; i++) {
            List<Location> locations = new ArrayList<>(ant.getVisitedLocations());
            if (locations.get(i).getId() != 1 && locations.get(i + 1).getId() != 1) {
                swap(locations, i, i + 1);
                if (getDistance(locations, distanceMatrix) < getDistance(ant.getVisitedLocations(), distanceMatrix)) {
                    swap(ant.getVisitedLocations(), i, i + 1);
                }
            }
        }
        ant.getDistance(distanceMatrix);
        return ant.clone();
    }

    private static <T> void swap (List<T> l, int i, int j) {
        Collections.swap(l, i, j);
    }

    private static double getDistance(List<Location> locations, double[][] distances) {
        double distance = 0.0;
        for (int i = 0; i < locations.size() - 1; i++) {
            distance += distances[locations.get(i).getId() - 1][locations.get(i + 1).getId() - 1];
        }
        return distance;
    }
}
