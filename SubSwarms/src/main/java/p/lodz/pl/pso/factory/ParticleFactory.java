package p.lodz.pl.pso.factory;

import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;
import p.lodz.pl.pso.model.Particle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ParticleFactory {

    private static final Properties properties = Config.getProperties();

    public List<Particle> createSwarm(int size) {
        List<Particle> swarm = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            swarm.add(new Particle(i, drawValue(properties.getXRange()[0], properties.getXRange()[1]), speedList()));
        }
        return swarm;
    }

    private List<Double> drawValue(double downRange, double upRange) {
        Random random = new Random();
        int size = properties.getDimension();
        List<Double> values = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            double value = downRange + (upRange - downRange) * random.nextDouble();
            values.add(value);
        }
        return values;
    }

    private List<Double> speedList() {
        int size = properties.getDimension();
        return new ArrayList<>(Collections.nCopies(size, 0.0));
    }
}
