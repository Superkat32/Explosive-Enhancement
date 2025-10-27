package net.superkat.explosiveenhancement.particles.normal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.random.Random;
import net.superkat.explosiveenhancement.config.ExplosiveConfig;
import net.superkat.explosiveenhancement.particles.AbstractExplosiveParticle;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class FireballParticle extends AbstractExplosiveParticle {
    protected boolean water;
    protected boolean sparks;
    protected boolean sparksOnly;
    protected boolean sparksImportant;

    public FireballParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, FireballParticleEffect params, SpriteProvider spriteProvider) {
        super(world, x, y, z, velX, velY, velZ, params.getScale(), params.isEmissive(), spriteProvider);
        this.maxAge = (int) (9 + Math.floor(this.scale / 5));

        this.water = params.isWater();
        this.sparks = params.isSparks();
        this.sparksOnly = params.isSparksOnly();
        this.sparksImportant = params.isSparksImportant();
    }

    public void tick() {
        this.lastX = this.x;
        this.lastY = this.y;
        this.lastZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
            return;
        }

        this.velocityY -= this.gravityStrength;
        this.move(this.velocityX, this.velocityY, this.velocityZ);

        // Fun fact: This is spawning multiple sparks EACH TICK above that .65 barrier, instead of only one!
        // I must have messed up by adding the >= instead of == waaay back when adding this and just never noticed.
        // The problem: "fixing" looks sadder because the overall spark effect/noticeable color lingers less.
        // It's stood the test of time, and this mistake will go on in history as... nothing much, but sorta funny
        if(this.sparks && this.age >= this.maxAge * 0.65) {
            float sparkScaleMultiplier = this.scale == 0 ? 1f : this.scale * 0.25f;
            float sparkScale = ExplosiveConfig.INSTANCE.sparkSize * sparkScaleMultiplier;
            float sparkAlpha = this.water ? ExplosiveConfig.INSTANCE.underwaterSparkOpacity : ExplosiveConfig.INSTANCE.sparkOpacity;
            this.world.addParticleClient(
                    new SparkParticleEffect(this.water, sparkScale, this.emissive, sparkAlpha), sparksImportant, sparksImportant,
                    this.x, this.y, this.z,
                    this.velocityX, this.velocityY, this.velocityZ
            );
        }

        this.updateSprite(this.spriteProvider);
    }

    @Override
    public ParticleTextureSheet textureSheet() {
        // simply don't render the particle if it shouldn't be enabled... it's that simple!
        return this.sparksOnly ? ParticleTextureSheet.NO_RENDER : super.textureSheet();
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider sprites) implements ParticleFactory<FireballParticleEffect> {
        @Override
        public @NotNull Particle createParticle(FireballParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new FireballParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters, sprites);
        }
    }

}
