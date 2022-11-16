package org.ant.config;

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

}
