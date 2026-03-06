package me.tronrp.tr0nrp.chat;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class SetSpeakRange implements CommandExecutor , Listener {
    public static HashMap<Player, Integer> speakrange = new HashMap<>();
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = (Player) sender;
        if(args.length<=1){
            player.sendMessage(prefixcmd + "§cSyntaxe invalide.");
        }else if (args.length == 2) {
            try{
                Integer.parseInt(args[1]);
            }catch(NumberFormatException e){
                player.sendMessage(prefixcmd + "§cSyntaxe ou commande invalide.");
                return false;
            }
            Player receiver = Bukkit.getPlayerExact(args[0]);
            if (receiver == null) {
                player.sendMessage(prefixcmd + "Joueur introuvable.");
                return false;
            }
            HashMap speakrange = SpeakRange.speakrange;
            speakrange.put(receiver,args[1]);
            System.out.println(speakrange);
            }
        return false;
    }
}
