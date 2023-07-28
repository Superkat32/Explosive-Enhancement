package net.superkat.explosiveenhancement.mixin;

import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.superkat.explosiveenhancement.ExplosiveEnhancementClient;
import net.superkat.explosiveenhancement.api.ExplosiveApi;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.superkat.explosiveenhancement.ExplosiveEnhancement.LOGGER;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
	@Shadow @Final private float power;
	@Shadow @Final private double x;
	@Shadow @Final private double y;
	@Shadow @Final private double z;
	@Shadow @Final private World world;

	@Shadow public abstract boolean shouldDestroy();

	private boolean isUnderWater = false;
	@Inject(method = "affectWorld(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"), cancellable = true)
	public void affectWorld(boolean particles, CallbackInfo ci) {
		var config = ExplosiveEnhancementClient.getConfig();
		if (config.modEnabled) {
			if (config.debugLogs) {
				LOGGER.info("affectWorld has been called!");
			}
			BlockPos pos = BlockPos.ofFloored(x, y, z);
			if(world.getFluidState(pos).isIn(FluidTags.WATER) && config.underwaterExplosions) {
				//If underwater
				isUnderWater = true;
				if (config.debugLogs) {
					LOGGER.info("particle is underwater!");
				}
			}
			ExplosiveApi.spawnParticles(world, x, y, z, power, isUnderWater, this.shouldDestroy());
			ci.cancel();
		}
	}
}