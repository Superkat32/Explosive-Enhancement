package net.superkat.explosiveenhancement.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleType;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;

public class FireballParticleEffect extends AbstractExplosiveParticleEffect {

    public static final MapCodec<FireballParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    getWaterCodec(),
                    getScaleCodec(),
                    getEmissiveCodec(),
                    Codec.BOOL.optionalFieldOf("sparks", false).forGetter(FireballParticleEffect::isSparks),
                    Codec.BOOL.optionalFieldOf("sparks_only", false).forGetter(FireballParticleEffect::isSparksOnly),
                    Codec.BOOL.optionalFieldOf("sparks_important", false).forGetter(FireballParticleEffect::isSparksImportant)
            ).apply(instance, FireballParticleEffect::new)
    );

    public static final PacketCodec<RegistryByteBuf, FireballParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, FireballParticleEffect::isWater,
            PacketCodecs.FLOAT, FireballParticleEffect::getScale,
            PacketCodecs.BOOLEAN, FireballParticleEffect::isEmissive,
            PacketCodecs.BOOLEAN, FireballParticleEffect::isSparks,
            PacketCodecs.BOOLEAN, FireballParticleEffect::isSparksOnly,
            PacketCodecs.BOOLEAN, FireballParticleEffect::isSparksImportant,
            FireballParticleEffect::new
    );

    private final boolean sparks;
    private final boolean sparksOnly;
    private final boolean sparksImportant;

    public FireballParticleEffect(boolean water, float scale, boolean emissive, boolean sparks, boolean sparksOnly, boolean sparksImportant) {
        super(water, scale, emissive);
        this.sparks = sparks;
        this.sparksOnly = sparksOnly;
        this.sparksImportant = sparksImportant;
    }

    public boolean isSparks() {
        return sparks;
    }

    public boolean isSparksOnly() {
        return sparksOnly;
    }

    public boolean isSparksImportant() {
        return sparksImportant;
    }

    @Override
    public ParticleType<?> getType() {
        return this.isWater() ? ExplosiveEnhancement.SHOCKWAVE : ExplosiveEnhancement.FIREBALL;
    }
}
