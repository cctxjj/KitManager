package net.ctxj.herowars.kitmanager;

import org.bukkit.plugin.java.JavaPlugin;

public final class KitManager extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("kit").setExecutor(new KitCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
