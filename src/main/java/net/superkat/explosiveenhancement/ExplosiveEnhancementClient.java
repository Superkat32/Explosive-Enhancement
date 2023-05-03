package net.superkat.explosiveenhancement;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class ExplosiveEnhancementClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ExplosiveConfig.INSTANCE.load();

        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.BLASTWAVE, BoomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.FIREBALL, BigExplosionParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.MUSHROOMCLOUD, LingerParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.BUBBLE, BubbleParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.SHOCKWAVE, BigExplosionParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.UNDERWATERBLASTWAVE, BoomParticle.Factory::new);

    }
}
