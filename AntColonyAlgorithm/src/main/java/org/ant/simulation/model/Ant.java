package org.ant.simulation.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Ant {

    private double distance;
    private Set<Integer> visitedLocations = new HashSet<>();


}
