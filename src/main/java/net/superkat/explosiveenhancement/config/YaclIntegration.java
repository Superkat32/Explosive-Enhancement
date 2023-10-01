package net.superkat.explosiveenhancement.config;

import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.OptionGroup;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.gui.controllers.BooleanController;
import dev.isxander.yacl.gui.controllers.slider.DoubleSliderController;
import dev.isxander.yacl.gui.controllers.slider.FloatSliderController;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.superkat.explosiveenhancement.ExplosiveEnhancementClient;

public class YaclIntegration {
    public static Screen makeScreen(Screen parent) {
        ExplosiveConfig config = ExplosiveEnhancementClient.config;
        ExplosiveConfig defaults = new ExplosiveConfig();
        var yacl = YetAnotherConfigLib.createBuilder()
                .title(Text.of("title"))
                .save(config::save);
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
        var sparkSize = Option.createBuilder(Float.class)
                .name(Text.translatable("explosiveenhancement.sparks.size"))
                .tooltip(Text.translatable("explosiveenhancement.sparks.size.tooltip"))
                .binding(
                        defaults.sparkSize,
                        () -> config.sparkSize,
                        val -> config.sparkSize = val
                )
                .available(true)
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
                .available(true)
                .controller(floatOption -> new <Number>FloatSliderController(floatOption, 0.00F, 1.00F, 0.05F))
                .build();
        var showSparks = Option.createBuilder(boolean.class)
                .name(Text.translatable("explosiveenhancement.sparks.enabled"))
                .tooltip(Text.translatable("explosiveenhancement.sparks.enabled.tooltip"))
                .binding(
                        defaults.showSparks,
                        () -> config.showSparks,
                        val -> config.showSparks = val
                )
                .listener((opt, val) -> sparkSize.setAvailable(val))
                .listener((opt, val) -> sparkOpacity.setAvailable(val))
                .controller(booleanOption -> new BooleanController(booleanOption, true))
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
        var underwaterSparkSize = Option.createBuilder(Float.class)
                .name(Text.translatable("explosiveenhancement.underwater.sparks.size"))
                .tooltip(Text.translatable("explosiveenhancement.underwater.sparks.size.tooltip"))
                .binding(
                        defaults.underwaterSparkSize,
                        () -> config.underwaterSparkSize,
                        val -> config.underwaterSparkSize = val
                )
                .available(false)
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
                .available(false)
                .controller(floatOption -> new <Number>FloatSliderController(floatOption, 0.00F, 1.00F, 0.05F))
                .build();
        var showUnderwaterSparks = Option.createBuilder(boolean.class)
                .name(Text.translatable("explosiveenhancement.underwater.sparks"))
                .tooltip(Text.translatable("explosiveenhancement.underwater.sparks.tooltip"))
                .binding(
                        defaults.showUnderwaterSparks,
                        () -> config.showUnderwaterSparks,
                        val -> config.showUnderwaterSparks = val
                )
                .listener((opt, val) -> underwaterSparkSize.setAvailable(val))
                .listener((opt, val) -> underwaterSparkOpacity.setAvailable(val))
                .controller(booleanOption -> new BooleanController(booleanOption, true))
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
                .tooltip(Text.translatable("explosiveenhancement.dynamic.group.tooltip"));

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
        var smallExplosionYOffset = Option.createBuilder(Double.class)
                .name(Text.translatable("explosiveenhancement.yoffset"))
                .tooltip(Text.translatable("explosiveenhancement.yoffset.tooltip"))
                .binding(
                        defaults.smallExplosionYOffset,
                        () -> config.smallExplosionYOffset,
                        val -> config.smallExplosionYOffset = val
                )
                .available(true)
                .controller(doubleOption -> new <Number>DoubleSliderController(doubleOption, -1.0, 0, 0.1))
                .build();
        var attemptBetterSmallExplosions = Option.createBuilder(boolean.class)
                .name(Text.translatable("explosiveenhancement.bettersmallexplosions.enabled"))
                .tooltip(Text.translatable("explosiveenhancement.bettersmallexplosions.enabled.tooltip"))
                .binding(
                        defaults.attemptBetterSmallExplosions,
                        () -> config.attemptBetterSmallExplosions,
                        val -> config.attemptBetterSmallExplosions = val
                )
                .listener((opt, val) -> smallExplosionYOffset.setAvailable(val))
                .controller(booleanOption -> new BooleanController(booleanOption, true))
                .build();

        dynamicExplosionGroup.option(dynamicExplosions);
        dynamicExplosionGroup.option(dynamicUnderwater);
        dynamicExplosionGroup.option(attemptBetterSmallExplosions);
        dynamicExplosionGroup.option(smallExplosionYOffset);
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
        var emissiveExplosion = Option.createBuilder(boolean.class)
                .name(Text.translatable("explosiveenhancement.extras.emissive"))
                .tooltip(Text.translatable("explosiveenhancement.extras.emissive.tooltip"))
                .binding(
                        defaults.emissiveExplosion,
                        () -> config.emissiveExplosion,
                        val -> config.emissiveExplosion = val
                )
                .controller(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var emissiveWaterExplosion = Option.createBuilder(boolean.class)
                .name(Text.translatable("explosiveenhancement.extras.emissivewater"))
                .tooltip(Text.translatable("explosiveenhancement.extras.emissivewater.tooltip"))
                .binding(
                        defaults.emissiveWaterExplosion,
                        () -> config.emissiveWaterExplosion,
                        val -> config.emissiveWaterExplosion = val
                )
                .controller(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var alwaysShow = Option.createBuilder(boolean.class)
                .name(Text.translatable("explosiveenhancement.extras.alwaysshow"))
                .tooltip(Text.translatable("explosiveenhancement.extras.alwaysshow.tooltip"))
                .binding(
                        defaults.alwaysShow,
                        () -> config.alwaysShow,
                        val -> config.alwaysShow = val
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
        extrasGroup.option(emissiveExplosion);
        extrasGroup.option(emissiveWaterExplosion);
        extrasGroup.option(alwaysShow);
        extrasGroup.option(debugLogs);
        extrasCategoryBuilder.group(extrasGroup.build());

        yacl.category(defaultCategoryBuilder.build());
        yacl.category(dynamicCategoryBuilder.build());
        yacl.category(extrasCategoryBuilder.build());

        return yacl.build().generateScreen(parent);
    }
}
