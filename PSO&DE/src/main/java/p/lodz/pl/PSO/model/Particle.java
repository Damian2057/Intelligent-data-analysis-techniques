package p.lodz.pl.PSO.model;

import lombok.Data;

@Data
public class Particle {
    private double x;
    private double y;
    private double speed;
    private double currentAdaptation;
    private double bestX;
    private double bestY;
    private double bestAdaptation;

    public Particle(double x, double y) {
        this.x = x;
        this.y = y;
        this.speed = 0;
        this.bestX = x;
        this.bestY = y;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "bestX=" + bestX +
                ", bestY=" + bestY +
                ", bestAdaptation=" + bestAdaptation +
                '}';
    }
}
