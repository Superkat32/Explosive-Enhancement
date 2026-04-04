package net.superkat.explosiveenhancement.particles.normal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.util.RandomSource;
import net.superkat.explosiveenhancement.particles.AbstractExplosiveParticle;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class BlastWaveParticle extends AbstractExplosiveParticle {
    // cache here for the ever-so-smallest amount of performance increase
    private static final float HUNDRED_EIGHTY_DEGREES = (float) Math.toRadians(180f);
    private static final float NINETY_DEGREES = (float) Math.toRadians(90);

    public BlastWaveParticle(
            ClientLevel world,
            double x, double y, double z,
            double velX, double velY, double velZ,
            BlastWaveParticleEffect params,
            SpriteSet spriteProvider
    ) {
        super(world, x, y, z, velX, velY, velZ, params.getScale(), params.isEmissive(), spriteProvider);
        this.lifetime = (int) (15 + (Math.floor(this.quadSize / 5)));
    }

    @Override
    public void extract(QuadParticleRenderState submittable, Camera camera, float tickProgress) {
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotationX(NINETY_DEGREES); // rotate 90 degrees to be horizontal
        this.extractRotatedQuad(submittable, camera, quaternionf, tickProgress);
        quaternionf.rotateYXZ(HUNDRED_EIGHTY_DEGREES, 0, 0); // flip upside down to be seen from beneath
        this.extractRotatedQuad(submittable, camera, quaternionf, tickProgress);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteProvider);
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteSet sprites) implements ParticleProvider<BlastWaveParticleEffect> {
        @Override
        public @NotNull Particle createParticle(BlastWaveParticleEffect parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
            return new BlastWaveParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters, this.sprites);
        }
    }
}