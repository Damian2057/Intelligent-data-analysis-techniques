package p.lodz.pl.pso;

import lombok.extern.java.Log;
import p.lodz.pl.chart.DataSet;
import p.lodz.pl.pso.model.Particle;

import java.util.List;
import java.util.concurrent.Future;

import static p.lodz.pl.enums.Const.ACCURACY;
import static p.lodz.pl.enums.Const.ITERATION;

@Log
public class OPSOAlgorithm extends BaseParams implements PSO {

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
                    for (Swarm env : swarms) {
                        env.calculateAdaptation();
                        env.setBestParticle();
                        for (Particle particle : env.getSwarm()) {
                            env.updateParticlePosition(particle);
                        }
                    }

//                    dataSets.add(new DataSet(i, getAvgAdaptation(), bestSolution.getBestAdaptation()));
                }

            } else if (ACCURACY.getName().equals(properties.getStopCondition())) {

                int repetitionCounter = 0;
                int index = 0;
                double repetition = properties.getSwarmSize() * 0.5;

//                while (repetitionCounter < repetition) {
//                    calculateAdaptation();
//                    bestParticle = getBestParticleInIteration().clone();
//                    bestSolution = getTheBestParticle().clone();
//                    double best = bestSolution.getBestAdaptation();
//                    for (Particle particle : swarm) {
//                        updateParticlePosition(particle);
//                    }
//                    bestSolution = getTheBestParticle().clone();
//                    dataSets.add(new DataSet(index, getAvgAdaptation(), bestSolution.getBestAdaptation()));
//                    if (best - bestSolution.getBestAdaptation() < properties.getNumber()) {
//                        repetitionCounter++;
//                    } else {
//                        repetitionCounter = 0;
//                    }
//                    index++;
//                }

            } else {
                throw new IllegalArgumentException("invalid stop condition of the algorithm");
            }

            return this;
        });
    }

    @Override
    public List<DataSet> getDataSets() {
        return null;
    }

    @Override
    public Particle getBest() {
        return null;
    }
}
