package net.superkat.explosiveenhancement;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.superkat.explosiveenhancement.config.ExplosiveConfig;
import net.superkat.explosiveenhancement.particles.*;

public class ExplosiveEnhancementClient implements ClientModInitializer {

    public static ExplosiveConfig config = ExplosiveConfig.INSTANCE;

    @Override
    public void onInitializeClient() {
        //Loads the config, GUI powered by YACL
        ExplosiveConfig.load();
        if(!FabricLoader.getInstance().isDevelopmentEnvironment() && !YaclLoaded()) {
            ExplosiveEnhancement.LOGGER.warn("YetAnotherConfigLib is not installed! If you wish to edit the config, please install it!");
        }

        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.BLASTWAVE, BlastWaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.FIREBALL, FireballParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.BLANK_FIREBALL, FireballParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.SMOKE, SmokeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.SPARKS, SparkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.BUBBLE, BubbleParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.SHOCKWAVE, ShockwaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.BLANK_SHOCKWAVE, ShockwaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.UNDERWATERBLASTWAVE, UnderwaterBlastWaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.UNDERWATERSPARKS, UnderwaterSparkParticle.Factory::new);
    }

    public static boolean YaclLoaded() {
        return FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
    }
}
