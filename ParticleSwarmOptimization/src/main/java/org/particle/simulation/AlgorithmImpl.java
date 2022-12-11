package org.particle.simulation;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.particle.config.Config;
import org.particle.factories.Factory;
import org.particle.model.Particle;
import org.particle.model.Properties;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Logger;

public class AlgorithmImpl implements Algorithm {

    private static final Properties properties = Config.getProperties();
    private final static int numberOfIterations = Config.getProperties().getNumberOfIteration();
    private final Expression expression = new ExpressionBuilder(properties.getAdaptationFunction())
            .variables("x", "y")
            .build();
    private final Logger logger = Logger.getLogger(Algorithm.class.getSimpleName());
    private final Factory factory = new Factory();
    private List<Particle> swarm;
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
         for (int i = 0; i < numberOfIterations; i++) {
             calculateAdaptation();
             for (Particle particle : swarm) {
                 bestParticle = findTheBest.apply(particle);
             }
             for (Particle particle: swarm) {
                 updateParticlePosition(particle);
             }
        }

        logger.info("Solution: " + bestParticle.toString());
    }

    private void updateParticlePosition(Particle particle) {

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

}
