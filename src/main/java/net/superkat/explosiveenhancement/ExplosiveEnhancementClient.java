package net.superkat.explosiveenhancement;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.superkat.explosiveenhancement.config.ExplosiveConfig;
import net.superkat.explosiveenhancement.particles.normal.BlastWaveParticle;
import net.superkat.explosiveenhancement.particles.normal.FireballParticle;
import net.superkat.explosiveenhancement.particles.normal.SmokeParticle;
import net.superkat.explosiveenhancement.particles.normal.SparkParticle;
import net.superkat.explosiveenhancement.particles.underwater.BubbleParticle;

public class ExplosiveEnhancementClient implements ClientModInitializer {

    public static final ExplosiveConfig CONFIG = ExplosiveConfig.INSTANCE;

    @Override
    public void onInitializeClient() {
        // Loads the config, GUI powered by YACL
        ExplosiveConfig.load();
        if(!FabricLoader.getInstance().isDevelopmentEnvironment() && !YaclLoaded()) {
            ExplosiveEnhancement.LOGGER.warn("[Explosive Enhancement]: YetAnotherConfigLib is not installed! If you wish to edit Explosive Enhancement's config, please install it!");
        }

        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.BLASTWAVE, BlastWaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.FIREBALL, FireballParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.SPARKS, SparkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.SMOKE, SmokeParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.WATER_BLASTWAVE, BlastWaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.SHOCKWAVE, FireballParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.WATER_SPARKS, SparkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.BUBBLE, BubbleParticle.Factory::new);
    }

    public static boolean YaclLoaded() {
        return FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
    }
}
