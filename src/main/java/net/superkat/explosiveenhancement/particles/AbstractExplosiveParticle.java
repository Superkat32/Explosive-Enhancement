package net.superkat.explosiveenhancement.particles;

import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;

public class AbstractExplosiveParticle extends BillboardParticle {
    protected final SpriteProvider spriteProvider;
    public boolean emissive;
    public AbstractExplosiveParticle(
            ClientWorld world,
            double x, double y, double z,
            double velocityX, double velocityY, double velocityZ,
            float scale, boolean emissive,
            SpriteProvider spriteProvider
    ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider.getFirst());
        this.setVelocity(velocityX, velocityY, velocityZ); // prevent super constructor adding randomness to velocity
        this.spriteProvider = spriteProvider;
        this.scale = scale;
        this.emissive = emissive;
    }

    // Makes the particle emissive
    @Override
    protected int getBrightness(float tint) {
        return this.emissive ? 15728880 : super.getBrightness(tint);
    }

    @Override
    protected RenderType getRenderType() {
        return RenderType.PARTICLE_ATLAS_TRANSLUCENT;
    }
}
