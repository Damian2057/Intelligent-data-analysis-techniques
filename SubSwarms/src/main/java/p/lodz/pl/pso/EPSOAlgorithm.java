package p.lodz.pl.pso;

import lombok.extern.java.Log;
import p.lodz.pl.chart.DataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

import static p.lodz.pl.enums.Const.ACCURACY;
import static p.lodz.pl.enums.Const.ITERATION;

@Log
public class EPSOAlgorithm extends AlgorithmBase implements PSO {

    public EPSOAlgorithm() {
        super("EPSO");
        for (int i = 0; i < properties.getNumberOfSubSwarms(); i++) {
            this.swarms.add(new Swarm(properties.getSwarmSize()));
        }
    }

    @Override
    public Future<PSO> start() {
        return executor.submit(() -> {
            log.info(String.format(ALG_START, Thread.currentThread().getName()));

            if (ITERATION.getName().equals(properties.getStopCondition())) {
                for (int i = 0; i < properties.getNumber(); i++) {
                    applyAlgorithm();
                    updateBestParticlesInEverySwarm();

                    dataSets.add(new DataSet(i, getAvgAdaptation(), getBestAdaptation()));
                }
            } else if (ACCURACY.getName().equals(properties.getStopCondition())) {

                int repetitionCounter = 0;
                int index = 0;
                double repetition = properties.getSwarmSize() * 0.5;

                List<Double> oldBest = new ArrayList<>(Collections.nCopies(properties.getNumberOfSubSwarms(), Double.MAX_VALUE));
                while (repetitionCounter < repetition) {
                    applyAlgorithm();
                    updateBestParticlesInEverySwarm();

                    dataSets.add(new DataSet(index, getAvgAdaptation(), getBestAdaptation()));
                    if (isImprovementInResult(oldBest)) {
                        repetitionCounter = 0;
                    } else {
                        repetitionCounter++;
                    }
                    index++;
                }
            } else {
                throw new IllegalArgumentException("invalid stop condition of the algorithm");
            }

            log.info(String.format(ALG_SOL,
                    Thread.currentThread().getName(),
                    getBestAdaptation(),
                    dataSets.size()));
            return this;
        });
    }

    private void updateBestParticlesInEverySwarm() {
        for (int i = 0; i < swarms.size(); i++) {
            List<Double> avgs = new ArrayList<>(Collections.nCopies(properties.getDimension(), 0.0));
            for (int j = 0; j < swarms.size(); j++) {
                if (i != j) {
                    for (int k = 0; k < properties.getDimension(); k++) {
                        avgs.set(k, swarms.get(j).getBestParticle().getXVector().get(k));
                    }
                }
            }
            for (int j = 0; j < properties.getDimension(); j++) {
                double avg = avgs.get(j) / (swarms.size() - 1) * (1.0 + gauss());
                avgs.set(j, avg);
            }
            swarms.get(i).getBestParticle().getXVector().set(i, 0.0);
        }
    }

    private double gauss() {
        return random.nextGaussian(0, 1);
    }
}
