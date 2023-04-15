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

	public static final DefaultParticleType BOOM = FabricParticleTypes.simple();
	public static final DefaultParticleType BIG_EXPLOSION = FabricParticleTypes.simple();
	public static final DefaultParticleType LINGER = FabricParticleTypes.simple();
	public static final DefaultParticleType BUBBLE = FabricParticleTypes.simple();
	public static final DefaultParticleType SHOCKWAVE = FabricParticleTypes.simple();

	@Override
	public void onInitialize() {
		//Loads the config
//		MidnightConfig.init("explosiveenhancement", ExplosiveConfig.class);

		Registry.register(Registries.PARTICLE_TYPE, new Identifier("explosiveenhancement", "boom"), BOOM);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier("explosiveenhancement", "big_explosion"), BIG_EXPLOSION);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier("explosiveenhancement", "linger"), LINGER);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier("explosiveenhancement", "bubble"), BUBBLE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier("explosiveenhancement", "shockwave"), SHOCKWAVE);
		LOGGER.info("Hello Fabric world!");
	}
}
