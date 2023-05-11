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
public class OPSOAlgorithm extends AlgorithmBase implements PSO {

    public OPSOAlgorithm() {
        super();
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
                    System.out.println(format.format(i / properties.getNumber() * 100) + " %");
                    applyAlgorithm();
                    applyOsmosis();

                    dataSets.add(new DataSet(i, getAvgAdaptation(), getBestAdaptation()));
                }

            } else if (ACCURACY.getName().equals(properties.getStopCondition())) {

                int repetitionCounter = 0;
                int index = 0;
                double repetition = properties.getSwarmSize() * 0.5;

                List<Double> oldBest = new ArrayList<>(Collections.nCopies(properties.getNumberOfSubSwarms(), Double.MAX_VALUE));
                while (repetitionCounter < repetition) {
                    System.out.println("Current best: " + getBestAdaptation());
                    applyAlgorithm();
                    applyOsmosis();

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

            return this;
        });
    }

    private void applyOsmosis() {

    }
}
