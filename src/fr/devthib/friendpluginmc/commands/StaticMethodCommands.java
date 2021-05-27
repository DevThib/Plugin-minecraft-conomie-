package fr.devthib.friendpluginmc.commands;

import com.mysql.fabric.xmlrpc.base.Array;
import com.mysql.jdbc.Buffer;
import fr.devthib.friendpluginmc.HDVAnnouncement;
import fr.devthib.friendpluginmc.Home;
import fr.devthib.friendpluginmc.entreprises.Contrat;
import fr.devthib.friendpluginmc.entreprises.Entreprise;
import fr.devthib.friendpluginmc.entreprises.EntrepriseProperties;
import fr.devthib.friendpluginmc.entreprises.EntrepriseType;
import fr.devthib.friendpluginmc.listeners.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.yaml.snakeyaml.events.Event;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class StaticMethodCommands {

    public static void showHdvMenu(Player player){

        Inventory inventory = Bukkit.createInventory(player,27,"HDV Menu");

        inventory.setItem(12,getItem(Material.DIAMOND,"§cAdminshop","Menu de l'adminshop"));
        inventory.setItem(13,getItem(Material.COMMAND_BLOCK,"§bCommandes achetables","Menu des commandes achetables"));
        inventory.setItem(14,getItem(Material.BOOK,"§bHôtel de vente","Affiche les annonces à l'hotêl de vente"));

        if(!EventListener.name.containsKey(player)){
            EventListener.name.put(player,"");
        }

        EventListener.name.replace(player,"HDV Menu");

        player.openInventory(inventory);
    }

    public static void showMenuAdminShop(Player player){

        Inventory inventory = Bukkit.createInventory(player,27,"AdminShop menu");

        inventory.setItem(12,getItem(Material.DEAD_BUSH,"§4Acheter","Aller dans le menu d'achat des items"));
        inventory.setItem(14,getItem(Material.DIAMOND_BLOCK,"§2Vendre","Aller dans le menu de vente des items"));
        inventory.setItem(26,getItem(Material.ARROW,"§5Retour",""));

        if(!EventListener.name.containsKey(player)){
            EventListener.name.put(player,"");
        }

        EventListener.name.replace(player,"AdminShop Menu");

        player.openInventory(inventory);
    }

    public static void showHotelMenu(Player player){

        ArrayList<HDVAnnouncement> announcements = StaticMethodCommands.getAllAnnouncements();

        Inventory inventory = Bukkit.createInventory(player,54,"§bHôtel de vente");

        for(int i = 0; i < announcements.size(); i++){
            int quant = announcements.get(i).getQuantity();
            for(int a = 0; a < quant; a++){
                inventory.addItem(getItem(announcements.get(i).getMaterial(),announcements.get(i).getPrice()+" $", announcements.get(i).getOwnerName()));
            }
        }

        if(!EventListener.name.containsKey(player)){
            EventListener.name.put(player,"");
        }

        EventListener.name.replace(player,"§bHôtel de vente");

        player.openInventory(inventory);

    }

    public static void showBuyAdminShop(Player player){

        Inventory inventory = Bukkit.createInventory(player,27,"AdminShop Achat");

        inventory.setItem(15,getItem(Material.WHITE_WOOL,"§fAcheter 1 Laine","Prix : 10 $"));
        inventory.setItem(14,getItem(Material.OAK_WOOD,"§6Acheter 1 Bois","Prix : 2 $"));
        inventory.setItem(13,getItem(Material.EMERALD,"§aAcheter 1 Emeraude","Prix : 75 $"));
        inventory.setItem(12,getItem(Material.IRON_INGOT,"§7Acheter 1 Fer","Prix : 25 $"));
        inventory.setItem(11,getItem(Material.GOLD_INGOT,"§eAcheter 1 Or","Prix : 40 $"));
        inventory.setItem(10,getItem(Material.DIAMOND,"§bAcheter 1 Diamant","Prix : 75 $"));

        inventory.setItem(26,getItem(Material.ARROW,"§5Retour",""));

        String msg = "§eVotre argent : "+getMoneyToAPlayer(player)+" $";
        inventory.setItem(8,getItem(Material.YELLOW_SHULKER_BOX,msg,""));

        if(!EventListener.name.containsKey(player)){
            EventListener.name.put(player,"");
        }

        EventListener.name.replace(player,"AdminShop Achat");

        player.openInventory(inventory);

    }

    public static void showSellAdminShop(Player player){

        Inventory inventory = Bukkit.createInventory(player,27,"AdminShop Vente");

        inventory.setItem(15,getItem(Material.WHITE_WOOL,"§fVendre 1 Laine","Vente : 2 $"));
        inventory.setItem(14,getItem(Material.EMERALD,"§aVendre 1 Emeraude","Vente : 35 $"));
        inventory.setItem(13,getItem(Material.IRON_INGOT,"§7Vendre 1 Fer","Vente : 12 $"));
        inventory.setItem(12,getItem(Material.GOLD_INGOT,"§eVendre 1 Or","Vente : 20 $"));
        inventory.setItem(11,getItem(Material.DIAMOND,"§bVendre 1 Diamant","Vente : 35 $"));

        inventory.setItem(26,getItem(Material.ARROW,"§5Retour",""));

        String msg = "§eVotre argent : "+getMoneyToAPlayer(player)+" $";
        inventory.setItem(8,getItem(Material.YELLOW_SHULKER_BOX,msg,""));

        if(!EventListener.name.containsKey(player)){
            EventListener.name.put(player,"");
        }

        EventListener.name.replace(player,"AdminShop Vente");

        player.openInventory(inventory);

    }

    public static void showBuyCommandMenu(Player player){

        Inventory inventory = Bukkit.createInventory(player,27,"Commandes Achetables");

        ArrayList<String> commands = getAvailableCommands(player);
        if(!commands.contains("homes")){
            inventory.setItem(13,getItem(Material.BRICK,"§4Homes","Prix : 1500 $"));
        }else{
            inventory.setItem(13,getItem(Material.BRICK,"§2Homes","Déjà Acheté !"));
        }

        if(!commands.contains("chest")){
            inventory.setItem(14,getItem(Material.CHEST,"§4Sauvegarde du stuff","Prix : 500 $"));
        }else{
            inventory.setItem(14,getItem(Material.CHEST,"§2Sauvegarde du stuff","Déjà Acheté !"));
        }

        inventory.setItem(26,getItem(Material.ARROW,"§5Retour",""));

        if(!EventListener.name.containsKey(player)){
            EventListener.name.put(player,"");
        }

        EventListener.name.replace(player,"Commandes Achetables");

        player.openInventory(inventory);

    }

    public static ItemStack getItem(Material material,String name,String description){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta1 = itemStack.getItemMeta();
        meta1.setDisplayName(name);
        meta1.setLore(Collections.singletonList(description));
        itemStack.setItemMeta(meta1);
        return itemStack;
    }

    public static void removeMoneyFromAPlayer(Player player,int toRemove){

        try {

            File file = new File(System.getProperty("user.dir"), "PluginMC\\economy\\money\\" + player.getName()+".txt");

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            int nb = Integer.parseInt(br.readLine());
            int diff = nb-toRemove;
            String toWrite = String.valueOf(diff);

            br.close();
            fr.close();

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(toWrite);

            bw.close();
            fw.close();

        }catch (IOException e ){}

    }

    public static void addMoneyToAPlayer(Player player,int toAdd){

        try {

            File file = new File(System.getProperty("user.dir"), "PluginMC\\economy\\money\\" + player.getName()+".txt");

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

           int nb = Integer.parseInt(br.readLine());
           int diff = nb+toAdd;
           String toWrite = String.valueOf(diff);

           br.close();
           fr.close();

           FileWriter fw = new FileWriter(file);
           BufferedWriter bw = new BufferedWriter(fw);

           bw.write(toWrite);

           bw.close();
           fw.close();

        }catch (IOException e ){}

    }

    public static int getMoneyToAPlayer(Player player){

        try {

            File file = new File(System.getProperty("user.dir"), "PluginMC\\economy\\money\\" + player.getName()+".txt");

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            return Integer.parseInt(br.readLine());

        }catch (IOException e ){
            player.sendMessage("§4Une erreur s'est produite votre argent n'a pas pu être récupéré");
        }

        return 0;
    }

    public static void createAccountToAPlayer(Player player){
        try {
            File file = new File(System.getProperty("user.dir"), "PluginMC\\economy\\money\\" + player.getName()+".txt");
            if (!file.exists()) {
                file.createNewFile();

                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write("50");

                bw.close();
                fw.close();

                System.out.println("-----Compte de "+player.getName()+" cree avec succes-----");
            }

        }catch (IOException e){
            System.out.println("-----Une erreur est survenue lors de la création du compte de "+player.getName()+"-----");
        }
    }

    public static void addHDVAnnouncements(HDVAnnouncement announcement){

        ArrayList<HDVAnnouncement> announcements = getAllAnnouncements();
        announcements.add(announcement);
        writeListOfAnnouncements(announcements);

    }

    public static void removeHDVAnnouncement(HDVAnnouncement announcement){

            boolean isRemoved = false;
            ArrayList<HDVAnnouncement> announcements = getAllAnnouncements();

            for(int i = 0; i < announcements.size(); i++){
                if(!isRemoved){
                    if(announcements.get(i) == announcement){
                        announcements.remove(announcement);
                        isRemoved = true;
                    }
                }
            }

            writeListOfAnnouncements(announcements);

    }

    public static void removeHDVAnnoncementWithNumber(int number){
        ArrayList<HDVAnnouncement> announcements = getAllAnnouncements();
        announcements.remove(announcements.get(number));
        writeListOfAnnouncements(announcements);
    }

    public static HDVAnnouncement getAnnoncementsWithNumber(int number){
        return getAllAnnouncements().get(number);
    }

    public static ArrayList<HDVAnnouncement> getAllAnnouncements(){

        ArrayList<HDVAnnouncement> announcements = new ArrayList<>();

        try {

            File file = new File(System.getProperty("user.dir"), "PluginMC\\economy\\hdv\\announcements.txt");

            if (file.exists()) {

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                String read = br.readLine();
                while (read != null) {

                    String[] str = read.split(",");
                    Material material = Material.valueOf(str[0]);
                    int price = Integer.parseInt(str[1]);
                    String ownerName = str[2];
                    int quantity = Integer.parseInt(str[3]);

                    announcements.add(new HDVAnnouncement(material,price,ownerName,quantity));

                    read = br.readLine();
                }
                br.close();
                fr.close();

            }


        }catch (IOException e){
            System.out.println("-----Une erreur est survenue lors de la suppression d'une annonce a l'hdv-----");
        }

       return announcements;
    }

    public static void writeListOfAnnouncements(ArrayList<HDVAnnouncement> announcements){

        try {

            File file = new File(System.getProperty("user.dir"), "PluginMC\\economy\\hdv\\announcements.txt");

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            for(int i = 0; i < announcements.size(); i++){
                bw.write(announcements.get(i).getMaterial()+","+announcements.get(i).getPrice()+","+announcements.get(i).getOwnerName()+","+announcements.get(i).getQuantity());
                bw.newLine();
            }

            bw.close();
            fw.close();

        }catch (IOException e){

        }

    }

    public static void addHome(Home home){

        ArrayList<Home> homes = StaticMethodCommands.getAllHomes();
        homes.add(home);
        StaticMethodCommands.writeListOfHomes(homes);

    }

    public static void removeHome(Home home){
        ArrayList<Home> homes = getAllHomes();
        homes.remove(home);
        writeListOfHomes(homes);
    }

    public static void removeHomeByName(String name){
        ArrayList<Home> homes = getAllHomes();
        boolean isRemoved = false;

        for(int i = 0; i < homes.size(); i++){
            if(!isRemoved && homes.get(i).getName().equalsIgnoreCase(name)){
              isRemoved = true;
              homes.remove(i);
            }
        }
        writeListOfHomes(homes);

    }

    public static ArrayList<Home> getHomesByAPlayer(String playerName){
        ArrayList<Home> homes = getAllHomes();
        ArrayList<Home> homes1 = new ArrayList<>();

        for(int i = 0; i < homes.size(); i++){
            if(homes.get(i).getOwnerName().equalsIgnoreCase(playerName)){
                homes1.add(homes.get(i));
            }
        }
        return homes1;
    }

    public static ArrayList<Home> getAllHomes(){

        ArrayList<Home> homes = new ArrayList<>();

        try {

            File file = new File(System.getProperty("user.dir"), "PluginMC\\homes\\homes.txt");

            if (file.exists()) {

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                String read = br.readLine();
                while (read != null) {

                    String[] str = read.split(",");
                    String name = str[0];
                    String ownerName = str[1];
                    double x = Double.parseDouble(str[2]);
                    double y = Double.parseDouble(str[3]);
                    double z = Double.parseDouble(str[4]);

                   homes.add(new Home(ownerName,name,x,y,z));

                    read = br.readLine();
                }
                br.close();
                fr.close();
            }

        }catch (IOException e){
            System.out.println("Une erreur est survenue lors de la création d'un home");
        }

        return homes;
    }

    public static void writeListOfHomes(ArrayList<Home> homes){

        try {

            File file = new File(System.getProperty("user.dir"), "PluginMC\\homes\\homes.txt");

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            for(int i = 0; i < homes.size(); i++){
                bw.write(homes.get(i).getName()+","+homes.get(i).getOwnerName()+","+homes.get(i).getX()+","+homes.get(i).getY()+","+homes.get(i).getZ());
                bw.newLine();
            }

            bw.close();
            fw.close();

        }catch (IOException e){
            System.out.println("Une erreur est survenue lors de l'écriture des homes");
        }

    }

    public static Location readSpawn(){

        Location loc = new Location(Bukkit.getWorld("world"),0,0,0);

        try {

            File file = new File(System.getProperty("user.dir"), "PluginMC\\files\\spawn\\spawn.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String all = br.readLine();
            String[] values = all.split(",");

            World world = Bukkit.getWorld(values[0]);
            double x = Double.parseDouble(values[1]);
            double y = Double.parseDouble(values[2]);
            double z = Double.parseDouble(values[3]);

            loc = new Location(world,x,y,z);

            br.close();
            fr.close();

        }catch (IOException e){

        }

     return loc;
    }

    public static void createCommandAccount(Player player){
        try {
            File file = new File(System.getProperty("user.dir"), "PluginMC\\economy\\buycommand\\" + player.getName()+".txt");
            if (!file.exists()) {
                file.createNewFile();

                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.close();
                fw.close();

                System.out.println("-----Compte de "+player.getName()+"pour les commandes achetables cree avec succes-----");
            }

        }catch (IOException e){
            System.out.println("-----Une erreur est survenue lors de la création du compte de "+player.getName()+"-----");
        }
    }

    public static ArrayList<String> getAvailableCommands(Player player){
        File file = new File(System.getProperty("user.dir"), "PluginMC\\economy\\buycommand\\" + player.getName()+".txt");

        ArrayList<String> commands = new ArrayList<>();

        try {

            if (file.exists()) {

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                String msg = br.readLine();
                while (msg != null) {
                    commands.add(msg);
                    msg = br.readLine();
                }
                return commands;
            }

        }catch (IOException e){e.printStackTrace();}
        
        return null;
    }

    public static void writeListOfCommands(ArrayList<String> commands,Player player){
        File file = new File(System.getProperty("user.dir"), "PluginMC\\economy\\buycommand\\"+player.getName()+".txt");

        try {

            if (file.exists()) {

                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);

                for(int i = 0; i < commands.size(); i++){
                    bw.write(commands.get(i));
                    bw.newLine();
                }

                bw.close();
                fw.close();

            }

        }catch (IOException e){}

    }

    //entreprises

    public static ArrayList<Entreprise> getAllEntreprises(){

        ArrayList<Entreprise> all = new ArrayList<>();

        try {

            File allEntr = new File(System.getProperty("user.dir"), "PluginMC\\entreprises\\allEntreprises.txt");
            if (allEntr.exists()) {

                FileReader fr = new FileReader(allEntr);
                BufferedReader br = new BufferedReader(fr);

                String entrepriseName = br.readLine();

                while(entrepriseName != null){
                    all.add(new Entreprise(entrepriseName));
                    entrepriseName = br.readLine();
                }

                br.close();
                fr.close();

            }

        }catch (IOException e){}

        return all;
    }

    public static Entreprise getEntrepriseByName(String name){
        ArrayList<Entreprise> entreprises = getAllEntreprises();

        for(Entreprise entreprise : entreprises){
            if(entreprise.getName().equalsIgnoreCase(name)){
                return entreprise;
            }
        }

        return null;
    }

    public static void writeListOfEntreprises(ArrayList<Entreprise> entreprises){

        try {

            File allEntr = new File(System.getProperty("user.dir"), "PluginMC\\entreprises\\allEntreprises.txt");
            if (allEntr.exists()) {

                FileWriter fw = new FileWriter(allEntr);
                BufferedWriter bw = new BufferedWriter(fw);

                for(Entreprise entreprise : entreprises){
                    bw.write(entreprise.getName());
                    bw.newLine();
                }

                bw.close();
                fw.close();

            }

        }catch (IOException e){}
    }

    public static Entreprise getEntrepriseToAPlayer(Player player){

        Entreprise toReturn = null;

        ArrayList<Entreprise> entreprises = getAllEntreprises();

        for(Entreprise entreprise : entreprises){
            EntrepriseProperties properties = new EntrepriseProperties(entreprise);
            if(properties.getContributors().contains(player)){
                toReturn = entreprise;
            }
        }
        return toReturn;
    }

    public static void addPlayerToEntreprise(Player toAdd,Entreprise entreprise){
        EntrepriseProperties properties = new EntrepriseProperties(entreprise);
        properties.addContributor(toAdd);
    }

    public static void showEntrepriseCreateMenu(Player player,String entrepriseName){

        if(!EventListener.type.containsKey(player)){
            EventListener.type.put(player, EntrepriseType.FARM);
        }

        Inventory inventory = Bukkit.createInventory(player,27,"Créer une entreprise");

        inventory.setItem(11,getItem(Material.WHITE_WOOL,"§9Nom",entrepriseName));
        inventory.setItem(12,getItem(Material.COMMAND_BLOCK,"§bType", EventListener.type.get(player)+" : cliquez pour changer"));
        inventory.setItem(13,getItem(Material.YELLOW_SHULKER_BOX,"§eBanque","0$"));
        inventory.setItem(14,getItem(Material.PAPER,"§fContrats signés","0"));
        inventory.setItem(15,getItem(Material.PLAYER_HEAD,"§3Propriétaire", player.getName()));
        inventory.setItem(22,getItem(Material.GREEN_BANNER,"§2Créer","Côut : 1500$"));

        if(!EventListener.name.containsKey(player)){
            EventListener.name.put(player,"");
        }

        EventListener.name.replace(player,"Créer une entreprise");

        player.openInventory(inventory);
    }

    public static void showEntrepriseInfoMenu(EntrepriseProperties properties,Player player){

        Inventory inventory = Bukkit.createInventory(player,27,"Info entreprises");

        inventory.setItem(11,getItem(Material.WHITE_WOOL,"§9Nom",properties.getName()));
        inventory.setItem(12,getItem(Material.COMMAND_BLOCK,"§bType", String.valueOf(properties.getType())));
        inventory.setItem(13,getItem(Material.YELLOW_SHULKER_BOX,"§eBanque",properties.getBank()+"$"));
        inventory.setItem(14,getItem(Material.PAPER,"§fContrats signés", String.valueOf(properties.getWritedContrats())));
        inventory.setItem(15,getItem(Material.PLAYER_HEAD,"§3Propriétaire", properties.getOwnerName()));
        inventory.setItem(22,getItem(Material.RED_BANNER,"§4Fermer",""));

        if(!EventListener.name.containsKey(player)){
            EventListener.name.put(player,"");
        }

        EventListener.name.replace(player,"Info entreprises");

        player.openInventory(inventory);

    }

    public static boolean isInEntreprise(Player player){
        Entreprise entreprise = getEntrepriseToAPlayer(player);
        if(entreprise != null){
            return true;
        }else{
            return false;
        }
    }

    public static boolean haveAContratWithEntreprise(Player player,Entreprise entreprise){
        EntrepriseProperties properties = new EntrepriseProperties(entreprise);

        ArrayList<Contrat> contrats = properties.getContrats();

        for(Contrat contrat : contrats){
            if(contrat.getWhoAsk() == player){
                return true;
            }
        }
        return false;
    }

    public static Contrat getContrat(Player player,Entreprise entreprise){

        if(haveAContratWithEntreprise(player,entreprise)){

            EntrepriseProperties properties = new EntrepriseProperties(entreprise);

            ArrayList<Contrat> contrats = properties.getContrats();

            for(Contrat contrat : contrats){
                if(contrat.getWhoAsk().getName().equalsIgnoreCase(player.getName())){
                    return contrat;
                }
            }

        }
        return null;
    }

    public static void showEntreprisesList(Player player){

    }

}
