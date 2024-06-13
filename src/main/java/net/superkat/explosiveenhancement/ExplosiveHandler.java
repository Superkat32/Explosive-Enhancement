package net.superkat.explosiveenhancement;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.superkat.explosiveenhancement.api.ExplosionParticleType;

import static net.superkat.explosiveenhancement.ExplosiveEnhancement.LOGGER;
import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.config;

public class ExplosiveHandler {
    public static void spawnParticles(World world, double x, double y, double z, float power, ExplosionParticleType explosionParticleType, boolean didDestroyBlocks, boolean isImportant) {
        if(config.modEnabled) {
            if(config.debugLogs) {
                LOGGER.info("ExplosiveHandler has been called!");
                LOGGER.info("Explosive Enhancement Enabled: " + config.modEnabled);
                ParticlesMode particlesMode = MinecraftClient.getInstance().options.getParticles().getValue();
                LOGGER.info("Minecraft particle settings: " + particlesMode);
                if(particlesMode == ParticlesMode.MINIMAL) {
                    //This is the cause of most issues, so giving the user a hint may help reduce bug reports
                    LOGGER.warn("[Explosive Enhancement]: Minecraft's particle settings is set to Minimal! This means that no explosion particles will be shown.");
                } else if (particlesMode == ParticlesMode.DECREASED) {
                    //This would have been helpful for me and saved me a good 5 minutes of my life
                    LOGGER.warn("[Explosive Enhancement]: Minecraft's particle settings is set to Decreased! This means that some explosions particles may not always be shown.");
                }
            }

            switch(explosionParticleType) {
                case NORMAL -> spawnExplosionParticles(world, x, y, z, power, didDestroyBlocks, isImportant);
                case WATER -> spawnUnderwaterExplosionParticles(world, x, y, z, power, didDestroyBlocks, isImportant);
                case WIND -> spawnWindExplosionParticles(world, x, y, z, power, didDestroyBlocks, isImportant); //unused for now
            }

            if(config.debugLogs) { LOGGER.info("ExplosiveHandler finished!"); }
        }
    }

    public static ExplosionParticleType determineParticleType(World world, double x, double y, double z, ParticleEffect particle, ParticleEffect emitterParticle) {
        //TODO - Wind takes priority over water for now, but water should take priority over wind in the future
        if (particlesAreWindGust(particle, emitterParticle)) {
            return ExplosionParticleType.WIND;
        } else if(config.underwaterExplosions && blockIsInWater(world, x, y, z)) {
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
        return config.underwaterExplosions && world.getFluidState(pos).isIn(FluidTags.WATER);
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

    public static void spawnExplosionParticles(World world, double x, double y, double z, float power, boolean didDestroyBlocks, boolean isImportant) {
        power = config.dynamicSize ? power : 4;
        y = config.attemptBetterSmallExplosions && power == 1 ? y + config.smallExplosionYOffset : y;
        isImportant = isImportant || config.alwaysShow;
        float blastwavePower = power * 1.75f;
        float fireballPower = power * 1.25f;
        float smokePower = power * 0.4f;

        if(config.showBlastWave) {
            world.addParticle(ExplosiveEnhancement.BLASTWAVE, isImportant, x, y, z, blastwavePower, 0, 0);
        }

        if(config.showFireball) {
            world.addParticle(ExplosiveEnhancement.FIREBALL, isImportant, x, y + 0.5, z, fireballPower, isImportant ? 1 : 0, 0);
        } else if (config.showSparks) {
            world.addParticle(ExplosiveEnhancement.BLANK_FIREBALL, isImportant, x, y + 0.5, z, fireballPower, isImportant ? 1 : 0, 0);
        }

        if(config.showMushroomCloud) {
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

        if(config.showDefaultExplosion) {
            spawnVanillaParticles(world, x, y, z, power, didDestroyBlocks, isImportant, false);
        }
    }

    public static void spawnUnderwaterExplosionParticles(World world, double x, double y, double z, float power, boolean didDestroyBlocks, boolean isImportant) {
        power = config.dynamicUnderwater ? power : 4;
        y = config.attemptBetterSmallExplosions && power == 1 ? y + config.smallExplosionYOffset : y;
        isImportant = isImportant || config.alwaysShow;
        float blastwavePower = power * 1.75f;
        float fireballPower = power * 1.25f;

        if(config.showUnderwaterBlastWave) {
            world.addParticle(ExplosiveEnhancement.UNDERWATERBLASTWAVE, isImportant, x, y + 0.5, z, blastwavePower, 0, 0);
        }

        if(config.showShockwave) {
            world.addParticle(ExplosiveEnhancement.SHOCKWAVE, isImportant, x, y + 0.5, z, fireballPower, isImportant ? 1 : 0, 0);
        } else if (config.showUnderwaterSparks) {
            world.addParticle(ExplosiveEnhancement.BLANK_SHOCKWAVE, isImportant, x, y + 0.5, z, fireballPower, isImportant ? 1 : 0, 0);
        }

        for(int total = config.bubbleAmount; total >= 1; total--) {
            spawnBubble(world, x, y, z, isImportant);
        }

        if(config.showDefaultExplosionUnderwater) {
            spawnVanillaParticles(world, x, y, z, power, didDestroyBlocks, isImportant, false);
        }
    }

    private static void spawnBubble(World world, double x, double y, double z, boolean isImportant) {
        double velX = world.random.nextBetween(1, 7) * 0.3 * world.random.nextBetween(-1, 1);
        double velY = world.random.nextBetween(1, 10) * 0.1;
        double velZ = world.random.nextBetween(1, 7) * 0.3 * world.random.nextBetween(-1, 1);
        world.addParticle(ExplosiveEnhancement.BUBBLE, isImportant, x, y, z, velX, velY, velZ);
    }

    public static void spawnWindExplosionParticles(World world, double x, double y, double z, float power, boolean didDestroyBlocks, boolean isImportant) {
        //This should never be called until I add a special wind effect
        spawnVanillaParticles(world, x, y, z, power, didDestroyBlocks, isImportant, true);
    }

    private static void spawnVanillaParticles(World world, double x, double y, double z, float power, boolean didDestroyBlocks, boolean isImportant, boolean wind) {
        ParticleEffect particle = wind ? ParticleTypes.GUST_EMITTER_SMALL : ParticleTypes.EXPLOSION;
        ParticleEffect emitter = wind ? ParticleTypes.GUST_EMITTER_LARGE : ParticleTypes.EXPLOSION_EMITTER;

        if(!(power < 2.0f) && didDestroyBlocks) {
            world.addParticle(emitter, isImportant, x, y, z, 1.0, 0.0, 0.0);
        } else {
            world.addParticle(particle, isImportant, x, y, z, 1.0, 0.0, 0.0);
        }
    }
}
