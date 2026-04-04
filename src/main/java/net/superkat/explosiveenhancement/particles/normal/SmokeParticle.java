package net.superkat.explosiveenhancement.particles.normal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.superkat.explosiveenhancement.particles.AbstractExplosiveParticle;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SmokeParticle extends AbstractExplosiveParticle {

    public SmokeParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ, SmokeParticleEffect params, SpriteSet spriteProvider) {
        super(world, x, y, z, velX, velY, velZ, params.getScale() * 0.25f, params.isEmissive(), spriteProvider);
        this.lifetime = (int) (this.random.nextInt(35) + params.getScale() * this.random.nextIntBetweenInclusive(3, 22));
        this.hasPhysics = true;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        if (this.age == 12) {
            this.xd = 0;
            this.yd = 0.05;
            this.zd = 0;
        }
        this.move(this.xd, this.yd, this.zd);
        this.setSpriteFromAge(this.spriteProvider);
    }

    @Override
    protected int getLightColor(float tint) {
        BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
        int normalBrightness = this.level.hasChunkAt(blockPos) ? LevelRenderer.getLightColor(this.level, blockPos) : 0;
        if(this.emissive) {
            if(this.age <= this.lifetime * 0.12) {
                return 15728880; // full emissive
            } else if(this.age <= lifetime * 0.17) {
                // fade out emissive based on age and max age
                return Mth.clamp(normalBrightness + this.age + 30, normalBrightness, 15728880);
            }
        }
        return normalBrightness;
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteSet sprites) implements ParticleProvider<SmokeParticleEffect> {
        @Override
        public @NotNull Particle createParticle(SmokeParticleEffect parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
            return new SmokeParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters, sprites);
        }
    }
}