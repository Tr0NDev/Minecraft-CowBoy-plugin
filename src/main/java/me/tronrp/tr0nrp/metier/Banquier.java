package me.tronrp.tr0nrp.metier;

import me.tronrp.tr0nrp.Main;
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
import me.tronrp.tr0nrp.Money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class Banquier implements CommandExecutor,Listener {

    private Main plugin;
    private Money money;

    public Banquier(Main plugin) {
        this.plugin = plugin;
        this.money = new Money(plugin);
    }


    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = (Player) sender;
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§4[§6Banquier§4]");
        npc.spawn(player.getLocation());
        TextComponent tete = new TextComponent(prefixcmd + "§4Cliquez ici");
        TextComponent msg = new TextComponent(" §4// Puis ici");
        TextComponent msg2 = new TextComponent(" §4// Puis ici");
        tete.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc select"));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc skin --url https://minesk.in/d462dc8e85534e80b7c7e87639616615"));
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
        if(npc.getRawName().equals("§4[§6Banquier§4]")){
            final Inventory menu = Bukkit.createInventory((InventoryHolder)null, 3*9, "§2[§aBanque§2]");
            menu.setItem(11, this.getItem(Material.HOPPER, "§aDéposer", 1,"§eVous avez: " + money.getMoney(player)));
            menu.setItem(15, this.getItem(Material.DROPPER, "§aRetier", 1,"§eVous avez: " + money.getMoney(player)));
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


    public void argent(Player player) {
        String prefixcmd = "§2[§aTRN§2] §a";
        if (!(player.getInventory().contains(Material.GHAST_TEAR) || player.getInventory().contains(Material.MAGMA_CREAM)
                || player.getInventory().contains(Material.PHANTOM_MEMBRANE))) {
            player.sendMessage(prefixcmd + "§cVous n'avez pas d'argent liquide.");
        } else {
            Integer tot = getnombre(player, Material.GHAST_TEAR) * 10 + getnombre(player, Material.MAGMA_CREAM) * 50 + getnombre(player, Material.PHANTOM_MEMBRANE);
            money.addMoney(player,tot);
            player.sendMessage(prefixcmd + "Vous avez maintenant §2" + money.getMoney(player) + "§a.");
            suppitem(player, Material.GHAST_TEAR, getnombre(player, Material.GHAST_TEAR));
            suppitem(player, Material.MAGMA_CREAM, getnombre(player, Material.MAGMA_CREAM));
            suppitem(player, Material.PHANTOM_MEMBRANE, getnombre(player, Material.PHANTOM_MEMBRANE));
            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT, 1, 1);
        }
    }

    private HashMap<Player,Integer> menuretier = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = (Player)event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        Inventory inv = player.getInventory();
        if (event.getView().getTitle().equals("§2[§aBanque§2]")) {
            Integer nombre = 0;
            event.setCancelled(true);
            player.closeInventory();
            if (current == null) {
                return;
            }
            switch (current.getType()) {
                case HOPPER: {
                    argent(player);
                    break;
                }
                case DROPPER: {
                    final Inventory menu = Bukkit.createInventory((InventoryHolder)null, 4*9, "§2[§aRetirer§2]");
                    menu.setItem(10, this.getItem(Material.PHANTOM_MEMBRANE, "§aAjouter 1", 1,"§eTotal: 0"));
                    menu.setItem(19, this.getItem(Material.PHANTOM_MEMBRANE, "§cRetirer 1", 1,"§eTotal: 0"));
                    menu.setItem(11, this.getItem(Material.GHAST_TEAR, "§aAjouter 10", 1,"§eTotal: 0"));
                    menu.setItem(20, this.getItem(Material.GHAST_TEAR, "§cRetirer 10", 1,"§eTotal: 0"));
                    menu.setItem(12, this.getItem(Material.MAGMA_CREAM, "§aAjouter 100", 1,"§eTotal: 0"));
                    menu.setItem(21, this.getItem(Material.MAGMA_CREAM, "§cRetirer 100", 1,"§eTotal: 0"));
                    menu.setItem(13, this.getItem(Material.DIAMOND, "§aMaximum", 1,"§eTotal: 0"));
                    menu.setItem(22, this.getItem(Material.BARRIER, "§aRéinitialisé", 1,"§eTotal: 0"));
                    menu.setItem(15, this.getItem(Material.GREEN_WOOL, "§aRetirer", 1,"§eTotal: 0"));
                    menu.setItem(27, this.getItem(Material.ARROW, "§cRetour", 1,null));
                    menuretier.put(player,0);
                    player.openInventory(menu);
                }
            }
        }
        if (event.getView().getTitle().equals("§2[§aRetirer§2]")) {
            Integer nombre = menuretier.get(player);
            event.setCancelled(true);
            if (current == null) {
                return;
            }
            Inventory menu = event.getInventory();
            switch (current.getItemMeta().getDisplayName()) {
                case "§cRetour": {
                    final Inventory retour = Bukkit.createInventory((InventoryHolder)null, 3*9, "§2[§aBanque§2]");
                    retour.setItem(11, this.getItem(Material.HOPPER, "§aDéposer", 1,"§eVous avez: " + money.getMoney(player)));
                    retour.setItem(15, this.getItem(Material.DROPPER, "§aRetier", 1,"§eVous avez: " + money.getMoney(player)));
                    player.openInventory(retour);
                    break;
                }
                case "§aAjouter 1": {
                    menuretier.put(player,nombre+1);
                    break;
                }
                case "§cRetirer 1": {
                    menuretier.put(player,nombre-1);
                    break;
                }
                case "§aAjouter 10": {
                    menuretier.put(player,nombre+10);
                    break;
                }
                case "§cRetirer 10": {
                    menuretier.put(player,nombre-10);
                    break;
                }
                case "§aAjouter 100": {
                    menuretier.put(player,nombre+100);
                    break;
                }
                case "§cRetirer 100": {
                    menuretier.put(player,nombre-100);
                    break;
                }
                case "§aMaximum": {
                    menuretier.put(player,money.getMoney(player));
                    break;
                }
                case "§aRéinitialisé": {
                    menuretier.put(player,0);
                    break;
                }
                case "§aRetirer": {
                    if(nombre>money.getMoney(player)){
                        player.sendMessage(prefixcmd + "§cVous n'avez pas assez d'argent sur votre compte.");
                    }else if(nombre<=0){
                        player.sendMessage(prefixcmd + "§cVous ne pouvez pas retirer ce montant.");
                    }else{
                        money.removeMoney(player,nombre);
                        player.sendMessage(prefixcmd + "Vous avez retiré " + nombre + "$.");
                        menuretier.remove(player);
                        player.closeInventory();
                        while(!(nombre==0)){
                            if(nombre>=50){
                                inv.addItem(getItem(Material.MAGMA_CREAM,"§aBillet de 50$",1,null));
                                nombre=nombre-50;
                            }else if(nombre>=10){
                                inv.addItem(getItem(Material.GHAST_TEAR,"§aBillet de 10$",1,null));
                                nombre=nombre-10;
                            }else if(nombre>=1){
                                inv.addItem(getItem(Material.PHANTOM_MEMBRANE,"§aBillet de 1$",1,null));
                                nombre=nombre-1;
                            }
                        }
                    }
                }
            }
            menu.setItem(10, this.getItem(Material.PHANTOM_MEMBRANE, "§aAjouter 1", 1,"§eTotal: " + menuretier.get(player)));
            menu.setItem(19, this.getItem(Material.PHANTOM_MEMBRANE, "§cRetirer 1", 1,"§eTotal: " + menuretier.get(player)));
            menu.setItem(11, this.getItem(Material.GHAST_TEAR, "§aAjouter 10", 1,"§eTotal: " + menuretier.get(player)));
            menu.setItem(20, this.getItem(Material.GHAST_TEAR, "§cRetirer 10", 1,"§eTotal: " + menuretier.get(player)));
            menu.setItem(12, this.getItem(Material.MAGMA_CREAM, "§aAjouter 100", 1,"§eTotal: " + menuretier.get(player)));
            menu.setItem(21, this.getItem(Material.MAGMA_CREAM, "§cRetirer 100", 1,"§eTotal: " + menuretier.get(player)));
            menu.setItem(13, this.getItem(Material.DIAMOND, "§aMaximum",1,"§eTotal: " + menuretier.get(player)));
            menu.setItem(22, this.getItem(Material.BARRIER, "§aRéinitialisé", 1,"§eTotal: " + menuretier.get(player)));
            menu.setItem(15, this.getItem(Material.GREEN_WOOL, "§aRetirer", 1,"§eTotal: " + menuretier.get(player)));
        }
    }
}


