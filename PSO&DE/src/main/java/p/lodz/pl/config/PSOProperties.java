package p.lodz.pl.config;

import lombok.Data;

@Data
public class PSOProperties {
    private int numberOfParticles;
    private String xBorder;
    private String yBorder;
    private double inertia;
    private double cognitiveConstant;
    private double socialConstant;
    private String adaptationFunction;
    private int numberOfIteration;
    private int display;
    private int startTimes;
    private final String stopCondition;

    public double getXDownBorder() {
        String[] border = xBorder.split(",");
        return Double.parseDouble(border[0]);
    }

    public double getXUpBorder() {
        String[] border = xBorder.split(",");
        return Double.parseDouble(border[1]);
    }

    public double getYDownBorder() {
        String[] border = yBorder.split(",");
        return Double.parseDouble(border[0]);
    }

    public double getYUpBorder() {
        String[] border = yBorder.split(",");
        return Double.parseDouble(border[1]);
    }
}
