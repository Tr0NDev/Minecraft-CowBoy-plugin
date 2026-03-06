package me.tronrp.tr0nrp.metier;


import me.tronrp.tr0nrp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Peche implements Listener {

    public static ItemStack typepoisson = null;
    private final Main plugin;

    private String prefixcmd = "§2[§aTRN§2] §a";
    public Peche(Main plugin) {
        this.plugin = plugin;
    }
    private static HashMap<Player,Boolean> reussit = new HashMap<>();

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        //if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
        if(event.getHook().isInOpenWater()){
            //Entity poisson = event.getCaught();
            //event.getHook().remove();
            //poisson.remove();
            final Inventory menu = Bukkit.createInventory((InventoryHolder)null, 9*6, "§2[§aPêche§2] " + player.getDisplayName());
            Random rand = new Random();
            int randompoisson = rand.nextInt(89);
            int randomcase = rand.nextInt(2) == 0 ? rand.nextInt(3) + 45 : rand.nextInt(3) + 51;
            if(randompoisson<=30){
                menu.setItem(randomcase, this.getItem(Material.COD, "§aMorue", 1,"§6★☆☆☆☆"));
                typepoisson = getItem(Material.COD, "§aMorue", 1,"§6★☆☆☆☆");
            }else if(randompoisson<=50){
                menu.setItem(randomcase, this.getItem(Material.SALMON, "§aSaumon", 1,"§6★★☆☆☆"));
                typepoisson = getItem(Material.SALMON, "§aSaumon", 1,"§6★★☆☆☆");
            }else if(randompoisson<=58){
                menu.setItem(randomcase, this.getItem(Material.PUFFERFISH, "§aPoisson Globe", 1,"§6★★★☆☆"));
                typepoisson = getItem(Material.PUFFERFISH, "§aPoisson Globe", 1,"§6★★★☆☆");
            }else if(randompoisson<=66){
                menu.setItem(randomcase, this.getItem(Material.TROPICAL_FISH, "§aPoisson Tropical", 1,"§6★★★☆☆"));
                typepoisson = getItem(Material.TROPICAL_FISH, "§aPoisson Tropical", 1,"§6★★★☆☆");
            }else if(randompoisson<=74){
            menu.setItem(randomcase, this.getItem(Material.SALMON, "§aBrochet", 1,"§6★★★☆☆"));
            typepoisson = getItem(Material.TROPICAL_FISH, "§aBrochet", 1,"§6★★★☆☆");
            }else if(randompoisson<=85){
                menu.setItem(randomcase, this.getItem(Material.SALMON, "§aPerche", 1,"§6★★★☆☆"));
                typepoisson = getItem(Material.TROPICAL_FISH, "§aPerche", 1,"§6★★★☆☆");
            }else if(randompoisson<=89){
                menu.setItem(randomcase, this.getItem(Material.SALMON, "§aSandre", 1,"§6★★★★☆"));
                typepoisson = getItem(Material.TROPICAL_FISH, "§aSandre", 1,"§6★★★★☆");
            }else if(randompoisson<=83){
                menu.setItem(randomcase, this.getItem(Material.SALMON, "§aBarracuda", 1,"§6★★★★☆"));
                typepoisson = getItem(Material.TROPICAL_FISH, "§aBarracuda", 1,"§6★★★★☆");
            }else if(randompoisson<=87){
                menu.setItem(randomcase, this.getItem(Material.SALMON, "§aThon", 1,"§6★★★★☆"));
                typepoisson = getItem(Material.TROPICAL_FISH, "§aThon", 1,"§6★★★★☆");
            }else if(randompoisson<=89){
                menu.setItem(randomcase, this.getItem(Material.SALMON, "§aRequin", 1,"§6★★★★★"));
                typepoisson = getItem(Material.TROPICAL_FISH, "§aRequin", 1,"§6★★★★★");
            }

            menu.setItem(48, this.getItem(Material.PURPLE_CARPET, "§5Gauche", 1,null));
            menu.setItem(49, this.getItem(Material.PURPLE_CARPET, "§5Haut", 1,null));
            menu.setItem(50, this.getItem(Material.PURPLE_CARPET, "§5Droite", 1,null));
            for(int i = 0; i < menu.getSize(); i++) {
                if(menu.getItem(i)==null){
                    ItemStack eau = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1);
                    menu.setItem(i, eau);
                }
            }
            for (int i = 1; i <= 13; i++) {
                ItemStack eau = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1);
                ItemStack pierre = getItem(Material.STONE, "§aRocher", 1,null);
                int randompierre = rand.nextInt(45);
                if(menu.getItem(randompierre).equals(eau)){
                    if(!(randompierre==randomcase-9)){
                        menu.setItem(randompierre, pierre);
                    }
                }
            }
            for (int i = 0; i <= 8; i++) {
                ItemStack eau = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1);
                ItemStack eaufonce = new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1);
                if(menu.getItem(i).equals(eau)){
                    menu.setItem(i,eaufonce);
                }
            }
            player.openInventory(menu);
            reussit.put(player,false);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(reussit.get(player).equals(false)){
                        player.sendMessage(prefixcmd + "Vous avez été trop lent et le poisson s'est enfuie.");
                        player.closeInventory();
                    }
                    reussit.remove(player);
                }
            }.runTaskLater(plugin, 20*5);
        }
    }
    public ItemStack getItem(final Material material, final String name, final int nombre, final String lore) {
        final ItemStack it = new ItemStack(material, nombre);
        final ItemMeta itM = it.getItemMeta();
        itM.setDisplayName(name);
        if(lore!=null){
            itM.setLore(Collections.singletonList(lore));
        }
        it.setItemMeta(itM);
        return it;
    }

    @EventHandler
    public boolean onClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        Inventory menu = event.getInventory();
        if (event.getView().getTitle().equals("§2[§aPêche§2] " + player.getDisplayName())) {
            event.setCancelled(true);
            if (current == null) {
                return false;
            }
            Integer locpoisson = 0;
            ItemStack eau = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1);
            ItemStack pierre = getItem(Material.STONE, "§aRocher", 1,null);
            for(int i = 0; i < menu.getSize(); i++) {
                if(menu.getItem(i).getItemMeta().getDisplayName().equals(typepoisson.getItemMeta().getDisplayName())){
                    locpoisson = i;
                }
            }
            if (locpoisson == 0 || locpoisson == 1 || locpoisson == 2 || locpoisson == 3 || locpoisson == 4 || locpoisson == 5 || locpoisson == 6 || locpoisson == 7 || locpoisson == 8 || locpoisson == 9) {
                player.closeInventory();
                player.getInventory().addItem(typepoisson);
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                player.sendMessage(prefixcmd + "Vous avez pêcher un §2" + typepoisson.getItemMeta().getDisplayName() + "§r§a.");
                reussit.put(player,true);
                return false;
            }
            switch (current.getType()) {
                case PURPLE_CARPET: {
                    switch (current.getItemMeta().getDisplayName()) {
                        case "§5Gauche": {
                            if (!(locpoisson == 51 || locpoisson == 45 || locpoisson == 36 || locpoisson == 27 || locpoisson == 18 || locpoisson == 9)) {
                                if(menu.getItem(locpoisson-1).equals(pierre)){
                                    player.closeInventory();
                                    player.sendMessage(prefixcmd + "§cVous avez cassé votre ligne");
                                    reussit.remove(player);
                                    break;
                                }else{
                                    menu.setItem(locpoisson-1, typepoisson);
                                    menu.setItem(locpoisson, eau);
                                    break;
                                }
                            }
                            break;
                        }
                        case "§5Droite": {
                            if (!(locpoisson == 53 || locpoisson == 47 || locpoisson == 44 || locpoisson == 35 || locpoisson == 26 || locpoisson == 17)) {
                                if(menu.getItem(locpoisson+1).equals(pierre)){
                                    player.closeInventory();
                                    player.sendMessage(prefixcmd + "§cVous avez cassé votre ligne");
                                    reussit.remove(player);
                                    break;
                                }else{
                                    menu.setItem(locpoisson+1, typepoisson);
                                    menu.setItem(locpoisson, eau);
                                    break;
                                }
                            }
                            break;
                        }
                        case "§5Haut": {
                            if(menu.getItem(locpoisson-9).equals(pierre)){
                                player.closeInventory();
                                player.sendMessage(prefixcmd + "§cVous avez cassé votre ligne");
                                reussit.remove(player);
                                break;
                            }else{
                                menu.setItem(locpoisson-9, typepoisson);
                                menu.setItem(locpoisson, eau);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
