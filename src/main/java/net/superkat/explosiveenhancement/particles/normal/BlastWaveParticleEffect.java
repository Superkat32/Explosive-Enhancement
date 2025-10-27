package net.superkat.explosiveenhancement.particles.normal;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleType;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;
import net.superkat.explosiveenhancement.particles.AbstractExplosiveParticleEffect;

public class BlastWaveParticleEffect extends AbstractExplosiveParticleEffect {
    public static final MapCodec<BlastWaveParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> createDefaultWaterCodec(instance).apply(instance, BlastWaveParticleEffect::new)
    );

    public static final PacketCodec<RegistryByteBuf, BlastWaveParticleEffect> PACKET_CODEC = createWaterPacketCodec(BlastWaveParticleEffect::new);

    public BlastWaveParticleEffect(boolean water, float scale, boolean emissive) {
        super(water, scale, emissive);
    }

    @Override
    public ParticleType<?> getType() {
        // This is incredibly cursed only for the fact that it isn't quite expected,
        // but it does work ¯\_(ツ)_/¯
        return this.isWater() ? ExplosiveEnhancement.WATER_BLASTWAVE : ExplosiveEnhancement.BLASTWAVE;
    }
}
