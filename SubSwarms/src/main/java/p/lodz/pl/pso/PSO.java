package p.lodz.pl.pso;

import p.lodz.pl.Algorithm;
import p.lodz.pl.pso.model.Particle;

import java.util.concurrent.Future;

public interface PSO extends Algorithm<Particle> {
    Future<PSO> start();
}
