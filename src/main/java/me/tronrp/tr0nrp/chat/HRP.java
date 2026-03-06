package me.tronrp.tr0nrp.chat;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
public class HRP implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        String prefixcmd = "§2[§aTRN§2] §a";
        String prefix = "§1[§9HRP§1] §b";
        if (args.length == 0 ) {
            player.sendMessage(prefixcmd + "§cSyntaxe ou commande invalide.");
        }else {
            String msg = "";
            for (int i = 0; i < args.length; i++) {
                msg = msg + args[i] + " ";
            }
            Bukkit.broadcastMessage(prefix + player.getDisplayName() + ": " + msg);
            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
        }
        return false;
    }
}
