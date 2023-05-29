package p.lodz.pl.algorithm.ngo;

import p.lodz.pl.Algorithm;
import p.lodz.pl.algorithm.ngo.model.GoShawk;

import java.util.concurrent.Future;

public interface NGO extends Algorithm<GoShawk> {
    Future<NGO> start();
}