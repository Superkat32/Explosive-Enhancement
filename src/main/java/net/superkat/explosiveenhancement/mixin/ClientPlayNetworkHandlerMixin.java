package net.superkat.explosiveenhancement.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.superkat.explosiveenhancement.ExplosiveEnhancementClient;
import net.superkat.explosiveenhancement.api.ExplosionParticleType;
import net.superkat.explosiveenhancement.api.ExplosiveApi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.CONFIG;
import static net.superkat.explosiveenhancement.ExplosiveEnhancement.LOGGER;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    //? if(>=1.21.3) {

    @Shadow public abstract ClientWorld getWorld();

    @Inject(method = "onExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"), cancellable = true)
    public void explosiveenhancement$spawnExplosiveParticles(ExplosionS2CPacket packet, CallbackInfo ci) {
        if (CONFIG.modEnabled) {
			if (CONFIG.debugLogs) { LOGGER.info("[Explosive Enhancement]: affectWorld has been called!"); }

            World world = this.getWorld();
            Vec3d pos = packet.center();

			ExplosionParticleType explosionParticleType = ExplosiveApi.determineParticleType(world, pos, packet.explosionParticle());
			if(explosionParticleType != ExplosionParticleType.WIND) { //allows normal wind particles to be shown
                float power = ExplosiveApi.getPowerFromExplosionPacket(world, packet);
//                float power = 0;
//                boolean powerFromKb = false;
//                if(packet.playerKnockback().isPresent()) {
//                    power = ExplosiveApi.getPowerFromKnockback(world, pos, MinecraftClient.getInstance().player, packet.playerKnockback().get());
//                    powerFromKb = true;
//                    if(CONFIG.debugLogs) { LOGGER.info("[Explosive Enhancement]: power from knockback: {}", power); }
//                }
//
//                if(!powerFromKb || Float.isNaN(power)) {
//                    //change to api method
//                    power = packet.explosionParticle() == ParticleTypes.EXPLOSION_EMITTER ? 4f : 2f;
//                }
                ExplosiveApi.spawnParticles(world, pos.getX(), pos.getY(), pos.getZ(), power, explosionParticleType, true);
                ci.cancel();
			}
		}
    }

    //?}
}
