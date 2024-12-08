package net.superkat.explosiveenhancement.particles.normal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

//? if (1.19.2) {
/*import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
*///?} else {
import org.joml.Quaternionf;
import org.joml.Vector3f;
//?}

import static net.superkat.explosiveenhancement.ExplosiveEnhancementClient.CONFIG;

@Environment(EnvType.CLIENT)
public class BlastWaveParticle extends SpriteBillboardParticle {
    private final SpriteProvider sprites;
    //? if (1.19.2) {
    /*private static final Quaternion QUATERNION = new Quaternion(0F, -0.7F, 0.7F, 0F);
    *///?} else {
    private static final Quaternionf QUATERNION = new Quaternionf(0F, -0.7F, 0.7F, 0F);
    //?}

    public BlastWaveParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider sprites) {
        super(world, x, y + 0.5, z, 0.0, 0.0, 0.0);
        this.scale = (float) velX;
        this.setVelocity(0, 0, 0);
        this.maxAge = (int) (15 + (Math.floor(this.scale / 5)));
        this.sprites = sprites;
        this.setSpriteForAge(sprites);
    }


    @Override
    public void buildGeometry(VertexConsumer buffer, Camera camera, float ticks) {
        Vec3d vec3 = camera.getPos();
        float x = (float) (MathHelper.lerp(ticks, this.prevPosX, this.x) - vec3.getX());
        float y = (float) (MathHelper.lerp(ticks, this.prevPosY, this.y) - vec3.getY());
        float z = (float) (MathHelper.lerp(ticks, this.prevPosZ, this.z) - vec3.getZ());

        //? if (1.19.2) {
        /*Vec3f[] vector3fs = new Vec3f[]{new Vec3f(-1.0F, -1.0F, 0.0F), new Vec3f(-1.0F, 1.0F, 0.0F), new Vec3f(1.0F, 1.0F, 0.0F), new Vec3f(1.0F, -1.0F, 0.0F)};
        // Additional vertices for underside faces
        Vec3f[] vector3fsBottom = new Vec3f[]{new Vec3f(-1.0F, -1.0F, 0.0F), new Vec3f(1.0F, -1.0F, 0.0F), new Vec3f(1.0F, -1.0F, 0.0F), new Vec3f(-1.0F, -1.0F, 0.0F)};
        *///?} else {
        Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        // Additional vertices for underside faces
        Vector3f[] vector3fsBottom = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, -1.0F, 0.0F)};
        //?}

        float f4 = this.getSize(ticks);

        for (int i = 0; i < 4; ++i) {
            //? if (1.19.2) {
            /*Vec3f vector3f = vector3fs[i];
            vector3f.rotate(QUATERNION);
            vector3f.scale(f4);
            vector3f.add(x, y, z);

            // Create additional vertices for underside faces
            Vec3f vector3fBottom = vector3fsBottom[i]; //this dont' even get used what why huh what
            vector3fBottom.rotate(QUATERNION);
            vector3fBottom.scale(f4);
            vector3fBottom.add(x, y - 0.1F, z); // Slightly lower to avoid z-fighting
            *///?} else {
            Vector3f vector3f = vector3fs[i];
            vector3f.rotate(QUATERNION);
            vector3f.mul(f4);
            vector3f.add(x, y + 0.01f, z); //slightly higher to avoid z-fighting with bottom particle and hopefully ground-level blocks as well

            // Create additional vertices for underside faces
            Vector3f vector3fBottom = vector3fsBottom[i];
            vector3fBottom.rotate(QUATERNION);
            vector3fBottom.mul(f4);
            vector3fBottom.add(x, y, z);
            //?}
        }

        float f7 = this.getMinU();
        float f8 = this.getMaxU();
        float f5 = this.getMinV();
        float f6 = this.getMaxV();
        int light = this.getBrightness(ticks);

        //? if (1.19.2) {
        /*// Render the top faces
        buffer.vertex(vector3fs[0].getX(), vector3fs[0].getY(), vector3fs[0].getZ()).texture(f8, f6).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        buffer.vertex(vector3fs[1].getX(), vector3fs[1].getY(), vector3fs[1].getZ()).texture(f8, f5).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        buffer.vertex(vector3fs[2].getX(), vector3fs[2].getY(), vector3fs[2].getZ()).texture(f7, f5).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        buffer.vertex(vector3fs[3].getX(), vector3fs[3].getY(), vector3fs[3].getZ()).texture(f7, f6).color(this.red, this.green, this.blue, this.alpha).light(light).next();

        // Render the underside faces
        buffer.vertex(vector3fs[3].getX(), vector3fs[3].getY(), vector3fs[3].getZ()).texture(f7, f6).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        buffer.vertex(vector3fs[2].getX(), vector3fs[2].getY(), vector3fs[2].getZ()).texture(f7, f5).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        buffer.vertex(vector3fs[1].getX(), vector3fs[1].getY(), vector3fs[1].getZ()).texture(f8, f5).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        buffer.vertex(vector3fs[0].getX(), vector3fs[0].getY(), vector3fs[0].getZ()).texture(f8, f6).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        *///?} else {
        // Render the top faces
        buffer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(f8, f6).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(f8, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(f7, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(f7, f6).color(this.red, this.green, this.blue, this.alpha).light(light);

        // Render the underside faces
        buffer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(f7, f6).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(f7, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(f8, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(f8, f6).color(this.red, this.green, this.blue, this.alpha).light(light);
        //?}
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    //Makes the particle emissive
    @Override
    protected int getBrightness(float tint) {
        return CONFIG.emissiveExplosion ? 15728880 : super.getBrightness(tint);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(this.sprites);
    }

    @Environment(EnvType.CLIENT)
    public record Factory<T extends ParticleEffect>(SpriteProvider sprites) implements ParticleFactory<T> {
        public Particle createParticle(T type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BlastWaveParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}