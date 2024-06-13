package net.superkat.explosiveenhancement;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplosiveEnhancement implements ModInitializer {
	public static final String MOD_ID = "explosiveenhancement";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final SimpleParticleType BLASTWAVE = FabricParticleTypes.simple();
	public static final SimpleParticleType FIREBALL = FabricParticleTypes.simple();
	public static final SimpleParticleType BLANK_FIREBALL = FabricParticleTypes.simple();
	public static final SimpleParticleType SMOKE = FabricParticleTypes.simple();
	public static final SimpleParticleType SPARKS = FabricParticleTypes.simple();
	public static final SimpleParticleType BUBBLE = FabricParticleTypes.simple();
	public static final SimpleParticleType SHOCKWAVE = FabricParticleTypes.simple();
	public static final SimpleParticleType BLANK_SHOCKWAVE = FabricParticleTypes.simple();
	public static final SimpleParticleType UNDERWATERBLASTWAVE = FabricParticleTypes.simple();
	public static final SimpleParticleType UNDERWATERSPARKS = FabricParticleTypes.simple();

	@Override
	public void onInitialize() {
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "blastwave"), BLASTWAVE);
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "fireball"), FIREBALL);
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "blank_fireball"), BLANK_FIREBALL);
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "smoke"), SMOKE);
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "bubble"), BUBBLE);
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "shockwave"), SHOCKWAVE);
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "blank_shockwave"), BLANK_SHOCKWAVE);
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "underwaterblastwave"), UNDERWATERBLASTWAVE);
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "sparks"), SPARKS);
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "underwatersparks"), UNDERWATERSPARKS);
	}
}
