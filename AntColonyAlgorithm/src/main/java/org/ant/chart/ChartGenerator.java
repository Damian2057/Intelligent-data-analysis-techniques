package org.ant.chart;


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

        super("Chart: " + title);

        final XYSeries originSeries = new XYSeries("f(x)");
        final XYSeries maxSeries = new XYSeries("fmax(x)");
        final XYSeries minSeries = new XYSeries("fmin(x)");
        for (DataSet dataSet : dataSets) {
        }

        final XYSeriesCollection data = new XYSeriesCollection(originSeries);
        data.addSeries(maxSeries);
        data.addSeries(minSeries);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Chart: " + title,
                "Epochs",
                "adaptation",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, false);

        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesShapesVisible(1, false);

        renderer.setSeriesLinesVisible(2, false);
        plot.setRenderer(renderer);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));
        setContentPane(chartPanel);
    }
}

