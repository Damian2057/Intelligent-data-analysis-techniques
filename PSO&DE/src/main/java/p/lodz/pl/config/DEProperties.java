package p.lodz.pl.config;

import lombok.Data;
import p.lodz.pl.config.DE.CrossOver;

@Data
public class DEProperties {
    private final int adaptationFunction;
    private final String stopCondition;
    private final double number;
    private final double[] xRange;
    private final int dimension;

    private final String reproductionType;
    private final int populationSize;
    private final double amplificationFactor;
    private final CrossOver crossOver;
}
