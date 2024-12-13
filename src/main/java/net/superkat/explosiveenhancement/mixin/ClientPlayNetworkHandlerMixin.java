package net.superkat.explosiveenhancement.mixin;

//? if (>=1.21.2) {
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;
import net.superkat.explosiveenhancement.api.ExplosionParticleType;
import net.superkat.explosiveenhancement.api.ExplosiveApi;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static net.superkat.explosiveenhancement.ExplosiveEnhancement.LOGGER;
import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.CONFIG;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayNetworkHandler.class)
//?}
public abstract class ClientPlayNetworkHandlerMixin {
    //? if (>=1.21.2) {

    @Shadow public abstract ClientWorld getWorld();

    @Inject(method = "onExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"), cancellable = true)
    public void explosiveenhancement$spawnExplosiveParticles(ExplosionS2CPacket packet, CallbackInfo ci) {
        if (CONFIG.modEnabled) {
			if (CONFIG.debugLogs) { LOGGER.info("[Explosive Enhancement]: affectWorld has been called!"); }

            World world = this.getWorld();
            Vec3d pos = packet.center();

            ParticleEffect particle = packet.explosionParticle();
            if(particle == ExplosiveEnhancement.NO_RENDER_PARTICLE && CONFIG.bypassPowerForSingleplayer) {
                return;
            }

			ExplosionParticleType explosionParticleType = ExplosiveApi.determineParticleType(world, pos, particle);
			if(explosionParticleType != ExplosionParticleType.WIND) { //allows normal wind particles to be shown
                float power = ExplosiveApi.getPowerFromExplosionPacket(world, packet);
                ExplosiveApi.spawnParticles(world, pos.getX(), pos.getY(), pos.getZ(), power, explosionParticleType);
                boolean showVanillaParticles =
                        (CONFIG.showDefaultExplosion && explosionParticleType == ExplosionParticleType.NORMAL)
                                || (CONFIG.showDefaultExplosionUnderwater && explosionParticleType == ExplosionParticleType.WATER);

                if(!showVanillaParticles) {
                    ci.cancel();
                }
			}
		}
    }

    //?}
}
