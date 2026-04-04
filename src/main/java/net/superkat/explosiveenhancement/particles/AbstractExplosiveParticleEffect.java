package net.superkat.explosiveenhancement.particles;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.BiFunction;

public abstract class AbstractExplosiveParticleEffect implements ParticleOptions {
    /**
     * Nice reusable way of creating the most common codec parameters with a special water variant. Makes a particle effect's codec go from this:
     * <pre>
     *     {@code
     *     public static final MapCodec<ExplosiveParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
     *             instance -> instance.group(
     *                     Codec.BOOL.optionalFieldOf("water", false).forGetter(ExplosiveParticleEffect::isWater)
     *                     Codec.FLOAT.optionalFieldOf("scale", 4f).forGetter(ExplosiveParticleEffect::getScale),
     *                     Codec.BOOL.optionalFieldOf("emissive", true).forGetter(ExplosiveParticleEffect::isEmissive)
     *             ).apply(instance, ExplosiveParticleEffect::new)
     *     );
     *     }
     * </pre>
     * to this:
     * <pre>
     *     {@code
     *     public static final MapCodec<ExplosiveParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
     *             instance -> createDefaultCodec(instance).apply(instance, ExplosiveParticleEffect::new)
     *     );
     *     }
     * </pre>
     * The first code block is there in case you are searching for examples on how to create a particle's MapCodec.<br><br>
     * If you want more examples, see some of Vanilla's unique particles:<br>
     * {@link net.minecraft.core.particles.ShriekParticleOption},<br>
     * {@link net.minecraft.core.particles.VibrationParticleOption},<br>
     * and {@link net.minecraft.core.particles.TrailParticleOption}.
     */
    public static <T extends AbstractExplosiveParticleEffect> Products.P3<RecordCodecBuilder.Mu<T>, Boolean, Float, Boolean> createDefaultWaterCodec(RecordCodecBuilder.Instance<T> instance) {
        return instance.group(getWaterCodec(), getScaleCodec(), getEmissiveCodec());
    }

    /**
     * Alternative Codec for particles with no water variant.
     */
    public static <T extends AbstractExplosiveParticleEffect> Products.P2<RecordCodecBuilder.Mu<T>, Float, Boolean> createDefaultCodec(RecordCodecBuilder.Instance<T> instance) {
        return instance.group(getScaleCodec(), getEmissiveCodec());
    }

    public static <T extends AbstractExplosiveParticleEffect> RecordCodecBuilder<T, Boolean> getWaterCodec() {
        return Codec.BOOL.optionalFieldOf("water", false).forGetter(AbstractExplosiveParticleEffect::isWater);
    }

    public static <T extends AbstractExplosiveParticleEffect> RecordCodecBuilder<T, Float> getScaleCodec() {
        return Codec.FLOAT.optionalFieldOf("scale", 4f).forGetter(AbstractExplosiveParticleEffect::getScale);
    }

    public static <T extends AbstractExplosiveParticleEffect> RecordCodecBuilder<T, Boolean> getEmissiveCodec() {
        return Codec.BOOL.optionalFieldOf("emissive", true).forGetter(AbstractExplosiveParticleEffect::isEmissive);
    }

    /**
     * Same thing as earlier, a simple reusable default PacketCodec. Goes from:
     * <pre>
     *     {@code
     *      PacketCodec.tuple(
     *          PacketCodecs.BOOLEAN, ExplosiveParticleEffect::isWater,
     *          PacketCodecs.FLOAT, ExplosiveParticleEffect::getScale,
     *          PacketCodecs.BOOLEAN, ExplosiveParticleEffect::isEmissive,
     *          ExplosiveParticleEffect::new
     *      );
     *     }
     * </pre>
     * to this sweet one-line solution:
     * <pre>
     *     {@code
     *     createPacketCodec(ExplosiveParticleEffect::new);
     *     }
     * </pre>
     * Each example from earlier also has a PacketCodec, if you want more examples.
     */
    public static <T extends AbstractExplosiveParticleEffect> StreamCodec<RegistryFriendlyByteBuf, T> createWaterPacketCodec(Function3<Boolean, Float, Boolean, T> applyFunction) {
        return StreamCodec.composite(
                ByteBufCodecs.BOOL, AbstractExplosiveParticleEffect::isWater,
                ByteBufCodecs.FLOAT, AbstractExplosiveParticleEffect::getScale,
                ByteBufCodecs.BOOL, AbstractExplosiveParticleEffect::isEmissive,
                applyFunction
        );
    }

    /**
     * Alternative PacketCodec for particles with no water variant.
     */
    public static <T extends AbstractExplosiveParticleEffect> StreamCodec<RegistryFriendlyByteBuf, T> createPacketCodec(BiFunction<Float, Boolean, T> applyFunction) {
        return StreamCodec.composite(
                ByteBufCodecs.FLOAT, AbstractExplosiveParticleEffect::getScale,
                ByteBufCodecs.BOOL, AbstractExplosiveParticleEffect::isEmissive,
                applyFunction
        );
    }

    private final boolean water;
    private final float scale;
    private final boolean emissive;

    public AbstractExplosiveParticleEffect(boolean water, float scale, boolean emissive) {
        this.water = water;
        this.scale = scale;
        this.emissive = emissive;
    }

    public AbstractExplosiveParticleEffect(float scale, boolean emissive) {
        this(false, scale, emissive);
    }

    /**
     * Used for particles with water variants (e.g. blastwave & underwater blastwave)
     */
    public boolean isWater() {
        return water;
    }

    public float getScale() {
        return this.scale;
    }

    public boolean isEmissive() {
        return this.emissive;
    }
}
