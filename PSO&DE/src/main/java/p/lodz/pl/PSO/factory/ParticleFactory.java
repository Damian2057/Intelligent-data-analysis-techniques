package p.lodz.pl.PSO.factory;

import p.lodz.pl.PSO.model.Particle;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.PSOProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleFactory {
    private static final PSOProperties prop = Config.getPSOProperties();

    public List<Particle> createSwarm(int size) {
        List<Particle> swarm = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            swarm.add(new Particle(drawValue(prop.getXDownBorder(), prop.getXUpBorder())));
        }
        return swarm;
    }

    private List<Double> drawValue(double downRange, double upRange) {
        Random random = new Random();
        int size = prop.getDimension();
        List<Double> values = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            double value = downRange + (upRange - downRange) * random.nextDouble();
            values.add(value);
        }
        return values;
    }

}
