package com.metaheuristics.readers.csv;

import com.metaheuristics.model.BagPackItem;
import com.metaheuristics.readers.Const;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.metaheuristics.readers.Const.COMMA_DELIMITER;
import static com.metaheuristics.readers.Const.ITEMS;

public final class CsvReader {

    public static List<BagPackItem> getBagPackItems() {
        List<BagPackItem> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ITEMS.getProperty()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER.getProperty());
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
