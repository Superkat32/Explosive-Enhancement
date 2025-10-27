package net.superkat.explosiveenhancement.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ExplosiveConfig {

    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    public static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("explosiveenhancement.json");
    public static final File file = PATH.toFile();
    public static final ExplosiveConfig INSTANCE = load();

    public boolean showBlastWave = true;
    public boolean showFireball = true;
    public boolean showSparks = true;
    public float sparkSize = 5.3F;
    public float sparkOpacity = 0.70F;
    public boolean showMushroomCloud = true;
    public boolean showDefaultExplosion = false;
    public boolean showDefaultSmoke = false;

    public boolean underwaterExplosions = true;
    public boolean showUnderwaterBlastWave = true;
    public boolean showShockwave = true;
    public boolean showUnderwaterSparks = false;
    public float underwaterSparkSize = 4.0F;
    public float underwaterSparkOpacity = 0.30F;
    public int bubbleAmount = 50;
    public boolean showDefaultExplosionUnderwater = false;
    public boolean showDefaultSmokeUnderwater = false;

    public boolean dynamicSize = true;
    public boolean dynamicUnderwater = true;
    public boolean extraPower = false;
    public float bigExtraPower = 0f;
    public float smallExtraPower = 0f;
    public boolean attemptBetterSmallExplosions = true;
    public double smallExplosionYOffset = -0.5;
    public boolean attemptPowerKnockbackCalc = false;
    public boolean bypassPowerForSingleplayer = true;

    public boolean modEnabled = true;
    public boolean emissiveExplosion = true;
    public boolean emissiveWaterExplosion = true;
    public boolean alwaysShow = false;
    public boolean debugLogs = false;

    // The following two methods and the above static variables were borrowed and slightly edited
    // with permission from Enjarai's absolutely amazing Do A Barrel Roll mod.
    // Please check it out!
    // https://github.com/enjarai/do-a-barrel-roll
    public static ExplosiveConfig load() {
        ExplosiveConfig config = null;
        if(file.exists()) {
            try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                config = GSON.fromJson(fileReader, ExplosiveConfig.class);
            } catch (IOException e) {
                // Throwing raw exception types should be avoided, so a log entry is used instead
                ExplosiveEnhancement.LOGGER.error("[Explosive Enhancement]: Could not load Explosive Config. Using default configuration.", e);
            }
        }

        if(config == null) {
            config = new ExplosiveConfig();
        }

        config.save();
        return config;
    }

    public void save() {
        try (Writer writer = Files.newBufferedWriter(PATH, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            ExplosiveEnhancement.LOGGER.error("[Explosive Enhancement]: Could not save Explosive Config.", e);
        }
    }

}
