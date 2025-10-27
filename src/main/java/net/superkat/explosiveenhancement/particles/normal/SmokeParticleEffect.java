package net.superkat.explosiveenhancement.particles.normal;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleType;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;
import net.superkat.explosiveenhancement.particles.AbstractExplosiveParticleEffect;

public class SmokeParticleEffect extends AbstractExplosiveParticleEffect {

    public static final MapCodec<SmokeParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> createDefaultCodec(instance).apply(instance, SmokeParticleEffect::new)
    );

    public static final PacketCodec<RegistryByteBuf, SmokeParticleEffect> PACKET_CODEC = createPacketCodec(SmokeParticleEffect::new);

    public SmokeParticleEffect(float scale, boolean emissive) {
        super(scale, emissive);
    }

    @Override
    public ParticleType<?> getType() {
        return ExplosiveEnhancement.SMOKE;
    }
}
