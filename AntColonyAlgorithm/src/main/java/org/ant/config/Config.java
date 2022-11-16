package org.ant.config;

import com.google.gson.Gson;
import lombok.Getter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class Config {

    private final Properties properties;

    public Config() {
        try {
            Path filePath = Path.of("src/main/resources/config.json");
            Gson gson = new Gson();
            this.properties = gson.fromJson(Files.readString(filePath), Properties.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
