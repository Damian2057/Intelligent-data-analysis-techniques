package p.lodz.pl.config;

import lombok.Data;

@Data
public class PSOProperties {
    private int numberOfParticles;
    private String xBorder;
    private final int dimension;
    private double inertia;
    private double cognitiveConstant;
    private double socialConstant;
    private final double number;
    private int display;
    private int startTimes;
    private final String stopCondition;
    private final int adaptationFunction;

    public double getXDownBorder() {
        String[] border = xBorder.split(",");
        return Double.parseDouble(border[0]);
    }

    public double getXUpBorder() {
        String[] border = xBorder.split(",");
        return Double.parseDouble(border[1]);
    }
}
