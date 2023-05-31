package p.lodz.pl.algorithm.ngo.model;

import lombok.Data;
import p.lodz.pl.Adaptation;

import java.util.ArrayList;
import java.util.List;

@Data
public class GoShawk implements Adaptation, Cloneable {

    private List<Double> xVector;
    private double adaptationValue = Double.MAX_VALUE;
    private List<Double> bestXVector;
    private double bestAdaptation = Double.MAX_VALUE;

    public GoShawk(List<Double> x) {
        this.xVector = x;
        this.bestXVector = x;
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
    public double getAdaptationValue() {
        return adaptationValue;
    }

    @Override
    public double getBestAdaptationValue() {
        return bestAdaptation;
    }

    @Override
    public GoShawk clone() {
        try {
            GoShawk clone = (GoShawk) super.clone();
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
    public String toString() {
        return "GoShawk{" +
                "xVector=" + xVector +
                ", adaptationValue=" + adaptationValue +
                ", bestXVector=" + bestXVector +
                ", bestAdaptation=" + bestAdaptation +
                '}';
    }
}
