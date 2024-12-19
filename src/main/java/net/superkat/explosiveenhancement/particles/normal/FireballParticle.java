package net.superkat.explosiveenhancement.particles.normal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;
import org.jetbrains.annotations.Nullable;

import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.CONFIG;

@Environment(EnvType.CLIENT)
public class FireballParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    boolean important;

    FireballParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, velX, velY, velZ);
        this.spriteProvider = spriteProvider;
        this.scale = (float) velX;
        this.maxAge = (int) (9 + Math.floor(scale / 5));
        important = CONFIG.alwaysShow;
        this.setVelocity(0, 0, 0);
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
            if(this.age >= this.maxAge * 0.65 && CONFIG.showSparks) {
                //?if (<=1.21.3) {
//                this.world.addParticle(ExplosiveEnhancement.SPARKS, important, this.x, this.y, this.z, this.scale, this.velocityY, this.velocityZ);
                //?} else {
                this.world.addParticle(ExplosiveEnhancement.SPARKS, important, important, this.x, this.y, this.z, this.scale, this.velocityY, this.velocityZ);
                //?}
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
        return CONFIG.emissiveExplosion ? 15728880 : super.getBrightness(tint);
    }


    @Environment(EnvType.CLIENT)
    public record Factory<T extends ParticleEffect>(SpriteProvider sprites) implements ParticleFactory<T> {
        public Particle createParticle(T type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FireballParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }

}
