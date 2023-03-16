package p.lodz.pl.PSO.model;

import lombok.Data;
import p.lodz.pl.Adaptation;

import java.util.ArrayList;
import java.util.List;

@Data
public class Particle implements Adaptation, Cloneable {
    private List<Double> xVector;
    private List<Double> speed;
    private double adaptationValue = Double.MAX_VALUE;
    private List<Double> bestXVector;
    private double bestAdaptation = Double.MAX_VALUE;

    public Particle(List<Double> x, List<Double> speed) {
        this.xVector = x;
        this.speed = speed;
        this.bestXVector = x;
    }

    public void setBestAdaptation(double adaptation) {
        if (adaptation < this.bestAdaptation) {
            this.bestAdaptation = adaptation;
            this.bestXVector = new ArrayList<>(xVector);
        }
    }

    @Override
    public Particle clone() {
        try {
            Particle clone = (Particle) super.clone();
            clone.setSpeed(new ArrayList<>(speed));
            clone.setBestAdaptation(bestAdaptation);
            clone.setAdaptationValue(adaptationValue);
            clone.setXVector(new ArrayList<>(xVector));
            clone.setBestXVector(new ArrayList<>(bestXVector));
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}