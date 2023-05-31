package p.lodz.pl.algorithm.pso;

import lombok.extern.java.Log;
import org.apache.commons.lang3.Range;
import p.lodz.pl.algorithm.common.AlgorithmBase;
import p.lodz.pl.algorithm.common.Factory;
import p.lodz.pl.algorithm.pso.factory.ParticleFactory;
import p.lodz.pl.algorithm.pso.model.Particle;
import p.lodz.pl.chart.DataSet;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Future;

import static p.lodz.pl.enums.Const.ACCURACY;
import static p.lodz.pl.enums.Const.ITERATION;

@Log
public class PSOAlgorithm extends AlgorithmBase implements PSO {

    private static final Range<Double> xRange = Range.between(properties.getXRange()[0], properties.getXRange()[1]);
    private final List<Particle> swarm;
    private Particle bestParticle;
    private Particle bestSolution;

    public PSOAlgorithm() {
        super("PSO");
        Factory<Particle> factory = new ParticleFactory();
        this.swarm = factory.create(properties.getSize());
        calculateAdaptation();
    }

    @Override
    public Future<PSO> start() {
        return executor.submit(() -> {
            log.info(String.format(ALG_START, Thread.currentThread().getName()));

            if (ITERATION.getName().equals(properties.getStopCondition())) {

                for (int i = 0; i < properties.getNumber(); i++) {
                    calculateAdaptation();
                    bestParticle = getBestParticleInIteration().clone();
                    bestSolution = getTheBestParticle().clone();
                    for (Particle particle : swarm) {
                        updateParticlePosition(particle);
                    }
                    dataSets.add(new DataSet(i, getAvgAdaptation(), bestSolution.getBestAdaptation()));
                }

            } else if (ACCURACY.getName().equals(properties.getStopCondition())) {

                int repetitionCounter = 0;
                int index = 0;
                double repetition = properties.getSize() * 0.5;

                while (repetitionCounter < repetition) {
                    calculateAdaptation();
                    bestParticle = getBestParticleInIteration().clone();
                    bestSolution = getTheBestParticle().clone();
                    double best = bestSolution.getBestAdaptation();
                    for (Particle particle : swarm) {
                        updateParticlePosition(particle);
                    }
                    bestSolution = getTheBestParticle().clone();
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
    public Particle getBest() {
        return bestSolution;
    }

    private void calculateAdaptation() {
        swarm.forEach(particle -> particle.setAdaptationValue(function.function(particle.getXVector())));
        swarm.forEach(particle -> particle.setBestAdaptation(Math.min(particle.getAdaptationValue(), particle.getBestAdaptation())));
    }

    private void updateParticlePosition(Particle particle) {
        for (int i = 0; i < properties.getDimension(); i++) {
            double speed = calculateSpeed(particle, i);
            particle.getSpeed().set(i, speed);
            double newXPos = particle.getXVector().get(i) + particle.getSpeed().get(i);
            if (xRange.contains(newXPos)) {
                particle.getXVector().set(i, newXPos);
            } else {
                if (newXPos > properties.getXRange()[1]) {
                    particle.getXVector().set(i, properties.getXRange()[1]);
                }
                else {
                    particle.getXVector().set(i, properties.getXRange()[0]);
                }
            }
        }
    }

    private double calculateSpeed(Particle particle, int index) {
        double inertia = properties.getInertia() * particle.getSpeed().get(index);

        double socialComponent = socialAcceleration() * (bestParticle.getXVector().get(index) - particle.getXVector().get(index));
        double cognitiveComponent = cognitiveAcceleration() * (particle.getBestXVector().get(index) - particle.getXVector().get(index));
        return inertia + socialComponent + cognitiveComponent;
    }

    private double socialAcceleration() {
        return properties.getSocialConstant() * getLevelOfComponent();
    }

    private double cognitiveAcceleration() {
        return properties.getCognitiveConstant() * getLevelOfComponent();
    }

    private double getLevelOfComponent() {
        return 0 + (1) * random.nextDouble();
    }

    private double getAvgAdaptation() {
        return swarm.stream()
                .mapToDouble(Particle::getBestAdaptation)
                .average()
                .orElse(0.0);
    }

    private Particle getBestParticleInIteration() {
        return swarm.stream()
                .min(Comparator.comparingDouble(Particle::getAdaptationValue))
                .orElseThrow(NoSuchElementException::new);
    }

    private Particle getTheBestParticle() {
        return swarm.stream()
                .min(Comparator.comparingDouble(Particle::getBestAdaptation))
                .orElseThrow(NoSuchElementException::new);
    }
}
