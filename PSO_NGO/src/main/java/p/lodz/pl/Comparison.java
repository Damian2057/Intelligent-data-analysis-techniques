package p.lodz.pl;

import lombok.extern.java.Log;
import p.lodz.pl.algorithm.pso.PSOAlgorithm;
import p.lodz.pl.chart.ChartGenerator;
import p.lodz.pl.chart.DataSet;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Log
public class Comparison {

    private final Properties properties = Config.getProperties();
    private final List<Algorithm<?>> results = new ArrayList<>();

    public void compare() {
        createTasks();

        log.info("\n========Data collecting========");
        Algorithm<?> pso = getBestResult("PSO");
        List<DataSet> psoBestResult = pso.getDataSets();
        List<DataSet> psoAvgResult = getAvgResult("PSO");

        Algorithm<?> ngo = getBestResult("NGO");
        List<DataSet> ngoBestResult = ngo.getDataSets();
        List<DataSet> ngoAvgResult = getAvgResult("NGO");

        log.info("\n========Generating charts========");

        String title = String.format("Comparison of two algorithms for %s runs", results.size() / 2);
        ChartGenerator chartCompare = new ChartGenerator(title,
                psoBestResult,
                psoAvgResult,
                ngoBestResult,
                ngoAvgResult);
        chartCompare.pack();
        chartCompare.setVisible(true);

        double epsoAvgRes = getAvgFinalResult("PSO");
        double opsoAvgRes = getAvgFinalResult("NGO");

        log.info(String.format("""
                        \n========Summary========
                        PSO Best result: %s
                        PSO Avg result: %s
                        PSO Deviation: %s
                        =======================
                        NGO Best result: %s
                        NGO Avg result: %s
                        NGO Deviation: %s""",
                pso.getBest().getBestAdaptationValue(),
                epsoAvgRes,
                getStandardDeviation(epsoAvgRes, "PSO"),
                ngo.getBest().getAdaptationValue(),
                opsoAvgRes,
                getStandardDeviation(opsoAvgRes, "NGO")));
    }

    private List<DataSet> getAvgResult(String type) {
        int minIndex = results.stream()
                .filter(algorithm -> algorithm.getType().equals(type))
                .mapToInt(x -> x.getDataSets().size())
                .min().orElse(0);
        List<DataSet> avgSet = new ArrayList<>();
        for (int i = 0; i < minIndex; i++) {
            int finalI = i;
            double value = results.stream()
                    .filter(algorithm -> algorithm.getType().equals(type))
                    .mapToDouble(x -> x.getDataSets().get(finalI).getAvgAdaptation())
                    .average().orElse(0.0);
            avgSet.add(new DataSet(i, value, -1));
        }

        return avgSet;
    }

    private double getAvgFinalResult(String type) {
        return results.stream()
                .filter(algorithm -> algorithm.getType().equals(type))
                .mapToDouble(x -> x.getBest().getAdaptationValue())
                .average()
                .orElse(0.0);
    }

    private Algorithm<?> getBestResult(String type) {
        return results.stream()
                .filter(algorithm -> algorithm.getType().equals(type))
                .min(Comparator.comparingDouble(x -> x.getBest().getAdaptationValue()))
                .orElseThrow(NoSuchElementException::new);
    }

    private double getStandardDeviation(double avg, String type) {
        return Math.sqrt(results.stream()
                .filter(algorithm -> algorithm.getType().equals(type))
                .mapToDouble(x -> Math.pow((x.getBest().getBestAdaptationValue() - avg), 2))
                .average().orElse(0.0));
    }

    private void createTasks() {
        try {
            List<Future<?>> tasks = new ArrayList<>();
            for (int i = 0; i < properties.getStartTimes(); i++) {
                Future<?> psoTask = new PSOAlgorithm().start();
//                Future<?> psoTask = new OPSOAlgorithm().start();
//                tasks.add(deTask);
                tasks.add(psoTask);
            }

            long start = System.currentTimeMillis();

            while (!tasks.stream().allMatch(Future::isDone)) {
                long current = System.currentTimeMillis();
                System.out.print(String.format("\rCompleted threads: %s/%s, time: %s ms",
                        tasks.stream().filter(Future::isDone).count(),
                        tasks.size(),
                        current - start
                ));
            }

            for (Future<?> f : tasks) {
                Algorithm<?> result = (Algorithm<?>) f.get();
                results.add(result);
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
