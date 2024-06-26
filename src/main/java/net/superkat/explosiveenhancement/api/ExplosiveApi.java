package net.superkat.explosiveenhancement.api;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import net.superkat.explosiveenhancement.ExplosiveHandler;

public interface ExplosiveApi {

    /**
     * Spawn Explosive Enhancement's particles while still keeping the user's config options.
     *
     * @param world The world to spawn the effect in.
     * @param x The effect's x coordinate.
     * @param y The effect's y coordinate.
     * @param z The effect's z coordinate.
     * @param power The explosion's power.
     * @param explosionParticleType The explosion particle effect to show(water, wind, or normal).
     *
     * @since Explosive Enhancement 1.2.3
     */
    static void spawnParticles(World world, double x, double y, double z, float power, ExplosionParticleType explosionParticleType) {
        spawnParticles(world, x, y, z, power, explosionParticleType, true, false);
    }

    /**
     * Spawn Explosive Enhancement's particles while still keeping the user's config options.
     *
     * @param world The world to spawn the effect in.
     * @param x The effect's x coordinate.
     * @param y The effect's y coordinate.
     * @param z The effect's z coordinate.
     * @param power The explosion's power.
     * @param explosionParticleType The explosion particle effect to show(water, wind, or normal).
     * @param didDestroyBlocks Used to help determine the particle type for the vanilla particles.
     *
     * @since Explosive Enhancement 1.2.3
     */
    static void spawnParticles(World world, double x, double y, double z, float power, ExplosionParticleType explosionParticleType, boolean didDestroyBlocks) {
        spawnParticles(world, x, y, z, power, explosionParticleType, didDestroyBlocks, false);
    }

    /**
     * Spawn Explosive Enhancement's particles while still keeping the user's config options.
     *
     * @param world The world to spawn the effect in.
     * @param x The effect's x coordinate.
     * @param y The effect's y coordinate.
     * @param z The effect's z coordinate.
     * @param power The explosion's power.
     * @param explosionParticleType The explosion particle effect to show(water, wind, or normal).
     * @param didDestroyBlocks Used to help determine the particle type for the vanilla particles.
     * @param isImportant Renders the effect from further away AND on lower particle settings. If true, the user's config option is ignored, otherwise it refers to the user's config option.
     *
     * @since Explosive Enhancement 1.2.3
     */
    static void spawnParticles(World world, double x, double y, double z, float power, ExplosionParticleType explosionParticleType, boolean didDestroyBlocks, boolean isImportant) {
        ExplosiveHandler.spawnParticles(world, x, y, z, power, explosionParticleType, didDestroyBlocks, isImportant);
    }

    /**
     * Determines the Explosive Enhancement explosion particle type based on the given parameters. The user's config options are accounted for.
     * <br> <br>
     * If the given coordinates are underwater, WATER is returned.<br>
     * Else if the particle and particleEmitter are Minecraft's wind gust particles, WIND is returned.<br>
     * Else NORMAL is returned.<br>
     *
     * @param world The world to check in.
     * @param x The x coordinate of the explosion.
     * @param y The y coordinate of the explosion.
     * @param z The z coordinate of the explosion.
     * @param particle The explosion's normal particle on for a lower explosion power.
     * @param emitterParticle The explosion's normal particle(normally an emitter particle) for a higher explosion power.
     * @return The appropriate ExplosionParticleType depending on the particles or coordinates given.
     *
     * @since Explosive Enhancement 1.2.3
     */
    static ExplosionParticleType determineParticleType(World world, double x, double y, double z, ParticleEffect particle, ParticleEffect emitterParticle) {
        return ExplosiveHandler.determineParticleType(world, x, y, z, particle, emitterParticle);
    }

    /**
     * Spawn Explosive Enhancement's particles while still keeping the user's config options
     *
     * @param world The world to spawn the effect in
     * @param x The effect's x coordinate
     * @param y The effect's y coordinate
     * @param z The effect's z coordinate
     * @param power The effect's size
     *
     * @since Explosive Enhancement 1.2.1
     */
    static void spawnParticles(World world, double x, double y, double z, float power) {
        spawnParticles(world, x, y, z, power, false, true, false);
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
     * @param didDestroyBlocks Helps to determine the particle type for the vanilla particles
     *
     * @since Explosive Enhancement 1.2.1
     */
    static void spawnParticles(World world, double x, double y, double z, float power, boolean isUnderWater, boolean didDestroyBlocks) {
        spawnParticles(world, x, y, z, power, isUnderWater, didDestroyBlocks, false);
    }

    /**
     * Spawn Explosive Enhancement's particles while still keeping the user's config options
     * @param world The world to spawn the effect in
     * @param x The effect's x coordinate
     * @param y The effect's y coordinate
     * @param z The effect's z coordinate
     * @param power The effect's size
     * @param isUnderWater Show the underwater effect
     * @param didDestroyBlocks Helps to determine the particle type for the vanilla particles
     * @param isImportant Renders the effect from far away AND on lower particle settings. If true, the user's config option for this specific option is ignored, otherwise, it defaults to the user's config.
     *
     * @since Explosive Enhancement 1.2.1
     */
    static void spawnParticles(World world, double x, double y, double z, float power, boolean isUnderWater, boolean didDestroyBlocks, boolean isImportant) {
        ExplosionParticleType explosionParticleType = isUnderWater ? ExplosionParticleType.WATER : ExplosionParticleType.NORMAL;
        spawnParticles(world, x, y, z, power, explosionParticleType, didDestroyBlocks, isImportant);
    }
}
