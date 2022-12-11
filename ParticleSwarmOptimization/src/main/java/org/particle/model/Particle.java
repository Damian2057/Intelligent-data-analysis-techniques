package org.particle.model;

public class Particle {
    private double x;
    private double y;
    private double speed = 0;
    private double currentAdaptation;
    private double bestX;
    private double bestY;
    private double bestAdaptation;

    public Particle(double x, double y) {
        this.x = x;
        this.y = y;
    }

}
