package me.tronrp.tr0nrp.chat;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class ClickMessage implements CommandExecutor, Listener {

    public static HashMap<Player, Player> clickmessage = new HashMap<>();
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = (Player) sender;
        if(args.length==0){
            player.sendMessage(prefixcmd + "§cSyntaxe invalide.");
        }else{
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

    Integer num = 0;
    @EventHandler
    public boolean onPlayerClick(final PlayerInteractEntityEvent event) {
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = event.getPlayer();
        Entity receiver = event.getRightClicked();
        ItemStack item = event.getPlayer().getItemInHand();
        if(!(item.getType() == Material.GOLDEN_SHOVEL && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aRevolver"))){
            if(receiver instanceof Player){
            ArrayList listejoueurs = ChatListener.listejoueurs;
            if(listejoueurs.contains(receiver)){
                num = num + 1;
                if(num==2){
                    num = 0;
                    if(!(clickmessage.containsKey(player))){
                        player.sendMessage(prefixcmd + "Vous entrez en conversation avec §2" + receiver.getName() + "§r§a.");
                        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                        clickmessage.put(player, (Player) receiver);
                    }else{
                        player.sendMessage(prefixcmd + "Vous sortez de la conversation avec §2" + receiver.getName() + "§r§a.");
                        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                        clickmessage.remove(player, (Player) receiver);
                        }
                    }
                }
            }
        }
        return false;
    }
    @EventHandler
    public boolean onMove(PlayerMoveEvent event){
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = event.getPlayer();
        if(clickmessage.containsKey(player)){
            Player receiver = clickmessage.get(player);
            Location posreceiver = receiver.getLocation();
            Location pos = player.getLocation();
            player.spawnParticle(Particle.FLAME, posreceiver.getX(),posreceiver.getY()+2.3,posreceiver.getZ(),0);
            if(pos.getX()>=posreceiver.getX()+5){
                player.sendMessage(prefixcmd + "Vous sortez de la conversation avec §2" + receiver.getName() + "§r§a.");
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                clickmessage.remove(player, (Player) receiver);
                return false;
            }
            if(pos.getX()<=posreceiver.getX()-5){
                player.sendMessage(prefixcmd + "Vous sortez de la conversation avec §2" + receiver.getName() + "§r§a.");
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                clickmessage.remove(player, (Player) receiver);
                return false;
            }
            if(pos.getZ()>=posreceiver.getZ()+5){
                player.sendMessage(prefixcmd + "Vous sortez de la conversation avec §2" + receiver.getName() + "§r§a.");
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                clickmessage.remove(player, (Player) receiver);
                return false;
            }
            if(pos.getZ()<=posreceiver.getZ()-5){
                player.sendMessage(prefixcmd + "Vous sortez de la conversation avec §2" + receiver.getName() + "§r§a.");
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                clickmessage.remove(player, (Player) receiver);
                return false;
            }
            if(pos.getY()>=posreceiver.getY()+5){
                player.sendMessage(prefixcmd + "Vous sortez de la conversation avec §2" + receiver.getName() + "§r§a.");
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                clickmessage.remove(player, (Player) receiver);
                return false;
            }
            if(pos.getY()<=posreceiver.getY()-5){
                player.sendMessage(prefixcmd + "Vous sortez de la conversation avec §2" + receiver.getName() + "§r§a.");
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                clickmessage.remove(player, (Player) receiver);
                return false;
            }
        }
        return false;
    }
}
