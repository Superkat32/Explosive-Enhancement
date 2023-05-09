//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.superkat.explosiveenhancement.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class BubbleParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    int startingAirTick = 0;
    int extraTimeBeforePopping = this.random.nextBetween(1, 10);
    boolean startAirTick = true;
    BubbleParticle(ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z);
        this.spriteProvider = spriteProvider;
        this.setBoundingBoxSpacing(0.02F, 0.02F);
        this.scale *= this.random.nextFloat() * 1.5F + 0.2F;
        this.velocityX = velX / this.random.nextBetween(1, 5);
        this.velocityY = velY / this.random.nextBetween((int) 1.4, (int) 4.5);
        this.velocityZ = velZ /  this.random.nextBetween(1, 5);
        this.maxAge = 120 + this.random.nextBetween(0, 40);
        this.setSpriteForAge(spriteProvider);
    }

    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.maxAge-- <= 0) {
            this.markDead();
            this.world.addParticle(ParticleTypes.BUBBLE_POP, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
        } else {
            this.velocityY += 0.002;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityX *= 0.6500000238418579;
            this.velocityY *= 0.8500000238418579;
            this.velocityZ *= 0.6500000238418579;
            if (!this.world.getFluidState(new BlockPos(this.x, this.y, this.z)).isIn(FluidTags.WATER)) {
                this.velocityY -= 0.002;
                if(startAirTick) {
                    startingAirTick = this.maxAge;
                    this.velocityY = 0;
                    startAirTick = false;
                }
                if(!startAirTick) {
                    if(this.maxAge == startingAirTick - extraTimeBeforePopping) {
                        this.markDead();
                        this.world.addParticle(ParticleTypes.BUBBLE_POP, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
                        this.world.playSound(this.x, this.y, this.z, SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.AMBIENT, 0.5f, 1f, false);
                    }
                }
            }
        }
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            BubbleParticle bubbleParticle = new BubbleParticle(clientWorld, d, e, f, g, h, i, spriteProvider);
            bubbleParticle.setSprite(this.spriteProvider);
            return bubbleParticle;
        }
    }
}
