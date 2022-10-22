package com.metaheuristics.readers.json;

import com.metaheuristics.exceptions.InvalidTypeException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static com.metaheuristics.readers.Const.*;

public final class JsonReader {

    public static Mutation getMutationProperties() {
        try {
            JSONObject jsonObject = getConfig().getJSONObject(MUTATION.getProperty());
            return new Mutation
                    .MutationBuilder()
                    .isEnable(Optional.of(jsonObject.getBoolean(ENABLE.getProperty())))
                    .value(Optional.of(jsonObject.getDouble(VALUE.getProperty())))
                    .build();
        } catch (IOException e) {
            throw new InvalidTypeException("Incorrect data in the mutation in the configuration");
        }
    }

    public static SelectionType getSelectionType() {
        try {
            return getConfig().getEnum(SelectionType.class, SELECTION.getProperty());
        } catch (IOException e) {
            throw new InvalidTypeException("Invalid selection type in the configuration");
        }
    }

    public static CrossoverType getCrossOverType() {
        try {
            return getConfig().getEnum(CrossoverType.class,CROSSOVER.getProperty());
        } catch (IOException e) {
            throw new InvalidTypeException("Invalid crossover strategy in the configuration");
        }
    }

    public static int getBackpackCapacity() {
        try {
            return getConfig().getInt(BACKPACK.name());
        } catch (IOException e) {
            throw new InvalidTypeException("Incorrect data in the size of backpack configuration");
        }
    }

    public static int getPopulationSize() {
        try {
            return getConfig().getInt(POPULATION.getProperty());
        } catch (IOException e) {
            throw new InvalidTypeException("Incorrect data in the population size configuration");
        }
    }

    public static int getNumberOfIterations() {
        try {
            return getConfig().getInt(ITERATIONS.getProperty());
        } catch (IOException e) {
            throw new InvalidTypeException("Incorrect data in the number of iterations in configuration");
        }
    }

    private static JSONObject getConfig() throws IOException {
        Path filePath = Path.of(CONFIG.getProperty());
        String jsonString = Files.readString(filePath);
        return new JSONObject(jsonString);
    }
}
