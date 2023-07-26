package net.superkat.explosiveenhancement.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

import static net.superkat.explosiveenhancement.ExplosiveConfig.INSTANCE;

@Environment(EnvType.CLIENT)
public class UnderwaterSparkParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    UnderwaterSparkParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.spriteProvider = spriteProvider;
        this.maxAge = (int) (5 + Math.floor(velX / 5));
        if(velX == 0) {
            this.scale = INSTANCE.getConfig().underwaterSparkSize;
        } else {
            this.scale = (float) (INSTANCE.getConfig().underwaterSparkSize * (velX * 0.25f));
        }
        this.setVelocity(0D, 0D, 0D);
//        this.scale = INSTANCE.getConfig().underwaterSparkSize;
////        this.gravityStrength = 0.008F;
//        this.velocityX = velocityX;
//        this.velocityY = velocityY;
//        this.velocityZ = velocityZ;
        this.alpha = INSTANCE.getConfig().underwaterSparkOpacity;
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
            this.setSpriteForAge(this.spriteProvider);
        }
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new UnderwaterSparkParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
