package p.lodz.pl;

import p.lodz.pl.DE.model.DataSet;
import p.lodz.pl.DE.model.Specimen;

import java.util.List;

public interface Algorithm {
    List<DataSet> getDataSets();
    Specimen getBest();
}
