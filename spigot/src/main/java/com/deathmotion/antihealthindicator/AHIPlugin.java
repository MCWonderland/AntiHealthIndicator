package com.deathmotion.antihealthindicator;

import com.deathmotion.antihealthindicator.scheduler.SchedulerAdapter;
import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public class AHIPlugin extends JavaPlugin {
    private final SpigotAntiHealthIndicator ahi = new SpigotAntiHealthIndicator(this);

    public void onLoad() {
        ahi.commonOnLoad();

        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().getSettings().reEncodeByDefault(false)
                .checkForUpdates(false)
                .bStats(true);

        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        new SchedulerAdapter();

        ahi.commonOnEnable();
        ahi.enableBStats();
    }

    @Override
    public void onDisable() {
        ahi.commonOnDisable();
    }
}