package org.ant.simulation;

import org.ant.config.Config;
import org.ant.config.Properties;
import org.ant.readers.LocationReader;

public class AlgorithmImpl implements Algorithm {

    private static final Properties properties = Config.getProperties();


    @Override
    public void run() {
        for (int i = 0; i < properties.getNumberOfIteration(); i++) {

        }
    }

}
