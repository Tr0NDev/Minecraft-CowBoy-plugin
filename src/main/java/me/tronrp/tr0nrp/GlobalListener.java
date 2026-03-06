package me.tronrp.tr0nrp;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GlobalListener implements Listener {

    public ItemStack getItemLore(final Material material, final String name, final int nombre, final  ArrayList lore) {
    final ItemStack it = new ItemStack(material, nombre);
    final ItemMeta itM = it.getItemMeta();
    itM.setDisplayName(name);
    itM.setLore(lore);
    it.setItemMeta(itM);
    return it;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(Cheval.chevalhashmap.containsKey(player)){
            Cheval.chevalhashmap.get(player).remove();
        }
    }
    @EventHandler
    public boolean onHaverst(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack it = event.getItem();
        if(it == null){
            return false;
        }
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(it.getType() == Material.GOLDEN_SHOVEL && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§aRevolver")){
                event.setCancelled(true);
            }
        }
        return false;
    }

    @EventHandler
    public boolean onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack it = event.getItem();
        if(it == null){
            return false;
        }else if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(it.getType() == Material.GOLDEN_HORSE_ARMOR && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§aChapeau de Cow-Boy")){
                event.setCancelled(true);

            }
        }else if(it.getType() == Material.GOLDEN_HORSE_ARMOR && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§aChapeau de Cow-Boy")){
            ItemStack chapeau = getItemLore(Material.GOLDEN_HORSE_ARMOR, "§aChapeau de Cow-Boy", 1,null);
            player.getInventory().setHelmet(chapeau);
        }
        return false;
    }
}
