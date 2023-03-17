package p.lodz.pl.DE.model;

import org.apache.commons.lang3.NotImplementedException;
import p.lodz.pl.Adaptation;

import java.util.List;

public class Specimen implements Adaptation {
    private final List<Double> x;
    private double adaptationValue = Double.MAX_VALUE;

    public Specimen(List<Double> x) {
        this.x = x;
    }

    public List<Double> getX() {
        return x;
    }

    @Override
    public double getAdaptationValue() {
        return adaptationValue;
    }

    @Override
    public double getBestAdaptationValue() {
        throw new NotImplementedException("This method is not supported");
    }

    public void setAdaptationValue(double adaptationValue) {
        this.adaptationValue = adaptationValue;
    }
}
