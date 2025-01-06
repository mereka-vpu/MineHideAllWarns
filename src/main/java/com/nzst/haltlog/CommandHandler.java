package com.nzst.haltlog;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {
    private final HaltLogPlugin plugin;

    public CommandHandler(HaltLogPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("Only console can execute this command.");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage("Usage: /haltlog <hide|restore> <1|2|3>");
            return true;
        }

        String action = args[0];
        int level;

        try {
            level = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid level. Use 1 for Error, 2 for Warn, or 3 for Any.");
            return true;
        }

        switch (action.toLowerCase()) {
            case "hide" -> {
                plugin.hideLogs(level);
                sender.sendMessage("Logs hidden for level " + level + ".");
            }
            case "restore" -> {
                plugin.restoreLogs(level);
                sender.sendMessage("Logs restored for level " + level + ".");
            }
            default -> sender.sendMessage("Invalid action. Use 'hide' or 'restore'.");
        }

        return true;
    }
}
