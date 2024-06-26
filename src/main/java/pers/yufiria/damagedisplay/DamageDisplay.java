package pers.yufiria.damagedisplay;

import crypticlib.BukkitPlugin;
import pers.yufiria.damagedisplay.listener.DamageListener;

public class DamageDisplay extends BukkitPlugin {

    public static DamageDisplay INSTANCE;

    @Override
    public void enable() {
        INSTANCE = this;
    }

    @Override
    public void disable() {
        DamageListener.INSTANCE.clearAllTextDisplay();
    }

}