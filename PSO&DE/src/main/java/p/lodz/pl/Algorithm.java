package p.lodz.pl;

import p.lodz.pl.DE.model.DataSet;
import p.lodz.pl.DE.model.Specimen;
import p.lodz.pl.PSO.model.Particle;

import java.util.List;

public interface Algorithm {
    List<DataSet> getDataSets();
    Specimen getBest();
    Particle getBestParticle();
}
