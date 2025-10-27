package net.superkat.explosiveenhancement.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;

/**
 * Despite this being a Server-To-Client packet, IT DOES NOT LOAD ON THE SERVER AS OF 1.3.0!!
 * <br><br>Explosive Enhancement is set to a "client" mod in the fabric.mod.json, meaning it is not loaded on a server environment.
 * <br><br>This packet is used to dynamically scale explosions in single player. I may use it for a server environment in the future.
 */
public record S2CExplosiveEnhancementParticles(
        double x, double y, double z, float power, ParticleEffect initParticle
) implements CustomPayload {
    public static final CustomPayload.Id<S2CExplosiveEnhancementParticles> ID = new Id<>(Identifier.of(ExplosiveEnhancement.MOD_ID));
    public static final PacketCodec<RegistryByteBuf, S2CExplosiveEnhancementParticles> CODEC = CustomPayload.codecOf(S2CExplosiveEnhancementParticles::write, S2CExplosiveEnhancementParticles::new);

    public S2CExplosiveEnhancementParticles(RegistryByteBuf buf) {
        this(
                buf.readDouble(),
                buf.readDouble(),
                buf.readDouble(),
                buf.readFloat(),
                ParticleTypes.PACKET_CODEC.decode(buf)
        );
    }

    public void write(RegistryByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeFloat(this.power);
        ParticleTypes.PACKET_CODEC.encode(buf, this.initParticle);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
