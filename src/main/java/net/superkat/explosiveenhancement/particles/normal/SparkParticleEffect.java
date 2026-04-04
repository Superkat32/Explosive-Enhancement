package net.superkat.explosiveenhancement.particles.normal;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;
import net.superkat.explosiveenhancement.particles.AbstractExplosiveParticleEffect;

public class SparkParticleEffect extends AbstractExplosiveParticleEffect {

    public static final MapCodec<SparkParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    getWaterCodec(),
                    getScaleCodec(),
                    getEmissiveCodec(),
                    ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("alpha", 0.7f).forGetter(SparkParticleEffect::getAlpha)
            ).apply(instance, SparkParticleEffect::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SparkParticleEffect> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, SparkParticleEffect::isWater,
            ByteBufCodecs.FLOAT, SparkParticleEffect::getScale,
            ByteBufCodecs.BOOL, SparkParticleEffect::isEmissive,
            ByteBufCodecs.FLOAT, SparkParticleEffect::getAlpha,
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
