package com.konrad0.nymoBlockDrop;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.IOException;

public class MenuListener implements Listener {


    private final NymoBlockDrop plugin;

    public MenuListener(NymoBlockDrop plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) throws IOException {

        if(ChatColor.translateAlternateColorCodes('&',e.getView().getTitle()).equals(ChatColor.RED.toString() + ChatColor.BOLD + "NYMO_BLOCK_DROP")
        && e.getCurrentItem() != null){

            switch (e.getRawSlot()){
                case 7, 25, 16, 34, 43, 52, 1, 10, 19, 28, 37, 46:
                    e.setCancelled(true);
                    break;
                case 8:
                    ConfigMenu configMenu = new ConfigMenu(plugin);
                    configMenu.saveToCofnig((Player) e.getWhoClicked());
                    e.getWhoClicked().closeInventory();
                    break;
            }
        }

    }
}
