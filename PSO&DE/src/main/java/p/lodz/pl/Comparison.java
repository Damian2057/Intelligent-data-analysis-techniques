package p.lodz.pl;

import lombok.extern.java.Log;
import p.lodz.pl.DE.DifferentialAlgorithm;
import p.lodz.pl.DE.DifferentialEvolution;
import p.lodz.pl.DE.model.DataSet;
import p.lodz.pl.chart.ChartGenerator;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.DEProperties;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Log
public class Comparison {

    private final DEProperties properties = Config.getDEProperties();
    private final List<Algorithm> results = new ArrayList<>();

    public void compare() {
        createTasks();
        log.info("\n========Generating charts========");

        String title = String.format("DifferentialEvolution for %s starts", results.size());
        ChartGenerator chartGenerator = new ChartGenerator(title, getBestDEResult().getDataSets(),
                getAvgDeResult());
        chartGenerator.pack();
        chartGenerator.setVisible(true);

    }

    private void createTasks() {
        try {
            List<Future<?>> tasks = new ArrayList<>();
            for (int i = 0; i < properties.getStartTimes(); i++) {
                Future<?> deTask = new DifferentialEvolution().start();
//                Future<?> psoTask = new PSOAlgorithm().start();
                tasks.add(deTask);
//                tasks.add(psoTask);

            }

            while (!tasks.stream().allMatch(Future::isDone)) {
            }

            for (Future<?> f : tasks) {
                Algorithm result = (Algorithm) f.get();
                results.add(result);
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Algorithm getBestDEResult() {
        return results.stream()
                .filter(x -> x instanceof DifferentialAlgorithm)
                .min(Comparator.comparingDouble(x -> x.getBest().getAdaptationValue()))
                .orElseThrow(NoSuchElementException::new);
    }

    private List<DataSet> getAvgDeResult() {
        int minIndex = results.stream().mapToInt(x -> x.getDataSets().size()).min().orElse(0);
        List<DataSet> avgSet = new ArrayList<>();
        for (int i = 0; i < minIndex; i++) {
            int finalI = i;
            double value = results.stream().mapToDouble(x -> x.getDataSets().get(finalI).getAvgAdaptation()).average().orElse(0.0);
            avgSet.add(new DataSet(i, value, -1));
        }

        return avgSet;
    }
}
