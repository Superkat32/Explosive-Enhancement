package net.superkat.explosiveenhancement.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;

public class AbstractExplosiveParticle extends SingleQuadParticle {
    protected final SpriteSet spriteProvider;
    public boolean emissive;
    public AbstractExplosiveParticle(
            ClientLevel world,
            double x, double y, double z,
            double velocityX, double velocityY, double velocityZ,
            float scale, boolean emissive,
            SpriteSet spriteProvider
    ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider.first());
        this.setParticleSpeed(velocityX, velocityY, velocityZ); // prevent super constructor adding randomness to velocity
        this.spriteProvider = spriteProvider;
        this.quadSize = scale;
        this.emissive = emissive;
    }

    // Makes the particle emissive
    @Override
    protected int getLightColor(float tint) {
        return this.emissive ? 15728880 : super.getLightColor(tint);
    }

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }
}
