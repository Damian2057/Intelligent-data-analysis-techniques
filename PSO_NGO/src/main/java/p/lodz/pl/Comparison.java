package p.lodz.pl;

import lombok.extern.java.Log;
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
        Algorithm<?> epso = getBestResult("EPSO");
        List<DataSet> epsoBestResult = epso.getDataSets();
        List<DataSet> epsoAvgResult = getAvgResult("EPSO");

        Algorithm<?> opso = getBestResult("OPSO");
        List<DataSet> opsoBestResult = opso.getDataSets();
        List<DataSet> opsoAvgResult = getAvgResult("OPSO");

        log.info("\n========Generating charts========");

        String title = String.format("Comparison of two algorithms for %s runs", results.size() / 2);
        ChartGenerator chartCompare = new ChartGenerator(title,
                epsoBestResult,
                epsoAvgResult,
                opsoBestResult,
                opsoAvgResult);
        chartCompare.pack();
        chartCompare.setVisible(true);

        double epsoAvgRes = getAvgFinalResult("EPSO");
        double opsoAvgRes = getAvgFinalResult("OPSO");

        log.info(String.format("""
                        \n========Summary========
                        EPSO Best result: %s
                        EPSO Avg result: %s
                        EPSO Deviation: %s
                        =======================
                        OPSO Best result: %s
                        OPSO Avg result: %s
                        OPSO Deviation: %s""",
                epso.getBest().getBestAdaptationValue(),
                epsoAvgRes,
                getStandardDeviation(epsoAvgRes, "EPSO"),
                opso.getBest().getAdaptationValue(),
                opsoAvgRes,
                getStandardDeviation(opsoAvgRes, "OPSO")));
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
//                Future<?> deTask = new EPSOAlgorithm().start();
//                Future<?> psoTask = new OPSOAlgorithm().start();
//                tasks.add(deTask);
//                tasks.add(psoTask);
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
