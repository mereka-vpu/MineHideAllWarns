package com.nzst.haltlog;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HaltLogPlugin extends JavaPlugin {
    private final Logger logger = getLogger();
    private boolean hideError = false;
    private boolean hideWarn = false;
    private boolean hideAny = false;

    @Override
    public void onEnable() {
        getLogger().info("HaltLog Plugin Enabled!");
        getCommand("haltlog").setExecutor(new CommandHandler(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("HaltLog Plugin Disabled!");
    }

    public void hideLogs(int level) {
        switch (level) {
            case 1 -> hideError = true;
            case 2 -> hideWarn = true;
            case 3 -> hideAny = true;
            default -> logger.warning("Invalid level specified for hideLogs.");
        }
    }

    public void restoreLogs(int level) {
        switch (level) {
            case 1 -> hideError = false;
            case 2 -> hideWarn = false;
            case 3 -> hideAny = false;
            default -> logger.warning("Invalid level specified for restoreLogs.");
        }
    }

    public boolean shouldHide(Level level) {
        if (hideAny) return true;
        return (hideError && level == Level.SEVERE) || (hideWarn && level == Level.WARNING);
    }
}
