package com.metaheuristics.simulation.algorithm;

import com.metaheuristics.readers.csv.BagPackItem;
import com.metaheuristics.readers.csv.CsvReader;
import com.metaheuristics.readers.json.JsonReader;
import com.metaheuristics.simulation.model.Specimen;

import java.util.List;

public class GeneticImpl implements Genetic {

    private final List<BagPackItem> bagPackItems = CsvReader.getBagPackItems();
    private final int backpackCapacity = JsonReader.getBackpackCapacity();

    @Override
    public double adaptationFunction(Specimen specimen) {
        int weight = 0;
        int price = 0;
        int index = 0;
        for (int gen : specimen.getGens()) {
            if(gen == 1) {
                weight += bagPackItems.get(index).getWeight();
                price += bagPackItems.get(index).getPrice();
            }
            index++;
        }
        if(weight > backpackCapacity) {
            return 0.0;
        }
        return price;
    }

    @Override
    public void adaptationFunction(List<Specimen> generation) {
        for (Specimen specimen : generation) {
            double adaptation = adaptationFunction(specimen);
            if (adaptation == 0.0) {
                specimen.setCorrect(false);
            } else {
                specimen.setAdaptation(adaptation);
            }
        }
    }

}
