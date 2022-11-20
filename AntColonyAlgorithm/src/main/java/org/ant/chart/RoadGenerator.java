package org.ant.chart;

import org.ant.model.Ant;
import org.ant.model.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class RoadGenerator extends JFrame {

    public static void generateRoad(Ant ant) {
        final JFrame f = new JFrame("Ant Road");
        f.setSize(600, 600);

        f.add(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D graphics = (Graphics2D) g.create();
                List<Double> x = new ArrayList<>();
                List<Double> y = new ArrayList<>();
                for (Location location : ant.getVisitedLocations()) {
                    x.add(50 + (double) location.getX() * 5);
                    y.add(50 +(double) location.getY() * 5);
                }
                Path2D polyline = new Path2D.Double();
                polyline.moveTo(x.get(0), y.get(0));
                for (int i = 1; i < x.size(); i++) {
                    polyline.lineTo(x.get(i), y.get(i));
                }
                graphics.draw(polyline);
                graphics.dispose();
            }
        });
        f.setVisible(true);
    }
}
