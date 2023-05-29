package p.lodz.pl.algorithm.common;

import p.lodz.pl.chart.DataSet;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;
import p.lodz.pl.functions.AdaptationFunction;
import p.lodz.pl.functions.Functions;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
    protected DecimalFormat format = new DecimalFormat("###.###");

    protected AlgorithmBase(String type) {
        this.type = type;
        this.function = new AdaptationFunction(properties.getAdaptationFunction());
    }

    public String getType() {
        return type;
    }
}