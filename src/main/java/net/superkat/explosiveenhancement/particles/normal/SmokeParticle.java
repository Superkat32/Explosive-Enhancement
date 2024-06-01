package net.superkat.explosiveenhancement.particles.normal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.MathHelper;

import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.CONFIG;

@Environment(EnvType.CLIENT)
public class SmokeParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    SmokeParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.setSpriteForAge(spriteProvider);
        this.velocityMultiplier = 0.6F;
        this.spriteProvider = spriteProvider;
        this.maxAge = this.random.nextInt(35) + 1;
        //velX or velZ can be either the size/power or actual velocity in that respective direction
        if(velZ == 0) {
            //for the particles going straight up
            scale = (float) velX * 0.25f;
            this.maxAge += (int) (velX * this.random.nextBetween(3, 22));
            this.velocityX = 0;
            this.velocityZ = 0;
        } else if(velX == 0.15 || velX == -0.15) {
            //for the particles where velZ is used as the power variable
            scale = (float) velZ * 0.25f;
            this.maxAge += (int) (velZ * this.random.nextBetween(3, 22));
            this.velocityX = velX * (velZ * 0.5);
            this.velocityZ = 0;
        } else if(velZ == 0.15 || velZ == -0.15) {
            //for the particles where velX is used for the power variable
            scale = (float) velX * 0.25f;
            this.maxAge += (int) (velX * this.random.nextBetween(3, 22));
            this.velocityX = 0;
            this.velocityZ = velZ * (velX * 0.5);
        }
        this.velocityY = velY / 1.85;
        this.gravityStrength = 3.0E-6F;
        this.collidesWithWorld = true;
    }

    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.setSpriteForAge(this.spriteProvider);
            if (this.age == 12) {
                this.velocityX = 0;
                this.velocityY = 0.05;
                this.velocityZ = 0;
            }
            this.move(this.velocityX, this.velocityY, this.velocityZ);
        }
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    //Makes the particle emissive
    @Override
    protected int getBrightness(float tint) {
        if(CONFIG.emissiveExplosion && this.age <= this.maxAge * 0.12) {
            return 15728880;
        } else if (CONFIG.emissiveExplosion && this.age <= this.maxAge * 0.17) {
            return MathHelper.clamp(super.getBrightness(tint) + this.age + 30, super.getBrightness(tint), 15728880);
        } else {
            return super.getBrightness(tint);
        }
    }

    @Environment(EnvType.CLIENT)
    public record Factory<T extends ParticleEffect>(SpriteProvider sprites) implements ParticleFactory<T> {
        public Particle createParticle(T type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SmokeParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}