package net.superkat.explosiveenhancement.mixin;

//? if (>=1.21.2) {
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;
import net.superkat.explosiveenhancement.ExplosiveEnhancementClient;
import net.superkat.explosiveenhancement.network.S2CExplosiveEnhancementParticles;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;

import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerWorld.class)
//?}
public class ServerWorldMixin {

    //? if (>=1.21.2) {

    @Shadow @Final private List<ServerPlayerEntity> players;

    @Inject(method = "createExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/explosion/ExplosionImpl;getKnockbackByPlayer()Ljava/util/Map;"))
    public void explosiveenhancement$scaleParticlesForSingleplayer(@Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, World.ExplosionSourceType explosionSourceType, ParticleEffect smallParticle, ParticleEffect largeParticle, RegistryEntry<SoundEvent> soundEvent, CallbackInfo ci, @Local(ordinal = 2) LocalRef<ParticleEffect> effect) {
        ServerWorld world = (ServerWorld)(Object)this;
        if (world.getServer().isSingleplayer() && this.players.size() == 1) {
            if(ExplosiveEnhancementClient.CONFIG.modEnabled && ExplosiveEnhancementClient.CONFIG.bypassPowerForSingleplayer) {
                ServerPlayNetworking.send(this.players.getFirst(), new S2CExplosiveEnhancementParticles(x, y, z, power, effect.get()));
                effect.set(ExplosiveEnhancement.NO_RENDER_PARTICLE);
            }
        }
    }

    //?}

}
