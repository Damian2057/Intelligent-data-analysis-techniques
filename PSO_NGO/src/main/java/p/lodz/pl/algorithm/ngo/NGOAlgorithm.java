package p.lodz.pl.algorithm.ngo;

import org.apache.commons.lang3.Range;
import p.lodz.pl.algorithm.common.AlgorithmBase;
import p.lodz.pl.algorithm.common.Factory;
import p.lodz.pl.algorithm.ngo.factory.GoShawkFactory;
import p.lodz.pl.algorithm.ngo.model.GoShawk;
import p.lodz.pl.chart.DataSet;

import java.util.List;
import java.util.concurrent.Future;

public class NGOAlgorithm extends AlgorithmBase implements NGO {

    private static final Range<Double> xRange = Range.between(properties.getXRange()[0], properties.getXRange()[1]);
    private final List<GoShawk> goShawks;
    private GoShawk bestSolution;

    public NGOAlgorithm() {
        super("NGO");
        Factory<GoShawk> factory = new GoShawkFactory();
        this.goShawks = factory.create(properties.getSize());
        calculateAdaptation();
    }

    @Override
    public Future<NGO> start() {
        return null;
    }

    @Override
    public List<DataSet> getDataSets() {
        return dataSets;
    }

    @Override
    public GoShawk getBest() {
        return bestSolution;
    }

    private void calculateAdaptation() {
    }
}
