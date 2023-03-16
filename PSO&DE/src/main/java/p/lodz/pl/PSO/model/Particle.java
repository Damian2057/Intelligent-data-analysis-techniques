package p.lodz.pl.PSO.model;

import lombok.Data;

@Data

public class Particle {
    private double[] xVector;
    private double speed;
    private double currentAdaptation;
    private double[] bestXVector;
    private double bestAdaptation;

    public Particle(double[] x) {
        this.xVector = x;
        this.speed = 0;
        this.bestXVector = x;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "bestX=" + bestXVector +
                ", bestAdaptation=" + bestAdaptation +
                '}';
    }
}