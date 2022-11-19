package org.ant.chart;


import org.ant.model.Ant;
import org.ant.model.Location;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.List;

public class ChartGenerator extends ApplicationFrame {

    public ChartGenerator(List<DataSet> dataSets, String title) {

        super("Chart: " + title);

        final XYSeries originSeries = new XYSeries("f(x)");
        final XYSeries avgSeries = new XYSeries("avg(x)");
        for (DataSet dataSet : dataSets) {
            for (Ant ant : dataSet.getList()) {
                originSeries.add(dataSet.getRound(), ant.getDistance());
            }
            avgSeries.add(dataSet.getRound(),getAvg(dataSet.getList()));
        }

        final XYSeriesCollection data = new XYSeriesCollection(originSeries);
        data.addSeries(avgSeries);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Chart: " + title,
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
        Rectangle rect = new Rectangle(1, 1);

        renderer.setDefaultStroke(new BasicStroke(5.0f));
        renderer.setAutoPopulateSeriesStroke(false);
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShape(0, rect);

        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);
        plot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));
        setContentPane(chartPanel);
    }

    private double getAvg(List<Ant> colony) {
        double sum = 0;
        for (Ant ant : colony) {
            sum += ant.getDistance();
        }
        return sum / colony.size();
    }

}

