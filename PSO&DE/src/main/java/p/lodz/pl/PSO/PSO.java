package p.lodz.pl.PSO;

import p.lodz.pl.Algorithm;
import p.lodz.pl.PSO.model.Particle;

import java.util.concurrent.Future;

public interface PSO extends Algorithm<Particle> {
    Future<PSO> start();
}
