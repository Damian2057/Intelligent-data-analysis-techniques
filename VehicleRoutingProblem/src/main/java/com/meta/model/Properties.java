package com.meta.model;

import lombok.Data;

@Data
public class Properties {
    private int numberOfAnts;
    private double probabilityOfRandomAttraction;
    private double alfa;
    private double beta;
    private int numberOfIteration;
    private double pheromoneEvaporation;
    private String schemaName;
    private int display;
    private int startTimes;
    private double initPheromone;
    private double vehicleCapacity;
}
