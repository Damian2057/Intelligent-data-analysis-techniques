package p.lodz.pl.PSO;

import lombok.extern.java.Log;
import p.lodz.pl.DE.model.DataSet;
import p.lodz.pl.DE.model.Specimen;
import p.lodz.pl.PSO.factory.ParticleFactory;
import p.lodz.pl.PSO.model.Particle;
import p.lodz.pl.chart.ChartGenerator;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.PSOProperties;
import org.apache.commons.lang3.Range;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.logging.Logger;
import p.lodz.pl.functions.AdaptationFunction;
import p.lodz.pl.functions.Functions;

import static p.lodz.pl.enums.Const.ACCURACY;
import static p.lodz.pl.enums.Const.ITERATION;

@Log
public class PSOAlgorithm implements PSO{

    private static final PSOProperties properties = Config.getPSOProperties();
    private static final Range<Double> xRange = Range.between(properties.getXDownBorder(), properties.getXUpBorder());
    private final Logger logger = Logger.getLogger(PSO.class.getSimpleName());
    private final ParticleFactory factory = new ParticleFactory();
    private final List<Particle> swarm;
    private Particle bestParticle;
    private final List<DataSet> dataSets = new ArrayList<>();
    private final Functions function;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Function<Particle, Particle> findTheBest = checked ->
            checked.getBestAdaptation() < bestParticle.getBestAdaptation() ? checked : bestParticle;

    public PSOAlgorithm() {
        logger.info("The simulation has started");
        this.function = new AdaptationFunction(properties.getAdaptationFunction());
        swarm = factory.createSwarm(properties.getNumberOfParticles());
        bestParticle = swarm.get(0);
    }

    public Future<PSO> start() {
        return executor.submit(() -> {
            if (ITERATION.getName().equals(properties.getStopCondition())) {
                for (int i = 0; i < properties.getNumber(); i++) {
                    calculateAdaptation();
                    for (Particle particle : swarm) {
                        bestParticle = findTheBest.apply(particle);
                    }
                    for (Particle particle : swarm) {
                        updateParticlePosition(particle);
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
            log.info(String.format("\n========SOLUTION========\nThread: %s \nResult: %s \nFound in: %s\n========================", Thread.currentThread().getName(),
                    bestParticle.getAdaptationValue(), dataSets.size()));
            return this;
        });
    }

    private void calculateAdaptation() {
        for (Particle particle : swarm) {
            double adaptation = adaptationValue(particle);
            particle.setAdaptationValue(adaptation);
            if (particle.getBestAdaptation() == 0) {
                particle.setBestAdaptation(adaptation);
            }
            if (particle.getAdaptationValue() < particle.getBestAdaptation()) {
                particle.setBestAdaptation(adaptation);
                particle.setBestXVector(particle.getXVector());
            }
        }
    }

    private double adaptationValue(Particle particle) {
        return particle.setAdaptationValue(function.function(particle.getXVector()));
    }

    private void updateParticlePosition(Particle particle) {
        particle.setSpeed(calculateSpeed(particle));

        List<Double> newPosition =  new ArrayList<>();
        List<Double> currentPosition = particle.getXVector();

        for (int i = 0; i < properties.getDimension(); i++) {
            double newPos = currentPosition.get(i) + particle.getSpeed();

            if (xRange.contains(newPos + particle.getSpeed())) {
                newPosition.add(newPos);
            } else {
                if (newPos + particle.getSpeed() > properties.getXUpBorder()) {
//                    particle.setXVector(properties.getXUpBorder());
                    newPosition.add(properties.getXUpBorder());
                }
                else {
//                    particle.setXVector(properties.getXDownBorder());
                    newPosition.add(properties.getXDownBorder());
                }
            }
        }
        particle.setXVector(newPosition);
    }

    private double calculateSpeed(Particle particle) {
        double inertia = properties.getInertia() * particle.getSpeed();

        double socialComponent = socialAcceleration() * euclideanDistance(bestParticle.getXVector(), particle.getXVector());
        double cognitiveComponent = cognitiveAcceleration() * euclideanDistance(particle.getBestXVector(), particle.getXVector());
        return inertia + socialComponent + cognitiveComponent;
    }

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
        return properties.getSocialConstant() * getLevelOfComponent();
    }

    private double cognitiveAcceleration() {
        return properties.getCognitiveConstant() * getLevelOfComponent();
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
    public Specimen getBest() {
        return null;
    }

    @Override
    public Particle getBestParticle() {
        return bestParticle;
    }
}
