package p.lodz.pl.PSO;

import lombok.extern.java.Log;
import org.apache.commons.lang3.Range;
import p.lodz.pl.BaseParams;
import p.lodz.pl.PSO.factory.ParticleFactory;
import p.lodz.pl.PSO.model.Particle;
import p.lodz.pl.chart.DataSet;

import java.util.*;
import java.util.concurrent.Future;

import static p.lodz.pl.enums.Const.ACCURACY;
import static p.lodz.pl.enums.Const.ITERATION;

@Log
public class PSOAlgorithm extends BaseParams implements PSO {

    private static final Range<Double> xRange = Range.between(properties.getXRange()[0], properties.getXRange()[1]);
    private final List<Particle> swarm;
    private Particle bestParticle;

    public PSOAlgorithm() {
        super();
        ParticleFactory factory = new ParticleFactory();
        swarm = factory.createSwarm(properties.getPso().getNumberOfParticles());
        this.bestParticle = swarm.get(0);
    }

    public Future<PSO> start() {
        return executor.submit(() -> {
            log.info(String.format(ALG_START,
                    Thread.currentThread().getName()));

            if (ITERATION.getName().equals(properties.getStopCondition())) {

                for (int i = 0; i < properties.getNumber(); i++) {
                    calculateAdaptation();
                    bestParticle = getBestParticle();
                    for (Particle particle : swarm) {
                        //updateParticlePosition(particle);
                    }
                    dataSets.add(new DataSet(i, getAvg(swarm), bestParticle.getAdaptationValue()));
                }

            } else if (ACCURACY.getName().equals(properties.getStopCondition())) {

                int counter = 0;
                while (counter < properties.getNumber()) {
                    counter++;
                }

            } else {
                throw new IllegalArgumentException("invalid stop condition of the algorithm");
            }
            log.info(String.format(ALG_SOL,
                    Thread.currentThread().getName(),
                    bestParticle.getAdaptationValue(),
                    dataSets.size()));
            return this;
        });
    }

    private void calculateAdaptation() {
        swarm.forEach(particle -> particle.setAdaptationValue(function.function(particle.getXVector())));
    }

//    private void updateParticlePosition(Particle particle) {
//        particle.setSpeed(calculateSpeed(particle));
//
//        List<Double> newPosition =  new ArrayList<>();
//        List<Double> currentPosition = particle.getXVector();
//
//        for (int i = 0; i < properties.getDimension(); i++) {
//            double newPos = currentPosition.get(i) + particle.getSpeed();
//
//            if (xRange.contains(newPos + particle.getSpeed())) {
//                newPosition.add(newPos);
//            } else {
//                if (newPos + particle.getSpeed() > properties.getXRange()[1]) {
//                    newPosition.add(properties.getXRange()[1]);
//                }
//                else {
//                    newPosition.add(properties.getXRange()[0]);
//                }
//            }
//        }
//        particle.setXVector(newPosition);
//    }
//
//    private double calculateSpeed(Particle particle) {
//        double inertia = properties.getPso().getInertia() * particle.getSpeed();
//
//        double socialComponent = socialAcceleration() * euclideanDistance(bestParticle.getXVector(), particle.getXVector());
//        double cognitiveComponent = cognitiveAcceleration() * euclideanDistance(particle.getBestXVector(), particle.getXVector());
//        return inertia + socialComponent + cognitiveComponent;
//    }

    private double euclideanDistance(List<Double> bestXVector, List<Double> xVector) {
        double distance = 0.0;
        int size = properties.getDimension();
        for (int i = 0; i < size; i++) {
            double diff = xVector.get(i) - bestXVector.get(i);
            distance += diff * diff;
        }
        return Math.sqrt(distance);
    }

    private double socialAcceleration() {
        return properties.getPso().getSocialConstant() * getLevelOfComponent();
    }

    private double cognitiveAcceleration() {
        return properties.getPso().getCognitiveConstant() * getLevelOfComponent();
    }

    private double getLevelOfComponent() {
        Random random = new Random();
        return 0 + (1) * random.nextDouble();
    }

    private double getAvg(List<Particle> swarm) {
        double sum = 0;
        for (Particle particle : swarm) {
            sum += particle.getBestAdaptation();
        }
        return sum / swarm.size();
    }

    @Override
    public List<DataSet> getDataSets() {
        return dataSets;
    }

    @Override
    public Particle getBest() {
        return bestParticle;
    }

    private Particle getBestParticle() {
        return swarm.stream()
                .min(Comparator.comparingDouble(Particle::getAdaptationValue))
                .orElseThrow(NoSuchElementException::new);
    }
}
