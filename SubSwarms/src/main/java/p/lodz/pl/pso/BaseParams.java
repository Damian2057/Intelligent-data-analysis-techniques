package p.lodz.pl.pso;

import org.apache.commons.lang3.Range;
import p.lodz.pl.chart.DataSet;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;
import p.lodz.pl.functions.AdaptationFunction;
import p.lodz.pl.functions.Functions;
import p.lodz.pl.pso.factory.ParticleFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseParams {

    protected static final String ALG_START = "\n========START========\nThread: %s\n=====================";
    protected static final String ALG_SOL =   "\n========SOLUTION========\nThread: %s \nResult: %s \nFound in: %s\n========================";
    protected static final Properties properties = Config.getProperties();
    protected static final Range<Double> xRange = Range.between(properties.getXRange()[0], properties.getXRange()[1]);
    protected final ExecutorService executor = Executors.newSingleThreadExecutor();
    protected final List<DataSet> dataSets = new ArrayList<>();
    protected final ParticleFactory factory = new ParticleFactory();
    protected final Functions function;
    protected final Random random = new Random();
    protected List<Swarm> swarms = new ArrayList<>();

    protected BaseParams() {
        this.function = new AdaptationFunction(properties.getAdaptationFunction());
    }
}
