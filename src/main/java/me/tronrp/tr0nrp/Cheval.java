package me.tronrp.tr0nrp;


import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.*;
;

public class Cheval implements CommandExecutor,Listener {

    private Main plugin;
    private File file;
    private YamlConfiguration horse;

    public Cheval(Main plugin) {
        this.plugin = plugin;
        this.file = plugin.getDataFileHorse();
        this.horse = YamlConfiguration.loadConfiguration(this.file);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!(horse.contains(player.getDisplayName()))) {
            horse.set(player.getDisplayName(), new ArrayList<>(Arrays.asList(true,false,false,false,false,false,false,false,false)));
            saveData();
        }
    }

    private void saveData() {
        try {
            horse.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player player = (Player)sender;
        final String prefixcmd = "§2[§aTRN§2] §a";
        final Inventory menu = Bukkit.createInventory((InventoryHolder)null, 9*5, "§2[§aGarage§2] §l§2" + player.getDisplayName());
        if(getHorse(player,0)){
            menu.setItem(10, this.getItemLore(Material.SADDLE,"§aMule",1,new ArrayList<>(Arrays.asList("§6★☆☆☆☆","§aVous possédez cette monture.","§eVie: 4§c❤","§eVitesse: 1","§eSaut: 0"))));
        }else{
            menu.setItem(10, this.getItemLore(Material.SADDLE,"§aMule",1,new ArrayList<>(Arrays.asList("§6★☆☆☆☆","§cVous ne possédez pas cette monture.","§eVie: 4§c❤","§eVitesse: 1","§eSaut: 0"))));
        }
        if(getHorse(player,1)){
            menu.setItem(11, this.getItemLore(Material.SADDLE,"§aCheval Classique",1,new ArrayList<>(Arrays.asList("§6★★☆☆☆","§aVous possédez cette monture.","§eVie: 5§c❤","§eVitesse: 2","§eSaut: 0"))));
        }else{
            menu.setItem(11, this.getItemLore(Material.SADDLE,"§aCheval Classique",1,new ArrayList<>(Arrays.asList("§6★★☆☆☆","§cVous ne possédez pas cette monture.","§eVie: 5§c❤","§eVitesse: 2","§eSaut: 0"))));
        }
        if(getHorse(player,2)){
            menu.setItem(12, this.getItemLore(Material.SADDLE,"§aCheval De l'Aventurier",1,new ArrayList<>(Arrays.asList("§6★★★☆☆","§aVous possédez cette monture.","§eVie: 10§c❤","§eVitesse: 3","§eSaut: 3"))));
        }else{
            menu.setItem(12, this.getItemLore(Material.SADDLE,"§aCheval De l'Aventurier",1,new ArrayList<>(Arrays.asList("§6★★★☆☆","§cVous ne possédez pas cette monture.","§eVie: 10§c❤","§eVitesse: 3","§eSaut: 3"))));
        }
        if(getHorse(player,3)){
            menu.setItem(14, this.getItemLore(Material.SADDLE,"§aCheval De Course",1,new ArrayList<>(Arrays.asList("§6★★★★☆","§aVous possédez cette monture.","§eVie: 5§c❤","§eVitesse: 5","§eSaut: 0"))));
        }else{
            menu.setItem(14, this.getItemLore(Material.SADDLE,"§aCheval De Course",1,new ArrayList<>(Arrays.asList("§6★★★★☆","§cVous ne possédez pas cette monture.","§eVie: 5§c❤","§eVitesse: 5","§eSaut: 0"))));
        }
        if(getHorse(player,4)){
            menu.setItem(23, this.getItemLore(Material.SADDLE,"§aCheval D'Oxer",1,new ArrayList<>(Arrays.asList("§6★★★★☆","§aVous possédez cette monture.","§eVie: 7§c❤","§eVitesse: 2","§eSaut: 7"))));
        }else{
            menu.setItem(23, this.getItemLore(Material.SADDLE,"§aCheval D'Oxer",1,new ArrayList<>(Arrays.asList("§6★★★★☆","§cVous ne possédez pas cette monture.","§eVie: 7§c❤","§eVitesse: 2","§eSaut: 7"))));
        }
        if(getHorse(player,5)){
            menu.setItem(32, this.getItemLore(Material.SADDLE,"§aCheval Résistant",1,new ArrayList<>(Arrays.asList("§6★★★★☆","§aVous possédez cette monture.","§eVie: 25§c❤","§eVitesse: 2","§eSaut: 0"))));
        }else{
            menu.setItem(32, this.getItemLore(Material.SADDLE,"§aCheval Résistant",1,new ArrayList<>(Arrays.asList("§6★★★★☆","§cVous ne possédez pas cette monture.","§eVie: 20§c❤","§eVitesse: 2","§eSaut: 0"))));
        }
        if(getHorse(player,7)){
            menu.setItem(16, this.getItemLore(Material.SADDLE,"§aCheval De Buffalo Bill",1,new ArrayList<>(Arrays.asList("§5★","§aVous possédez cette monture.","§eVie: 20§c❤","§eVitesse: 6","§eSaut: 4"))));
        }
        if(getHorse(player,8)){
            menu.setItem(25, this.getItemLore(Material.SADDLE,"§aCheval De Billy The Kid",1,new ArrayList<>(Arrays.asList("§5★","§aVous possédez cette monture.","§eVie: 20§c❤","§eVitesse: 5","§eSaut: 8"))));
        }
        player.openInventory(menu);
        return false;
    }

    public boolean getHorse(Player player,Integer nombre){
        ArrayList<Boolean> playerData = (ArrayList<Boolean>) horse.get(player.getDisplayName());
        return playerData.get(nombre);
    }

    public static Map<Player, Entity> chevalhashmap = new HashMap<>();
    public static ArrayList<Entity> chevalacces = new ArrayList<>();

    @EventHandler
    public void onClick(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if(player.isSneaking()){
            if(chevalhashmap.containsKey(player)){
                if(chevalhashmap.get(player) == entity){
                    final Inventory menu = Bukkit.createInventory((InventoryHolder)null, 9, "§2[§aCheval de " + player.getDisplayName() + "§2]");
                    if(chevalacces.contains(chevalhashmap.get(player))){
                        menu.setItem(2, this.getItem(Material.SLIME_BALL, "§aAccès de la monture.", 1,"§eRestreindre l'accès a votre monture."));
                    }else{
                        menu.setItem(2, this.getItem(Material.SLIME_BALL, "§cAccès de la monture.", 1,"§eAutorise l'accès a votre monture."));
                    }
                    menu.setItem(4, this.getItem(Material.NAME_TAG, "§aChoisir le nom.", 1,null));
                    menu.setItem(0, this.getItem(Material.BARRIER, "§cSupprimer votre monture.", 1,null));
                    player.openInventory(menu);
                }
            }
        }
    }

    private void createCheval(Player player,Integer vie,Double speed,Double jump){
        summonHorse(player);
        Horse horse = player.getWorld().spawn(player.getLocation(), Horse.class);
        horse.setCustomName("§2[§aCheval de " + player.getDisplayName() + "§2]");
        horse.setCustomNameVisible(true);
        horse.setHealth(vie);
        horse.setMaxHealth(vie);
        horse.setOwner(player);
        horse.setTamed(true);
        horse.setAdult();
        horse.setJumpStrength(jump);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.getAttribute( Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
        horse.setAI(false);
        chevalhashmap.put(player,horse);
    }

    private void summonHorse(Player player){
        if(chevalhashmap.containsKey(player)){
            chevalhashmap.get(player).remove();
        }
    }

    public static ArrayList<Player> chevalnom = new ArrayList<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        String prefixcmd = "§2[§aTRN§2] §a";
        Inventory menu = event.getInventory();
        Player player = (Player)event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if (event.getView().getTitle().equals("§2[§aGarage§2] §l§2" + player.getDisplayName())) {
            player.closeInventory();
            event.setCancelled(true);
            if (current == null) {
                return;
            }
            switch (current.getItemMeta().getDisplayName()) {
                case "§aMule": {
                    if(getHorse(player,0)){
                        summonHorse(player);
                        Mule mule = (Mule) player.getWorld().spawnEntity(player.getLocation(), EntityType.MULE);
                        mule.setCustomName("§2[§aMule de " + player.getDisplayName() + "§2]");
                        mule.setCustomNameVisible(true);
                        mule.setHealth(4);
                        mule.setMaxHealth(4);
                        mule.setOwner(player);
                        mule.setTamed(true);
                        mule.setAdult();
                        mule.setJumpStrength(0);
                        mule.getInventory().setSaddle(new ItemStack(Material.SADDLE));
                        mule.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2);
                        mule.setAI(false);
                        chevalhashmap.put(player, mule);
                        break;
                    }else{
                        player.sendMessage(prefixcmd + "§cVous ne possédez pas cette monture.");
                        break;
                    }
                }
                case "§aCheval Classique": {
                    if(getHorse(player,1)){
                        createCheval(player,10, 0.2, (double) 0);
                        break;
                    }else{
                        player.sendMessage(prefixcmd + "§cVous ne possédez pas cette monture.");
                        break;
                    }
                }
                case "§aCheval De l'Aventurier": {
                    if(getHorse(player,2)){
                        createCheval(player,20,  0.3, 0.3);
                        break;
                    }else{
                        player.sendMessage(prefixcmd + "§cVous ne possédez pas cette monture.");
                        break;
                    }
                }
                case "§aCheval De Course": {
                    if(getHorse(player,3)){
                        createCheval(player,5, 0.5, (double) 0);
                        break;
                    }else{
                        player.sendMessage(prefixcmd + "§cVous ne possédez pas cette monture.");
                        break;
                    }
                }case "§aCheval D'Oxer": {
                    if(getHorse(player,4)){
                        createCheval(player,7, 0.2, 0.7);
                        break;
                    }else{
                        player.sendMessage(prefixcmd + "§cVous ne possédez pas cette monture.");
                        break;
                    }
                }case "§aCheval Résistant": {
                    if(getHorse(player,5)){
                        createCheval(player,20, 0.2, (double) 0);
                        break;
                    }else{
                        player.sendMessage(prefixcmd + "§cVous ne possédez pas cette monture.");
                        break;
                    }
                }case "§aCheval De Buffalo Bill": {
                    if(getHorse(player,7)){
                        createCheval(player,20, 0.6, 0.4);
                        break;
                    }else{
                        player.sendMessage(prefixcmd + "§cVous ne possédez pas cette monture.");
                        break;
                    }
                }case "§aCheval De Billy The Kid": {
                    if(getHorse(player,8)){
                        createCheval(player,20, 0.5, 0.8);
                        break;
                    }else{
                        player.sendMessage(prefixcmd + "§cVous ne possédez pas cette monture.");
                        break;
                    }
                }

            }
        }
        if(event.getView().getTitle().equals("§2[§aCheval de " + player.getDisplayName() + "§2]") || event.getView().getTitle().equals("§2[§aMule de " + player.getDisplayName() + "§2]") ) {
            event.setCancelled(true);
            if (current == null) {
                return;
            }
            switch (current.getType()) {
                case SLIME_BALL: {
                    if(!(chevalacces.contains(chevalhashmap.get(player)))){
                        chevalacces.add(chevalhashmap.get(player));
                        player.sendMessage(prefixcmd + "Vous avez autorisez l'accès a votre monture.");
                        menu.setItem(2, getItem(Material.SLIME_BALL, "§aAccès de la monture.", 1,"§eRestreindre l'accès a votre monture."));
                    }else{
                        chevalacces.remove(chevalhashmap.get(player));
                        player.sendMessage(prefixcmd + "Vous avez restreint l'accès a votre monture.");
                        chevalhashmap.get(player).eject();
                        menu.setItem(2, getItem(Material.SLIME_BALL, "§cAccès de la monture.", 1,"§eAutorise l'accès a votre monture."));
                    }
                    player.openInventory(menu);
                    break;
                }
                case BARRIER: {
                    chevalhashmap.get(player).remove();;
                    chevalhashmap.remove(player);
                    player.closeInventory();
                } case NAME_TAG: {
                    player.sendMessage(prefixcmd + "Entrez le nom de votre cheval: ('retour' pour annuler)");
                    chevalnom.add(player);
                    player.closeInventory();
                }
            }
        }
    }

    @EventHandler
    public void onChat(PlayerChatEvent event){
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = event.getPlayer();
        if(chevalnom.contains(player)){
            event.setCancelled(true);
            String msg = event.getMessage();
            if(msg.equals("retour")){
                chevalnom.remove(player);
                player.sendMessage(prefixcmd + "§cCustomisaton annulée.");
            }
            else if(msg.length()>20){
                player.sendMessage(prefixcmd +"§cNom trop long, veuillez remttre un nom.");
            }
            else{
                Entity monture = chevalhashmap.get(player);
                monture.setCustomName("§a"+msg);
                chevalhashmap.remove(player);
                chevalhashmap.put(player,monture);
                player.sendMessage(prefixcmd + "Votre monture s'appelle maintenant: " + msg);
                chevalnom.remove(player);
            }
        }
    }

    @EventHandler
    public void onHorseClick(PlayerInteractEntityEvent event){
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if(player.isSneaking()){
            event.setCancelled(true);
        }
        if(entity.getType().equals(EntityType.HORSE) || entity.getType().equals(EntityType.MULE)){
            if(!(entity.equals(chevalhashmap.get(player)))){
                if(!(chevalacces.contains(entity))){
                    player.sendMessage(prefixcmd + "Ce cheval n'est pas en accès libre.");
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public boolean onMove(PlayerMoveEvent event){
        String prefixcmd = "§2[§aTRN§2] §a";
        Player player = event.getPlayer();
        if(chevalhashmap.containsKey(player)){
            Entity cheval = chevalhashmap.get(player);
            Location poscheval = cheval.getLocation();
            Location pos = player.getLocation();
            if(pos.getX()>=poscheval.getX()+20){
                player.sendMessage(prefixcmd + "Votre cheval a disparu car vous étiez trop loin.");
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                chevalhashmap.remove(player, cheval);
                cheval.remove();
                return false;
            }
            if(pos.getX()<=poscheval.getX()-20){
                player.sendMessage(prefixcmd + "Votre cheval a disparu car vous étiez trop loin.");
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                chevalhashmap.remove(player, cheval);
                cheval.remove();
                return false;
            }
            if(pos.getZ()>=poscheval.getZ()+20){
                player.sendMessage(prefixcmd + "Votre cheval a disparu car vous étiez trop loin.");
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                chevalhashmap.remove(player, cheval);
                cheval.remove();
                return false;
            }
            if(pos.getZ()<=poscheval.getZ()-20){
                player.sendMessage(prefixcmd + "Votre cheval a disparu car vous étiez trop loin.");
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT,1,1);
                chevalhashmap.remove(player, cheval);
                cheval.remove();
                return false;
            }
        }
        return false;
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        Entity entity = event.getEntity();
        if(chevalhashmap.containsValue(entity)){
            event.getDrops().clear();
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
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getHolder() instanceof AbstractHorse) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onRoute(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation().subtract(0, 2, 0);
        Block block = loc.getBlock();
        if (block.getType() == Material.BEDROCK) {
            PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, 2, 2);
            player.addPotionEffect(speedEffect);
        }
    }
}