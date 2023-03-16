package p.lodz.pl.config.PSO;

import lombok.Data;

@Data
public class PSOProperties {
    private int numberOfParticles;
    private double inertia;
    private double cognitiveConstant;
    private double socialConstant;
}
