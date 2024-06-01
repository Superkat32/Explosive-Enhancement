package net.superkat.explosiveenhancement.mixin;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.superkat.explosiveenhancement.api.ExplosionParticleType;
import net.superkat.explosiveenhancement.api.ExplosiveApi;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.superkat.explosiveenhancement.ExplosiveEnhancement.LOGGER;
import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.CONFIG;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
	@Shadow @Final private World world;
	@Shadow @Final private double x;
	@Shadow @Final private double y;
	@Shadow @Final private double z;
	@Shadow @Final private float power;
	@Shadow @Final private ParticleEffect particle;
	@Shadow @Final private ParticleEffect emitterParticle;
	@Shadow public abstract boolean shouldDestroy();

	@Inject(method = "affectWorld(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"), cancellable = true)
	public void explosiveenhancement$spawnExplosiveParticles(boolean particles, CallbackInfo ci) {
		if (CONFIG.modEnabled && particles) {
			if (CONFIG.debugLogs) { LOGGER.info("[Explosive Enhancement]: affectWorld has been called!"); }

			ExplosionParticleType explosionParticleType = ExplosiveApi.determineParticleType(world, x, y, z, particle, emitterParticle);
			if(explosionParticleType != ExplosionParticleType.WIND) { //allows normal wind particles to be shown until I add special effect
				ExplosiveApi.spawnParticles(world, x, y, z, power, explosionParticleType, this.shouldDestroy());
				ci.cancel();
			}
		}
	}
}