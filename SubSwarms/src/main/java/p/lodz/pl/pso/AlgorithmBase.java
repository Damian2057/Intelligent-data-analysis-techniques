package p.lodz.pl.pso;

import p.lodz.pl.chart.DataSet;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;
import p.lodz.pl.functions.AdaptationFunction;
import p.lodz.pl.functions.Functions;
import p.lodz.pl.pso.model.Particle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AlgorithmBase {

    protected static final String ALG_START = "\n========START========\nThread: %s\n=====================";
    protected static final String ALG_SOL =   "\n========SOLUTION========\nThread: %s \nResult: %s \nFound in: %s\n========================";
    protected static final Properties properties = Config.getProperties();
    protected final ExecutorService executor = Executors.newSingleThreadExecutor();
    protected final List<DataSet> dataSets = new ArrayList<>();
    protected final Functions function;
    protected final Random random = new Random();
    private final String type;
    protected List<Swarm> swarms = new ArrayList<>();
    protected DecimalFormat format = new DecimalFormat("###.###");

    protected AlgorithmBase(String type) {
        this.type = type;
        this.function = new AdaptationFunction(properties.getAdaptationFunction());
    }

    public double getAvgAdaptation() {
        return swarms.stream()
                .mapToDouble(swarm -> swarm.getBestParticle().getBestAdaptation())
                .average()
                .orElseThrow();
    }

    protected void applyAlgorithm() {
        for (Swarm env : swarms) {
            for (Particle particle : env.getSwarm()) {
                env.updateParticlePosition(particle);
            }
            env.calculateAdaptation();
            env.setBestParticle();
        }
    }

    protected boolean isImprovementInResult(List<Double> oldBest) {
        for (int i = 0; i < swarms.size(); i++) {
            double div = oldBest.get(i) - swarms.get(i).getBestParticle().getBestAdaptation();
            if (div > properties.getNumber()) {
                for (int j = 0; j < oldBest.size(); j++) {
                    oldBest.set(j, swarms.get(j).getBestParticle().getBestAdaptation());
                }
                return true;
            }
        }
        return false;
    }

    public double getBestAdaptation() {
        return swarms.stream()
                .mapToDouble(swarm -> swarm.getBestParticle().getBestAdaptation())
                .min().orElseThrow();
    }

    public List<DataSet> getDataSets() {
        return dataSets;
    }

    public Particle getBest() {
        return swarms.stream()
                .map(Swarm::getBestParticle)
                .min(Comparator.comparingDouble(Particle::getBestAdaptation))
                .orElse(null);
    }

    public String getType() {
        return type;
    }
}
