package p.lodz.pl.DE.model;

import java.util.List;

public class Specimen {
    private final List<Double> x;
    private double adaptationValue = Double.MAX_VALUE;

    public Specimen(List<Double> x) {
        this.x = x;
    }

    public List<Double> getX() {
        return x;
    }

    public double getAdaptationValue() {
        return adaptationValue;
    }

    public void setAdaptationValue(double adaptationValue) {
        this.adaptationValue = adaptationValue;
    }
}
