package me.tronrp.tr0nrp.metier;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Pecheur implements CommandExecutor,Listener {

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = (Player) sender;
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§4[§6Pécheur§4]");
        npc.spawn(player.getLocation());
        TextComponent tete = new TextComponent(prefixcmd + "§4Cliquez ici");
        TextComponent msg = new TextComponent(" §4// Puis ici");
        TextComponent msg2 = new TextComponent(" §4// Puis ici");
        tete.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc select"));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc skin --url https://minesk.in/95960ca329c74c34862d4a716bf320e6"));
        msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc lookclose"));
        tete.addExtra(msg);
        tete.addExtra(msg2);
        player.spigot().sendMessage(tete);
        return false;
    }

    @EventHandler
    public void onClick(NPCRightClickEvent event){
        Player player = event.getClicker();
        NPC npc = event.getNPC();
        if(npc.getRawName().equals("§4[§6Pécheur§4]")){
            final Inventory menu = Bukkit.createInventory((InventoryHolder)null, 4*9, "§2[§aPécheur§2]");
            menu.setItem(10, this.getItem(Material.COD, "§aMorue", 1,"§e5$"));
            menu.setItem(12, this.getItem(Material.SALMON, "§aSaumon", 1,"§e10$"));
            menu.setItem(14, this.getItem(Material.PUFFERFISH, "§aPoisson Globe", 1,"§e100$"));
            menu.setItem(16, this.getItem(Material.TROPICAL_FISH, "§aPoisson Tropical", 1,"§e200$"));
            menu.setItem(19, this.getItemLore(Material.COD, "§aMorue", 64,new ArrayList<>(Arrays.asList("§e5$ unité", "§cTout vendre. (" + getnombre(player,Material.COD) + ")"))));
            menu.setItem(21, this.getItemLore(Material.SALMON, "§aSaumon", 64,new ArrayList<>(Arrays.asList("§e10$ unité", "§cTout vendre. (" + getnombre(player,Material.SALMON) + ")"))));
            menu.setItem(23, this.getItemLore(Material.PUFFERFISH, "§aPoisson Globe", 64,new ArrayList<>(Arrays.asList("§e100$ unité", "§cTout vendre. (" + getnombre(player,Material.PUFFERFISH) + ")"))));
            menu.setItem(25, this.getItemLore(Material.TROPICAL_FISH, "§aPoisson Tropical", 64,new ArrayList<>(Arrays.asList("§e200$ unité", "§cTout vendre. (" + getnombre(player,Material.TROPICAL_FISH) + ")"))));
            player.openInventory(menu);
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
    public ItemStack getItemLore(final Material material, final String name, final int nombre, final  ArrayList lore) {
        final ItemStack it = new ItemStack(material, nombre);
        final ItemMeta itM = it.getItemMeta();
        itM.setDisplayName(name);
        itM.setLore(lore);
        it.setItemMeta(itM);
        return it;
    }

    public Inventory suppitem(final Player player,final Material nom,final Integer nombre){
        Inventory inv = player.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null)
                continue;

            if (item.getType() == nom) {
                int itemAmount = item.getAmount();
                item.setAmount(itemAmount - nombre);
                if(nombre==1){
                    return inv;
                }
            }
        }
        return inv;
    }
    public Integer getnombre(final Player player, final Material nom){
        Inventory inv = player.getInventory();
        Integer tot = 0;
        for (ItemStack item : inv.getContents()) {
            if (item == null)
                continue;
            if (item.getType() == nom) {
                tot = tot + item.getAmount();
            }
        }
        return tot;
    }

    public void vente(final Player player, final Material item, final ItemStack current, final Integer nombre, Integer prix){
        String prefixcmd = "§2[§aTRN§2] §a";
        Inventory inv = player.getInventory();
        player.sendMessage(prefixcmd + "Vous avez vendu §2" + nombre + " " + current.getItemMeta().getDisplayName() + "§a pour §2" + nombre*prix +"$§a.");
        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
        ItemStack billet1 = getItem(Material.PHANTOM_MEMBRANE,"§aBillet de 1$",1,null);
        ItemStack billet10 = getItem(Material.GHAST_TEAR,"§aBillet de 10$",1,null);
        ItemStack billet50 = getItem(Material.MAGMA_CREAM,"§aBillet de 50$",1,null);
        Integer max = prix * nombre;
        while(!(max==0)){
            if(max>=50){
                inv.addItem(billet50);
                max=max-50;
            }else if(max>=10){
                inv.addItem(billet10);
                max=max-10;
            }else if(max>=1){
                inv.addItem(billet1);
                max=max-1;
            }
        }
        suppitem(player,item,nombre);
    }

    public void guiswitch(final ItemStack current,final Player player,final Integer prix){
        String prefixcmd = "§2[§aTRN§2] §a";
        switch (current.getAmount()) {
            case 1: {
                if(!(player.getInventory().contains(current.getType()))){
                    player.sendMessage(prefixcmd + "Vous n'avez pas de §2" + current.getItemMeta().getDisplayName() + "§r§a.");
                    break;
                }else{
                    vente(player,current.getType(),current,1,prix);
                    break;
                }
            }
            case 64: {
                if(!(player.getInventory().contains(current.getType()))){
                    player.sendMessage(prefixcmd + "Vous n'avez pas de §2" + current.getItemMeta().getDisplayName() + "§r§a.");
                    break;
                }else{
                    vente(player,current.getType(),current,getnombre(player,current.getType()),prix);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = (Player)event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        Inventory inv = player.getInventory();
        if (event.getView().getTitle().equals("§2[§aPécheur§2]")) {
            event.setCancelled(true);
            player.closeInventory();
            if (current == null) {
                return;
            }
            switch (current.getType()) {
                case COD: {
                    guiswitch(current,player,5);
                    break;
                }
                case SALMON: {
                    guiswitch(current,player,10);
                    break;
                }
                case PUFFERFISH: {
                    guiswitch(current,player,100);
                    break;
                }
                case TROPICAL_FISH: {
                    guiswitch(current,player,200);
                    break;
                }
            }
        }
    }
}


