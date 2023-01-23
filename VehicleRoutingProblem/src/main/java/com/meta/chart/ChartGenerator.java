package com.meta.chart;

import com.meta.model.Ant;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.List;
public class ChartGenerator extends ApplicationFrame {

    public ChartGenerator(List<DataSet> dataSets, List<MaxDataSet> maxDataSets, String title) {

        super("Chart: " + title);

        final XYSeries avgSeries = new XYSeries("avg(x)");
        for (DataSet dataSet : dataSets) {
            avgSeries.add(dataSet.getRound(), getAvg(dataSet.getList()));
        }

        final XYSeries maxSeries = new XYSeries("min(x)");
        for (MaxDataSet dataSet : maxDataSets) {
            maxSeries.add(dataSet.getRound(), dataSet.getAnt().getDistance());
        }

        final XYSeriesCollection data = new XYSeriesCollection(avgSeries);
        data.addSeries(maxSeries);

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

        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));
        setContentPane(chartPanel);
    }

    public ChartGenerator(List<DataSet> dataSets) {

        super("Chart Colony ");

        final XYSeries originSeries = new XYSeries("f(x)");
        for (DataSet dataSet : dataSets) {
            for (Ant ant : dataSet.getList()) {
                originSeries.add(dataSet.getRound(), ant.getDistance());
            }
        }

        final XYSeriesCollection data = new XYSeriesCollection(originSeries);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Chart Colony",
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

        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShape(0, rect);

        plot.setRenderer(renderer);

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

    private Ant getMin(List<Ant> colony) {
        Ant maxAnt = colony.get(0);
        for (Ant ant : colony) {
            if(ant.getDistance() < maxAnt.getDistance()) {
                maxAnt = ant;
            }
        }
        return maxAnt;
    }

}