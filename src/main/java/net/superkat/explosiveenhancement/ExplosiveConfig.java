package net.superkat.explosiveenhancement;

import eu.midnightdust.lib.config.MidnightConfig;

public class ExplosiveConfig extends MidnightConfig {

    @Comment(centered = true) public static Comment normalExplosions;
    @Entry public static boolean showBoom = true;
    @Entry public static boolean showBigExplosion = true;
    @Entry public static boolean showLingerParticles = true;
    @Entry public static boolean showDefaultExplosion = false;
    @Comment(centered = true) public static Comment underwaterExplosions;
    @Entry public static BubbleEnum bubbleEnum = BubbleEnum.MEDIUM;
    public enum BubbleEnum {
        NONE, LOW, MEDIUM, HIGH, INSANELYHIGH
    }
    @Comment(centered = true) public static Comment extras;
    @Entry public static boolean modEnabled = true;

}
