package p.lodz.pl;

import lombok.extern.java.Log;
import p.lodz.pl.chart.ChartGenerator;
import p.lodz.pl.chart.DataSet;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;
import p.lodz.pl.pso.EPSOAlgorithm;
import p.lodz.pl.pso.OPSOAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Log
public class Comparison {

    private final Properties properties = Config.getProperties();
    private final List<Algorithm<?>> results = new ArrayList<>();

    public void compare() {
        createTasks();

        log.info("\n========Data collecting========");
//        Algorithm<?> de = getBestDEResult();
//        List<DataSet> deBestResult = de.getDataSets();
//        List<DataSet> deAvgResult = getAvgDeResult();
//
//        Algorithm<?> pso = getBestPSOResult();
//        List<DataSet> psoBestResult = pso.getDataSets();
//        List<DataSet> psoAvgResult = getAvgPSOResult();

        log.info("\n========Generating charts========");

        String title = String.format("Comparison of two algorithms for %s runs", results.size() / 2);
//        ChartGenerator chartCompare = new ChartGenerator(title,
//                deBestResult,
//                deAvgResult,
//                psoBestResult,
//                psoAvgResult);
//        chartCompare.pack();
//        chartCompare.setVisible(true);
//
//        double psoAvgRes = getAvgPsoResult();
//        double deAvgRes = getAvgDEResult();

//        log.info(String.format("""
//                        \n========Summary========
//                        PSO Best result: %s
//                        PSO Avg result: %s
//                        PSO Deviation: %s
//                        =======================
//                        DE Best result: %s
//                        DE Avg result: %s
//                        DE Deviation: %s""",
//                pso.getBest().getBestAdaptationValue(),
//                psoAvgRes,
//                getPsoStandardDeviation(psoAvgRes),
//                de.getBest().getAdaptationValue(),
//                deAvgRes,
//                getDeStandardDeviation(deAvgRes)));
    }

    private void createTasks() {
        try {
            List<Future<?>> tasks = new ArrayList<>();
            for (int i = 0; i < properties.getStartTimes(); i++) {
                Future<?> deTask = new EPSOAlgorithm().start();
                Future<?> psoTask = new OPSOAlgorithm().start();
                tasks.add(deTask);
                tasks.add(psoTask);
            }

            while (!tasks.stream().allMatch(Future::isDone)) {
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
