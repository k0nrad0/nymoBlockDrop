package com.konrad0.nymoBlockDrop;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class DataManager {
    private final JavaPlugin plugin;
    private final File file;
    private FileConfiguration config;

    public DataManager(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), fileName);
        load();
    }

    public void load() {
        if (!file.exists()) {
            plugin.saveResource(file.getName(), false); // lub utwórz pusty plik
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}