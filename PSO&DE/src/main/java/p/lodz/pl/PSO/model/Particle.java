package p.lodz.pl.PSO.model;

import lombok.Data;

import java.util.List;

@Data
public class Particle {
    private List<Double> xVector;
    private double speed;
    private double adaptationValue = Double.MAX_VALUE;
    private List<Double> bestXVector;
    private double bestAdaptation;

    public Particle(List<Double> x) {
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

    public double setAdaptationValue(double adaptationValue) {
        this.adaptationValue = adaptationValue;
        return adaptationValue;
    }
}