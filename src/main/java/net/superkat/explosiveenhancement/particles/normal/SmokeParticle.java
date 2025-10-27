package net.superkat.explosiveenhancement.particles.normal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.superkat.explosiveenhancement.particles.AbstractExplosiveParticle;
import net.superkat.explosiveenhancement.particles.SmokeParticleEffect;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SmokeParticle extends AbstractExplosiveParticle {

    public SmokeParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SmokeParticleEffect params, SpriteProvider spriteProvider) {
        super(world, x, y, z, velX, velY, velZ, params.getScale() * 0.25f, params.isEmissive(), spriteProvider);
        this.maxAge = (int) (this.random.nextInt(35) + params.getScale() * this.random.nextBetween(3, 22));
        this.collidesWithWorld = true;
    }

    public void tick() {
        this.lastX = this.x;
        this.lastY = this.y;
        this.lastZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
            return;
        }

        if (this.age == 12) {
            this.velocityX = 0;
            this.velocityY = 0.05;
            this.velocityZ = 0;
        }
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.updateSprite(this.spriteProvider);
    }

    @Override
    protected int getBrightness(float tint) {
        BlockPos blockPos = BlockPos.ofFloored(this.x, this.y, this.z);
        int normalBrightness = this.world.isChunkLoaded(blockPos) ? WorldRenderer.getLightmapCoordinates(this.world, blockPos) : 0;
        if(this.emissive) {
            if(this.age <= this.maxAge * 0.12) {
                return 15728880; // full emissive
            } else if(this.age <= maxAge * 0.17) {
                // fade out emissive based on age and max age
                return MathHelper.clamp(normalBrightness + this.age + 30, normalBrightness, 15728880);
            }
        }
        return normalBrightness;
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider sprites) implements ParticleFactory<SmokeParticleEffect> {
        @Override
        public @NotNull Particle createParticle(SmokeParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new SmokeParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters, sprites);
        }
    }
}