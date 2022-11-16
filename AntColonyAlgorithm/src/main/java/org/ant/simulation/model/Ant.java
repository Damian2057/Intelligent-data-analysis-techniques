package org.ant.simulation.model;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Ant {

    private double distance;
    private Set<Integer> visitedLocations = new HashSet<>();
    private Set<Integer> allLocations = new HashSet<>();

    public void addVisitedLocation(int number) {
        if(visitedLocations.contains(number)) {
            throw new RuntimeException();
        } else {
            visitedLocations.add(number);
        }
    }

    public List<Integer> getAvailableLocations() {
        Set<Integer> temp = new HashSet<>(allLocations);
        temp.removeAll(visitedLocations);
        return temp.stream().toList();
    }


}
