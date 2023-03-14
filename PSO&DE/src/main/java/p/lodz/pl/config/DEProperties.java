package p.lodz.pl.config;

import lombok.Data;
import p.lodz.pl.enums.StopCondition;

@Data
public class DEProperties {
    private final int adaptationFunction;
    private final StopCondition stopCondition;
    private final double number;
}
