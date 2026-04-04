package net.superkat.explosiveenhancement.particles.underwater;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BubbleParticle extends SingleQuadParticle {
    public int startingAirTick = 0;
    public int extraTimeBeforePopping = this.random.nextIntBetweenInclusive(1, 10);
    public boolean startAirTick = true;

    public BubbleParticle(ClientLevel clientWorld, double x, double y, double z, double velX, double velY, double velZ, SpriteSet spriteProvider, RandomSource random) {
        super(clientWorld, x, y, z, velX, velY, velZ, spriteProvider.get(random));

        // why did I do this :sob:
        this.age = this.lifetime;
        this.lifetime = 120 + this.random.nextIntBetweenInclusive(0, 40);
        this.quadSize *= this.random.nextFloat() * 1.5F + 0.2F;

        this.xd = velX / this.random.nextIntBetweenInclusive(1, 5);
        this.yd = velY / this.random.nextIntBetweenInclusive((int) 1.4, (int) 4.5);
        this.zd = velZ /  this.random.nextIntBetweenInclusive(1, 5);

        this.setSize(0.02F, 0.02F);
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) { // count up not down !!!! :sob:
            this.remove();
            this.level.addParticle(ParticleTypes.BUBBLE_POP, this.x, this.y, this.z, this.xd, this.yd, this.zd);
            return;
        }

        this.yd += 0.002;
        this.move(this.xd, this.yd, this.zd);
        this.yd *= 0.8200000238418579;
        if(this.lifetime >= this.age * 0.97) {
            // where on earth did I get these numbers from??
            this.xd *= 0.8300000238418579;
            this.zd *= 0.8300000238418579;
        } else {
            this.xd *= 0.6200000238418579;
            this.zd *= 0.6200000238418579;
        }

        if (!this.level.getFluidState(new BlockPos((int) this.x, (int) this.y, (int) this.z)).is(FluidTags.WATER)) {
            this.yd -= 0.002;
            if(startAirTick) {
                startingAirTick = this.lifetime;
                this.yd = 0;
                startAirTick = false;
            } else if(this.lifetime == startingAirTick - extraTimeBeforePopping) {
                    this.remove();
                    this.level.addParticle(ParticleTypes.BUBBLE_POP, this.x, this.y, this.z, this.xd, this.yd, this.zd);
                    this.level.playLocalSound(this.x, this.y, this.z, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.AMBIENT, 0.5f, 1f, false);
                }
            }
        }

    @Override
    protected @NonNull Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public record Factory<T extends ParticleOptions>(SpriteSet sprites) implements ParticleProvider<T> {
        @Override
        public @NotNull Particle createParticle(T parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
            return new BubbleParticle(world, x, y, z, velocityX, velocityY, velocityZ, sprites, random);
        }
    }
}