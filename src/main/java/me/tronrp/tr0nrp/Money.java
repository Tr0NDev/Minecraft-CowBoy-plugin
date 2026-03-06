package me.tronrp.tr0nrp;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class Money implements Listener {
    private Main plugin;
    private File file;
    private YamlConfiguration money;

    public Money(Main plugin) {
        this.plugin = plugin;
        this.file = plugin.getDataFile();
        this.money = YamlConfiguration.loadConfiguration(this.file);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!(money.contains(player.getDisplayName()))) {
            money.set(player.getDisplayName(), 0);
            saveData();
        }
    }

    private void saveData() {
        try {
            money.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMoney(Player player){
        return money.getInt(player.getDisplayName());
    }

    public void addMoney(Player player,Integer nombre){
        int moneyAmount = money.getInt(player.getDisplayName());
        money.set(player.getDisplayName(), moneyAmount + nombre);
        saveData();
    }

    public void removeMoney(Player player,Integer nombre){
        int moneyAmount = money.getInt(player.getDisplayName());
        money.set(player.getDisplayName(), moneyAmount - nombre);
        saveData();
    }

    public boolean playerExist(String player){
       if(money.contains(String.valueOf(player))){
           return true;
       }else{
           return false;
       }
    }
}

