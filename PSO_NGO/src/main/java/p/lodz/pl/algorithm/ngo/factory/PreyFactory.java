package p.lodz.pl.algorithm.ngo.factory;

import p.lodz.pl.algorithm.common.Factory;
import p.lodz.pl.algorithm.ngo.model.GoShawk;
import p.lodz.pl.algorithm.ngo.model.Prey;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PreyFactory implements Factory<Prey> {
    private static final Properties properties = Config.getProperties();

    @Override
    public List<Prey> create(int size) {
        List<Prey> swarm = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            swarm.add(new Prey(drawValue(properties.getXRange()[0], properties.getXRange()[1])));
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
}
