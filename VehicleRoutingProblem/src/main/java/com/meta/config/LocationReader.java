package com.meta.config;

import com.meta.model.Location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class LocationReader {

    private static final String PATH = "src/main/resources/";

    public static List<Location> getLocations(String name) {
        List<Location> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PATH + name))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" ");
                items.add(Location
                        .builder()
                        .id(Integer.parseInt(values[1]))
                        .x(Double.parseDouble(values[2]))
                        .y(Double.parseDouble(values[3]))
                        .demand(Double.parseDouble(values[4]))
                        .readyTime(Double.parseDouble(values[5]))
                        .dueDate(Double.parseDouble(values[6]))
                        .serviceTime(Double.parseDouble(values[7]))
                        .build());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return items;
    }
}
