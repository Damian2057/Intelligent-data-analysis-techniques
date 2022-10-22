package com.metaheuristics.readers.csv;

import com.metaheuristics.model.BagPackItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    private static final String COMMA_DELIMITER = ",";

    public static List<BagPackItem> getBagPackItems() {
        List<BagPackItem> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/objects.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                items.add(BagPackItem
                        .builder()
                        .id(Integer.parseInt(values[0]))
                        .name(values[1])
                        .weight(Integer.parseInt(values[2]))
                        .price(Integer.parseInt(values[3])).build());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return items;
    }
}
