package p.lodz.pl.config.DE;

import lombok.Data;

@Data
public class CrossOver {
    private final String crossoverType;
    private final double CR;
    private final int numberOfCopies;
}
