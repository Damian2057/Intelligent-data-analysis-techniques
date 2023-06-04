package p.lodz.pl.config;

import lombok.Data;

@Data
public class PSO {
    private final double inertia;
    private final double cognitiveConstant;
    private final double socialConstant;
    private final String crossoverType;
}
