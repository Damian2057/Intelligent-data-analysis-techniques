package p.lodz.pl.algorithm.pso;

import p.lodz.pl.Algorithm;
import p.lodz.pl.algorithm.common.Particle;

import java.util.concurrent.Future;

public interface PSO extends Algorithm<Particle> {
    Future<PSO> start();
}

