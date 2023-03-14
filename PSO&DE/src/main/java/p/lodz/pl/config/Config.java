package p.lodz.pl.config;

import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Path;

import static p.lodz.pl.enums.Const.DE_CONFIG;
import static p.lodz.pl.enums.Const.PSO_CONFIG;

public final class Config {
    private static DEProperties DEproperties;
    private static PSOProperties PSOproperties;

    public static DEProperties getDEProperties() {
        try {
            if (DEproperties == null) {
                Path filePath = Path.of(DE_CONFIG.getName());
                Gson gson = new Gson();
                DEproperties =  gson.fromJson(Files.readString(filePath), DEProperties.class);
            }
            return DEproperties;
        } catch (Exception e) {
            throw new RuntimeException("Error while reading startup parameters", e);
        }
    }

    public static PSOProperties getPSOProperties() {
        try {
            if (PSOproperties == null) {
                Path filePath = Path.of(PSO_CONFIG.getName());
                Gson gson = new Gson();
                PSOproperties =  gson.fromJson(Files.readString(filePath), PSOProperties.class);
            }
            return PSOproperties;
        } catch (Exception e) {
            throw new RuntimeException("Error while reading startup parameters", e);
        }
    }
}
