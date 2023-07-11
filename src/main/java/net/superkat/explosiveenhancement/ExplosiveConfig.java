package net.superkat.explosiveenhancement;

import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.OptionGroup;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.config.ConfigEntry;
import dev.isxander.yacl.config.ConfigInstance;
import dev.isxander.yacl.config.GsonConfigInstance;
import dev.isxander.yacl.gui.controllers.BooleanController;
import dev.isxander.yacl.gui.controllers.slider.FloatSliderController;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.nio.file.Path;

public class ExplosiveConfig {

    public static final ConfigInstance<ExplosiveConfig> INSTANCE = new GsonConfigInstance<>(ExplosiveConfig.class, Path.of("./config/explosiveenhancement.json"));

    @ConfigEntry public boolean showBlastWave = true;
    @ConfigEntry public boolean showFireball = true;
    @ConfigEntry public boolean showMushroomCloud = true;
    @ConfigEntry public boolean showSparks = true;
    @ConfigEntry public float sparkSize = 5.3F;
    @ConfigEntry public float sparkOpacity = 0.70F;
    @ConfigEntry public boolean showDefaultExplosion = false;
    @ConfigEntry public boolean underwaterExplosions = true;
    @ConfigEntry public boolean showShockwave = true;
    @ConfigEntry public boolean showUnderwaterBlastWave = true;
    @ConfigEntry public int bubbleAmount = 50;
    @ConfigEntry public boolean showUnderwaterSparks = false;
    @ConfigEntry public float underwaterSparkSize = 4.0F;
    @ConfigEntry public float underwaterSparkOpacity = 0.30F;
    @ConfigEntry public boolean showDefaultExplosionUnderwater = false;
    @ConfigEntry public boolean dynamicSize = true;
    @ConfigEntry public boolean dynamicUnderwater = true;
    @ConfigEntry public boolean attemptBetterSmallExplosions = true;
    @ConfigEntry public boolean modEnabled = true;
    @ConfigEntry public boolean debugLogs = false;

    public static Screen makeScreen(Screen parent) {
        return YetAnotherConfigLib.create(INSTANCE, (defaults, config, builder) -> {
            //Default Explosion category
            var defaultCategoryBuilder = ConfigCategory.createBuilder()
                    .name(Text.translatable("explosiveenhancement.category.default"));

            //Explosion particles group
            var explosionGroup = OptionGroup.createBuilder()
                    .name(Text.translatable("explosiveenhancement.explosion.group"))
                    .tooltip(Text.translatable("explosiveenhancement.explosion.group.tooltip"));
            var showBlastWave = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.blastwave.enabled"))
                    .tooltip(Text.translatable("explosiveenhancement.blastwave.enabled.tooltip"))
                    .binding(
                            defaults.showBlastWave,
                            () -> config.showBlastWave,
                            val -> config.showBlastWave = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var showFireball = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.fireball.enabled"))
                    .tooltip(Text.translatable("explosiveenhancement.fireball.enabled.tooltip"))
                    .binding(
                            defaults.showFireball,
                            () -> config.showFireball,
                            val -> config.showFireball = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var showMushroomCloud = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.mushroomcloud.enabled"))
                    .tooltip(Text.translatable("explosiveenhancement.mushroomcloud.enabled.tooltip"))
                    .binding(
                            defaults.showMushroomCloud,
                            () -> config.showMushroomCloud,
                            val -> config.showMushroomCloud = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var showSparks = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.sparks.enabled"))
                    .tooltip(Text.translatable("explosiveenhancement.sparks.enabled.tooltip"))
                    .binding(
                            defaults.showSparks,
                            () -> config.showSparks,
                            val -> config.showSparks = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var sparkSize = Option.createBuilder(Float.class)
                    .name(Text.translatable("explosiveenhancement.sparks.size"))
                    .tooltip(Text.translatable("explosiveenhancement.sparks.size.tooltip"))
                    .binding(
                            defaults.sparkSize,
                            () -> config.sparkSize,
                            val -> config.sparkSize = val
                    )
                    .controller(floatOption -> new <Number>FloatSliderController(floatOption, 0F, 10F, 0.1F))
                    .build();
            var sparkOpacity = Option.createBuilder(Float.class)
                    .name(Text.translatable("explosiveenhancement.sparks.opacity"))
                    .tooltip(Text.translatable("explosiveenhancement.sparks.opacity.tooltip"))
                    .binding(
                            defaults.sparkOpacity,
                            () -> config.sparkOpacity,
                            val -> config.sparkOpacity = val
                    )
                    .controller(floatOption -> new <Number>FloatSliderController(floatOption, 0.00F, 1.00F, 0.05F))
                    .build();
            var showDefaultExplosion = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.default.enabled"))
                    .tooltip(Text.translatable("explosiveenhancement.default.enabled.tooltip"))
                    .binding(
                            defaults.showDefaultExplosion,
                            () -> config.showDefaultExplosion,
                            val -> config.showDefaultExplosion = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            explosionGroup.option(showBlastWave);
            explosionGroup.option(showFireball);
            explosionGroup.option(showMushroomCloud);
            explosionGroup.option(showSparks);
            explosionGroup.option(sparkSize);
            explosionGroup.option(sparkOpacity);
            explosionGroup.option(showDefaultExplosion);
            defaultCategoryBuilder.group(explosionGroup.build());

            var underwaterGroup = OptionGroup.createBuilder()
                    .name(Text.translatable("explosiveenhancement.underwater.group"))
                    .tooltip(Text.translatable("explosiveenhancement.underwater.group.tooltip"));

            var underwaterExplosions = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.underwater.enabled"))
                    .tooltip(Text.translatable("explosiveenhancement.underwater.enabled.tooltip"))
                    .binding(
                            defaults.underwaterExplosions,
                            () -> config.underwaterExplosions,
                            val -> config.underwaterExplosions = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var showShockwave = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.underwater.shockwave"))
                    .tooltip(Text.translatable("explosiveenhancement.underwater.shockwave.tooltip"))
                    .binding(
                            defaults.showShockwave,
                            () -> config.showShockwave,
                            val -> config.showShockwave = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var showUnderwaterBlast = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.underwater.blast"))
                    .tooltip(Text.translatable("explosiveenhancement.underwater.blast.tooltip"))
                    .binding(
                            defaults.showUnderwaterBlastWave,
                            () -> config.showUnderwaterBlastWave,
                            val -> config.showUnderwaterBlastWave = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var bubbleAmount = Option.createBuilder(Integer.class)
                    .name(Text.translatable("explosiveenhancement.underwater.bubbleamount"))
                    .tooltip(Text.translatable("explosiveenhancement.underwater.bubbleamount.tooltip"))
                    .tooltip(Text.translatable("explosiveenhancement.underwater.bubbleamount.warningtooltip"))
                    .binding(
                            defaults.bubbleAmount,
                            () -> config.bubbleAmount,
                            val -> config.bubbleAmount = val
                    )
                    .controller(integerOption -> new <Number>IntegerSliderController(integerOption, 0, 500, 5))
                    .build();
            var showUnderwaterSparks = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.underwater.sparks"))
                    .tooltip(Text.translatable("explosiveenhancement.underwater.sparks.tooltip"))
                    .binding(
                            defaults.showUnderwaterSparks,
                            () -> config.showUnderwaterSparks,
                            val -> config.showUnderwaterSparks = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var underwaterSparkSize = Option.createBuilder(Float.class)
                    .name(Text.translatable("explosiveenhancement.underwater.sparks.size"))
                    .tooltip(Text.translatable("explosiveenhancement.underwater.sparks.size.tooltip"))
                    .binding(
                            defaults.underwaterSparkSize,
                            () -> config.underwaterSparkSize,
                            val -> config.underwaterSparkSize = val
                    )
                    .controller(floatOption -> new <Number>FloatSliderController(floatOption, 0F, 10F, 0.1F))
                    .build();
            var underwaterSparkOpacity = Option.createBuilder(Float.class)
                    .name(Text.translatable("explosiveenhancement.underwater.sparks.opacity"))
                    .tooltip(Text.translatable("explosiveenhancement.underwater.sparks.opacity.tooltip"))
                    .binding(
                            defaults.underwaterSparkOpacity,
                            () -> config.underwaterSparkOpacity,
                            val -> config.underwaterSparkOpacity = val
                    )
                    .controller(floatOption -> new <Number>FloatSliderController(floatOption, 0.00F, 1.00F, 0.05F))
                    .build();
            var showDefaultExplosionUnderwater = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.underwater.default"))
                    .tooltip(Text.translatable("explosiveenhancement.underwater.default.tooltip"))
                    .binding(
                            defaults.showDefaultExplosionUnderwater,
                            () -> config.showDefaultExplosionUnderwater,
                            val -> config.showDefaultExplosionUnderwater = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();

            underwaterGroup.option(underwaterExplosions);
            underwaterGroup.option(showShockwave);
            underwaterGroup.option(showUnderwaterBlast);
            underwaterGroup.option(bubbleAmount);
            underwaterGroup.option(showUnderwaterSparks);
            underwaterGroup.option(underwaterSparkSize);
            underwaterGroup.option(underwaterSparkOpacity);
            underwaterGroup.option(showDefaultExplosionUnderwater);
            defaultCategoryBuilder.group(underwaterGroup.build());

            var dynamicCategoryBuilder = ConfigCategory.createBuilder()
                    .name(Text.translatable("explosiveenhancement.category.dynamic"));

            var dynamicExplosionGroup = OptionGroup.createBuilder()
                    .name(Text.translatable("explosiveenhancement.dynamic.group"))
                    .tooltip(Text.translatable("explosiveenhancement.dynamic.group.tootlip"));

            var dynamicExplosions = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.dynamicexplosions.enabled"))
                    .tooltip(Text.translatable("explosiveenhancement.dynamicexplosions.enabled.tooltip"))
                    .binding(
                            defaults.dynamicSize,
                            () -> config.dynamicSize,
                            val -> config.dynamicSize = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var dynamicUnderwater = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.dynamicunderwater.enabled"))
                    .tooltip(Text.translatable("explosiveenhancement.dynamicunderwater.enabled.tooltip"))
                    .binding(
                            defaults.dynamicUnderwater,
                            () -> config.dynamicUnderwater,
                            val -> config.dynamicUnderwater = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var attemptBetterSmallExplosions = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.bettersmallexplosions.enabled"))
                    .tooltip(Text.translatable("explosiveenhancement.bettersmallexplosions.enabled.tooltip"))
                    .binding(
                            defaults.attemptBetterSmallExplosions,
                            () -> config.attemptBetterSmallExplosions,
                            val -> config.attemptBetterSmallExplosions = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();

            dynamicExplosionGroup.option(dynamicExplosions);
            dynamicExplosionGroup.option(dynamicUnderwater);
            dynamicExplosionGroup.option(attemptBetterSmallExplosions);
            dynamicCategoryBuilder.group(dynamicExplosionGroup.build());



            var extrasCategoryBuilder = ConfigCategory.createBuilder()
                    .name(Text.translatable("explosiveenhancement.category.extras"));

            var extrasGroup = OptionGroup.createBuilder()
                    .name(Text.translatable("explosiveenhancement.extras.group"))
                    .tooltip(Text.translatable("explosiveenhancement.extras.group.tooltip"));

            var modEnabled = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.extras.enabled"))
                    .tooltip(Text.translatable("explosiveenhancement.extras.enabled.tooltip"))
                    .binding(
                            defaults.modEnabled,
                            () -> config.modEnabled,
                            val -> config.modEnabled = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var debugLogs = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.extras.logs"))
                    .tooltip(Text.translatable("explosiveenhancement.extras.logs.tooltip"))
                    .tooltip(Text.translatable("explosiveenhancement.extras.logs.warningtooltip"))
                    .binding(
                            defaults.debugLogs,
                            () -> config.debugLogs,
                            val -> config.debugLogs = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            extrasGroup.option(modEnabled);
            extrasGroup.option(debugLogs);
            extrasCategoryBuilder.group(extrasGroup.build());

            return builder
                    .title(Text.translatable("explosiveenhancement.title"))
                    .category(defaultCategoryBuilder.build())
                    .category(dynamicCategoryBuilder.build())
                    .category(extrasCategoryBuilder.build());
        }).generateScreen(parent);
    }

}
