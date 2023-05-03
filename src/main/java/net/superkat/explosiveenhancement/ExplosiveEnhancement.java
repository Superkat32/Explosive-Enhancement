package net.superkat.explosiveenhancement;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplosiveEnhancement implements ModInitializer {
	public static final String MOD_ID = "explosiveenhancement";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final DefaultParticleType BLASTWAVE = FabricParticleTypes.simple();
	public static final DefaultParticleType FIREBALL = FabricParticleTypes.simple();
	public static final DefaultParticleType MUSHROOMCLOUD = FabricParticleTypes.simple();
	public static final DefaultParticleType BUBBLE = FabricParticleTypes.simple();
	public static final DefaultParticleType SHOCKWAVE = FabricParticleTypes.simple();
	public static final DefaultParticleType UNDERWATERBLASTWAVE = FabricParticleTypes.simple();

	@Override
	public void onInitialize() {
		//Loads the config
//		MidnightConfig.init("explosiveenhancement", ExplosiveConfig.class);

		Registry.register(Registry.PARTICLE_TYPE, new Identifier("explosiveenhancement", "blastwave"), BLASTWAVE);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier("explosiveenhancement", "fireball"), FIREBALL);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier("explosiveenhancement", "mushroomcloud"), MUSHROOMCLOUD);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier("explosiveenhancement", "bubble"), BUBBLE);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier("explosiveenhancement", "shockwave"), SHOCKWAVE);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier("explosiveenhancement", "underwaterblastwave"), UNDERWATERBLASTWAVE);
	}
}
