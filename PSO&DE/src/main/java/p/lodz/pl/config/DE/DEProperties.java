package p.lodz.pl.config.DE;

import lombok.Data;

@Data
public class DEProperties {
    private final String reproductionType;
    private final int populationSize;
    private final double amplificationFactor;
    private final CrossOver crossOver;
}
