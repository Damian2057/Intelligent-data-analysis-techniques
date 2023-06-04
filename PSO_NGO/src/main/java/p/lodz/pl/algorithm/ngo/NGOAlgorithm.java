package p.lodz.pl.algorithm.ngo;

import lombok.extern.java.Log;
import org.apache.commons.lang3.Range;
import p.lodz.pl.algorithm.common.AlgorithmBase;
import p.lodz.pl.algorithm.common.Factory;
import p.lodz.pl.algorithm.ngo.factory.GoShawkFactory;
import p.lodz.pl.algorithm.ngo.model.GoShawk;
import p.lodz.pl.chart.DataSet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
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
                for (int i = 0; i < properties.getNumber(); i++) {
                    bestSolution = getTheBestParticle().clone();



                    dataSets.add(new DataSet(i, getAvgAdaptation(), bestSolution.getBestAdaptation()));
                }

            } else if (ACCURACY.getName().equals(properties.getStopCondition())) {
                int repetitionCounter = 0;
                int index = 0;
                double repetition = properties.getSize() * 0.5;

                while (repetitionCounter < repetition) {
                    calculateAdaptation();
                    bestSolution = getTheBestParticle().clone();
                    double best = bestSolution.getBestAdaptation();



                    dataSets.add(new DataSet(index, getAvgAdaptation(), bestSolution.getBestAdaptation()));
                    if (best - bestSolution.getBestAdaptation() < properties.getNumber()) {
                        repetitionCounter++;
                    } else {
                        repetitionCounter = 0;
                    }
                    index++;
                }

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

    private GoShawk getTheBestParticle() {
        return goShawks.stream()
                .min(Comparator.comparingDouble(GoShawk::getBestAdaptation))
                .orElseThrow(NoSuchElementException::new);
    }

    private void calculateAdaptation() {
        goShawks.forEach(particle -> particle.setAdaptationValue(function.function(particle.getXVector())));
        goShawks.forEach(particle -> particle.setBestAdaptation(Math.min(particle.getAdaptationValue(), particle.getBestAdaptation())));
    }

    private double getAvgAdaptation() {
        return goShawks.stream()
                .mapToDouble(GoShawk::getBestAdaptation)
                .average()
                .orElse(0.0);
    }

    private GoShawk cross(GoShawk current, GoShawk best, int barrier) {
        List<Double> x = new ArrayList<>();
        x.addAll(getRange(current.getXVector(), 0, barrier));
        x.addAll(getRange(best.getXVector(), barrier, properties.getDimension()));

        return new GoShawk(x);
    }

    private List<Double> getRange(List<Double> list, int start, int end) {
        List<Double> newList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            newList.add(list.get(i));
        }

        return newList;
    }

    private int random(int min) {
        return random.nextInt(properties.getDimension() - min) + min;
    }
}
