package net.superkat.explosiveenhancement.particles.normal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;

import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.CONFIG;

@Environment(EnvType.CLIENT)
public class SparkParticle extends BillboardParticle {
    private final SpriteProvider spriteProvider;

    SparkParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, velX, velY, velZ, spriteProvider.getFirst());
        this.spriteProvider = spriteProvider;
        this.maxAge = (int) (5 + Math.floor(velX / 5));
        if(velX == 0) {
            this.scale = CONFIG.sparkSize;
        } else {
            this.scale = (float) (CONFIG.sparkSize * (velX * 0.25f));
        }
        this.setVelocity(0, 0, 0);
        this.alpha = CONFIG.sparkOpacity;
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

    // Makes the particle emissive
    @Override
    protected int getBrightness(float tint) {
        return CONFIG.emissiveExplosion ? 15728880 : super.getBrightness(tint);
    }

    @Override
    protected RenderType getRenderType() {
        return RenderType.PARTICLE_ATLAS_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public record Factory<T extends ParticleEffect>(SpriteProvider sprites) implements ParticleFactory<T> {
        @Override
        public @NotNull Particle createParticle(T parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new SparkParticle(world, x, y, z, velocityX, velocityY, velocityZ, sprites);
        }
    }
}
