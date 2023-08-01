package net.superkat.explosiveenhancement;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.superkat.explosiveenhancement.config.ExplosiveConfig;
import net.superkat.explosiveenhancement.config.ExplosiveNoYACLConfig;
import net.superkat.explosiveenhancement.particles.*;

public class ExplosiveEnhancementClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //Loads the config, powered by YACL
        //If YACL isn't found, then the "config" will only be the default settings
        //This is to allow developers using the API to not have to worry about an extra dependency
        if(FabricLoader.getInstance().isModLoaded("yet-another-config-lib")) {
            ExplosiveConfig.INSTANCE.load();
        } else if(!FabricLoader.getInstance().isDevelopmentEnvironment()) {
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
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.UNDERWATERBLASTWAVE, BlastWaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.UNDERWATERSPARKS, UnderwaterSparkParticle.Factory::new);
    }

    public static ExplosiveNoYACLConfig getConfig() {
        var config = new ExplosiveNoYACLConfig();
        if(FabricLoader.getInstance().isModLoaded("yet-another-config-lib")) {
            config = new ExplosiveConfig();
        }
        return config;
    }
}
