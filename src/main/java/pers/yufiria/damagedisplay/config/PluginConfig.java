package pers.yufiria.damagedisplay.config;

import crypticlib.config.ConfigHandler;
import crypticlib.config.entry.DoubleConfigEntry;
import crypticlib.config.entry.IntConfigEntry;
import crypticlib.config.entry.StringConfigEntry;

@ConfigHandler(path = "config.yml")
public class PluginConfig {

    public static final StringConfigEntry damageFormat = new StringConfigEntry("damage-format", "&c❤ %.2f");
    public static final StringConfigEntry criticalDamageFormat = new StringConfigEntry("critical-damage-format", "&4&l❤ %.2f");
    public static final IntConfigEntry removeTick = new IntConfigEntry("remove-tick", 25);
    public static final IntConfigEntry moveDuration = new IntConfigEntry("teleport-duration", 3);
    public static final DoubleConfigEntry upHeight = new DoubleConfigEntry("up-height", 0.6);
    public static final DoubleConfigEntry offsetX = new DoubleConfigEntry("offset.x", 0.4);
    public static final DoubleConfigEntry offsetY = new DoubleConfigEntry("offset.y", 0.2);
    public static final DoubleConfigEntry offsetZ = new DoubleConfigEntry("offset.z", 0.4);
    public static final DoubleConfigEntry criticalTextScale = new DoubleConfigEntry("critical-text-scale", 1.5);
    public static final StringConfigEntry locationType = new StringConfigEntry("location-type", "eye-location");

}
