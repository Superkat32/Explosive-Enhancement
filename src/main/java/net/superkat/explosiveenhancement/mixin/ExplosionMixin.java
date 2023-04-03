package net.superkat.explosiveenhancement.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.superkat.explosiveenhancement.ExplosiveConfig;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.superkat.explosiveenhancement.ExplosiveEnhancement.LOGGER;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
	@Shadow @Final private Random random;


	@Shadow public abstract DamageSource getDamageSource();

    @Shadow @Final private ObjectArrayList<BlockPos> affectedBlocks;
    private boolean isUnderWater = false;
    private final float power;
	private final DamageSource damageSource;

    public ExplosionMixin(float power, @Nullable DamageSource damageSource) {
        this.power = power;
		this.damageSource = damageSource;
    }

//    @Inject(method = "affectWorld(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isAir()Z"))
//	public void isInAir(boolean particles, CallbackInfo ci) {
//		if(this.power > 2.0F) {
//        }
//		LOGGER.info(String.valueOf(this.power));
//	}

	@Redirect(method = "affectWorld(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
	public void affectWorld(World world, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		LOGGER.info("affectWorld has been called!");
		if(this.affectedBlocks.isEmpty()) {
            LOGGER.info("E");
		} else {
            LOGGER.info("A");
		}
		if(ExplosiveConfig.modEnabled) {
//			if (particles) {
			if(!isUnderWater) {
				LOGGER.info("particle has been shown!");
				if(ExplosiveConfig.showBoom) {
					//Boom particle
//					this.world.addParticle(ExplosiveEnhancement.BOOM, this.x, this.y, this.z, 0, 0, 0);
					world.addParticle(ExplosiveEnhancement.BOOM, x, y, z, 0, 0, 0);
				}
				if(ExplosiveConfig.showBigExplosion) {
					//Big explosion particle
//					this.world.addParticle(ExplosiveEnhancement.BIG_EXPLOSION, this.x, this.y, this.z, 0, 0, 0);
					world.addParticle(ExplosiveEnhancement.BIG_EXPLOSION, x, y + 0.5, z, 0, 0, 0);
				}
				if(ExplosiveConfig.showLingerParticles) {
					//Smoke linger particles
					//I'm aware DRY is a thing, but I couldn't figure out any other way to get even a similar effect that I was happy with, so unfortunately, this will have to do.
					world.addParticle(ExplosiveEnhancement.LINGER, x, y, z, 0, 0.15, 0);
					world.addParticle(ExplosiveEnhancement.LINGER, x, y, z, 0, 0.4, 0);
					world.addParticle(ExplosiveEnhancement.LINGER, x, y, z, 0.15, 0.4, 0);
					world.addParticle(ExplosiveEnhancement.LINGER, x, y, z, 0, 0.4, 0.15);
		//			world.addParticle(ExplosiveEnhancement.LINGER, x, y, z, 0.15, 0.3, 0.15);
					world.addParticle(ExplosiveEnhancement.LINGER, x, y, z, -0.15, 0.4, 0);
					world.addParticle(ExplosiveEnhancement.LINGER, x, y, z, 0, 0.4, -0.15);
		//			world.addParticle(ExplosiveEnhancement.LINGER, x, y, z, -0.15, 0.3, -0.15);
				}
				if(ExplosiveConfig.showDefaultExplosion) {
					world.addParticle(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 1.0, 0.0, 0.0);
				}
			} else {
				LOGGER.info("IS UNDERWATER!");
			}
//			}
		} else {
			world.addParticle(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 1.0, 0.0, 0.0);
		}
	}
}
