package pers.yufiria.damagedisplay.listener;

import crypticlib.CrypticLib;
import crypticlib.chat.TextProcessor;
import crypticlib.listener.BukkitListener;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import pers.yufiria.damagedisplay.DamageDisplay;
import pers.yufiria.damagedisplay.config.PluginConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@BukkitListener
public class DamageListener implements Listener {

    public static final DamageListener INSTANCE = new DamageListener();
    private final List<TextDisplay> textDisplays = new ArrayList<>();
    private final Random random = new Random();

    private DamageListener() {}

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamageSource().getCausingEntity() instanceof LivingEntity))
            return;
        if (!(event.getEntity() instanceof LivingEntity entity))
            return;

        Location location = entity.getEyeLocation();
        LocationType locationType = LocationType.valueOf(PluginConfig.locationType.value().toUpperCase().replace("-", "_"));
        switch (locationType) {
            case EYE_LOCATION -> {
                location = entity.getEyeLocation();
            }
            case LOCATION -> {
                location = entity.getLocation();
            }
            default -> {
                Location loc1 = entity.getEyeLocation().clone();
                Location loc2 = entity.getLocation().clone();
                double y = (loc1.getY() + loc2.getY()) / 2;
                location = loc1.set(loc1.getX(), y, loc1.getZ());
            }
        }
        location = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());

        boolean critical;
        if (CrypticLib.platform().isPaper()) {
            critical = event.isCritical();
        } else {
            critical = false;
        }
        summonDamageDisplay(location, event.getFinalDamage(), critical);
    }

    public void summonDamageDisplay(Location location, double damage, boolean critical) {
        World world = location.getWorld();
        if (world == null)
            return;
        String damageStr;
        if (critical) {
            damageStr = PluginConfig.criticalDamageFormat.value();
        } else {
            damageStr = PluginConfig.damageFormat.value();
        }
        damageStr = String.format(damageStr, damage);
        damageStr = TextProcessor.color(damageStr);
        TextDisplay textDisplay = world.spawn(location, TextDisplay.class);
        textDisplay.setText(damageStr);
        textDisplay.setGravity(false);
        textDisplay.setAlignment(TextDisplay.TextAlignment.CENTER);
        textDisplay.setDefaultBackground(false);
        textDisplay.setBackgroundColor(Color.fromARGB(10, 1, 1, 1));
        textDisplay.setBillboard(Display.Billboard.VERTICAL);
        textDisplay.setTeleportDuration(PluginConfig.moveDuration.value());
        textDisplay.setInterpolationDuration(PluginConfig.moveDuration.value());
        if (critical) {
            float scale = PluginConfig.criticalTextScale.value().floatValue();
            textDisplay.setTransformation(
                new Transformation(
                    new Vector3f(),
                    new AxisAngle4f(),
                    new Vector3f(scale, scale, scale),
                    new AxisAngle4f()
                )
            );
        }
        textDisplays.add(textDisplay);
        CrypticLib.platform().scheduler().runTaskLater(DamageDisplay.INSTANCE, () -> {
            double offsetX = PluginConfig.offsetX.value();
            double offsetY = PluginConfig.offsetY.value();
            double offsetZ = PluginConfig.offsetZ.value();
            CrypticLib.platform().teleportEntity(
                textDisplay,
                location.add(
                    random.nextDouble(-offsetX, offsetX),
                    PluginConfig.upHeight.value() + random.nextDouble(-offsetY, offsetY),
                    random.nextDouble(-offsetZ, offsetZ)
                )
            );
        }, 2L);
        CrypticLib.platform().scheduler().runTaskLater(DamageDisplay.INSTANCE, () -> {
            textDisplay.remove();
            textDisplays.remove(textDisplay);
        }, PluginConfig.removeTick.value());
    }

    public void clearAllTextDisplay() {
        if (CrypticLib.minecraftVersion() >= 11904) {
            return;
        }
        for (TextDisplay textDisplay : textDisplays) {
            textDisplay.remove();
        }
    }

    enum LocationType {
        EYE_LOCATION, LOCATION, MIDDLE
    }

}
