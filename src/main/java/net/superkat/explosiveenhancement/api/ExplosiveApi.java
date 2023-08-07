package net.superkat.explosiveenhancement.api;

import net.minecraft.world.World;
import net.superkat.explosiveenhancement.ExplosiveHandler;

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
     */
    static void spawnParticles(World world, double x, double y, double z, float power, boolean isUnderWater, boolean didDestroyBlocks, boolean isImportant) {
        ExplosiveHandler.spawnParticles(world, x, y, z, power, isUnderWater, didDestroyBlocks, isImportant);
    }
}
