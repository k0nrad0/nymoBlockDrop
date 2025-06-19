package com.konrad0.nymoBlockDrop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class NymoBlockDrop extends JavaPlugin {


    private DataManager dataManager;

    @Override
    public void onEnable() {
        System.out.println("nymoBlockDrop enabled :D");
        getCommand("nymoblockdrop").setExecutor(new Command(this));
        Bukkit.getPluginManager().registerEvents(new MenuListener(this), this);



        dataManager = new DataManager(this, "blocks.yml");
        FileConfiguration config = dataManager.getConfig();

        if (config.getKeys(false).isEmpty()) {
            config.set("block1.material", "STONE");
            config.set("block1.drop1.material", "SAND");
            config.set("block1.drop1.quantity", 1);
            dataManager.save();
        }

        ConfigMenu configMenu = new ConfigMenu(this);
        Object[] data = configMenu.readData();

        Bukkit.getPluginManager().registerEvents(new DropListener(this, data), this);


    }



    public DataManager getDataManager() {
        return dataManager;
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
