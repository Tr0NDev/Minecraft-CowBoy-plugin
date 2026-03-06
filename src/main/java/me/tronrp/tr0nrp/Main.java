package me.tronrp.tr0nrp;

import me.tronrp.tr0nrp.armes.Carabine;
import me.tronrp.tr0nrp.armes.Revolver;
import me.tronrp.tr0nrp.chat.*;
import me.tronrp.tr0nrp.metier.Banquier;
import me.tronrp.tr0nrp.metier.Peche;
import me.tronrp.tr0nrp.metier.Pecheur;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin implements  Listener{


    @Override
    public void onEnable() {

        Bukkit.getConsoleSender().sendMessage("§2______----- TR0N RP-----______");
        Bukkit.getConsoleSender().sendMessage("§2[§aTRN§2] §aPlugin §l§2Tr0N RP §r§a(" + this.getDescription().getVersion() + ") fonctionnel!");
        Bukkit.getConsoleSender().sendMessage("§2------_____ ____ _____------");


        File file = new File(getDataFolder(), "money.yml");
        if (!(file.exists())) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File filelettres = new File(getDataFolder(), "lettres.yml");
        if (!(filelettres.exists())) {
            filelettres.getParentFile().mkdirs();
            try {
                filelettres.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File filehorse = new File(getDataFolder(), "horse.yml");
        if (!(filehorse.exists())) {
            filehorse.getParentFile().mkdirs();
            try {
                filehorse.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.getServer().getPluginManager().registerEvents(new Cheval(this), this);
        this.getCommand("cheval").setExecutor(new Cheval(this));

        this.getServer().getPluginManager().registerEvents(new ClickMessage(), this);

        this.getCommand("voix").setExecutor(new SpeakRange());
        this.getServer().getPluginManager().registerEvents(new SpeakRange(), this);

        this.getCommand("setvoix").setExecutor(new SetSpeakRange());

        this.getCommand("showvoix").setExecutor(new ShowVoix());
        this.getServer().getPluginManager().registerEvents(new ShowVoix(), this);

        this.getCommand("HRP").setExecutor(new HRP());

        this.getServer().getPluginManager().registerEvents(new ChatListener(), this);

        this.getCommand("pecheur").setExecutor(new Pecheur());
        this.getServer().getPluginManager().registerEvents(new Pecheur(), this);

        this.getServer().getPluginManager().registerEvents(new Peche(this), this);

        this.getCommand("banquier").setExecutor(new Banquier(this));
        this.getServer().getPluginManager().registerEvents(new Banquier(this), this);

        this.getServer().getPluginManager().registerEvents(new GlobalListener(), this);

        this.getServer().getPluginManager().registerEvents(new Revolver(this), this);
        this.getServer().getPluginManager().registerEvents(new Carabine(this), this);

        this.getServer().getPluginManager().registerEvents(new Money(this), this);

        this.getCommand("item").setExecutor(new GiveArme());

    }

    public File getDataFile() {
        return new File(getDataFolder(), "money.yml");
    }
    public File getDataFileLettres() {
        return new File(getDataFolder(), "lettres.yml");
    }
    public File getDataFileHorse() {
        return new File(getDataFolder(), "horse.yml");
    }
}



