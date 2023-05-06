package net.superkat.explosiveenhancement.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.superkat.explosiveenhancement.ExplosiveConfig;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.superkat.explosiveenhancement.ExplosiveEnhancement.LOGGER;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
	@Shadow @Final private Random random;
    @Shadow @Final private ObjectArrayList<BlockPos> affectedBlocks;
	private boolean isUnderWater = false;

	@Redirect(method = "affectWorld(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
	public void affectWorld(World world, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		if(ExplosiveConfig.debugLogs) {
			LOGGER.info("affectWorld has been called!");
		}
		BlockPos pos = new BlockPos(x, y, z);
		if(world.getFluidState(pos).isIn(FluidTags.WATER) && ExplosiveConfig.underwaterExplosions) {
			//If underwater
			isUnderWater = true;
			if(ExplosiveConfig.debugLogs) {
				LOGGER.info("Particle is underwater!");
			}
		}
		if(ExplosiveConfig.modEnabled) {
			if(!isUnderWater) {
				if(ExplosiveConfig.debugLogs) {
					LOGGER.info("Particle is being shown!");
				}
				if(ExplosiveConfig.showBlastWave) {
					//Boom particle
//					this.world.addParticle(ExplosiveEnhancement.BOOM, this.x, this.y, this.z, 0, 0, 0);
					world.addParticle(ExplosiveEnhancement.BLASTWAVE, x, y, z, 0, 0, 0);
				}
				if(ExplosiveConfig.showFireball) {
					//Big explosion particle
//					this.world.addParticle(ExplosiveEnhancement.BIG_EXPLOSION, this.x, this.y, this.z, 0, 0, 0);
					world.addParticle(ExplosiveEnhancement.FIREBALL, x, y + 0.5, z, 0, 0, 0);
				}
				if(ExplosiveConfig.showMushroomCloud) {
					//Smoke linger particles
					//I'm aware DRY is a thing, but I couldn't figure out any other way to get even a similar effect that I was happy with, so unfortunately, this will have to do.
					world.addParticle(ExplosiveEnhancement.SMOKE, x, y, z, 0, 0.15, 0);
					world.addParticle(ExplosiveEnhancement.SMOKE, x, y, z, 0, 0.4, 0);
					world.addParticle(ExplosiveEnhancement.SMOKE, x, y, z, 0.15, 0.4, 0);
					world.addParticle(ExplosiveEnhancement.SMOKE, x, y, z, 0, 0.4, 0.15);
		//			world.addParticle(ExplosiveEnhancement.LINGER, x, y, z, 0.15, 0.3, 0.15);
					world.addParticle(ExplosiveEnhancement.SMOKE, x, y, z, -0.15, 0.4, 0);
					world.addParticle(ExplosiveEnhancement.SMOKE, x, y, z, 0, 0.4, -0.15);
		//			world.addParticle(ExplosiveEnhancement.LINGER, x, y, z, -0.15, 0.3, -0.15);
				}
				if(ExplosiveConfig.showDefaultExplosion) {
					world.addParticle(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 1.0, 0.0, 0.0);
				}
//				world.addParticle(ExplosiveEnhancement.SPARKS, x, y + 0.5, z, 0, 0, 0);
			} else {
				if(ExplosiveConfig.showShockwave) {
					world.addParticle(ExplosiveEnhancement.SHOCKWAVE, x, y + 0.5, z, 0, 0, 0);
				}
				if(ExplosiveConfig.showUnderwaterBlastWave) {
					world.addParticle(ExplosiveEnhancement.UNDERWATERBLASTWAVE, x, y + 0.5, z, 0, 0, 0);
				}
				for(int total = ExplosiveConfig.bubbleAmount; total >= 1; total--) {
					world.addParticle(ExplosiveEnhancement.BUBBLE, x, y, z, this.random.nextBetween(1, 7) * 0.3 * this.random.nextBetween(-1, 1), this.random.nextBetween(1, 10) * 0.1, this.random.nextBetween(1, 7) * 0.3 * this.random.nextBetween(-1, 1));
				}
				if(ExplosiveConfig.showDefaultExplosionUnderwater) {
					world.addParticle(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 1.0, 0.0, 0.0);
				}
			}
		} else {
			world.addParticle(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 1.0, 0.0, 0.0);
		}
	}
}
