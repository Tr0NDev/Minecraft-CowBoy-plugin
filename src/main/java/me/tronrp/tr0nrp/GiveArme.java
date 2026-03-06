package me.tronrp.tr0nrp;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class GiveArme implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = (Player) sender;
        Inventory inv = player.getInventory();
        inv.addItem(getItemLore(Material.GOLDEN_SHOVEL, "§aRevolver", 1,new ArrayList<>(Arrays.asList("§6★☆☆☆☆","§aMunition: Légère","§eDégats: 5§c❤", "§eTemps: 1s"))));
        inv.addItem(getItemLore(Material.GOLDEN_PICKAXE, "§aCarabine", 1,new ArrayList<>(Arrays.asList("§6★★☆☆☆","§aMunition: Lourde","§eDégats: 10§c❤", "§eTemps: 3s"))));
        inv.addItem(getItemLore(Material.IRON_NUGGET, "§aMunition Légère", 64,new ArrayList<>(Arrays.asList("§6★☆☆☆☆","§aUtilisable:"," §e-Revolver"))));
        inv.addItem(getItemLore(Material.QUARTZ, "§aMunition Lourde", 64,new ArrayList<>(Arrays.asList("§6★★☆☆☆","§aUtilisable:"," §e-Carabine"))));
        inv.addItem(getItemLore(Material.GOLDEN_HORSE_ARMOR, "§aChapeau de Cow-Boy", 1,null));
        inv.addItem(getItemLore(Material.PHANTOM_MEMBRANE,"§aBillet de 1$",1,null));
        inv.addItem(getItemLore(Material.GHAST_TEAR,"§aBillet de 10$",1,null));
        inv.addItem(getItemLore(Material.MAGMA_CREAM,"§aBillet de 50$",1,null));
        return false;
    }

    public ItemStack getItemLore(final Material material, final String name, final int nombre, final ArrayList lore) {
        final ItemStack it = new ItemStack(material, nombre);
        final ItemMeta itM = it.getItemMeta();
        itM.setDisplayName(name);
        itM.setLore(lore);
        it.setItemMeta(itM);
        return it;
    }
}
