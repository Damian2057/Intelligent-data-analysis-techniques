package p.lodz.pl.algorithm.common;

import lombok.Data;
import p.lodz.pl.Adaptation;

import java.util.ArrayList;
import java.util.List;

@Data
public class Particle implements Adaptation, Cloneable {

    private int id;
    private List<Double> xVector;
    private List<Double> speed;
    private double adaptationValue = Double.MAX_VALUE;
    private List<Double> bestXVector;
    private double bestAdaptation = Double.MAX_VALUE;

    public Particle(int id, List<Double> x, List<Double> speed) {
        this.id = id;
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
            clone.setXVector(xVector);
            clone.setBestXVector(bestXVector);
            clone.setAdaptationValue(adaptationValue);
            clone.setBestAdaptation(bestAdaptation);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public double getBestAdaptationValue() {
        return bestAdaptation;
    }
}