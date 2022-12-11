package org.particle.chart;

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

    public ChartGenerator(List<DataSet> dataSets, String title) {
        super(title);

        final XYSeries avgSeries = new XYSeries("f(x)");
        for (DataSet dataSet : dataSets) {
            avgSeries.add(dataSet.getRound(), dataSet.getValue());
        }

        final XYSeriesCollection data = new XYSeriesCollection(avgSeries);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Round",
                "Distance",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesShapesVisible(0, false);
        plot.setRenderer(renderer);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));
        setContentPane(chartPanel);
    }
}
