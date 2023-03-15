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
import p.lodz.pl.DE.model.DataSet;

import java.util.List;

public class ChartGenerator extends ApplicationFrame {

    public ChartGenerator(String title, List<DataSet> dataSets, List<DataSet> avgSets) {

        super("Chart: "+ title);

        final XYSeries bestSeries = new XYSeries("fbest(x)");
        for (DataSet set : dataSets) {
            bestSeries.add(set.getRound(), set.getBestAdaptation());
        }
        final XYSeries avgSeries = new XYSeries("favg(x)");
        for (DataSet set : avgSets) {
            avgSeries.add(set.getRound(), set.getAvgAdaptation());
        }

        final XYSeriesCollection data = new XYSeriesCollection(bestSeries);
        data.addSeries(avgSeries);

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

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));
        setContentPane(chartPanel);
    }
}
