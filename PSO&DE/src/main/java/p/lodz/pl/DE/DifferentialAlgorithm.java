package p.lodz.pl.DE;

import p.lodz.pl.DE.model.DataSet;
import p.lodz.pl.DE.model.Specimen;

import java.util.List;

public interface DifferentialAlgorithm extends Runnable {
    List<DataSet> getDataSets();
    Specimen getBest();
}
