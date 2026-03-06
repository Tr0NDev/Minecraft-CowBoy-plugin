package me.tronrp.tr0nrp.chat;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashMap;

public class SpeakRange  implements CommandExecutor , Listener {
    public static HashMap<Player, Integer> speakrange = new HashMap<>();
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player player = (Player)sender;
        final Inventory menu = Bukkit.createInventory((InventoryHolder)null, 9, "§2[§aPuissance de la Voix§2]");
        if(ShowVoix.showvoix.contains(player)){
            menu.setItem(0, this.getItem(Material.ENDER_EYE, "§aPortée de la Voix", 1,"§eSneak pour afficher"));
        }else{
            menu.setItem(0, this.getItem(Material.ENDER_EYE, "§cPortée de la Voix", 1,"§eSneak pour afficher"));

        }
        menu.setItem(2, this.getItem(Material.BOOK, "§aChuchoter", 1,"§e1 block autour de vous"));
        menu.setItem(4, this.getItem(Material.BOOK, "§aParler", 2,"§e2 block autour de vous"));
        menu.setItem(6, this.getItem(Material.BOOK, "§aCrier", 4,"§e4 block autour de vous"));
        player.openInventory(menu);
        return false;
    }

    public ItemStack getItem(final Material material, final String name, final int nombre, final String lore) {
        final ItemStack it = new ItemStack(material, nombre);
        final ItemMeta itM = it.getItemMeta();
        itM.setDisplayName(name);
        itM.setLore(Collections.singletonList(lore));
        it.setItemMeta(itM);
        return it;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        String prefixcmd = "§2[§aTRN§2] §a";
        if (event.getView().getTitle().equals("§2[§aPuissance de la Voix§2]")) {
            event.setCancelled(true);
            if (current == null) {
                return;
            }
            switch (current.getType()) {
                case ENDER_EYE: {
                    final ShowVoix showvoix = new ShowVoix();
                    showvoix.onCommand((CommandSender)player, null, "", null);
                    break;
                }
                case BOOK: {
                    switch (current.getAmount()) {
                        case 1: {
                            player.sendMessage(prefixcmd + "Vous vous mettez à chuchoter.");
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aVoix : Basse"));
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                            speakrange.put(player,1);
                            break;
                        }
                        case 2: {
                            player.sendMessage(prefixcmd + "Vous vous mettez à parler.");
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aVoix : Normale"));
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                            speakrange.put(player,2);
                            break;
                        }
                        case 4: {
                            player.sendMessage(prefixcmd + "Vous vous mettez à crier.");
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aVoix : Forte"));
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                            speakrange.put(player,4);
                            System.out.println(speakrange);
                            break;
                        }
                    }
                }
            }
        }
    }
}
