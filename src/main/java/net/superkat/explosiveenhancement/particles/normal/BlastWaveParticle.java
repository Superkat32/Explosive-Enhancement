package net.superkat.explosiveenhancement.particles.normal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.BillboardParticleSubmittable;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.random.Random;
import net.superkat.explosiveenhancement.particles.AbstractExplosiveParticle;
import net.superkat.explosiveenhancement.particles.BlastWaveParticleEffect;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class BlastWaveParticle extends AbstractExplosiveParticle {
    // cache here for the ever-so-smallest amount of performance increase
    private static final float HUNDRED_EIGHTY_DEGREES = (float) Math.toRadians(180f);
    private static final float NINETY_DEGREES = (float) Math.toRadians(90);

    public BlastWaveParticle(
            ClientWorld world,
            double x, double y, double z,
            double velX, double velY, double velZ,
            BlastWaveParticleEffect params,
            SpriteProvider spriteProvider
    ) {
        super(world, x, y, z, velX, velY, velZ, params.getScale(), params.isEmissive(), spriteProvider);
        this.maxAge = (int) (15 + (Math.floor(this.scale / 5)));
    }

    @Override
    public void render(BillboardParticleSubmittable submittable, Camera camera, float tickProgress) {
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotationX(NINETY_DEGREES); // rotate 90 degrees to be horizontal
        this.render(submittable, camera, quaternionf, tickProgress);
        quaternionf.rotateYXZ(HUNDRED_EIGHTY_DEGREES, 0, 0); // flip upside down to be seen from beneath
        this.render(submittable, camera, quaternionf, tickProgress);
    }

    @Override
    public void tick() {
        super.tick();
        this.updateSprite(this.spriteProvider);
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider sprites) implements ParticleFactory<BlastWaveParticleEffect> {
        @Override
        public @NotNull Particle createParticle(BlastWaveParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new BlastWaveParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters, this.sprites);
        }
    }
}