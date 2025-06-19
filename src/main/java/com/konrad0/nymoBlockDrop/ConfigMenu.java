package com.konrad0.nymoBlockDrop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;


public class ConfigMenu {

    private final NymoBlockDrop plugin;
    private DataManager dataManager;

    private ArrayList<String> materialsList = new ArrayList<String>();
    private ArrayList<Dictionary> fullDropsList = new ArrayList<Dictionary>();

    public ConfigMenu(NymoBlockDrop plugin){
//        dataManager = new DataManager(plugin);
        this.plugin = plugin;
    }



    //read data from json
    public Object[] readData(){


        DataManager dataManager = this.plugin.getDataManager();
        FileConfiguration config = dataManager.getConfig();

        int blocksAmount = config.getKeys(false).size();


        for (int i = 1; i <= blocksAmount; i++) {
            ConfigurationSection section = config.getConfigurationSection(String.format(String.format("block%s", i)));
            if(section == null){
                blocksAmount++;
                continue;
            }
            int dropsAmount = section.getKeys(false).size() - 1;
            String material = section.getString("material");
            Dictionary<String, Integer> dropsTable = new Hashtable<>();


            for (int j = 1; j <= dropsAmount; j++) {
                ConfigurationSection dropSection = config.getConfigurationSection(String.format(String.format("block%s.drop%s", i,j)));
                if(dropSection == null){
                    dropsAmount++;
                    continue;
                }
                String dropMaterial = dropSection.getString("material");
                int dropQuantity = Integer.parseInt(dropSection.getString("quantity"));
                dropsTable.put(dropMaterial,dropQuantity);
            }

            materialsList.add(material);
            fullDropsList.add(dropsTable);

        }

        //return for change drops
        return new Object[]{materialsList, fullDropsList};

    }

    private Inventory createMenu(){

        readData();

        Inventory configMenuInv = Bukkit.createInventory(null, 54, ChatColor.RED.toString() + ChatColor.BOLD + "NYMO_BLOCK_DROP");
        ItemStack save = new ItemStack(Material.GREEN_TERRACOTTA, 1);
        ItemMeta data = save.getItemMeta();
        data.setDisplayName(ChatColor.GREEN + "SAVE AND LEAVE");
        save.setItemMeta(data);

        configMenuInv.setItem(8, save);

        for (int i = 0; i < 6; i++) {
            configMenuInv.setItem(7+i*9, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            configMenuInv.setItem(1+i*9, new ItemStack(Material.ORANGE_STAINED_GLASS_PANE));
        }



        for (int i = 0; i < materialsList.size(); i++) {
            ItemStack item = new ItemStack(Material.valueOf(materialsList.get(i)),1);
            configMenuInv.setItem(9*i, item);

            int j = 2;
            Enumeration<String> k = fullDropsList.get(i).keys();
            while (k.hasMoreElements()) {
                String key = k.nextElement();
                ItemStack drop = new ItemStack(Material.valueOf(key), (Integer) fullDropsList.get(i).get(key));
                configMenuInv.setItem(9 * i + j, drop);
                j++;
            }

        }

        return configMenuInv;
    }

    public void saveToCofnig(Player player) throws IOException {

        DataManager dataManager = this.plugin.getDataManager();
        FileConfiguration config = dataManager.getConfig();

        for (String key : config.getKeys(false)) {
            config.set(key, null); // usuwa klucz
        }

        Inventory saveInv = player.getOpenInventory().getTopInventory();

        for (int i = 1; i <= 6; i++) {

            if(saveInv.getItem(i*9-9) != null){

                config.set("block" + i + ".material", saveInv.getItem(i*9-9).getType().name());

                for (int j = 1; j <= 5; j++) {

                    if(saveInv.getItem(i*9+1-9+j) != null){
                        Bukkit.getLogger().info(i + " " + j);
                        config.set("block" + i + ".drop" + j + ".material", saveInv.getItem(i*9+1+j-9).getType().name());
                        config.set("block" + i + ".drop" + j + ".quantity", saveInv.getItem(i*9+1+j-9).getAmount());
                    }

                }

            }
        }

        dataManager.save();

    }

    public void openMenu(Player player){

        Inventory configMenuInv = createMenu();
        player.openInventory(configMenuInv);
    }
}
