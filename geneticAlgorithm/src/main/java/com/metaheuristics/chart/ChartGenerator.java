package com.metaheuristics.chart;

import com.metaheuristics.simulation.model.Specimen;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ChartGenerator extends ApplicationFrame {

    public ChartGenerator(List<DataSet> dataSets, String title) {

        super("Chart: "+ title);

        final XYSeries originSeries = new XYSeries("f(x)");
        final XYSeries maxSeries = new XYSeries("fmax(x)");
        final XYSeries minSeries = new XYSeries("fmin(x)");
        for (DataSet dataSet : dataSets) {
            originSeries.add(dataSet.getRound(), getAvg(dataSet.getList()));
            Collections.sort(dataSet.getList());
            maxSeries.add(dataSet.getRound(), dataSet.getList().get(0).getAdaptation());
            minSeries.add(dataSet.getRound(), dataSet.getList().get(dataSet.getList().size()-1).getAdaptation());
        }

        final XYSeriesCollection data = new XYSeriesCollection(originSeries);
        data.addSeries(maxSeries);
        data.addSeries(minSeries);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Chart: "+ title,
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

        renderer.setSeriesLinesVisible(1, false);

        renderer.setSeriesLinesVisible(2, false);
        plot.setRenderer(renderer);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));
        setContentPane(chartPanel);
    }

    private double getAvg(List<Specimen> specimen) {
        double sum = 0;
        for (Specimen spec :
                specimen) {
            sum += spec.getAdaptation();
        }
        return sum / specimen.size();
    }

    public ChartGenerator(List<DataSet> dataSets, String title,  int x) {
        super( "Chart: " + title);
        JFreeChart barChart = ChartFactory.createBarChart(
                "Chart: " + title,
                "Epoch",
                "",
                createDataset(dataSets),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }

    private CategoryDataset createDataset(List<DataSet> dataSets) {

        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset( );

        for (DataSet data: dataSets) {
            dataset.addValue(getValue(data.getList(), "min"),"min", String.valueOf(data.getRound()));
            dataset.addValue(getValue(data.getList(), "max"), "max", String.valueOf(data.getRound()));
            dataset.addValue(getValue(data.getList(), "avg"), "avg", String.valueOf(data.getRound()));
        }

        return dataset;
    }

    private int getValue(List<Specimen> list, String key) {
        int min = 0;
        int max = 0;
        int avgI = 0;
        double avg = getAvg(list);
        for (Specimen spec : list) {
            if(spec.getAdaptation() > avg) {
                max++;
            } else if(spec.getAdaptation() < avg) {
                min++;
            } else {
                avgI++;
            }
        }

        if(Objects.equals(key, "min")) {
            return min;
        } else if(Objects.equals(key, "max")) {
            return max;
        } else {
            return avgI;
        }
    }

}