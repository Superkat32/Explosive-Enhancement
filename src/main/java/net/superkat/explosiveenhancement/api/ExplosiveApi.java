package net.superkat.explosiveenhancement.api;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.superkat.explosiveenhancement.ExplosiveConfig;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;

import static net.superkat.explosiveenhancement.ExplosiveEnhancement.LOGGER;

public interface ExplosiveApi {

    /**
     * Spawn Explosive Enhancement's particles while still keeping the user's config options
     *
     * @param world The world to spawn the effect in
     * @param x The effect's x coordinate
     * @param y The effect's y coordinate
     * @param z The effect's z coordinate
     * @param power The effect's size
     */
    static void spawnParticles(World world, double x, double y, double z, float power) {
        spawnParticles(world, x, y, z, power, false, true, true, false);
    }

    /**
     * Spawn Explosive Enhancement's particles while still keeping the user's config options
     *
     * @param world The world to spawn the effect in
     * @param x The effect's x coordinate
     * @param y The effect's y coordinate
     * @param z The effect's z coordinate
     * @param power The effect's size
     * @param isUnderWater Show the underwater effect
     * @param didDestroyBlocks Helps to determine the particle type for the vanilla particle
     */
    static void spawnParticles(World world, double x, double y, double z, float power, boolean isUnderWater, boolean didDestroyBlocks) {
        spawnParticles(world, x, y, z, power, isUnderWater, didDestroyBlocks, false, false);
    }

    /**
     * Spawn Explosive Enhancement's particles while still keeping the user's config options
     * @param world The world to spawn the effect in
     * @param x The effect's x coordinate
     * @param y The effect's y coordinate
     * @param z The effect's z coordinate
     * @param power The effect's size
     * @param isUnderWater Show the underwater effect
     * @param didDestroyBlocks Helps to determine the particle type for the vanilla particle
     * @param isImportant Renders from afar
     */
    static void spawnParticles(World world, double x, double y, double z, float power, boolean isUnderWater, boolean didDestroyBlocks, boolean allowVanilla, boolean isImportant) {
        ExplosiveConfig config = ExplosiveConfig.INSTANCE.getConfig(); //Thanks Andrew Grant!!!
        if(config.debugLogs) {
            LOGGER.info("ExplosiveApi has been called!");
        }
        power = config.dynamicSize ? power : 4;
        y = config.attemptBetterSmallExplosions && power == 1 ? y + config.smallExplosionYOffset : y;
        if(config.modEnabled) {
            if (isUnderWater) {
                power = config.dynamicUnderwater ? power : 4;
                if(config.showUnderwaterBlastWave) {
                    world.addParticle(ExplosiveEnhancement.UNDERWATERBLASTWAVE, isImportant, x, y + 0.5, z, power * 1.75, 0, 0);
                }
                if(config.showShockwave) {
                    world.addParticle(ExplosiveEnhancement.SHOCKWAVE, isImportant, x, y + 0.5, z, power * 1.25, 0, 0);
                } else if (config.showUnderwaterSparks) {
                    world.addParticle(ExplosiveEnhancement.BLANK_SHOCKWAVE, isImportant, x, y + 0.5, z, power * 1.25, 0, 0);
                }
                for(int total = config.bubbleAmount; total >= 1; total--) {
                    world.addParticle(ExplosiveEnhancement.BUBBLE, isImportant, x, y, z, nextBetween(1, 7) * 0.3 * nextBetween(-1, 1), nextBetween(1, 10) * 0.1, nextBetween(1, 7) * 0.3 * nextBetween(-1, 1));
                }
                if(config.showDefaultExplosionUnderwater) {
                    showDefaultParticles(world, x, y, z, power, didDestroyBlocks, allowVanilla, isImportant);
                }
            } else {
                if(config.debugLogs) {
                    LOGGER.info("Particle is being shown!");
                }
                if(config.showBlastWave) {
                    world.addParticle(ExplosiveEnhancement.BLASTWAVE, isImportant, x, y, z, power * 1.75, 0, 0);
                }
                if(config.showFireball) {
                    world.addParticle(ExplosiveEnhancement.FIREBALL, isImportant, x, y + 0.5, z, power * 1.25, 0, 0);
                } else if (config.showSparks) {
                    world.addParticle(ExplosiveEnhancement.BLANK_FIREBALL, isImportant, x, y + 0.5, z, power * 1.25, 0, 0);
                }
                if(config.showMushroomCloud) {
                    //I'm aware DRY is a thing, but I couldn't figure out any other way to get even a similar effect that I was happy with, so unfortunately, this will have to do.
                    //x, y, z, [size(power)/velX], velY, [size(power)/velZ]
                    //This is to allow for dynamic smoke depending on the explosion's power
                    //The smoke particle factory (should be) able to determine if the velX/velZ is the size or actual velocity
                    world.addParticle(ExplosiveEnhancement.SMOKE, isImportant, x, y, z, power, power * 0.25, 0);
                    world.addParticle(ExplosiveEnhancement.SMOKE, isImportant, x, y, z, power, power * 0.4, 0);

                    world.addParticle(ExplosiveEnhancement.SMOKE, isImportant, x, y, z, 0.15, power * 0.4, power);
                    world.addParticle(ExplosiveEnhancement.SMOKE, isImportant, x, y, z, -0.15, power * 0.4, power);
                    world.addParticle(ExplosiveEnhancement.SMOKE, isImportant, x, y, z, power, power * 0.4, 0.15);
                    world.addParticle(ExplosiveEnhancement.SMOKE, isImportant, x, y, z, power, power * 0.4, -0.15);
                }
                if(config.showDefaultExplosion) {
                    showDefaultParticles(world, x, y, z, power, didDestroyBlocks, allowVanilla, isImportant);
                }
            }
            if(config.debugLogs) {
                LOGGER.info("Particle finished!");
            }
        }
    }

    static void showDefaultParticles(World world, double x, double y, double z, float power, boolean didDestroyBlocks, boolean allowVanilla, boolean isImportant) {
        if(!(power < 2.0f) && didDestroyBlocks) {
            world.addParticle(ParticleTypes.EXPLOSION_EMITTER, isImportant, x, y, z, 1.0, 0.0, 0.0);
        } else {
            world.addParticle(ParticleTypes.EXPLOSION, isImportant, x, y, z, 1.0, 0.0, 0.0);
        }
    }

    private static int nextBetween(int min, int max) {
        return MathHelper.nextBetween(Random.create(), min, max);
    }
}
