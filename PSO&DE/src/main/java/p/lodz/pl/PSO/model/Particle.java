package p.lodz.pl.PSO.model;

import lombok.Data;
import p.lodz.pl.Adaptation;

import java.util.ArrayList;
import java.util.List;

@Data
public class Particle implements Adaptation {
    private List<Double> xVector;
    private List<Double> speed;
    private double adaptationValue = Double.MAX_VALUE;
    private List<Double> bestXVector;
    private double bestAdaptation;

    public Particle(List<Double> x, List<Double> speed) {
        this.xVector = x;
        this.speed = speed;
        this.bestXVector = x;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "bestX=" + bestXVector +
                ", bestAdaptation=" + bestAdaptation +
                '}';
    }

    public void setAdaptationValue(double adaptationValue) {
        this.adaptationValue = adaptationValue;
    }
}