package p.lodz.pl.algorithm.ngo;

import lombok.extern.java.Log;
import org.apache.commons.lang3.Range;
import p.lodz.pl.algorithm.common.AlgorithmBase;
import p.lodz.pl.algorithm.common.Factory;
import p.lodz.pl.algorithm.ngo.factory.GoShawkFactory;
import p.lodz.pl.algorithm.ngo.model.GoShawk;
import p.lodz.pl.chart.DataSet;

import java.util.List;
import java.util.concurrent.Future;

import static p.lodz.pl.enums.Const.ACCURACY;
import static p.lodz.pl.enums.Const.ITERATION;

@Log
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
        return executor.submit(() -> {
            log.info(String.format(ALG_START, Thread.currentThread().getName()));

            if (ITERATION.getName().equals(properties.getStopCondition())) {

            } else if (ACCURACY.getName().equals(properties.getStopCondition())) {

            } else {
                throw new IllegalArgumentException("invalid stop condition of the algorithm");
            }
            log.info(String.format(ALG_SOL,
                    Thread.currentThread().getName(),
                    bestSolution.getBestAdaptation(),
                    dataSets.size()));

            return this;
        });
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
