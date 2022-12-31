package net.superkat.explosiveenhancement.mixin;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
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

@Mixin(Explosion.class)
public class ExampleMixin {
	@Shadow @Final private Random random;
//	private final double x;
//	private final double y;
//	private final double z;
//	private final World world;

//	public ExampleMixin(World world, double x, double y, double z) {
//		this.x = x;
//		this.y = y;
//		this.z = z;
////		this.world = world;
//	}


	@Redirect(method = "affectWorld(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
	public void affectWorld(World world, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		ExplosiveEnhancement.LOGGER.info("affectWorld has been called!");
		if(ExplosiveConfig.modEnabled) {
//			if (particles) {
				ExplosiveEnhancement.LOGGER.info("particle has been shown!");
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
//			}
		}
	}
}
