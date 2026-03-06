package me.tronrp.tr0nrp.chat;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.ArrayList;

public class ShowVoix implements CommandExecutor, Listener {

    public static ArrayList<Player> showvoix = new ArrayList<>();
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = (Player) sender;
        if(showvoix.contains(player)){
            showvoix.remove(player);
            player.sendMessage(prefixcmd + "Vous ne voyez plus la portée de votre voix.");
        }else{
            showvoix.add(player);
            player.sendMessage(prefixcmd + "Vous affichez la portée de votre voix. (sneak)");
        }
        return false;
    }

    @EventHandler
    public boolean onPlayer(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        ArrayList<Player> showvoix = ShowVoix.showvoix;
        Integer num = SpeakRange.speakrange.get(player);
        if(showvoix.contains(player)) {
            player.spawnParticle(Particle.END_ROD, loc.getX()+num,loc.getY()+0.5,loc.getZ()+num,0);
            player.spawnParticle(Particle.END_ROD, loc.getX()+num,loc.getY()+0.5,loc.getZ()-num, 0);
            player.spawnParticle(Particle.END_ROD, loc.getX()-num,loc.getY()+0.5,loc.getZ()+num, 0);
            player.spawnParticle(Particle.END_ROD, loc.getX()-num,loc.getY()+0.5,loc.getZ()-num, 0);
        }
        return false;
    }

}