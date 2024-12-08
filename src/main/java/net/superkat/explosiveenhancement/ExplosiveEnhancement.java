package net.superkat.explosiveenhancement;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? if (1.19.2) {
/*import net.minecraft.util.registry.Registry;
 *///?} else {
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
//?}

//? if (<1.20) {
/*import net.minecraft.particle.DefaultParticleType;
 *///?} else {
import net.minecraft.particle.SimpleParticleType;
//?}

//? if(>=1.21.2) {
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.superkat.explosiveenhancement.network.S2CExplosiveEnhancementParticles;
//?}

public class ExplosiveEnhancement implements ModInitializer {
	public static final String MOD_ID = "explosiveenhancement";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	//? if (<1.20) {
	/*public static final DefaultParticleType BLASTWAVE = FabricParticleTypes.simple();
	public static final DefaultParticleType FIREBALL = FabricParticleTypes.simple();
	public static final DefaultParticleType BLANK_FIREBALL = FabricParticleTypes.simple();
	public static final DefaultParticleType SMOKE = FabricParticleTypes.simple();
	public static final DefaultParticleType SPARKS = FabricParticleTypes.simple();
	public static final DefaultParticleType BUBBLE = FabricParticleTypes.simple();
	public static final DefaultParticleType SHOCKWAVE = FabricParticleTypes.simple();
	public static final DefaultParticleType BLANK_SHOCKWAVE = FabricParticleTypes.simple();
	public static final DefaultParticleType UNDERWATERBLASTWAVE = FabricParticleTypes.simple();
	public static final DefaultParticleType UNDERWATERSPARKS = FabricParticleTypes.simple();
	*///?} else {
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
	//?}

	//? if(>=1.21.2)
	public static final SimpleParticleType NO_RENDER_PARTICLE = FabricParticleTypes.simple();
	//?}

	//TODO - change flash particle to custom "noexplosion" particle

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

		//? if(>=1.21.2) {
		//Used for the single player dynamic explosions
		registerParticle(id("norenderparticle"), NO_RENDER_PARTICLE);
		PayloadTypeRegistry.playS2C().register(S2CExplosiveEnhancementParticles.ID, S2CExplosiveEnhancementParticles.CODEC);
		//?}
	}

	public void registerParticle(Identifier id, ParticleType<?> particle) {
		//? if (1.19.2) {
		/*Registry.register(Registry.PARTICLE_TYPE, id, particle);
		*///?} else {
		Registry.register(Registries.PARTICLE_TYPE, id, particle);
		//?}
	}

	private static Identifier id(String path) {
		//? if (>=1.21) {
		return Identifier.of(MOD_ID, path);
		//?} else {
		/*return new Identifier(MOD_ID, path);
		*///?}
	}
}
