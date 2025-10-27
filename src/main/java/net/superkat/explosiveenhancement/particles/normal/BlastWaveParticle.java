package net.superkat.explosiveenhancement.particles.normal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.BillboardParticleSubmittable;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.CONFIG;

@Environment(EnvType.CLIENT)
public class BlastWaveParticle extends BillboardParticle {
    // cache here for the ever-so-smallest amount of performance increase
    private static final float HUNDRED_EIGHTY_DEGREES = (float) Math.toRadians(180f);
    private static final float NINETY_DEGREES = (float) Math.toRadians(90);
    private final SpriteProvider spriteProvider;

    public BlastWaveParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider spriteProvider) {
        super(world, x, y + 0.5, z, 0.0, 0.0, 0.0, spriteProvider.getFirst());
        this.scale = (float) velX;
        this.setVelocity(0, 0, 0);
        this.maxAge = (int) (15 + (Math.floor(this.scale / 5)));
        this.spriteProvider = spriteProvider;
    }

    @Override
    public void render(BillboardParticleSubmittable submittable, Camera camera, float tickProgress) {
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotationX(NINETY_DEGREES); // rotate 90 degrees to be horizontal
        this.render(submittable, camera, quaternionf, tickProgress);
        quaternionf.rotateYXZ(HUNDRED_EIGHTY_DEGREES, 0, 0); // flip upside down to be seen from beneath
        this.render(submittable, camera, quaternionf, tickProgress);
    }

    // Makes the particle emissive
    @Override
    protected int getBrightness(float tint) {
        return CONFIG.emissiveExplosion ? 15728880 : super.getBrightness(tint);
    }

    @Override
    public void tick() {
        super.tick();
        this.updateSprite(this.spriteProvider);
    }

    @Override
    protected RenderType getRenderType() {
        return RenderType.PARTICLE_ATLAS_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public record Factory<T extends ParticleEffect>(SpriteProvider sprites) implements ParticleFactory<T> {
        @Override
        public @NotNull Particle createParticle(T parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new BlastWaveParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.sprites);
        }
    }
}