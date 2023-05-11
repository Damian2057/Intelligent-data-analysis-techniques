package p.lodz.pl.pso;

import lombok.extern.java.Log;
import p.lodz.pl.chart.DataSet;
import p.lodz.pl.pso.model.Particle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
            log.info(String.format(ALG_SOL,
                    Thread.currentThread().getName(),
                    getBestAdaptation(),
                    dataSets.size()));

            return this;
        });
    }

    private void applyOsmosis() {
        for (int i = 0; i < swarms.size() - 1; i++) {
            thresholdLogic(i, i + 1);
        }
        thresholdLogic(swarms.size() - 1, 0);
    }

    private void thresholdLogic(int x, int y) {
        double div = Math.abs(swarms.get(x).getCurrentBestParticle().getAdaptationValue() -
                swarms.get(y).getCurrentBestParticle().getAdaptationValue());
        double threshold = div / Math.max(swarms.get(x).getCurrentBestParticle().getAdaptationValue(),
                swarms.get(y).getCurrentBestParticle().getAdaptationValue());
        if (div > threshold) {
            List<Particle> selectedBest = new ArrayList<>();
            Comparator<Particle> comparing = Comparator.comparing(Particle::getAdaptationValue);
            swarms.get(x).getSwarm().sort(comparing);
            int numItems = (int) Math.round(swarms.get(x).getSwarm().size() * threshold);
            for (int j = 0; j < numItems; j++) {
                selectedBest.add(swarms.get(x).getSwarm().get(j));
            }
            swarms.get(x).getSwarm().removeAll(selectedBest);
            swarms.get(y).getSwarm().addAll(selectedBest);

            List<Particle> selectedWorst = new ArrayList<>();
            int size = swarms.get(y).getSwarm().size();
            swarms.get(y).getSwarm().sort(comparing);
            Collections.reverse(swarms.get(x).getSwarm());
            for (int j = size - 1; j > size - numItems - 1; j--) {
                selectedWorst.add(swarms.get(y).getSwarm().get(j));
            }
            swarms.get(y).getSwarm().removeAll(selectedWorst);
            swarms.get(x).getSwarm().addAll(selectedWorst);
        }
    }
}
