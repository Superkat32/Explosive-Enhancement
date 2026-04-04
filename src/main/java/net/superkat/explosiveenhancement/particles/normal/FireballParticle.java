package net.superkat.explosiveenhancement.particles.normal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import net.superkat.explosiveenhancement.config.ExplosiveConfig;
import net.superkat.explosiveenhancement.particles.AbstractExplosiveParticle;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class FireballParticle extends AbstractExplosiveParticle {
    protected boolean water;
    protected boolean sparks;
    protected boolean sparksOnly;
    protected boolean sparksImportant;

    public FireballParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ, FireballParticleEffect params, SpriteSet spriteProvider) {
        super(world, x, y, z, velX, velY, velZ, params.getScale(), params.isEmissive(), spriteProvider);
        this.lifetime = (int) (9 + Math.floor(this.quadSize / 5));

        this.water = params.isWater();
        this.sparks = params.isSparks();
        this.sparksOnly = params.isSparksOnly();
        this.sparksImportant = params.isSparksImportant();
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        this.yd -= this.gravity;
        this.move(this.xd, this.yd, this.zd);

        // Fun fact: This is spawning multiple sparks EACH TICK above that .65 barrier, instead of only one!
        // I must have messed up by adding the >= instead of == waaay back when adding this and just never noticed.
        // The problem: "fixing" looks sadder because the overall spark effect/noticeable color lingers less.
        // It's stood the test of time, and this mistake will go on in history as... nothing much, but sorta funny
        if(this.sparks && this.age >= this.lifetime * 0.65) {
            float sparkScaleMultiplier = this.quadSize == 0 ? 1f : this.quadSize * 0.25f;
            float sparkScale = ExplosiveConfig.INSTANCE.sparkSize * sparkScaleMultiplier;
            float sparkAlpha = this.water ? ExplosiveConfig.INSTANCE.underwaterSparkOpacity : ExplosiveConfig.INSTANCE.sparkOpacity;
            this.level.addParticle(
                    new SparkParticleEffect(this.water, sparkScale, this.emissive, sparkAlpha), sparksImportant, sparksImportant,
                    this.x, this.y, this.z,
                    this.xd, this.yd, this.zd
            );
        }

        this.setSpriteFromAge(this.spriteProvider);
    }

    @Override
    public ParticleRenderType getGroup() {
        // simply don't render the particle if it shouldn't be enabled... it's that simple!
        return this.sparksOnly ? ParticleRenderType.NO_RENDER : super.getGroup();
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteSet sprites) implements ParticleProvider<FireballParticleEffect> {
        @Override
        public @NotNull Particle createParticle(FireballParticleEffect parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
            return new FireballParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters, sprites);
        }
    }

}
