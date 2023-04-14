package net.superkat.explosiveenhancement;

import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.OptionGroup;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.config.ConfigEntry;
import dev.isxander.yacl.config.ConfigInstance;
import dev.isxander.yacl.config.GsonConfigInstance;
import dev.isxander.yacl.gui.controllers.BooleanController;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.nio.file.Path;

public class ExplosiveConfig {

    public static final ConfigInstance<ExplosiveConfig> INSTANCE = new GsonConfigInstance<>(ExplosiveConfig.class, Path.of("./config/explosive-config.json"));

    @ConfigEntry public static boolean showBoom = true;
    @ConfigEntry public static boolean showBigExplosion = true;
    @ConfigEntry public static boolean showLingerParticles = true;
    @ConfigEntry public static boolean showDefaultExplosion = false;
    @ConfigEntry public static boolean underwaterExplosions = true;
    @ConfigEntry public static boolean shockwave = true;
    @ConfigEntry public static int bubbleAmount = 50;
    @ConfigEntry public static boolean debugLogs = false;
    @ConfigEntry public static boolean modEnabled = true;

    public static Screen makeScreen(Screen parent) {
        return YetAnotherConfigLib.create(INSTANCE, (defaults, config, builder) -> {
            //Default Explosion category
            var defaultCategoryBuilder = ConfigCategory.createBuilder()
                    .name(Text.translatable("explosiveenhancement.category.default"));

            //Explosion particles group
            var explosionGroup = OptionGroup.createBuilder()
                    .name(Text.translatable("explosiveenhancement.explosion.group"))
                    .tooltip(Text.translatable("explosiveenhancement.explosion.group.tooltip"));
            var showBoom = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.boom.enabled"))
                    .tooltip(Text.translatable("explosiveenhancement.boom.enabled.tooltip"))
                    .binding(
                            defaults.showBoom,
                            () -> config.showBoom,
                            val -> config.showBoom = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var showBigExplosion = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.bigexplosion.enabled"))
                    .tooltip(Text.translatable("explosiveenhancement.bigexplosion.enabled.tooltip"))
                    .binding(
                            defaults.showBigExplosion,
                            () -> config.showBigExplosion,
                            val -> config.showBigExplosion = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();
            var showLingerParticles = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.linger.enabled"))
                    .tooltip(Text.translatable("explosiveenhancement.linger.enabled.tooltip"))
                    .binding(
                            defaults.showLingerParticles,
                            () -> config.showLingerParticles,
                            val -> config.showLingerParticles = val
                    )
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
            explosionGroup.option(showBoom);
            explosionGroup.option(showBigExplosion);
            explosionGroup.option(showLingerParticles);
            explosionGroup.option(showDefaultExplosion);
            defaultCategoryBuilder.group(explosionGroup.build());

            var underwaterGroup = OptionGroup.createBuilder()
                    .name(Text.translatable("explosiveenhancement.underwater.group"))
                    .tooltip(Text.translatable("explosiveenhancement.underwater.group.tooltip"));

            var shockwave = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.underwater.shockwave"))
                    .tooltip(Text.translatable("explosiveenhancement.underwater.shockwave.tooltip"))
                    .binding(
                            defaults.shockwave,
                            () -> config.shockwave,
                            val -> config.shockwave = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .available(true)
                    .build();

            var bubbleAmount = Option.createBuilder(Integer.class)
                    .name(Text.translatable("explosiveenhancement.underwater.bubbleamount"))
                    .tooltip(Text.translatable("explosiveenhancement.underwater.bubbleamount.tooltip"))
                    .binding(
                            defaults.bubbleAmount,
                            () -> config.bubbleAmount,
                            val -> config.bubbleAmount = val
                    )
                    .controller(integerOption -> new <Number>IntegerSliderController(integerOption, 0, 500, 5))
                    .available(true)
                    .build();

            var underwaterExplosions = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.underwater.enabled"))
                    .tooltip(Text.translatable("explosiveenhancement.underwater.enabled.tooltip"))
                    .binding(
                            defaults.underwaterExplosions,
                            () -> config.underwaterExplosions,
                            val -> config.underwaterExplosions = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .listener((opt, newValue) -> shockwave.setAvailable(newValue))
                    .listener((opt, newValue) -> bubbleAmount.setAvailable(newValue))
                    .build();
            underwaterGroup.option(underwaterExplosions);
            underwaterGroup.option(shockwave);
            underwaterGroup.option(bubbleAmount);
            defaultCategoryBuilder.group(underwaterGroup.build());



            var extrasCategoryBuilder = ConfigCategory.createBuilder()
                    .name(Text.translatable("explosiveenhancement.category.extras"));

            var extrasGroup = OptionGroup.createBuilder()
                    .name(Text.translatable("explosiveenhancement.extras.group"))
                    .tooltip(Text.translatable("explosiveenhancement.extras.group.tooltip"));

            var debugLogs = Option.createBuilder(boolean.class)
                    .name(Text.translatable("explosiveenhancement.extras.logs"))
                    .tooltip(Text.translatable("explosiveenhancement.extras.logs.tooltip"))
                    .binding(
                            defaults.debugLogs,
                            () -> config.debugLogs,
                            val -> config.debugLogs = val
                    )
                    .controller(booleanOption -> new BooleanController(booleanOption, true))
                    .build();

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
            extrasGroup.option(debugLogs);
            extrasGroup.option(modEnabled);
            extrasCategoryBuilder.group(extrasGroup.build());

            return builder
                    .title(Text.translatable("explosiveenhancement.title"))
                    .category(defaultCategoryBuilder.build())
                    .category(extrasCategoryBuilder.build());
        }).generateScreen(parent);
    }

}
