package org.particle.simulation;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.lang3.Range;
import org.particle.chart.ChartGenerator;
import org.particle.chart.DataSet;
import org.particle.config.Config;
import org.particle.factories.Factory;
import org.particle.model.Particle;
import org.particle.model.Properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Logger;

public class AlgorithmImpl implements Algorithm {

    private static final Properties properties = Config.getProperties();
    private static final int NUMBER_OF_ITERATION = Config.getProperties().getNumberOfIteration();
    private static final int DISPLAY = Config.getProperties().getDisplay();
    private static final Range<Double> xRange = Range.between(properties.getXDownBorder(), properties.getXUpBorder());
    private static final Range<Double> yRange = Range.between(properties.getYDownBorder(), properties.getYUpBorder());
    private final Expression expression = new ExpressionBuilder(properties.getAdaptationFunction())
            .variables("x", "y")
            .build();
    private final Logger logger = Logger.getLogger(Algorithm.class.getSimpleName());
    private final Factory factory = new Factory();
    private final List<Particle> swarm;
    private Particle bestParticle;
    private final Function<Particle, Particle> findTheBest = checked ->
            checked.getBestAdaptation() < bestParticle.getBestAdaptation() ? checked : bestParticle;

    public AlgorithmImpl() {
        logger.info("The simulation has started");
        swarm = factory.createSwarm(properties.getNumberOfParticles());
        bestParticle = swarm.get(0);
    }

    @Override
    public void run() {
        List<DataSet> dataSets = new ArrayList<>();
        List<DataSet> maxDataSets = new ArrayList<>();
         for (int i = 0; i < NUMBER_OF_ITERATION; i++) {
             calculateAdaptation();
             for (Particle particle : swarm) {
                 bestParticle = findTheBest.apply(particle);
             }
             for (Particle particle: swarm) {
                 updateParticlePosition(particle);
             }
             if(i % DISPLAY == 0) {
                 logger.info("Round of simulation number: " + i);
                 logger.info("Current best: " + bestParticle.toString());
                 dataSets.add(new DataSet(i, getAvg(swarm)));
                 maxDataSets.add(new DataSet(i, bestParticle.getBestAdaptation()));
             }
        }

        logger.info("Solution: " + bestParticle.toString());
        ChartGenerator chartGenerator = new ChartGenerator(dataSets,
                "Avg: " + bestParticle.getBestX() + " " + bestParticle.getBestY());
        chartGenerator.pack();
        chartGenerator.setVisible(true);

        ChartGenerator chartGenerator2 = new ChartGenerator(maxDataSets,
                "Solution: " + bestParticle.getBestX() + " " + bestParticle.getBestY());
        chartGenerator2.pack();
        chartGenerator2.setVisible(true);

    }

    private void updateParticlePosition(Particle particle) {
        particle.setSpeed(calculateSpeed(particle));
        if(xRange.contains(particle.getX() + particle.getSpeed())) {
            particle.setX(particle.getX() + particle.getSpeed());
        } else {
            if(particle.getX() + particle.getSpeed() > properties.getXUpBorder()) {
                particle.setX(properties.getXUpBorder());
            } else {
                particle.setX(properties.getXDownBorder());
            }
        }
        if(yRange.contains(particle.getY() + particle.getSpeed())) {
            particle.setY(particle.getY() + particle.getSpeed());
        } else {
            if(particle.getY() + particle.getSpeed() > properties.getYUpBorder()) {
                particle.setY(properties.getYUpBorder());
            } else {
                particle.setY(properties.getYDownBorder());
            }
        }
    }

    private double calculateSpeed(Particle particle) {
        double inertia = properties.getInertia() * particle.getSpeed();
        double socialComponent = socialAcceleration() * euclideanDistance(bestParticle.getBestX(),
                bestParticle.getBestY(),
                particle.getX(),
                particle.getY());
        double cognitiveComponent = cognitiveAcceleration() * euclideanDistance(particle.getBestX(),
                particle.getBestY(),
                particle.getX(),
                particle.getY());
        return inertia + socialComponent + cognitiveComponent;
    }

    private double euclideanDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    private double socialAcceleration() {
        return properties.getSocialConstant() * getLevelOfComponent();
    }

    private double cognitiveAcceleration() {
        return properties.getCognitiveConstant() * getLevelOfComponent();
    }

    private void calculateAdaptation() {
        for (Particle particle : swarm) {
            double adaptation = adaptationValue(particle.getX(), particle.getY());
            particle.setCurrentAdaptation(adaptation);
            if (particle.getBestAdaptation() == 0) {
                particle.setBestAdaptation(adaptation);
            }
            if (particle.getCurrentAdaptation() < particle.getBestAdaptation()) {
                particle.setBestAdaptation(adaptation);
                particle.setBestX(particle.getX());
                particle.setBestY(particle.getY());
            }
        }
    }

    private double adaptationValue(double x, double y) {
        return expression.setVariable("x", x).setVariable("y", y).evaluate();
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

}
