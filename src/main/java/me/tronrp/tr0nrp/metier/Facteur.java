package me.tronrp.tr0nrp.metier;

import me.tronrp.tr0nrp.Main;
import me.tronrp.tr0nrp.Money;
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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class Facteur implements CommandExecutor,Listener {

    private Main plugin;
    private Money money;
    private File file;

    public Facteur(Main plugin) {
        this.plugin = plugin;
        this.money = new Money(plugin);
        this.file = plugin.getDataFileLettres();
    }



    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = (Player) sender;
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§4[§6Facteur§4]");
        npc.spawn(player.getLocation());
        TextComponent tete = new TextComponent(prefixcmd + "§4Cliquez ici");
        TextComponent msg = new TextComponent(" §4// Puis ici");
        TextComponent msg2 = new TextComponent(" §4// Puis ici");
        tete.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc select"));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc skin --url https://minesk.in/f2a0b2d6a7b746069e46ac0d9800d46a"));
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
        if(npc.getRawName().equals("§4[§6Facteur§4]")){
            final Inventory menu = Bukkit.createInventory((InventoryHolder)null, 3*9, "§2[§aFacteur§2]");
            menu.setItem(11, this.getItem(Material.PAPER, "§aEnvoyer", 1,"§ePrix: 5$"));
            menu.setItem(15, this.getItem(Material.BOOK, "§aLettres reçues", 1,null));
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

    public static HashMap<Player,Integer> chatlistener = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = (Player)event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        Inventory inv = player.getInventory();
        if (event.getView().getTitle().equals("§2[§aFacteur§2]")) {
            event.setCancelled(true);
            player.closeInventory();
            if (current == null) {
                return;
            }
            switch (current.getType()) {
                case PAPER: {
                    argent(player);
                    chatlistener.put(player,0);
                    player.sendMessage(prefixcmd + "Veuillez entre le nom du destinataire. (retour pour annuler)");
                    player.closeInventory();
                    break;
                }
            }
        }
    }
    @EventHandler
    public boolean onChat(PlayerChatEvent event) {
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = event.getPlayer();
        HashMap chatlistener = Facteur.chatlistener;
        String msg = event.getMessage();
        if(chatlistener.containsKey(player)){
            event.setCancelled(true);
            if(msg.equals("retour")){
                player.sendMessage(prefixcmd + "Vous avez annuler le message.");
                chatlistener.remove(player);
                return false;
            }
            if(chatlistener.get(player).equals(0)){
                if(!(money.playerExist(msg)==true)){
                    player.sendMessage(prefixcmd + "§cVeuillez saisir un nom valide.");
                }
                else{
                    String destinataire = msg;
                    player.sendMessage(prefixcmd + "Veuillez saisir votre message. (retour pour annuler)");
                    chatlistener.put(player,1);
                }
            }else if(chatlistener.get(player).equals(1)){

            }
        }
        return false;
    }
}


