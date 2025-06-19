package com.konrad0.nymoBlockDrop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

public class DropListener implements Listener {

    private final NymoBlockDrop plugin;
    private final ArrayList<String> materialsList;
    private final ArrayList<Dictionary> fullDropsList;

    public DropListener(NymoBlockDrop plugin, Object[] data){
        this.plugin = plugin;
        this.materialsList = (ArrayList<String>) data[0];
        this.fullDropsList = (ArrayList<Dictionary>) data[1];

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        e.getPlayer().sendMessage("zniszczyłeś blok: " + e.getBlock().getType().name());

        for (int i = 0; i < materialsList.size(); i++) {

            Bukkit.getLogger().info(materialsList.get(i));

            if(e.getBlock().getType().name().equals(materialsList.get(i))){
                e.getPlayer().sendMessage("zniszeyłeś blok z listy :)");

                e.setDropItems(false);

                Enumeration<String> k = fullDropsList.get(i).keys();
                while (k.hasMoreElements()) {
                    String key = k.nextElement();
                    ItemStack drop = new ItemStack(Material.valueOf(key), (Integer) fullDropsList.get(i).get(key));
                    e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), drop);

                }




            }
        }
    }

}
