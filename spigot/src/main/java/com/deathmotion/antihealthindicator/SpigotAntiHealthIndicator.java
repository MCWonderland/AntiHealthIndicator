package com.deathmotion.antihealthindicator;

import com.deathmotion.antihealthindicator.enums.ConfigOption;
import com.deathmotion.antihealthindicator.managers.ConfigManager;
import io.github.retrooper.packetevents.bstats.Metrics;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

@Getter
public class SpigotAntiHealthIndicator extends AHIPlatform<JavaPlugin> {
    private final JavaPlugin plugin;
    private final ConfigManager configManager;

    public SpigotAntiHealthIndicator(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configManager = new ConfigManager(plugin);
    }

    @Override
    public JavaPlugin getPlatform() {
        return this.plugin;
    }

    @Override
    public boolean hasPermission(UUID sender, String permission) {
        CommandSender commandSender = Bukkit.getPlayer(sender);
        if (commandSender == null) return false;

        return commandSender.hasPermission(permission);
    }

    @Override
    public boolean getConfigurationOption(ConfigOption option) {
        return this.configManager.getConfigurationOption(option);
    }

    @Override
    public String getPluginVersion() {
        return this.plugin.getDescription().getVersion();
    }

    public void enableBStats() {
        try {
            new Metrics(this.plugin, 20803);
        } catch (Exception e) {
            this.plugin.getLogger().warning("Something went wrong while enabling bStats.\n" + e.getMessage());
        }
    }
}