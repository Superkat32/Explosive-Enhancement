package net.superkat.explosiveenhancement.particles.underwater;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.random.Random;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;
import org.jetbrains.annotations.NotNull;

import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.CONFIG;

@Environment(EnvType.CLIENT)
public class ShockwaveParticle extends BillboardParticle {
    private final SpriteProvider spriteProvider;
    public boolean important;

    public ShockwaveParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, velX, velY, velZ, spriteProvider.getFirst());
        this.spriteProvider = spriteProvider;
        this.scale = (float) velX;
        this.maxAge = (int) (9 + Math.floor(this.scale / 5));
        important = CONFIG.alwaysShow;
        this.setVelocity(0, 0, 0);
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
            if(this.age >= this.maxAge * 0.65 && CONFIG.showUnderwaterSparks) {
                this.world.addParticleClient(ExplosiveEnhancement.UNDERWATERSPARKS, important, important, this.x, this.y, this.z, scale, this.velocityY, this.velocityZ);
            }
            this.updateSprite(this.spriteProvider);
        }
    }

    // Makes the particle emissive
    @Override
    protected int getBrightness(float tint) {
        return CONFIG.emissiveWaterExplosion ? 15728880 : super.getBrightness(tint);
    }

    @Override
    protected RenderType getRenderType() {
        return RenderType.PARTICLE_ATLAS_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public record Factory<T extends ParticleEffect>(SpriteProvider sprites) implements ParticleFactory<T> {
        @Override
        public @NotNull Particle createParticle(T parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new ShockwaveParticle(world, x, y, z, velocityX, velocityY, velocityZ, sprites);
        }
    }
}
