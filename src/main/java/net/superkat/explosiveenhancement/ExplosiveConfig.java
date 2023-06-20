package net.superkat.explosiveenhancement;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.config.ConfigEntry;
import dev.isxander.yacl3.config.GsonConfigInstance;
import dev.isxander.yacl3.gui.controllers.BooleanController;
import dev.isxander.yacl3.gui.controllers.slider.FloatSliderController;
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.nio.file.Path;

public class ExplosiveConfig {
    public static final GsonConfigInstance<ExplosiveConfig> INSTANCE = GsonConfigInstance.createBuilder(ExplosiveConfig.class)
        .setPath(Path.of("./config/explosiveenhancement.json")).build();

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
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.explosion.group.tooltip"))
                            .build());
            var showBlastWave = Option.<Boolean>createBuilder()
                    .name(Text.translatable("explosiveenhancement.blastwave.enabled"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.blastwave.enabled.tooltip"))
                            .build())
                    .binding(
                            defaults.showBlastWave,
                            () -> config.showBlastWave,
                            val -> config.showBlastWave = val
                    )
                    .customController(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var showFireball = Option.<Boolean>createBuilder()
                    .name(Text.translatable("explosiveenhancement.fireball.enabled"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.fireball.enabled.tooltip"))
                            .build())
                    .binding(
                            defaults.showFireball,
                            () -> config.showFireball,
                            val -> config.showFireball = val
                    )
                    .customController(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var showMushroomCloud = Option.<Boolean>createBuilder()
                    .name(Text.translatable("explosiveenhancement.mushroomcloud.enabled"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.mushroomcloud.enabled.tooltip"))
                            .build())
                    .binding(
                            defaults.showMushroomCloud,
                            () -> config.showMushroomCloud,
                            val -> config.showMushroomCloud = val
                    )
                    .customController(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var showSparks = Option.<Boolean>createBuilder()
                    .name(Text.translatable("explosiveenhancement.sparks.enabled"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.sparks.enabled.tooltip"))
                            .build())
                    .binding(
                            defaults.showSparks,
                            () -> config.showSparks,
                            val -> config.showSparks = val
                    )
                    .customController(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var sparkSize = Option.<Float>createBuilder()
                    .name(Text.translatable("explosiveenhancement.sparks.size"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.sparks.size.tooltip"))
                            .build())
                    .binding(
                            defaults.sparkSize,
                            () -> config.sparkSize,
                            val -> config.sparkSize = val
                    )
                    .customController(floatOption -> new <Number>FloatSliderController(floatOption, 0F, 10F, 0.1F))
                    .build();
            var sparkOpacity = Option.<Float>createBuilder()
                    .name(Text.translatable("explosiveenhancement.sparks.opacity"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.sparks.opacity.tooltip"))
                            .build())
                    .binding(
                            defaults.sparkOpacity,
                            () -> config.sparkOpacity,
                            val -> config.sparkOpacity = val
                    )
                    .customController(floatOption -> new <Number>FloatSliderController(floatOption, 0.00F, 1.00F, 0.05F))
                    .build();
            var showDefaultExplosion = Option.<Boolean>createBuilder()
                    .name(Text.translatable("explosiveenhancement.default.enabled"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.default.enabled.tooltip"))
                            .build())
                    .binding(
                            defaults.showDefaultExplosion,
                            () -> config.showDefaultExplosion,
                            val -> config.showDefaultExplosion = val
                    )
                    .customController(booleanOption -> new BooleanController(booleanOption, true))
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.underwater.group.tooltip"))
                            .build());

            var underwaterExplosions = Option.<Boolean>createBuilder()
                    .name(Text.translatable("explosiveenhancement.underwater.enabled"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.underwater.enabled.tooltip"))
                            .build())
                    .binding(
                            defaults.underwaterExplosions,
                            () -> config.underwaterExplosions,
                            val -> config.underwaterExplosions = val
                    )
                    .customController(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var showShockwave = Option.<Boolean>createBuilder()
                    .name(Text.translatable("explosiveenhancement.underwater.shockwave"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.underwater.shockwave.tooltip"))
                            .build())
                    .binding(
                            defaults.showShockwave,
                            () -> config.showShockwave,
                            val -> config.showShockwave = val
                    )
                    .customController(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var showUnderwaterBlast = Option.<Boolean>createBuilder()
                    .name(Text.translatable("explosiveenhancement.underwater.blast"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.underwater.blast.tooltip"))
                            .build())
                    .binding(
                            defaults.showUnderwaterBlastWave,
                            () -> config.showUnderwaterBlastWave,
                            val -> config.showUnderwaterBlastWave = val
                    )
                    .customController(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var bubbleAmount = Option.<Integer>createBuilder()
                    .name(Text.translatable("explosiveenhancement.underwater.bubbleamount"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.underwater.bubbleamount.tooltip"))
                            .text(Text.translatable("explosiveenhancement.underwater.bubbleamount.warningtooltip"))
                            .build())
                    .binding(
                            defaults.bubbleAmount,
                            () -> config.bubbleAmount,
                            val -> config.bubbleAmount = val
                    )
                    .customController(integerOption -> new <Number>IntegerSliderController(integerOption, 0, 500, 5))
                    .build();
            var showUnderwaterSparks = Option.<Boolean>createBuilder()
                    .name(Text.translatable("explosiveenhancement.underwater.sparks"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.underwater.sparks.tooltip"))
                            .build())
                    .binding(
                            defaults.showUnderwaterSparks,
                            () -> config.showUnderwaterSparks,
                            val -> config.showUnderwaterSparks = val
                    )
                    .customController(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var underwaterSparkSize = Option.<Float>createBuilder()
                    .name(Text.translatable("explosiveenhancement.underwater.sparks.size"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.underwater.sparks.size.tooltip"))
                            .build())
                    .binding(
                            defaults.underwaterSparkSize,
                            () -> config.underwaterSparkSize,
                            val -> config.underwaterSparkSize = val
                    )
                    .customController(floatOption -> new <Number>FloatSliderController(floatOption, 0F, 10F, 0.1F))
                    .build();
            var underwaterSparkOpacity = Option.<Float>createBuilder()
                    .name(Text.translatable("explosiveenhancement.underwater.sparks.opacity"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.underwater.sparks.opacity.tooltip"))
                            .build())
                    .binding(
                            defaults.underwaterSparkOpacity,
                            () -> config.underwaterSparkOpacity,
                            val -> config.underwaterSparkOpacity = val
                    )
                    .customController(floatOption -> new <Number>FloatSliderController(floatOption, 0.00F, 1.00F, 0.05F))
                    .build();
            var showDefaultExplosionUnderwater = Option.<Boolean>createBuilder()
                    .name(Text.translatable("explosiveenhancement.underwater.default"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.underwater.default.tooltip"))
                            .build())
                    .binding(
                            defaults.showDefaultExplosionUnderwater,
                            () -> config.showDefaultExplosionUnderwater,
                            val -> config.showDefaultExplosionUnderwater = val
                    )
                    .customController(booleanOption -> new BooleanController(booleanOption, true))
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



            var extrasCategoryBuilder = ConfigCategory.createBuilder()
                    .name(Text.translatable("explosiveenhancement.category.extras"));

            var extrasGroup = OptionGroup.createBuilder()
                    .name(Text.translatable("explosiveenhancement.extras.group"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.extras.group.tooltip"))
                            .build());


            var modEnabled = Option.<Boolean>createBuilder()
                    .name(Text.translatable("explosiveenhancement.extras.enabled"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.extras.enabled.tooltip"))
                            .build())
                    .binding(
                            defaults.modEnabled,
                            () -> config.modEnabled,
                            val -> config.modEnabled = val
                    )
                    .customController(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var debugLogs = Option.<Boolean>createBuilder()
                    .name(Text.translatable("explosiveenhancement.extras.logs"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("explosiveenhancement.extras.logs.tooltip"))
                            .text(Text.translatable("explosiveenhancement.extras.logs.warningtooltip"))
                            .build())
                    .binding(
                            defaults.debugLogs,
                            () -> config.debugLogs,
                            val -> config.debugLogs = val
                    )
                    .customController(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            extrasGroup.option(modEnabled);
            extrasGroup.option(debugLogs);
            extrasCategoryBuilder.group(extrasGroup.build());

            return builder
                    .title(Text.translatable("explosiveenhancement.title"))
                    .category(defaultCategoryBuilder.build())
                    .category(extrasCategoryBuilder.build());
        }).generateScreen(parent);
    }

}
