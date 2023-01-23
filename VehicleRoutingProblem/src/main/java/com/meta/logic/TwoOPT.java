package com.meta.logic;

import com.meta.model.Ant;
import com.meta.model.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwoOPT {

    public static Ant improveSolution(Ant ant, double[][] distanceMatrix) {
        List<Location> locations = new ArrayList<>(ant.getVisitedLocations());
        Collections.sort(locations);
        for (Location location : ant.getVisitedLocations()) {
            for (int i = 1; i < locations.size() - 1; i++) {
                if (location.getId() != 1) {
                    //TODO: not implemented
                }
            }
        }

        return ant;
    }

    private void isCorrect() {

    }
}
