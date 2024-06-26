package pers.yufiria.damagedisplay.config;

import crypticlib.config.ConfigHandler;
import crypticlib.config.entry.DoubleConfigEntry;
import crypticlib.config.entry.IntConfigEntry;
import crypticlib.config.entry.StringConfigEntry;

@ConfigHandler(path = "config.yml")
public class PluginConfig {

    public static final StringConfigEntry damageFormat = new StringConfigEntry("damage-format", "&c❤ %.2f");
    public static final IntConfigEntry textDisplayRemoveTick = new IntConfigEntry("text-display.remove-tick", 25);
    public static final IntConfigEntry textDisplayTeleportDuration = new IntConfigEntry("text-display.teleport-duration", 3);
    public static final DoubleConfigEntry textDisplayUpHeight = new DoubleConfigEntry("text-display.up-height", 0.6);
    public static final DoubleConfigEntry textDisplayOffsetX = new DoubleConfigEntry("text-display.offset.x", 0.4);
    public static final DoubleConfigEntry textDisplayOffsetY = new DoubleConfigEntry("text-display.offset.y", 0.2);
    public static final DoubleConfigEntry textDisplayOffsetZ = new DoubleConfigEntry("text-display.offset.z", 0.4);

}
