package net.superkat.explosiveenhancement;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplosiveEnhancement implements ModInitializer {
	public static final String MOD_ID = "explosiveenhancement";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final DefaultParticleType BLASTWAVE = FabricParticleTypes.simple();
	public static final DefaultParticleType FIREBALL = FabricParticleTypes.simple();
	public static final DefaultParticleType SMOKE = FabricParticleTypes.simple();
	public static final DefaultParticleType SPARKS = FabricParticleTypes.simple();
	public static final DefaultParticleType BUBBLE = FabricParticleTypes.simple();
	public static final DefaultParticleType SHOCKWAVE = FabricParticleTypes.simple();
	public static final DefaultParticleType UNDERWATERBLASTWAVE = FabricParticleTypes.simple();
	public static final DefaultParticleType UNDERWATERSPARKS = FabricParticleTypes.simple();

	@Override
	public void onInitialize() {
		Registry.register(Registries.PARTICLE_TYPE, new Identifier("explosiveenhancement", "blastwave"), BLASTWAVE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier("explosiveenhancement", "fireball"), FIREBALL);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier("explosiveenhancement", "smoke"), SMOKE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier("explosiveenhancement", "bubble"), BUBBLE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier("explosiveenhancement", "shockwave"), SHOCKWAVE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier("explosiveenhancement", "underwaterblastwave"), UNDERWATERBLASTWAVE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier("explosiveenhancement", "sparks"), SPARKS);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier("explosiveenhancement", "underwatersparks"), UNDERWATERSPARKS);
	}
}
