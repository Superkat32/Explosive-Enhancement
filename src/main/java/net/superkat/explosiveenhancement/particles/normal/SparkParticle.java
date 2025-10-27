package net.superkat.explosiveenhancement.particles.normal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.random.Random;
import net.superkat.explosiveenhancement.particles.AbstractExplosiveParticle;
import net.superkat.explosiveenhancement.particles.SparkParticleEffect;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SparkParticle extends AbstractExplosiveParticle {
    public SparkParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SparkParticleEffect params, SpriteProvider spriteProvider) {
        super(world, x, y, z, velX, velY, velZ, params.getScale(), params.isEmissive(), spriteProvider);
        this.maxAge = (int) (5 + Math.floor(velX / 5));
        this.alpha = params.getAlpha();
    }

    public void tick() {
        this.lastX = this.x;
        this.lastY = this.y;
        this.lastZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.velocityY -= this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.updateSprite(this.spriteProvider);
        }
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider sprites) implements ParticleFactory<SparkParticleEffect> {
        @Override
        public @NotNull Particle createParticle(SparkParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new SparkParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters, sprites);
        }
    }
}
