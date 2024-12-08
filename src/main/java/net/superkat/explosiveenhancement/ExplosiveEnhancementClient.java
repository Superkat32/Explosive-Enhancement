package net.superkat.explosiveenhancement;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.particle.NoRenderParticle;
import net.superkat.explosiveenhancement.config.ExplosiveConfig;
import net.superkat.explosiveenhancement.particles.normal.BlastWaveParticle;
import net.superkat.explosiveenhancement.particles.normal.FireballParticle;
import net.superkat.explosiveenhancement.particles.normal.NoRender;
import net.superkat.explosiveenhancement.particles.normal.SmokeParticle;
import net.superkat.explosiveenhancement.particles.normal.SparkParticle;
import net.superkat.explosiveenhancement.particles.underwater.BubbleParticle;
import net.superkat.explosiveenhancement.particles.underwater.ShockwaveParticle;
import net.superkat.explosiveenhancement.particles.underwater.UnderwaterBlastWaveParticle;
import net.superkat.explosiveenhancement.particles.underwater.UnderwaterSparkParticle;

//? if(>=1.21.2) {
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.superkat.explosiveenhancement.network.S2CExplosiveEnhancementParticles;
import net.superkat.explosiveenhancement.api.ExplosionParticleType;
import net.superkat.explosiveenhancement.api.ExplosiveApi;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
//?}

public class ExplosiveEnhancementClient implements ClientModInitializer {

    public static ExplosiveConfig CONFIG = ExplosiveConfig.INSTANCE;

    @Override
    public void onInitializeClient() {
        //Loads the config, GUI powered by YACL
        ExplosiveConfig.load();
        if(!FabricLoader.getInstance().isDevelopmentEnvironment() && !YaclLoaded()) {
            ExplosiveEnhancement.LOGGER.warn("[Explosive Enhancement]: YetAnotherConfigLib is not installed! If you wish to edit Explosive Enhancement's config, please install it!");
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

        //? if(>=1.21.2) {
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.NO_RENDER_PARTICLE, NoRender.Factory::new);

        ClientPlayNetworking.registerGlobalReceiver(S2CExplosiveEnhancementParticles.ID, (payload, context) -> {
            if(CONFIG.bypassPowerForSingleplayer) {
                World world = context.client().world;
                double x = payload.x();
                double y = payload.y();
                double z = payload.z();
                ExplosionParticleType explosionParticleType = ExplosiveApi.determineParticleType(world, new Vec3d(x, y, z), payload.initParticle());
                ExplosiveApi.spawnParticles(world, x, y, z, payload.power(), explosionParticleType);

                boolean showVanillaParticles =
                        (CONFIG.showDefaultExplosion && explosionParticleType == ExplosionParticleType.NORMAL)
                                || (CONFIG.showDefaultExplosionUnderwater && explosionParticleType == ExplosionParticleType.WATER);
                if(showVanillaParticles) {
                    world.addParticle(payload.initParticle(), x, y, z, 0f, 0f, 0f);
                }
            }
        });
        //?}
    }

    public static boolean YaclLoaded() {
        //? if (<=1.19.3) {
//        /*return FabricLoader.getInstance().isModLoaded("yet-another-config-lib");
        //?} else {
        return FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
        //?}
    }
}
