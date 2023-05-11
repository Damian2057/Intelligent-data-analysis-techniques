package p.lodz.pl.pso;

import lombok.extern.java.Log;
import p.lodz.pl.chart.DataSet;
import p.lodz.pl.pso.model.Particle;

import java.util.List;
import java.util.concurrent.Future;

import static p.lodz.pl.enums.Const.ACCURACY;
import static p.lodz.pl.enums.Const.ITERATION;

@Log
public class EPSOAlgorithm extends BaseParams implements PSO {

    public EPSOAlgorithm() {
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
                    System.out.println(i);
                    for (Swarm env : swarms) {
                        for (Particle particle : env.getSwarm()) {
                            env.updateParticlePosition(particle);
                        }
                        env.calculateAdaptation();
                        env.setBestParticle();
                    }
                    updateBestParticlesInEverySwarm();

//                    dataSets.add(new DataSet(i, getAvgAdaptation(), bestSolution.getBestAdaptation()));
                }
                System.out.println(swarms);
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

    private void updateBestParticlesInEverySwarm() {
//        for (int i = 0; i < swarms.size(); i++) {
//            for (int j = 0; j < swarms.size(); j++) {
//                if (i != j) {
//                    double
//                    for (int k = 0; k < properties.getNumberOfSubSwarms(); k++) {
//
//                    }
//                    sum += swarms.get(i).getBestParticle().getXVector().get(index);
//                    sum /= properties.getDimension();
//                }
//            }
//        }
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
