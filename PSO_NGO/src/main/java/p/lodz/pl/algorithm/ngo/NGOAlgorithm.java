package p.lodz.pl.algorithm.ngo;

import lombok.extern.java.Log;
import org.apache.commons.lang3.Range;
import p.lodz.pl.algorithm.common.AlgorithmBase;
import p.lodz.pl.algorithm.common.Factory;
import p.lodz.pl.algorithm.ngo.factory.GoShawkFactory;
import p.lodz.pl.algorithm.ngo.factory.PreyFactory;
import p.lodz.pl.algorithm.ngo.model.GoShawk;
import p.lodz.pl.algorithm.ngo.model.Prey;
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
    private static final Factory<Prey> preyFactory = new PreyFactory();
    private final List<GoShawk> goShawks;
    private List<Prey> preys = new ArrayList<>();
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
                    double R = 0.02 * (1 - i / properties.getNumber());
                    bestSolution = getTheBestParticle().clone();
                    fillPreys();
                    goShawks.forEach(goShawk -> goShawk.setPrey(getRandomPrey()));
                    goShawks.forEach(this::firstPhase);
                    goShawks.forEach(goShawk -> secondPhase(goShawk, R));
                    calculateAdaptation();
                    bestSolution = getTheBestParticle().clone();

                    goShawks.forEach(goShawk -> {
                        GoShawk cross = cross(goShawk, bestSolution, random(1));
                        cross.setAdaptationValue(function.function(goShawk.getXVector()));
                        if (cross.getAdaptationValue() < goShawk.getAdaptationValue()) {
                            goShawk.setBestAdaptation(cross.getAdaptationValue());
                        }
                    });
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

    private void firstPhase(GoShawk goShawk) {
        List<Double> goShawkXPos = new ArrayList<>();
        if (function.function(goShawk.getXVector()) < function.function(goShawk.getPrey().getXVector())) {
            for (int i = 0; i < properties.getDimension(); i++) {
                double newXPos = goShawk.getXVector().get(i) + random(0 ,2) * (goShawk.getXVector().get(i) - random(1, 2) * goShawk.getPrey().getXVector().get(i));
                if (xRange.contains(newXPos)) {
                    goShawkXPos.add(newXPos);
                } else {
                    if (newXPos > properties.getXRange()[1]) {
                        goShawkXPos.add(properties.getXRange()[1]);
                    }
                    else {
                        goShawkXPos.add(properties.getXRange()[0]);
                    }
                }
            }
        } else {
            for (int i = 0; i < properties.getDimension(); i++) {
                double newXPos = goShawk.getXVector().get(i) + random(0,2) * (goShawk.getPrey().getXVector().get(i) - random(1, 2) * goShawk.getXVector().get(i));
                if (xRange.contains(newXPos)) {
                    goShawkXPos.add(newXPos);
                } else {
                    if (newXPos > properties.getXRange()[1]) {
                        goShawkXPos.add(properties.getXRange()[1]);
                    }
                    else {
                        goShawkXPos.add(properties.getXRange()[0]);
                    }
                }
            }
        }
        if (function.function(goShawkXPos) < function.function(goShawk.getXVector())) {
            goShawk.setXVector(goShawkXPos);
        }

        List<Double> preyXPos = new ArrayList<>();
        if (function.function(goShawk.getXVector()) < function.function(goShawk.getPrey().getXVector())) {
            for (int i = 0; i < properties.getDimension(); i++) {
                double newXPos = goShawk.getXVector().get(i) + random(0 ,2) * (goShawk.getXVector().get(i) - random(1, 2) * goShawk.getPrey().getXVector().get(i));
                if (xRange.contains(newXPos)) {
                    preyXPos.add(newXPos);
                } else {
                    if (newXPos > properties.getXRange()[1]) {
                        preyXPos.add(properties.getXRange()[1]);
                    }
                    else {
                        preyXPos.add(properties.getXRange()[0]);
                    }
                }
            }
        } else {
            for (int i = 0; i < properties.getDimension(); i++) {
                double newXPos = goShawk.getXVector().get(i) + random(0,2) * (goShawk.getPrey().getXVector().get(i) - random(1, 2) * goShawk.getXVector().get(i));
                if (xRange.contains(newXPos)) {
                    preyXPos.add(newXPos);
                } else {
                    if (newXPos > properties.getXRange()[1]) {
                        preyXPos.add(properties.getXRange()[1]);
                    }
                    else {
                        preyXPos.add(properties.getXRange()[0]);
                    }
                }
            }
        }
        if (function.function(preyXPos) < function.function(goShawk.getPrey().getXVector())) {
            goShawk.getPrey().setXVector(preyXPos);
        }
    }

    private void secondPhase(GoShawk goShawk, double R) {
        List<Double> goShawkX = new ArrayList<>();
        List<Double> preyX = new ArrayList<>();
        for (int i = 0; i < properties.getDimension(); i++) {
            double newGoShawkXPos = goShawk.getXVector().get(i) + R * (2 * random(0, 2) - 1) * goShawk.getXVector().get(i);
            if (xRange.contains(newGoShawkXPos)) {
                goShawkX.add(newGoShawkXPos);
            } else {
                if (newGoShawkXPos > properties.getXRange()[1]) {
                    goShawkX.add(properties.getXRange()[1]);
                }
                else {
                    goShawkX.add(properties.getXRange()[0]);
                }
            }
            double newPreyXPos = goShawk.getPrey().getXVector().get(i) + R * (2 * random(0, 2) - 1) * goShawk.getPrey().getXVector().get(i);
            if (xRange.contains(newPreyXPos)) {
                preyX.add(newPreyXPos);
            } else {
                if (newPreyXPos > properties.getXRange()[1]) {
                    preyX.add(properties.getXRange()[1]);
                }
                else {
                    preyX.add(properties.getXRange()[0]);
                }
            }
        }
        if (function.function(goShawkX) < function.function(goShawk.getXVector())) {
            goShawk.setXVector(goShawkX);
        }
        if (function.function(preyX) < function.function(goShawk.getPrey().getXVector())) {
            goShawk.getPrey().setXVector(preyX);
        }
    }

    private GoShawk getTheBestParticle() {
        return goShawks.stream()
                .min(Comparator.comparingDouble(GoShawk::getBestAdaptation))
                .orElseThrow(NoSuchElementException::new);
    }

    private void calculateAdaptation() {
        goShawks.forEach(goShawk -> goShawk.setAdaptationValue(function.function(goShawk.getXVector())));
        goShawks.forEach(goShawk -> goShawk.setBestAdaptation(Math.min(goShawk.getAdaptationValue(), goShawk.getBestAdaptation())));
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

    private int random(int min, int max) {
        return random.nextInt(max) + min;
    }

    private void fillPreys() {
        preys = preyFactory.create(properties.getSize());
    }

    private Prey getRandomPrey() {
        int index = random.nextInt(preys.size());
        return preys.remove(index);
    }

    private int random(int min) {
        return random.nextInt(properties.getDimension() - min) + min;
    }
}
