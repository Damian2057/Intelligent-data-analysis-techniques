package org.particle.factories;

import org.particle.config.Config;
import org.particle.model.Particle;
import org.particle.model.Properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Factory {

    private static final Properties prop = Config.getProperties();

    public List<Particle> createSwarm(int size) {
        List<Particle> swarm = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            swarm.add(new Particle(drawValue(prop.getXDownBorder(), prop.getXUpBorder()),
                    drawValue(prop.getYDownBorder(), prop.getYUpBorder())));
        }
        return swarm;
    }

    private double drawValue(double downRange, double upRange) {
        Random random = new Random();
        return downRange + (upRange - downRange) * random.nextDouble();
    }

}
