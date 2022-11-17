package org.ant.config;

import org.ant.model.Location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class LocationReader {

    private static final String path = "src/main/resources/";

    public static List<Location> getBagPackItems(String name) {
        List<Location> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path + name))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" ");
                items.add(Location
                        .builder()
                        .id(Integer.parseInt(values[1]))
                        .x(Integer.parseInt(values[2]))
                        .y(Integer.parseInt(values[3]))
                        .build());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

}
