package net.superkat.explosiveenhancement.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;

import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.config;

@Environment(EnvType.CLIENT)
public class FireballParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    boolean important;

    FireballParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.spriteProvider = spriteProvider;
        this.maxAge = (int) (9 + Math.floor(velX / 5));
        this.scale = (float) velX;
        important = velY == 1;
        this.setVelocity(0D, 0D, 0D);
        this.setSpriteForAge(spriteProvider);
    }

    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.velocityY -= (double)this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            if(this.age >= this.maxAge * 0.65 && config.showSparks) {
                this.world.addParticle(ExplosiveEnhancement.SPARKS, important, this.x, this.y, this.z, scale, this.velocityY, this.velocityZ);
            }
            this.setSpriteForAge(this.spriteProvider);
        }
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    //Makes the particle emissive
    @Override
    protected int getBrightness(float tint) {
        return config.emissiveExplosion ? 15728880 : super.getBrightness(tint);
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new FireballParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
