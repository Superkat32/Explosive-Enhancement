package net.superkat.explosiveenhancement.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.particle.BlockParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.superkat.explosiveenhancement.api.ExplosionParticleType;
import net.superkat.explosiveenhancement.api.ExplosiveApi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import static net.superkat.explosiveenhancement.ExplosiveEnhancement.LOGGER;
import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.CONFIG;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Shadow public abstract ClientWorld getWorld();

    @WrapOperation(method = "onExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;addParticleClient(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
    public void explosiveenhancement$spawnExplosiveParticles(ClientWorld instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Void> original, @Local(argsOnly = true) ExplosionS2CPacket packet) {
        boolean callOriginal = true;
        if(CONFIG.modEnabled) {
            if (CONFIG.debugLogs) { LOGGER.info("[Explosive Enhancement]: affectWorld has been called!"); }

            World world = this.getWorld();
            Vec3d pos = packet.center();
            ParticleEffect particle = packet.explosionParticle();
            float power = packet.radius();

            ExplosionParticleType explosionParticleType = ExplosiveApi.determineParticleType(world, pos, particle);
            if(explosionParticleType != ExplosionParticleType.WIND) { // allows normal wind particles to be shown
                ExplosiveApi.spawnParticles(world, pos.getX(), pos.getY(), pos.getZ(), power, explosionParticleType);
                boolean showVanillaParticles =
                        (CONFIG.showDefaultExplosion && explosionParticleType == ExplosionParticleType.NORMAL)
                                || (CONFIG.showDefaultExplosionUnderwater && explosionParticleType == ExplosionParticleType.WATER);

                if(!showVanillaParticles) {
                    callOriginal = false;
                }
            }
        }

        if(callOriginal) {
            original.call(instance, parameters, x, y, z, velocityX, velocityY, velocityZ);
        }
    }

    @WrapOperation(method = "onExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;addBlockParticleEffects(Lnet/minecraft/util/math/Vec3d;FILnet/minecraft/util/collection/Pool;)V"))
    public void explosiveenhancement$replaceVanillaSmokeParticles(ClientWorld instance, Vec3d center, float radius, int blockCount, Pool<BlockParticleEffect> particles, Operation<Void> original, @Local(argsOnly = true) ExplosionS2CPacket packet) {
        boolean callOriginal = true;
        if(CONFIG.modEnabled) {
            ExplosionParticleType explosionParticleType = ExplosiveApi.determineParticleType(instance, center, packet.explosionParticle());

            boolean showVanillaParticles =
                    (CONFIG.showDefaultSmoke && explosionParticleType == ExplosionParticleType.NORMAL)
                            || (CONFIG.showDefaultSmokeUnderwater && explosionParticleType == ExplosionParticleType.WATER);

            if(!showVanillaParticles) {
                callOriginal = false;
            }
        }

        if(callOriginal) {
            original.call(instance, center, radius, blockCount, particles);
        }
    }
}
