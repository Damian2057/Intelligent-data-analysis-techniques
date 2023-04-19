package p.lodz.pl.config;

import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Path;

import static p.lodz.pl.enums.Const.CONFIG;

public class Config {

    private static Properties properties;

    public static Properties getProperties() {
        try {
            if (properties == null) {
                Path filePath = Path.of(CONFIG.getName());
                Gson gson = new Gson();
                properties =  gson.fromJson(Files.readString(filePath), Properties.class);
            }
            return properties;
        } catch (Exception e) {
            throw new RuntimeException("Error while reading startup parameters", e);
        }
    }
}
