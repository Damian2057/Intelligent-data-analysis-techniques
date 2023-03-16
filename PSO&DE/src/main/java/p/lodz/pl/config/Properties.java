package p.lodz.pl.config;

import lombok.Data;
import p.lodz.pl.config.DE.DEProperties;
import p.lodz.pl.config.PSO.PSOProperties;

@Data
public class Properties {
    private final int adaptationFunction;
    private final String stopCondition;
    private final double number;
    private final double[] xRange;
    private final int dimension;
    private final int startTimes;
    private final DEProperties de;
    private final PSOProperties pso;
}
