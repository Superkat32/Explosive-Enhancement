package net.superkat.explosiveenhancement.mixin;

//? if (<=1.21.1) {

/*import net.minecraft.client.particle.Particle;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.superkat.explosiveenhancement.api.ExplosionParticleType;
import net.superkat.explosiveenhancement.api.ExplosiveApi;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.superkat.explosiveenhancement.ExplosiveEnhancement.LOGGER;
import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.CONFIG;

*///?}

import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
	//The client-side explosion handling changed in 1.21.2/3, requiring a new mixin

	//? if (<=1.21.1) {
	/*@Shadow @Final private World world;
	@Shadow @Final private double x;
	@Shadow @Final private double y;
	@Shadow @Final private double z;
	@Shadow @Final private float power;
	//? if (>=1.20.0) {
	@Shadow @Final private ParticleEffect particle;
	@Shadow @Final private ParticleEffect emitterParticle;
	//?}
	//? if (>=1.19.3) {
	@Shadow public abstract boolean shouldDestroy();
	//?}
	//? if (1.19.2) {
	/^@Shadow @Final private Explosion.DestructionType destructionType;
	^///?}

	@Inject(method = "affectWorld(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"), cancellable = true)
	public void explosiveenhancement$spawnExplosiveParticles(boolean particles, CallbackInfo ci) {
		if (CONFIG.modEnabled && particles) {
			if (CONFIG.debugLogs) { LOGGER.info("[Explosive Enhancement]: affectWorld has been called!"); }

			//? if (<=1.19.4) {
			/^ParticleEffect particle = ParticleTypes.EXPLOSION;
			ParticleEffect emitterParticle = ParticleTypes.EXPLOSION_EMITTER;
			^///?}

			ExplosionParticleType explosionParticleType = ExplosiveApi.determineParticleType(world, x, y, z, particle, emitterParticle);
			if(explosionParticleType != ExplosionParticleType.WIND) { //allows normal wind particles to be shown

				boolean destroyedBlocks =
						//? if (>=1.19.3) {
						this.shouldDestroy();
						//?} else {
						/^this.destructionType != Explosion.DestructionType.NONE;
						^///?}

				ExplosiveApi.spawnParticles(world, x, y, z, power, explosionParticleType, destroyedBlocks);
				ci.cancel();
			}
		}
	}
	*///?}
}