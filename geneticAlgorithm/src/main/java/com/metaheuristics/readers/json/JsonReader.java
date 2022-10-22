package com.metaheuristics.readers.json;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static com.metaheuristics.readers.Const.*;

public final class JsonReader {

    public static Mutation getMutationProperties() throws IOException {
        JSONObject jsonObject = getConfig().getJSONObject(MUTATION.getProperty());
        return new Mutation
                .MutationBuilder()
                .isEnable(Optional.of(jsonObject.getBoolean(ENABLE.getProperty())))
                .value(Optional.of(jsonObject.getDouble(VALUE.getProperty())))
                .build();
    }

    public static SelectionType getSelectionType() throws IOException {
        return getConfig().getEnum(SelectionType.class, SELECTION.getProperty());
    }

    public static CrossoverType getCrossOverType() throws IOException {
        return getConfig().getEnum(CrossoverType.class,CROSSOVER.getProperty());
    }

    public static int getBackpackCapacity() throws IOException {
        return getConfig().getInt(BACKPACK.name());
    }

    public static int getPopulationSize() throws IOException {
        return getConfig().getInt(POPULATION.getProperty());
    }

    public static int getNumberOfIterations() throws IOException {
        return getConfig().getInt(ITERATIONS.getProperty());
    }

    private static JSONObject getConfig() throws IOException {
        Path filePath = Path.of(CONFIG.getProperty());
        String jsonString = Files.readString(filePath);
        return new JSONObject(jsonString);
    }
}
