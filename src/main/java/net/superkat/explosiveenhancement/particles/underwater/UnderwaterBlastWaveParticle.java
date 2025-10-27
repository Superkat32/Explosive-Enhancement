package net.superkat.explosiveenhancement.particles.underwater;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.superkat.explosiveenhancement.particles.normal.BlastWaveParticle;
import org.jetbrains.annotations.NotNull;

import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.CONFIG;

public class UnderwaterBlastWaveParticle extends BlastWaveParticle {
    public UnderwaterBlastWaveParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider sprites) {
        super(world, x, y, z, velX, velY, velZ, sprites);
    }

    // Makes the particle emissive
    // Doesn't use super.getBrightness because that would cause the particle to appear emissive if
    // emissive underwater explosion is turned off and emissive explosion is turned on
    @Override
    protected int getBrightness(float tint) {
        BlockPos blockPos = BlockPos.ofFloored(x, y, z);
        return CONFIG.emissiveWaterExplosion ? 15728880 : this.world.isChunkLoaded(blockPos) ? WorldRenderer.getLightmapCoordinates(this.world, blockPos) : 0;
    }

    @Environment(EnvType.CLIENT)
    public record Factory<T extends ParticleEffect>(SpriteProvider sprites) implements ParticleFactory<T> {
        @Override
        public @NotNull Particle createParticle(T parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new UnderwaterBlastWaveParticle(world, x, y, z, velocityX, velocityY, velocityZ, sprites);
        }
    }
}
