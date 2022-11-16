package org.ant.simulation.algorithm;

import org.ant.config.Config;
import org.ant.config.Properties;

public class AlgorithmImpl implements Algorithm {

    private static final Properties properties = Config.getProperties();


    @Override
    public void run() {
        for (int i = 0; i < properties.getNumberOfIteration(); i++) {

        }
    }

}
