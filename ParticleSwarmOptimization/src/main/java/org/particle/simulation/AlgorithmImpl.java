package org.particle.simulation;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.particle.config.Config;
import org.particle.factories.Factory;
import org.particle.model.Particle;
import org.particle.model.Properties;

import java.util.List;
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


    public AlgorithmImpl() {
        logger.info("The simulation has started");
        swarm = factory.createSwarm(properties.getNumberOfParticles());
    }

    @Override
    public void run() {
         for (int i = 0; i < numberOfIterations; i++) {

        }
    }

    private double calculateAdaptation(double x, double y) {
        return expression.setVariable("x", x).setVariable("y", y).evaluate();
    }

}
