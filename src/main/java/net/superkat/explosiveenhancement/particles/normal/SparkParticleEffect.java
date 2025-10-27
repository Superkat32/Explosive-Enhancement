package net.superkat.explosiveenhancement.particles.normal;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.dynamic.Codecs;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;
import net.superkat.explosiveenhancement.particles.AbstractExplosiveParticleEffect;

public class SparkParticleEffect extends AbstractExplosiveParticleEffect {

    public static final MapCodec<SparkParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    getWaterCodec(),
                    getScaleCodec(),
                    getEmissiveCodec(),
                    Codecs.POSITIVE_FLOAT.optionalFieldOf("alpha", 0.7f).forGetter(SparkParticleEffect::getAlpha)
            ).apply(instance, SparkParticleEffect::new)
    );

    public static final PacketCodec<RegistryByteBuf, SparkParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, SparkParticleEffect::isWater,
            PacketCodecs.FLOAT, SparkParticleEffect::getScale,
            PacketCodecs.BOOLEAN, SparkParticleEffect::isEmissive,
            PacketCodecs.FLOAT, SparkParticleEffect::getAlpha,
            SparkParticleEffect::new
    );

    private final float alpha;

    public SparkParticleEffect(boolean water, float scale, boolean emissive, float alpha) {
        super(water, scale, emissive);
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }

    @Override
    public ParticleType<?> getType() {
        return this.isWater() ? ExplosiveEnhancement.WATER_SPARKS : ExplosiveEnhancement.SPARKS;
    }
}
