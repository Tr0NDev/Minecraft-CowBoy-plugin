package me.tronrp.tr0nrp.armes;

import me.tronrp.tr0nrp.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;


public class Revolver implements Listener {

    private Main plugin;

    private HashMap<Player, Boolean> tire = new HashMap<>();

    public Revolver(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = event.getPlayer();
        ItemStack it = event.getPlayer().getItemInHand();
        if(it.getType() == Material.GOLDEN_SHOVEL && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§aRevolver")){
            if(!(tire.containsKey(player)))
                if(player.getInventory().contains(Material.IRON_NUGGET)){
                    Arrow bullet = player.launchProjectile(Arrow.class);
                    Vector v = player.getLocation().getDirection().multiply(5D);
                    bullet.setVelocity(v);
                    bullet.setCustomName("Munition légère");
                    bullet.setShooter(player);
                    suppitem(player,Material.IRON_NUGGET,1);
                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 2, 1);
                    tire.put(player,true);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            tire.remove(player);
                        }
                    }.runTaskLater(plugin, 20 * 1);
            }else{
                player.sendMessage(prefixcmd + "§cVous n'avez pas de munitions.");
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Entity hitEntity = event.getHitEntity();
        Projectile projectile = event.getEntity();
        if (projectile.getCustomName() != null && projectile.getCustomName().equals("Munition légère")) {
            event.setCancelled(true);
            if (hitEntity instanceof LivingEntity) {
                ((LivingEntity) hitEntity).damage(5);
            }
        }
    }

    public ItemStack getItemLore(final Material material, final String name, final ArrayList lore) {
        final ItemStack it = new ItemStack(material);
        final ItemMeta itM = it.getItemMeta();
        itM.setDisplayName(name);
        itM.setLore(lore);
        it.setItemMeta(itM);
        return it;
    }
    public Inventory suppitem(final Player player, final Material nom, final Integer nombre){
        Inventory inv = player.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null)
                continue;

            if (item.getType() == nom) {
                int itemAmount = item.getAmount();
                item.setAmount(itemAmount - 1);
                if(nombre==1){
                    return inv;
                }
            }
        }
        return inv;
    }
}
