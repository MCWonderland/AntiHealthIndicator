/*
 * This file is part of AntiHealthIndicator - https://github.com/Bram1903/AntiHealthIndicator
 * Copyright (C) 2024 Bram and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.deathmotion.antihealthindicator;

import com.deathmotion.antihealthindicator.schedulers.VelocityScheduler;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "antihealthindicator",
        name = "AntiHealthIndicator",
        version = "2.1.0",
        description = "Prevent health indicators from being displayed on the client",
        authors = {"Bram"},
        url = "https://github.com/Bram1903/AntiHealthIndicator",
        dependencies = {
                @Dependency(id = "packetevents"),
        }
)
public class AHIVelocity {
    private final VelocityAntiHealthIndicator ahi;
    private final ProxyServer server;

    @Inject
    public AHIVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.ahi = new VelocityAntiHealthIndicator(server, logger, dataDirectory);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        ahi.setScheduler(new VelocityScheduler(this.server));
        ahi.setConfigManager(new ConfigManager(this));

        ahi.commonOnEnable();
        ahi.enableBStats();
    }

    @Subscribe()
    public void onProxyShutdown(ProxyShutdownEvent event) {
        ahi.commonOnDisable();
    }
}