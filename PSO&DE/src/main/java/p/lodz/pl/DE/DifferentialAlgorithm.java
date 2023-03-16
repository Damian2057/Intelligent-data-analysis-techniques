package p.lodz.pl.DE;

import p.lodz.pl.Algorithm;
import p.lodz.pl.DE.model.Specimen;

import java.util.concurrent.Future;

public interface DifferentialAlgorithm extends Algorithm<Specimen> {
    Future<DifferentialAlgorithm> start();
}
