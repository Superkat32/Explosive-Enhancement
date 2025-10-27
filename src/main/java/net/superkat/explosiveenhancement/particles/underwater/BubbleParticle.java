package net.superkat.explosiveenhancement.particles.underwater;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class BubbleParticle extends BillboardParticle {
    public int startingAirTick = 0;
    public int extraTimeBeforePopping = this.random.nextBetween(1, 10);
    public boolean startAirTick = true;

    public BubbleParticle(ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider spriteProvider, Random random) {
        super(clientWorld, x, y, z, velX, velY, velZ, spriteProvider.getSprite(random));

        // why did I do this :sob:
        this.age = this.maxAge;
        this.maxAge = 120 + this.random.nextBetween(0, 40);
        this.scale *= this.random.nextFloat() * 1.5F + 0.2F;

        this.velocityX = velX / this.random.nextBetween(1, 5);
        this.velocityY = velY / this.random.nextBetween((int) 1.4, (int) 4.5);
        this.velocityZ = velZ /  this.random.nextBetween(1, 5);

        this.setBoundingBoxSpacing(0.02F, 0.02F);
    }

    public void tick() {
        this.lastX = this.x;
        this.lastY = this.y;
        this.lastZ = this.z;
        if (this.maxAge-- <= 0) { // count up not down !!!! :sob:
            this.markDead();
            this.world.addParticleClient(ParticleTypes.BUBBLE_POP, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
            return;
        }

        this.velocityY += 0.002;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityY *= 0.8200000238418579;
        if(this.maxAge >= this.age * 0.97) {
            // where on earth did I get these numbers from??
            this.velocityX *= 0.8300000238418579;
            this.velocityZ *= 0.8300000238418579;
        } else {
            this.velocityX *= 0.6200000238418579;
            this.velocityZ *= 0.6200000238418579;
        }

        if (!this.world.getFluidState(new BlockPos((int) this.x, (int) this.y, (int) this.z)).isIn(FluidTags.WATER)) {
            this.velocityY -= 0.002;
            if(startAirTick) {
                startingAirTick = this.maxAge;
                this.velocityY = 0;
                startAirTick = false;
            } else if(this.maxAge == startingAirTick - extraTimeBeforePopping) {
                    this.markDead();
                    this.world.addParticleClient(ParticleTypes.BUBBLE_POP, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
                    this.world.playSoundClient(this.x, this.y, this.z, SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.AMBIENT, 0.5f, 1f, false);
                }
            }
        }

    @Override
    protected RenderType getRenderType() {
        return RenderType.PARTICLE_ATLAS_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public record Factory<T extends ParticleEffect>(SpriteProvider sprites) implements ParticleFactory<T> {
        @Override
        public @NotNull Particle createParticle(T parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new BubbleParticle(world, x, y, z, velocityX, velocityY, velocityZ, sprites, random);
        }
    }
}