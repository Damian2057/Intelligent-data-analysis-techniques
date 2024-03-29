package com.metaheuristics.readers.json;

import com.metaheuristics.exceptions.InvalidTypeException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.metaheuristics.readers.Const.*;

public final class JsonReader {

    public static Mutation getMutationProperties() {
        try {
            JSONObject jsonObject = getConfig().getJSONObject(MUTATION.getProperty());
            return new Mutation
                    .MutationBuilder()
                    .isEnable(jsonObject.getBoolean(ENABLE.getProperty()))
                    .probability(jsonObject.getDouble(PROBABILITY.getProperty()))
                    .build();
        } catch (IOException | JSONException e) {
            throw new InvalidTypeException("Incorrect data in the mutation configuration");
        }
    }

    public static SelectionType getSelectionType() {
        try {
            return getConfig().getEnum(SelectionType.class, SELECTION.getProperty());
        } catch (IOException | JSONException e) {
            throw new InvalidTypeException("Invalid selection type configuration");
        }
    }

    public static CrossOver getCrossOverProperties() {
        try {
            JSONObject jsonObject = getConfig().getJSONObject(CROSSOVER.getProperty());
            return new CrossOver
                    .CrossOverBuilder()
                    .crossoverType(jsonObject.getEnum(CrossoverType.class, CROSSOVERTYPE.getProperty()))
                    .probability(jsonObject.getDouble(PROBABILITY.getProperty()))
                    .build();
        } catch (IOException | JSONException e) {
            throw new InvalidTypeException("Incorrect data in the CrossOver configuration");
        }
    }

    public static int getBackpackCapacity() {
        try {
            return getConfig().getInt(BACKPACK.getProperty());
        } catch (IOException | JSONException e) {
            throw new InvalidTypeException("Incorrect data in the size of backpack configuration");
        }
    }

    public static int getPopulationSize() {
        try {
            return getConfig().getInt(POPULATION.getProperty());
        } catch (IOException | JSONException e) {
            throw new InvalidTypeException("Incorrect data in the population size configuration");
        }
    }

    public static int getNumberOfIterations() {
        try {
            return getConfig().getInt(ITERATIONS.getProperty());
        } catch (IOException | JSONException e) {
            throw new InvalidTypeException("Incorrect data in the number of iterations in configuration");
        }
    }

    @Deprecated
    public static int getNumberOfParents() {
        try {
            return getConfig().getInt(PARENTS.getProperty());
        } catch (IOException | JSONException e) {
            throw new InvalidTypeException("Incorrect data in the number of parents configuration");
        }
    }

    public static int getDisplay() {
        try {
            return getConfig().getInt(DISPLAY.getProperty());
        } catch (IOException | JSONException e) {
            throw new InvalidTypeException("Incorrect data in the population size configuration");
        }
    }

    public static int getProgramStartTimes() {
        try {
            return getConfig().getInt(STARTS.getProperty());
        } catch (IOException | JSONException e) {
            throw new InvalidTypeException("Incorrect data in the start times configuration");
        }
    }

    private static JSONObject getConfig() throws IOException {
        Path filePath = Path.of(CONFIG.getProperty());
        String jsonString = Files.readString(filePath);
        return new JSONObject(jsonString);
    }
}
