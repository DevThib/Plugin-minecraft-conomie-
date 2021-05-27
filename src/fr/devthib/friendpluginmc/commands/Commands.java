package fr.devthib.friendpluginmc.commands;

import fr.devthib.friendpluginmc.HDVAnnouncement;
import fr.devthib.friendpluginmc.Home;
import net.minecraft.server.v1_16_R3.InventoryCrafting;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if(command.getName().equalsIgnoreCase("hdv")){
            if(sender instanceof  Player){
                Player p = (Player) sender;
                StaticMethodCommands.showHdvMenu(p);
            }
        }
        if(command.getName().equalsIgnoreCase("alert")){
            if(sender instanceof Player){

                Player player = (Player) sender;
                String msg = "";

                if(strings.length == 0){

                    player.sendMessage("§4 Utilisation : /alert [message]");

                }else {

                    for (int i = 0; i < strings.length; i++) {
                        msg += strings[i] + " ";
                    }

                    sender.sendMessage("msg : "+msg);
                    Bukkit.broadcastMessage("§4[MESSAGE] §2" + msg);
                }

            }
        }
        if(command.getName().equalsIgnoreCase("money")){
            if(sender instanceof Player){

                Player player = (Player) sender;
                player.sendMessage("§9Votre argent : §a"+StaticMethodCommands.getMoneyToAPlayer(player)+" $");

            }
        }
        if(command.getName().equalsIgnoreCase("sell")){
            if(sender instanceof Player){

                if(StaticMethodCommands.getAllAnnouncements().size() >= 54){
                    sender.sendMessage("§4Il y a actuellemnt 54 annonces a l'hdv,Veuillez attendre qu'une place se libère");
                }else {

                    try {

                        Player player = (Player) sender;

                        if (player.getItemInHand() != null) {

                            if (player.getItemInHand().getType() != Material.AIR) {

                                if (!player.getItemInHand().getItemMeta().hasDisplayName()) {

                                    int price = Integer.parseInt(strings[0]);
                                    Material material = player.getItemInHand().getType();
                                    int quantity = player.getItemInHand().getAmount();

                                    StaticMethodCommands.addHDVAnnouncements(new HDVAnnouncement(material, price, player.getName(), quantity));
                                    for (int i = 0; i < quantity; i++) {
                                        player.getInventory().removeItem(new ItemStack(material));
                                    }

                                    player.sendMessage("§b[HDV] §2L'item a été mit en vente au prix de " + price + " $ unité");

                                } else {
                                    player.sendMessage("§4Cet item est renommé,il ne peut pas être vendu");
                                }

                            } else {
                                player.sendMessage("§4Vous n'avez rien dans votre main");
                            }
                        } else {
                            player.sendMessage("§4Veuillez mettre sous la forme : /sell <prix a l'unité>");
                        }

                    } catch (IndexOutOfBoundsException e) {
                        sender.sendMessage("§4Veuillez mettre sous la forme : /sell <prix a l'unité>");
                    }

                }

            }
        }
        if(command.getName().equalsIgnoreCase("sethome")){
            if(sender instanceof Player){

                Player player = (Player) sender;

                try {

                    String name = strings[0];

                    ArrayList<Home> playerHomes = StaticMethodCommands.getHomesByAPlayer(player.getName());

                    if(playerHomes.size() < 3){

                        Location location = player.getLocation();

                        if(playerHomes.size() == 0){

                            player.sendMessage("§2Home défini !");
                            StaticMethodCommands.addHome(new Home(player.getName(), name, location.getX(), location.getY(), location.getZ()));

                        }else{

                            for (int i = 0; i < playerHomes.size(); i++) {
                                if (!playerHomes.get(i).getName().equalsIgnoreCase(name)) {
                                    player.sendMessage("§2Home défini !");
                                    StaticMethodCommands.addHome(new Home(player.getName(), name, location.getX(), location.getY(), location.getZ()));
                                } else {
                                    player.sendMessage("§4Vous avez déjà prit ce nom de home...");
                                }
                            }

                        }

                    }else{
                        player.sendMessage("§4La limite de homes est de 3");
                    }

                }catch (IndexOutOfBoundsException e){
                    player.sendMessage("§4Veuillez mettre un nom au home...");
                }

            }
        }
        if(command.getName().equalsIgnoreCase("home")){
            if(sender instanceof Player){

                Player player = (Player) sender;

                int money = StaticMethodCommands.getMoneyToAPlayer(player);

                ArrayList<String> commands = StaticMethodCommands.getAvailableCommands(player);
                if(commands.contains("homes")) {

                    try {

                        String name = strings[0];

                        ArrayList<Home> homes = StaticMethodCommands.getHomesByAPlayer(player.getName());

                        for (int i = 0; i < homes.size(); i++) {
                            if (homes.get(i).getName().equalsIgnoreCase(name)) {
                                Location location = new Location(player.getWorld(), homes.get(i).getX(), homes.get(i).getY(), homes.get(i).getZ());

                                Location playerLoc = player.getLocation();

                                double diffX = location.getX() - playerLoc.getX();
                                double diffZ = location.getZ() - playerLoc.getZ();

                                if (diffX < 0) {
                                    diffX = diffX * (-1);
                                }
                                if (diffZ < 0) {
                                    diffZ = diffZ * (-1);
                                }

                                int toPay = (int) ((diffX + diffZ) / 2);

                                if (money >= toPay) {
                                    player.teleport(location);
                                    StaticMethodCommands.removeMoneyFromAPlayer(player, toPay);
                                    player.sendMessage("§cLa téléportation vous a couté " + toPay + " $");
                                } else {
                                    player.sendMessage("§4La téléportation côute " + toPay + " $ ! Vous n'avez pas assez d'argent !");
                                }

                            }
                        }

                    } catch (IndexOutOfBoundsException e) {
                        ArrayList<Home> homes = StaticMethodCommands.getHomesByAPlayer(player.getName());
                        String toSend = "";

                        for (int i = 0; i < homes.size(); i++) {
                            toSend += homes.get(i).getName() + ",";
                        }

                        player.sendMessage("§9Vos homes : §2" + toSend);
                    }

                }else{
                    player.sendMessage("§4Vous devez d'abord acheter cette commande avant de l'utiliser ! Faites /hdv");
                }

            }
        }
        if(command.getName().equalsIgnoreCase("delhome")){
            if(sender instanceof Player){

                Player player = (Player) sender;

                try {

                    String name = strings[0];
                    ArrayList<Home> homes = StaticMethodCommands.getHomesByAPlayer(player.getName());

                    if (homes.size() == 0) {
                        player.sendMessage("§4Vous n'avez pas de home");
                    } else {

                        for (int i = 0; i < homes.size(); i++) {
                            if (homes.get(i).getName().equalsIgnoreCase(name)) {
                                StaticMethodCommands.removeHomeByName(name);
                                player.sendMessage("§2Home supprimé !");
                            }
                        }


                    }

                }catch (IndexOutOfBoundsException e){
                  player.sendMessage("§4Vous n'avez pas mit de nom de home");
                }

            }

        }
        if(command.getName().equalsIgnoreCase("pay")){
            if(sender instanceof Player){

                Player player = (Player) sender;

                try {

                    Player player1 = Bukkit.getPlayer(strings[0]);
                    int value = Integer.parseInt(strings[1]);

                    int money1 = StaticMethodCommands.getMoneyToAPlayer(player);

                    if(money1 >= value){

                        if(player != player1){
                            StaticMethodCommands.addMoneyToAPlayer(player1,value);
                            StaticMethodCommands.removeMoneyFromAPlayer(player,value);

                            player.sendMessage("§2Vous avez envoyé "+value+" $ à "+player1.getName());
                            player1.sendMessage("§2Vous avez recu "+value+" $ de "+player.getName());
                        }else{
                            player.sendMessage("§4Vous ne pouvez pas vous envoyer d'argent a vous même !");
                        }

                    }else{
                        player.sendMessage("§4Vous n'avez pas assez d'argent !");
                    }

                }catch (IndexOutOfBoundsException e){
                    player.sendMessage("§4Cette personne n'existe pas ou n'est pas connectée");
                }catch (NumberFormatException e){
                    player.sendMessage("§4Somme invalide !");
                }catch (NullPointerException e){
                    player.sendMessage("§4Cette personne n'existe pas !");
                }

            }
        }
        if(command.getName().equalsIgnoreCase("repair")){
            if(sender instanceof Player){

                Player player = (Player) sender;

                if(player.isOp()){

                    try {

                        File file = new File(System.getProperty("user.dir"), "PluginMC\\economy\\hdv\\announcements.txt");
                        if (file.exists()) {
                            file.delete();
                            file.createNewFile();
                            player.sendMessage("§2Hdv réparé avec succès");
                        } else {
                            player.sendMessage("§4Fichier HDV inexsistant,création en cour");
                            file.createNewFile();
                        }

                    }catch (IOException e){
                        player.sendMessage("§4Une erreur est survenue lors de la création du fichier HDV");
                    }

                }else{
                    player.sendMessage("§4Vous n'avez pas la permission pour cette commande");
                }

            }
        }
        if(command.getName().equalsIgnoreCase("spawn")) {
            if(sender instanceof Player){

              Player player = (Player) sender;

                Location location = StaticMethodCommands.readSpawn();
                Location playerLoc = player.getLocation();

                double diffX = location.getX()-playerLoc.getX();
                double diffZ = location.getZ()-playerLoc.getZ();

                if(diffX < 0){
                    diffX = diffX*(-1);
                }
                if(diffZ < 0){
                    diffZ = diffZ*(-1);
                }

                int toPay = (int) ((diffX+diffZ)/2);

                int money = StaticMethodCommands.getMoneyToAPlayer(player);

                if(money >= toPay){
                    player.teleport(location);
                    StaticMethodCommands.removeMoneyFromAPlayer(player,toPay);
                    player.sendMessage("§cLa téléportation vous a couté "+toPay+" $");
                }else{
                    player.sendMessage("§4La téléportation côute "+toPay+" $ ! Vous n'avez pas assez d'argent !");
                }

            }
        }
        if(command.getName().equalsIgnoreCase("setspawn")){
            if (sender instanceof Player) {

                try {

                    Player player = (Player) sender;

                    if(player.isOp()) {

                        File file = new File(System.getProperty("user.dir"), "PluginMC\\files\\spawn\\spawn.txt");
                        FileWriter fw = new FileWriter(file);
                        BufferedWriter bw = new BufferedWriter(fw);

                        Location location = player.getLocation();

                        bw.write(player.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ());

                        bw.close();
                        fw.close();

                        int x = (int) location.getX();
                        int y = (int) location.getY();
                        int z = (int) location.getZ();

                        player.sendMessage("§2Spawn défini aux coordonnées \nX: " + x + "\nY: " + y+ "\nZ: " + z);

                    }else{
                        player.sendMessage("§4Vous n'avez pas la permission d'éxécuter cette commande");
                    }

                }catch (IOException e){}

            }

        }
        if(command.getName().equalsIgnoreCase("craft")){

        }

        return true;
    }
}
