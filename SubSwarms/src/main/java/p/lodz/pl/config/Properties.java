package p.lodz.pl.config;

import lombok.Data;

@Data
public class Properties {
    private final int adaptationFunction;
    private final String stopCondition;
    private final double number;
    private final double[] xRange;
    private final int dimension;
    private final int startTimes;
    private final int swarmSize;
    private final int numberOfSubSwarms;
    private double inertia;
    private double cognitiveConstant;
    private double socialConstant;
}
