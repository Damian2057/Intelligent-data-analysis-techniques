package p.lodz.pl;

import p.lodz.pl.pso.OPSOAlgorithm;
import p.lodz.pl.pso.PSO;

public class Main {
    public static void main(String[] args) {
//        Comparison comparison = new Comparison();
//        comparison.compare();
        PSO pso = new OPSOAlgorithm();
        pso.start();
    }
}