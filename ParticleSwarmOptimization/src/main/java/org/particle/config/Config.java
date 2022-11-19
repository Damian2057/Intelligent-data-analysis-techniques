package org.particle.config;

import com.google.gson.Gson;
import org.particle.model.Properties;

import java.nio.file.Files;
import java.nio.file.Path;

public class Config {

    public static Properties getProperties() {
        try {
            Path filePath = Path.of("src/main/resources/config.json");
            Gson gson = new Gson();
            return gson.fromJson(Files.readString(filePath), Properties.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}