package com.metaheuristics;


import com.metaheuristics.readers.csv.CsvReader;
import com.metaheuristics.readers.json.JsonReader;

import java.io.IOException;

public class MainApp {

    public static void main(String[] args) {
        System.out.println(CsvReader.getBagPackItems());
    }
}
