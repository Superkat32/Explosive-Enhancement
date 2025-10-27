package net.superkat.explosiveenhancement;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.superkat.explosiveenhancement.particles.BlastWaveParticleEffect;
import net.superkat.explosiveenhancement.particles.FireballParticleEffect;
import net.superkat.explosiveenhancement.particles.SmokeParticleEffect;
import net.superkat.explosiveenhancement.particles.SparkParticleEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplosiveEnhancement implements ModInitializer {
	public static final String MOD_ID = "explosiveenhancement";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ParticleType<BlastWaveParticleEffect> BLASTWAVE = FabricParticleTypes.complex(BlastWaveParticleEffect.CODEC, BlastWaveParticleEffect.PACKET_CODEC);
	public static final ParticleType<FireballParticleEffect> FIREBALL = FabricParticleTypes.complex(FireballParticleEffect.CODEC, FireballParticleEffect.PACKET_CODEC);
	public static final ParticleType<SparkParticleEffect> SPARKS = FabricParticleTypes.complex(SparkParticleEffect.CODEC, SparkParticleEffect.PACKET_CODEC);
	public static final ParticleType<SmokeParticleEffect> SMOKE = FabricParticleTypes.complex(SmokeParticleEffect.CODEC, SmokeParticleEffect.PACKET_CODEC);

	public static final ParticleType<BlastWaveParticleEffect> WATER_BLASTWAVE = FabricParticleTypes.complex(BlastWaveParticleEffect.CODEC, BlastWaveParticleEffect.PACKET_CODEC);
	public static final ParticleType<FireballParticleEffect> SHOCKWAVE = FabricParticleTypes.complex(FireballParticleEffect.CODEC, FireballParticleEffect.PACKET_CODEC);
	public static final ParticleType<SparkParticleEffect> WATER_SPARKS = FabricParticleTypes.complex(SparkParticleEffect.CODEC, SparkParticleEffect.PACKET_CODEC);
	public static final SimpleParticleType BUBBLE = FabricParticleTypes.simple();

	@Override
	public void onInitialize() {
		registerParticle(id("blastwave"), BLASTWAVE);
		registerParticle(id("fireball"), FIREBALL);
		registerParticle(id("sparks"), SPARKS);
		registerParticle(id("smoke"), SMOKE);
		registerParticle(id("underwaterblastwave"), WATER_BLASTWAVE);
		registerParticle(id("shockwave"), SHOCKWAVE);
		registerParticle(id("underwatersparks"), WATER_SPARKS);
		registerParticle(id("bubble"), BUBBLE);
	}

	public void registerParticle(Identifier id, ParticleType<?> particle) {
		Registry.register(Registries.PARTICLE_TYPE, id, particle);
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
