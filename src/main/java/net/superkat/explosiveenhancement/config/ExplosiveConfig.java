package net.superkat.explosiveenhancement.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ExplosiveConfig {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("explosiveenhancement.json");
    public static File file = CONFIG_FILE.toFile();
    public static ExplosiveConfig INSTANCE = load();

    public boolean showBlastWave = true;
    public boolean showFireball = true;
    public boolean showMushroomCloud = true;
    public boolean showSparks = true;
    public float sparkSize = 5.3F;
    public float sparkOpacity = 0.70F;
    public boolean showDefaultExplosion = false;
    public boolean underwaterExplosions = true;
    public boolean showShockwave = true;
    public boolean showUnderwaterBlastWave = true;
    public int bubbleAmount = 50;
    public boolean showUnderwaterSparks = false;
    public float underwaterSparkSize = 4.0F;
    public float underwaterSparkOpacity = 0.30F;
    public boolean showDefaultExplosionUnderwater = false;
    public boolean dynamicSize = true;
    public boolean dynamicUnderwater = true;
    public boolean attemptBetterSmallExplosions = true;
    public double smallExplosionYOffset = -0.5;
    public boolean modEnabled = true;
    public boolean alwaysShow = false;
    public boolean debugLogs = false;

    public static ExplosiveConfig load() {
        ExplosiveConfig config = null;
        if(file.exists()) {
            try (BufferedReader fileReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)
            )) {
                config = GSON.fromJson(fileReader, ExplosiveConfig.class);
            } catch (IOException e) {
                throw new RuntimeException("Could not load Explosive Config: ", e);
            }
        }

        if(config == null) {
            config = new ExplosiveConfig();
        }

        config.save();
        return config;
    }

    public void save() {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
