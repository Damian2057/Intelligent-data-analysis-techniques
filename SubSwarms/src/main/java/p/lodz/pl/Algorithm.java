package p.lodz.pl;

import p.lodz.pl.chart.DataSet;

import java.util.List;

public interface Algorithm<T extends Adaptation> {
    List<DataSet> getDataSets();
    T getBest();
}
