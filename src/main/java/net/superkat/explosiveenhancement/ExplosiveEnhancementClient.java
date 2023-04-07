package net.superkat.explosiveenhancement;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class ExplosiveEnhancementClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.BOOM, BoomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.BIG_EXPLOSION, BigExplosionParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.LINGER, LingerParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ExplosiveEnhancement.BUBBLE, BubbleParticle.Factory::new);

    }
}
