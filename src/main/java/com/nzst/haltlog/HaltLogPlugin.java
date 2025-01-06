package com.nzst.haltlog;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
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

        // Set a custom filter to handle log suppression
        Logger rootLogger = getServer().getLogger();
        rootLogger.setFilter(new LogFilter());
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

    private class LogFilter implements Filter {
        @Override
        public boolean isLoggable(LogRecord record) {
            if (hideAny) return false;
            if (hideError && record.getLevel() == Level.SEVERE) return false;
            if (hideWarn && record.getLevel() == Level.WARNING) return false;

            return true; // Allow other logs
        }
    }
}
