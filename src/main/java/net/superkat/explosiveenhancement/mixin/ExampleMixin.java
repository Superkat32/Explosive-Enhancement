package net.superkat.explosiveenhancement.mixin;

import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public class ExampleMixin {
	@Shadow @Final private Random random;
	private final double x;
	private final double y;
	private final double z;
	private final World world;

	public ExampleMixin(World world, double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
	}


	@Inject(method = "affectWorld(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V", shift =  At.Shift.AFTER))
	public void affectWorld(boolean particles, CallbackInfo ci) {
		ExplosiveEnhancement.LOGGER.info("affectWorld has been called!");
		if (particles) {
			ExplosiveEnhancement.LOGGER.info("particle has been shown!");
			//Boom particle
			this.world.addParticle(ExplosiveEnhancement.BOOM, this.x, this.y, this.z, 0, 0, 0);
			//Big explosion particle
			this.world.addParticle(ExplosiveEnhancement.BIG_EXPLOSION, this.x, this.y, this.z, 0, 0, 0);
			//Smoke linger particles
			this.world.addParticle(ExplosiveEnhancement.LINGER, this.x, this.y, this.z, 0, 0.1, 0);
			this.world.addParticle(ExplosiveEnhancement.LINGER, this.x, this.y, this.z, 0, 0.3, 0);
			this.world.addParticle(ExplosiveEnhancement.LINGER, this.x, this.y, this.z, 0.15, 0.3, 0);
			this.world.addParticle(ExplosiveEnhancement.LINGER, this.x, this.y, this.z, 0, 0.3, 0.15);
//			this.world.addParticle(ExplosiveEnhancement.LINGER, this.x, this.y, this.z, 0.15, 0.3, 0.15);
			this.world.addParticle(ExplosiveEnhancement.LINGER, this.x, this.y, this.z, -0.15, 0.3, 0);
			this.world.addParticle(ExplosiveEnhancement.LINGER, this.x, this.y, this.z, 0, 0.3, -0.15);
//			this.world.addParticle(ExplosiveEnhancement.LINGER, this.x, this.y, this.z, -0.15, 0.3, -0.15);
			//Spark particles

		}
	}
}
