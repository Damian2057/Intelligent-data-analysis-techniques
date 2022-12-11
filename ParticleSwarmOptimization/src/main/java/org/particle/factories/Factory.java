package org.particle.factories;

import org.particle.config.Config;
import org.particle.model.Particle;
import org.particle.model.Properties;

import java.util.ArrayList;
import java.util.List;

public class Factory {

    private static final Properties prop = Config.getProperties();

    public List<Particle> createSwarm(int size) {
        List<Particle> swarm = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            swarm.add(new Particle());
        }
        return swarm;
    }

}
