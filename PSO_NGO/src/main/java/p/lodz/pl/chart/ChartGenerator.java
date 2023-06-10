package p.lodz.pl.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;

public class ChartGenerator extends ApplicationFrame {

    public ChartGenerator(String title, List<DataSet> psoDataBestSets,
                          List<DataSet> psoDataAvgSets,
                          List<DataSet> ngoDataBestSets,
                          List<DataSet> ngoDataAvgSets) {

        super("Chart: "+ title);

        final XYSeries bestPsoSeries = new XYSeries("psoBest(x)");
        for (DataSet set : psoDataBestSets) {
            bestPsoSeries.add(set.getRound(), set.getBestAdaptation());
        }
        final XYSeries avgPsoSeries = new XYSeries("psoAvg(x)");
        for (DataSet set : psoDataAvgSets) {
            avgPsoSeries.add(set.getRound(), set.getAvgAdaptation());
        }
        final XYSeries bestNgoSeries = new XYSeries("ngoBest(x)");
        for (DataSet set : ngoDataBestSets) {
            bestNgoSeries.add(set.getRound(), set.getBestAdaptation());
        }
        final XYSeries avgNgoSeries = new XYSeries("ngoAvg(x)");
        for (DataSet set : ngoDataAvgSets) {
            avgNgoSeries.add(set.getRound(), set.getAvgAdaptation());
        }

        final XYSeriesCollection data = new XYSeriesCollection(bestPsoSeries);
        data.addSeries(avgPsoSeries);
        data.addSeries(bestNgoSeries);
        data.addSeries(avgNgoSeries);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Chart: "+ title,
                "Round",
                "Adaptation",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        renderer.setSeriesShapesVisible(2, false);
        renderer.setSeriesShapesVisible(3, false);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));
        setContentPane(chartPanel);
    }
}