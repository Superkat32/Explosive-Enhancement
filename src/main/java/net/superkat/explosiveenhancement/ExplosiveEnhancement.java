package net.superkat.explosiveenhancement;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.superkat.explosiveenhancement.network.S2CExplosiveEnhancementParticles;
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

	// Special particle for single player dynamic explosions
	public static final SimpleParticleType NO_RENDER_PARTICLE = FabricParticleTypes.simple();

	@Override
	public void onInitialize() {
		registerParticle(id("blastwave"), BLASTWAVE);
		registerParticle(id("fireball"), FIREBALL);
		registerParticle(id("blank_fireball"), BLANK_FIREBALL);
		registerParticle(id("smoke"), SMOKE);
		registerParticle(id("bubble"), BUBBLE);
		registerParticle(id("shockwave"), SHOCKWAVE);
		registerParticle(id("blank_shockwave"), BLANK_SHOCKWAVE);
		registerParticle(id("underwaterblastwave"), UNDERWATERBLASTWAVE);
		registerParticle(id("sparks"), SPARKS);
		registerParticle(id("underwatersparks"), UNDERWATERSPARKS);

		// Used for the single player dynamic explosions
		registerParticle(id("norenderparticle"), NO_RENDER_PARTICLE);
		PayloadTypeRegistry.playS2C().register(S2CExplosiveEnhancementParticles.ID, S2CExplosiveEnhancementParticles.CODEC);
	}

	public void registerParticle(Identifier id, ParticleType<?> particle) {
		Registry.register(Registries.PARTICLE_TYPE, id, particle);
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
