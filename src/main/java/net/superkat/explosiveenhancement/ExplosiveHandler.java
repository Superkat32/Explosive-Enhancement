package net.superkat.explosiveenhancement;

import net.minecraft.client.MinecraftClient;
//? if (<=1.20) {
/*import net.minecraft.client.option.ParticlesMode;
*///?} else {
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.particle.ParticlesMode;
//?}
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionImpl;
import net.superkat.explosiveenhancement.api.ExplosionParticleType;
import net.superkat.explosiveenhancement.api.ExplosiveApi;

import static net.superkat.explosiveenhancement.ExplosiveEnhancement.LOGGER;
import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.CONFIG;

public class ExplosiveHandler {

    public static void spawnParticles(World world, double x, double y, double z, float power, ExplosionParticleType explosionParticleType, boolean didDestroyBlocks, boolean isImportant) {
        if(CONFIG.modEnabled) {

            logDebugInfo();

            switch(explosionParticleType) {
                case NORMAL -> spawnExplosionParticles(world, x, y, z, power, didDestroyBlocks, isImportant);
                case WATER -> spawnUnderwaterExplosionParticles(world, x, y, z, power, didDestroyBlocks, isImportant);
                case WIND -> spawnWindExplosionParticles(world, x, y, z, power, didDestroyBlocks, isImportant); //unused for now
            }

            if (CONFIG.debugLogs) { LOGGER.info("ExplosiveHandler finished!"); }
        }
    }

    private static void logDebugInfo() {
        if (!CONFIG.debugLogs) return;

        LOGGER.info("ExplosiveHandler has been called!");
        LOGGER.info("Explosive Enhancement Enabled: {}", CONFIG.modEnabled);

        ParticlesMode particlesMode = MinecraftClient.getInstance().options.getParticles().getValue();
        LOGGER.info("Minecraft particle settings: {}", particlesMode);

        if (particlesMode == ParticlesMode.MINIMAL) {
            LOGGER.warn("[Explosive Enhancement]: Minecraft's particle settings is set to Minimal! This means that no explosion particles will be shown.");
        } else if (particlesMode == ParticlesMode.DECREASED) {
            LOGGER.warn("[Explosive Enhancement]: Minecraft's particle settings is set to Decreased! This means that some explosions particles may not always be shown.");
        }
    }

    public static ExplosionParticleType determineParticleType(World world, double x, double y, double z, ParticleEffect particle, ParticleEffect emitterParticle) {
        //TODO - Wind takes priority over water for now, but water should take priority over wind in the future
        if (particlesAreWindGust(particle, emitterParticle)) {
            return ExplosionParticleType.WIND;
        } else if(CONFIG.underwaterExplosions && blockIsInWater(world, x, y, z)) {
            return ExplosionParticleType.WATER;
        } else {
            return ExplosionParticleType.NORMAL;
        }
    }

    public static ExplosionParticleType determineParticleType(World world, Vec3d pos, ParticleEffect particle) {
        if (particle == ParticleTypes.GUST_EMITTER_SMALL || particle == ParticleTypes.GUST_EMITTER_LARGE) {
            return ExplosionParticleType.WIND;
        } else if(CONFIG.underwaterExplosions && blockIsInWater(world, pos.getX(), pos.getY(), pos.getZ())) {
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
    public static boolean blockIsInWater(World world, double x, double y, double z) {
        BlockPos pos = BlockPos.ofFloored(x, y, z);
        return CONFIG.underwaterExplosions && world.getFluidState(pos).isIn(FluidTags.WATER);
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
    public static boolean particlesAreWindGust(ParticleEffect particle, ParticleEffect emitterParticle) {
        return particle == ParticleTypes.GUST_EMITTER_SMALL && emitterParticle == ParticleTypes.GUST_EMITTER_LARGE;
    }

    public static boolean particlesAreEmitter(ParticleEffect particle) {
        return particle == ParticleTypes.EXPLOSION_EMITTER;
    }

    private static float getPowerFromParticle(ParticleEffect particle) {
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

    public static float getPowerFromExplosionPacket(World world, ExplosionS2CPacket packet) {
        float power = 0;
        ParticleEffect particle = packet.explosionParticle();

        if(CONFIG.attemptPowerKnockbackCalc && packet.playerKnockback().isPresent()) {
            power = ExplosiveApi.getPowerFromKnockback(world, packet.center(), MinecraftClient.getInstance().player, packet.playerKnockback().get());
        }

        if(Float.isNaN(power) || power == 0) {
            power = getPowerFromParticle(particle);
        }

        return power;
    }

    public static float attemptDeterminePowerFromKnockback(World world, Vec3d explosionPos, LivingEntity entity, Vec3d knockback) {
        double e = entity.getX() - explosionPos.getX();
        double g = entity.getEyeY() - explosionPos.getY();
        double h = entity.getZ() - explosionPos.getZ();
        double o = Math.sqrt(e * e + g * g + h * h);
        if(o != 0.0) {
            float dist = (float) Math.sqrt(entity.squaredDistanceTo(explosionPos));

            float kbModifier = 1f;
            float damageRecieved = ExplosionImpl.calculateReceivedDamage(explosionPos, entity);
            float powerX = powerCalc((float) entity.getX(), (float) knockback.getX(), (float) explosionPos.getX(), dist, (float) o, kbModifier, damageRecieved);
            float powerY = powerCalc((float) entity.getEyeY(), (float) knockback.getY(), (float) explosionPos.getY(), dist, (float) o, kbModifier, damageRecieved);
            float powerZ = powerCalc((float) entity.getZ(), (float) knockback.getZ(), (float) explosionPos.getZ(), dist, (float) o, kbModifier, damageRecieved);

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
        //algebra classes coming in handy for once
        return (float) ( (dist / ( -((knockbackAmount * distO) / ( (entityPos - explosionPos) * knockbackModifier * receivedDamage)) + 1) ) / 2 );
    }

    public static void spawnExplosionParticles(World world, double x, double y, double z, float power, boolean didDestroyBlocks, boolean isImportant) {
        power = CONFIG.dynamicSize ? power : 4;
        y = CONFIG.attemptBetterSmallExplosions && power == 1 ? y + CONFIG.smallExplosionYOffset : y;
        isImportant |= CONFIG.alwaysShow;

        float blastwavePower = power * 1.75f;
        float fireballPower = power * 1.25f;
        float smokePower = power * 0.4f;

        if(CONFIG.showBlastWave) {
            world.addParticle(ExplosiveEnhancement.BLASTWAVE, isImportant, x, y, z, blastwavePower, 0, 0);
        }

        if(CONFIG.showFireball) {
            world.addParticle(ExplosiveEnhancement.FIREBALL, isImportant, x, y + 0.5, z, fireballPower, isImportant ? 1 : 0, 0);
        } else if (CONFIG.showSparks) {
            world.addParticle(ExplosiveEnhancement.BLANK_FIREBALL, isImportant, x, y + 0.5, z, fireballPower, isImportant ? 1 : 0, 0);
        }

        if(CONFIG.showMushroomCloud) {
            spawnMushroomCloud(world, x, y, z, power, smokePower, isImportant);
        }

        if(CONFIG.showDefaultExplosion) {
            spawnVanillaParticles(world, x, y, z, power, didDestroyBlocks, isImportant, false);
        }
    }

    public static void spawnUnderwaterExplosionParticles(World world, double x, double y, double z, float power, boolean didDestroyBlocks, boolean isImportant) {
        power = CONFIG.dynamicUnderwater ? power : 4;
        y = CONFIG.attemptBetterSmallExplosions && power == 1 ? y + CONFIG.smallExplosionYOffset : y;
        isImportant = isImportant || CONFIG.alwaysShow;

        float blastwavePower = power * 1.75f;
        float fireballPower = power * 1.25f;

        if(CONFIG.showUnderwaterBlastWave) {
            world.addParticle(ExplosiveEnhancement.UNDERWATERBLASTWAVE, isImportant, x, y + 0.5, z, blastwavePower, 0, 0);
        }

        if(CONFIG.showShockwave) {
            world.addParticle(ExplosiveEnhancement.SHOCKWAVE, isImportant, x, y + 0.5, z, fireballPower, isImportant ? 1 : 0, 0);
        } else if (CONFIG.showUnderwaterSparks) {
            world.addParticle(ExplosiveEnhancement.BLANK_SHOCKWAVE, isImportant, x, y + 0.5, z, fireballPower, isImportant ? 1 : 0, 0);
        }

        spawnBubble(world, x, y, z, isImportant);

        if(CONFIG.showDefaultExplosionUnderwater) {
            spawnVanillaParticles(world, x, y, z, power, didDestroyBlocks, isImportant, false);
        }
    }

    private static void spawnMushroomCloud(World world, double x, double y, double z, float power, double smokePower, boolean isImportant) {
        //I'm aware DRY is a thing, but I couldn't figure out any other way to get even a similar effect that I was happy with, so unfortunately, this will have to do.
        //x, y, z, [size(power)/velX], velY, [size(power)/velZ]
        //This is to allow for dynamic smoke depending on the explosion's power
        //The smoke particle factory (should be) able to determine if the velX/velZ is the size or actual velocity
        world.addParticle(ExplosiveEnhancement.SMOKE, isImportant, x, y, z, power, power * 0.25, 0);
        world.addParticle(ExplosiveEnhancement.SMOKE, isImportant, x, y, z, power, smokePower, 0);
        world.addParticle(ExplosiveEnhancement.SMOKE, isImportant, x, y, z, 0.15, smokePower, power);
        world.addParticle(ExplosiveEnhancement.SMOKE, isImportant, x, y, z, -0.15, smokePower, power);
        world.addParticle(ExplosiveEnhancement.SMOKE, isImportant, x, y, z, power, smokePower, 0.15);
        world.addParticle(ExplosiveEnhancement.SMOKE, isImportant, x, y, z, power, smokePower, -0.15);
    }

    private static void spawnBubble(World world, double x, double y, double z, boolean isImportant) {
        for (int i = 0; i < CONFIG.bubbleAmount; i++) {
            double velX = world.random.nextBetween(1, 7) * 0.3 * world.random.nextBetween(-1, 1);
            double velY = world.random.nextBetween(1, 10) * 0.1;
            double velZ = world.random.nextBetween(1, 7) * 0.3 * world.random.nextBetween(-1, 1);
            world.addParticle(ExplosiveEnhancement.BUBBLE, isImportant, x, y, z, velX, velY, velZ);
        }
    }

    public static void spawnWindExplosionParticles(World world, double x, double y, double z, float power, boolean didDestroyBlocks, boolean isImportant) {
        //This should never be called until I add a special wind effect
        spawnVanillaParticles(world, x, y, z, power, didDestroyBlocks, isImportant, true);
    }

    private static void spawnVanillaParticles(World world, double x, double y, double z, float power, boolean didDestroyBlocks, boolean isImportant, boolean wind) {
        ParticleEffect particle = wind ? ParticleTypes.GUST_EMITTER_SMALL : ParticleTypes.EXPLOSION;
        ParticleEffect emitter = wind ? ParticleTypes.GUST_EMITTER_LARGE : ParticleTypes.EXPLOSION_EMITTER;
        world.addParticle(power >= 2.0f && didDestroyBlocks ? emitter : particle, isImportant, x, y, z, 1.0, 0.0, 0.0);
    }
}
