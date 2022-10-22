package com.metaheuristics.readers.json;

import org.json.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class JsonReader {

    public static Mutation getMutationProperties() throws IOException {
        JSONObject jsonObject = getConfig().getJSONObject("mutation");
        return new Mutation
                .MutationBuilder()
                .isEnable(Optional.of(jsonObject.getBoolean("isEnable")))
                .value(Optional.of(jsonObject.getDouble("value")))
                .build();
    }

    public static SelectionType getSelectionType() throws IOException {
        return getConfig().getEnum(SelectionType.class, "selectionType");
    }

    public static CrossoverType getCrossOverType() throws IOException {
        return getConfig().getEnum(CrossoverType.class,"crossoverType");
    }

    private static JSONObject getConfig() throws IOException {
        Path filePath = Path.of("src/main/resources/config.json");
        String jsonString = Files.readString(filePath);
        return new JSONObject(jsonString);
    }
}
