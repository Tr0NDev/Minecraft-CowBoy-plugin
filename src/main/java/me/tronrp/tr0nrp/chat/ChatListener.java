package me.tronrp.tr0nrp.chat;

import me.tronrp.tr0nrp.metier.Facteur;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatListener implements Listener {

    public static ArrayList<Player> listejoueurs = new ArrayList<>();

    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        HashMap speakrange = SpeakRange.speakrange;
        speakrange.put(player,2);
        listejoueurs.add(player);
        return false;
    }
    @EventHandler
    public boolean onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        listejoueurs.remove(player);
        return false;
    }

    @EventHandler
    public boolean onPlayerChat(PlayerChatEvent event){
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = event.getPlayer();
        HashMap clickmessage = ClickMessage.clickmessage;
        if(clickmessage.containsKey(player)){
            Player receiver = (Player) clickmessage.get(player);
            player.sendMessage(prefixcmd + "§dVous à §5" + receiver.getDisplayName() + "§r§d: " + event.getMessage());
            receiver.sendMessage(prefixcmd + "§5" + player.getDisplayName() + "§r§d à Vous: " + event.getMessage());
            event.setCancelled(true);
            return false;

        }else if(Facteur.chatlistener.containsKey(player)){
            return false;
        }else{
            Location pos = player.getLocation();
            HashMap speakrange = SpeakRange.speakrange;
            if(speakrange.containsKey(player)){
                ArrayList<String> liste = new ArrayList<>();
                Integer num = (Integer) speakrange.get(player);
                for(Player receiver : Bukkit.getOnlinePlayers()){
                    Location posreceiver = receiver.getLocation();
                    if(!(receiver==player)){
                        if(!(pos.getX()<=posreceiver.getX()+num)){
                            event.setCancelled(true);
                            break;
                        }
                        if(!(posreceiver.getX()-num<=pos.getX())){
                            event.setCancelled(true);
                            break;
                        }
                        if(!(pos.getZ()<=posreceiver.getZ()+num)){
                            event.setCancelled(true);
                            break;
                        }
                        if(!(posreceiver.getZ()-num<=pos.getZ())){
                            event.setCancelled(true);
                            break;
                        }
                        event.setCancelled(true);
                        TextComponent msg = new TextComponent("§e" + player.getDisplayName() + ": " + event.getMessage());
                        TextComponent tete = new TextComponent("§2[§a" + num + "§2]§e ");
                        tete.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aModifier la Voix").create()));
                        tete.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/voix"));
                        tete.addExtra(msg);
                        receiver.spigot().sendMessage(tete);
                        liste.add(receiver.getDisplayName());
                    }
                }
                if(liste.isEmpty() == true){
                    player.sendMessage(prefixcmd + "§cPersonne ne vous a entendu.");
                    event.setCancelled(true);
                    return false;
                }else{
                    TextComponent msg = new TextComponent("§e" + player.getDisplayName() + ": " + event.getMessage());
                    msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§a"+String.valueOf(liste)).create()));
                    TextComponent tete = new TextComponent("§2[§a" + num + "§2]§e ");
                    tete.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aModifier la Voix").create()));
                    tete.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/voix"));
                    tete.addExtra(msg);
                    player.spigot().sendMessage(tete);
                }
            }else{
                return false;
            }
        }
        return false;
    }
}
