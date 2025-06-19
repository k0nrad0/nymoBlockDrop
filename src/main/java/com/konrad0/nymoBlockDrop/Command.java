package com.konrad0.nymoBlockDrop;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class Command implements CommandExecutor {

    private final ConfigMenu configMenu;
    private final NymoBlockDrop plugin;
    Command(NymoBlockDrop plugin){
        this.plugin = plugin;
        this.configMenu = new ConfigMenu(this.plugin);

    }



    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(sender instanceof Player){

            ConfigMenu configMenu = new ConfigMenu(plugin);
            Player player = (Player) sender;
            sender.sendMessage(ChatColor.RED+"POZDRAWIAM");
            configMenu.openMenu(player);

        }

        return false;
    }
}
