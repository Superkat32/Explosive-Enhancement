package net.superkat.explosiveenhancement;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.level.ParticleStatus;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerExplosion;
import net.minecraft.world.phys.Vec3;
import net.superkat.explosiveenhancement.api.ExplosionParticleType;
import net.superkat.explosiveenhancement.particles.normal.BlastWaveParticleEffect;
import net.superkat.explosiveenhancement.particles.normal.FireballParticleEffect;
import net.superkat.explosiveenhancement.particles.normal.SmokeParticleEffect;

import static net.superkat.explosiveenhancement.ExplosiveEnhancement.LOGGER;
import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.CONFIG;

public class ExplosiveHandler {

    public static void spawnParticles(Level world, double x, double y, double z, float power, ExplosionParticleType explosionParticleType, boolean didDestroyBlocks, boolean isImportant) {
        if(!CONFIG.modEnabled) return;

        logDebugInfo();

        switch(explosionParticleType) {
            case NORMAL -> spawnExplosionParticles(world, x, y, z, power, didDestroyBlocks, isImportant);
            case WATER -> spawnUnderwaterExplosionParticles(world, x, y, z, power, didDestroyBlocks, isImportant);
            case WIND -> spawnWindExplosionParticles(world, x, y, z, power, didDestroyBlocks, isImportant); //unused for now
        }

        if (CONFIG.debugLogs) { LOGGER.info("ExplosiveHandler finished!"); }
    }

    private static void logDebugInfo() {
        if (!CONFIG.debugLogs) return;

        LOGGER.info("ExplosiveHandler has been called!");
        LOGGER.info("Explosive Enhancement Enabled: {}", CONFIG.modEnabled);

        ParticleStatus particlesMode = Minecraft.getInstance().options.particles().get();
        LOGGER.info("Minecraft particle settings: {}", particlesMode);

        if (particlesMode == ParticleStatus.MINIMAL) {
            LOGGER.warn("[Explosive Enhancement]: Minecraft's particle settings is set to Minimal! This means that no explosion particles will be shown.");
        } else if (particlesMode == ParticleStatus.DECREASED) {
            LOGGER.warn("[Explosive Enhancement]: Minecraft's particle settings is set to Decreased! This means that some explosions particles may not always be shown.");
        }
    }

    public static ExplosionParticleType determineParticleType(Level world, double x, double y, double z, ParticleOptions particle, ParticleOptions emitterParticle) {
        // TODO - Wind takes priority over water for now, but water should take priority over wind in the future
        if (particlesAreWindGust(particle, emitterParticle)) {
            return ExplosionParticleType.WIND;
        } else if(CONFIG.underwaterExplosions && blockIsInWater(world, x, y, z)) {
            return ExplosionParticleType.WATER;
        } else {
            return ExplosionParticleType.NORMAL;
        }
    }

    public static ExplosionParticleType determineParticleType(Level world, Vec3 pos, ParticleOptions particle) {
        if (particle == ParticleTypes.GUST_EMITTER_SMALL || particle == ParticleTypes.GUST_EMITTER_LARGE) {
            return ExplosionParticleType.WIND;
        } else if(CONFIG.underwaterExplosions && blockIsInWater(world, pos.x(), pos.y(), pos.z())) {
            return ExplosionParticleType.WATER;
        } else {
            return ExplosionParticleType.NORMAL;
        }
    }

    /**
     * Checks if the coordinates are underwater.
     *
     * @param world The world to check in.
     * @param x The x coordinate to check.
     * @param y The y coordinate to check.
     * @param z The z coordinate to check.
     * @return If the given coordinates are underwater or not.
     */
    public static boolean blockIsInWater(Level world, double x, double y, double z) {
        BlockPos pos = BlockPos.containing(x, y, z);
        return CONFIG.underwaterExplosions && world.getFluidState(pos).is(FluidTags.WATER);
    }

    /**
     * Checks if the particles are Minecraft's wind gust particles.
     *
     * @param particle The particle if the explosion power is small.
     * @param emitterParticle The particle if the explosion power is high.
     * @return If the particles are Minecraft's wind gust particles.
     *
     * @see ParticleTypes#GUST_EMITTER_SMALL
     * @see ParticleTypes#GUST_EMITTER_LARGE
     */
    public static boolean particlesAreWindGust(ParticleOptions particle, ParticleOptions emitterParticle) {
        return particle == ParticleTypes.GUST_EMITTER_SMALL && emitterParticle == ParticleTypes.GUST_EMITTER_LARGE;
    }

    public static boolean particlesAreEmitter(ParticleOptions particle) {
        return particle == ParticleTypes.EXPLOSION_EMITTER;
    }

    private static float getPowerFromParticle(ParticleOptions particle) {
        float power;
        boolean emitter = particlesAreEmitter(particle);
        if (emitter) {
            power = 4f;
            if(CONFIG.extraPower) { power += CONFIG.bigExtraPower; }
        } else {
            power = 1f;
            if(CONFIG.extraPower) { power += CONFIG.smallExtraPower; }
        }
        return power;
    }

    public static float getPowerFromExplosionPacket(Level world, ClientboundExplodePacket packet) {
        return packet.radius(); // Thank you, 1.21.9 :)
//        float power = 0;
//        ParticleEffect particle = packet.explosionParticle();
//
//        if(CONFIG.attemptPowerKnockbackCalc && packet.playerKnockback().isPresent()) {
//            power = ExplosiveApi.getPowerFromKnockback(world, packet.center(), MinecraftClient.getInstance().player, packet.playerKnockback().get());
//        }
//
//        if(Float.isNaN(power) || power == 0) {
//            power = getPowerFromParticle(particle);
//        }
//        return power;
    }

    public static float attemptDeterminePowerFromKnockback(Level world, Vec3 explosionPos, LivingEntity entity, Vec3 knockback) {
        double e = entity.getX() - explosionPos.x();
        double g = entity.getEyeY() - explosionPos.y();
        double h = entity.getZ() - explosionPos.z();
        double o = Math.sqrt(e * e + g * g + h * h);
        if(o != 0.0) {
            float dist = (float) Math.sqrt(entity.distanceToSqr(explosionPos));

            float kbModifier = 1f;
            float damageRecieved = ServerExplosion.getSeenPercent(explosionPos, entity);
            float powerX = powerCalc((float) entity.getX(), (float) knockback.x(), (float) explosionPos.x(), dist, (float) o, kbModifier, damageRecieved);
            float powerY = powerCalc((float) entity.getEyeY(), (float) knockback.y(), (float) explosionPos.y(), dist, (float) o, kbModifier, damageRecieved);
            float powerZ = powerCalc((float) entity.getZ(), (float) knockback.z(), (float) explosionPos.z(), dist, (float) o, kbModifier, damageRecieved);

            float avgPower = (powerX + powerY + powerZ) / 3f;
            return avgPower;
        }
        return 0;
    }

    /**
     * The calculation to reverse engineer the power variable based on knockback and positioning. It isn't the most perfect thing, but it is better than nothing.
     * <br> <br>
     * This method takes in x, y, or z coordinates to allow you to average them out.
     * <br> <br>
     * The parentheses on this calculation are so sensitive that messing up just one explodes the entire thing.
     * @param entityPos The entity's x/eyeY/z pos
     * @param knockbackAmount The knockback x/y/z amount from the explosion
     * @param explosionPos The explosions x/y/z pos
     * @param dist sqrt(player#distanceSquared(explosionPos))
     * @param distO I guess just player#distanceSquared(explosionPos) but not because it was inaccurate?
     * @param knockbackModifier The knockback modifier for the entity
     * @param receivedDamage The received damage from the explosion
     * @return A rough calculation for the power.
     */
    public static float powerCalc(float entityPos, float knockbackAmount, float explosionPos, float dist, float distO, float knockbackModifier, float receivedDamage) {
        // algebra classes coming in handy for once
        return (dist / ( -((knockbackAmount * distO) / ( (entityPos - explosionPos) * knockbackModifier * receivedDamage)) + 1) ) / 2;
    }

    public static void spawnExplosionParticles(Level world, double x, double y, double z, float power, boolean didDestroyBlocks, boolean isImportant) {
        power = CONFIG.dynamicSize ? power : 4;
        y = CONFIG.attemptBetterSmallExplosions && power == 1 ? y + CONFIG.smallExplosionYOffset : y;
        isImportant |= CONFIG.alwaysShow;

        float blastwavePower = power * 1.75f;
        float fireballPower = power * 1.25f;
        boolean emissive = CONFIG.emissiveExplosion;

        if(CONFIG.showBlastWave) {
            addParticle(world, new BlastWaveParticleEffect(false, blastwavePower, emissive), isImportant, x, y + 0.5, z, 0, 0, 0);
        }

        if(CONFIG.showFireball || CONFIG.showSparks) {
            addParticle(world, new FireballParticleEffect(false, fireballPower, emissive, CONFIG.showSparks, !CONFIG.showFireball, isImportant), isImportant, x, y + 0.5, z, 0, 0, 0);
        }

        if(CONFIG.showMushroomCloud) {
            spawnMushroomCloud(world, x, y, z, power, isImportant);
        }
    }

    public static void spawnUnderwaterExplosionParticles(Level world, double x, double y, double z, float power, boolean didDestroyBlocks, boolean isImportant) {
        power = CONFIG.dynamicUnderwater ? power : 4;
        y = CONFIG.attemptBetterSmallExplosions && power == 1 ? y + CONFIG.smallExplosionYOffset : y;
        isImportant = isImportant || CONFIG.alwaysShow;

        float blastwavePower = power * 1.75f;
        float fireballPower = power * 1.25f;
        boolean emissive = CONFIG.emissiveWaterExplosion;

        if(CONFIG.showUnderwaterBlastWave) {
            addParticle(world, new BlastWaveParticleEffect(true, blastwavePower, emissive), isImportant, x, y + 0.5, z, 0, 0, 0);
        }

        if(CONFIG.showShockwave || CONFIG.showUnderwaterSparks) {
            addParticle(world, new FireballParticleEffect(true, fireballPower, emissive, CONFIG.showUnderwaterSparks, !CONFIG.showShockwave, isImportant), isImportant, x, y + 0.5, z, 0, 0, 0);
        }

        spawnBubbles(world, x, y, z, isImportant);
    }

    private static void spawnMushroomCloud(Level world, double x, double y, double z, float power, boolean isImportant) {
        boolean emissive = CONFIG.emissiveExplosion;
        float reducedPower = power * 0.4f;
        float xzVel = 0.15f * power * 0.5f;
        float velY = reducedPower / 1.85f;
        // I'm aware DRY is a thing, but I couldn't figure out any other way to get even
        // a similar effect that I was happy with, so unfortunately, this will have to do.
        addParticle(world, new SmokeParticleEffect(power, emissive), isImportant, x, y, z, 0, (power * 0.25) / 1.85f, 0);
        addParticle(world, new SmokeParticleEffect(power, emissive), isImportant, x, y, z, 0, velY, 0);
        addParticle(world, new SmokeParticleEffect(power, emissive), isImportant, x, y, z, xzVel, velY, 0);
        addParticle(world, new SmokeParticleEffect(power, emissive), isImportant, x, y, z, -xzVel, velY, 0);
        addParticle(world, new SmokeParticleEffect(power, emissive), isImportant, x, y, z, 0, velY, xzVel);
        addParticle(world, new SmokeParticleEffect(power, emissive), isImportant, x, y, z, 0, velY, -xzVel);
    }

    private static void spawnBubbles(Level world, double x, double y, double z, boolean isImportant) {
        for (int i = 0; i < CONFIG.bubbleAmount; i++) {
            double velX = world.getRandom().nextIntBetweenInclusive(1, 7) * 0.3 * world.getRandom().nextIntBetweenInclusive(-1, 1);
            double velY = world.getRandom().nextIntBetweenInclusive(1, 10) * 0.1;
            double velZ = world.getRandom().nextIntBetweenInclusive(1, 7) * 0.3 * world.getRandom().nextIntBetweenInclusive(-1, 1);
            addParticle(world, ExplosiveEnhancement.BUBBLE, isImportant, x, y, z, velX, velY, velZ);
        }
    }

    public static void spawnWindExplosionParticles(Level world, double x, double y, double z, float power, boolean didDestroyBlocks, boolean isImportant) {
        // This should never be called until I add a special wind effect
        spawnVanillaParticles(world, x, y, z, power, didDestroyBlocks, isImportant, true);
    }

    @Deprecated
    private static void spawnVanillaParticles(Level world, double x, double y, double z, float power, boolean didDestroyBlocks, boolean isImportant, boolean wind) {
        ParticleOptions particle = wind ? ParticleTypes.GUST_EMITTER_SMALL : ParticleTypes.EXPLOSION;
        ParticleOptions emitter = wind ? ParticleTypes.GUST_EMITTER_LARGE : ParticleTypes.EXPLOSION_EMITTER;
        addParticle(world, power >= 2.0f && didDestroyBlocks ? emitter : particle, isImportant, x, y, z, 1.0, 0.0, 0.0);
    }

    private static void addParticle(Level world, ParticleOptions particle, boolean isImportant, double x, double y, double z, double velX, double velY, double velZ) {
        world.addParticle(particle, isImportant, isImportant, x, y, z, velX, velY, velZ);
    }
}
