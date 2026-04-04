package net.superkat.explosiveenhancement.particles.normal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import net.superkat.explosiveenhancement.particles.AbstractExplosiveParticle;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SparkParticle extends AbstractExplosiveParticle {
    public SparkParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ, SparkParticleEffect params, SpriteSet spriteProvider) {
        super(world, x, y, z, velX, velY, velZ, params.getScale(), params.isEmissive(), spriteProvider);
        this.lifetime = (int) (5 + Math.floor(velX / 5));
        this.alpha = params.getAlpha();
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.setSpriteFromAge(this.spriteProvider);
        }
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteSet sprites) implements ParticleProvider<SparkParticleEffect> {
        @Override
        public @NotNull Particle createParticle(SparkParticleEffect parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
            return new SparkParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters, sprites);
        }
    }
}
