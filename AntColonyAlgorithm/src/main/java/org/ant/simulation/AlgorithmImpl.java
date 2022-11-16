package org.ant.simulation;

import lombok.extern.slf4j.Slf4j;
import org.ant.config.Config;
import org.ant.config.Properties;
import org.ant.factory.Factory;
import org.ant.model.Ant;

import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

public class AlgorithmImpl implements Algorithm {

    private static final Properties properties = Config.getProperties();
    private final static int numberOfIterations = Config.getProperties().getNumberOfIteration();
    private static final int display = Config.getProperties().getDisplay();
    private final Factory factory = new Factory();
    private List<Ant> colony;
    private Ant theBestAnt;
    private final Logger logger = Logger.getLogger(Algorithm.class.getSimpleName());
    private final Function<Ant, Ant> fineTheBest = best -> best.getDistance() < theBestAnt.getDistance() ? best : theBestAnt;

    public AlgorithmImpl() {
        logger.info("The simulation has started");
        colony = factory.createColony(properties.getNumberOfAnts());
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfIterations; i++) {
            if(i % display == 0) {
                logger.info("Round of simulation number: " + i);
                logger.info("Current best distance: " + theBestAnt.getDistance());
//                dataSets.add(new DataSet(i,generation));
            }

        }
    }

}
