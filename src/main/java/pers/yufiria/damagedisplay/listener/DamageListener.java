package pers.yufiria.damagedisplay.listener;

import crypticlib.CrypticLib;
import crypticlib.chat.TextProcessor;
import crypticlib.listener.BukkitListener;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        if (!(event.getEntity() instanceof LivingEntity))
            return;
        Player player = (Player) event.getDamager();
        double finalDamage = event.getFinalDamage();
        String str = PluginConfig.damageFormat.value();
        str = String.format(str, finalDamage);
        str = TextProcessor.color(TextProcessor.placeholder(player, str));

        LivingEntity entity = (LivingEntity) event.getEntity();
        Location location = new Location(entity.getWorld(), entity.getEyeLocation().getX(), entity.getEyeLocation().getY(), entity.getEyeLocation().getZ());
        summonDamageDisplay(location, str);
    }

    public void summonDamageDisplay(Location location, String damageStr) {
        if (CrypticLib.minecraftVersion() >= 11904) {
            World world = location.getWorld();
            if (world == null)
                return;
            TextDisplay textDisplay = world.spawn(location, TextDisplay.class);
            textDisplay.setText(damageStr);
            textDisplay.setGravity(false);
            textDisplay.setAlignment(TextDisplay.TextAlignment.CENTER);
            textDisplay.setDefaultBackground(false);
            textDisplay.setBackgroundColor(Color.fromARGB(10, 1, 1, 1));
            textDisplay.setBillboard(Display.Billboard.VERTICAL);
            textDisplay.setTeleportDuration(PluginConfig.textDisplayTeleportDuration.value());
            textDisplay.setInterpolationDuration(PluginConfig.textDisplayTeleportDuration.value());
            textDisplays.add(textDisplay);
            CrypticLib.platform().scheduler().runTaskLater(DamageDisplay.INSTANCE, () -> {
                double offsetX = PluginConfig.textDisplayOffsetX.value();
                double offsetY = PluginConfig.textDisplayOffsetY.value();
                double offsetZ = PluginConfig.textDisplayOffsetZ.value();
                textDisplay.teleport(
                    location.add(
                        random.nextDouble(-offsetX, offsetX),
                        PluginConfig.textDisplayUpHeight.value() + random.nextDouble(-offsetY, offsetY),
                        random.nextDouble(-offsetZ, offsetZ)
                    ));
            }, 2L);
            CrypticLib.platform().scheduler().runTaskLater(DamageDisplay.INSTANCE, () -> {
                textDisplay.remove();
                textDisplays.remove(textDisplay);
            }, PluginConfig.textDisplayRemoveTick.value());
        } else {
            throw new UnsupportedOperationException("还没实现");
        }
    }

    public void clearAllTextDisplay() {
        if (CrypticLib.minecraftVersion() >= 11904) {
            return;
        }
        for (TextDisplay textDisplay : textDisplays) {
            textDisplay.remove();
        }
    }

}
