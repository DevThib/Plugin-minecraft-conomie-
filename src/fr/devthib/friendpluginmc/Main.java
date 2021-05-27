package fr.devthib.friendpluginmc;

import fr.devthib.friendpluginmc.commands.Commands;
import fr.devthib.friendpluginmc.entreprises.EntrepriseCommandExecutor;
import fr.devthib.friendpluginmc.listeners.EventListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("\n____________________________________\nPlugin ON pret a faire feu\n____________________________________");
        getCommand("hdv").setExecutor(new Commands());
        getCommand("alert").setExecutor(new Commands());
        getCommand("money").setExecutor(new Commands());
        getCommand("sell").setExecutor(new Commands());
        getCommand("sethome").setExecutor(new Commands());
        getCommand("home").setExecutor(new Commands());
        getCommand("delhome").setExecutor(new Commands());
        getCommand("pay").setExecutor(new Commands());
        getCommand("repair").setExecutor(new Commands());
        getCommand("spawn").setExecutor(new Commands());
        getCommand("setspawn").setExecutor(new Commands());
        getCommand("craft").setExecutor(new Commands());
        getCommand("anvil").setExecutor(new Commands());
        getCommand("e").setExecutor(new EntrepriseCommandExecutor());

        getServer().getPluginManager().registerEvents(new EventListener(),this);
        createAllFiles();
    }

    @Override
    public void onDisable() {
        System.out.println("\n____________________________________\nPlugin eteint rip :(\n____________________________________");
    }

    public static void main(String[] args) {



    }

    public void createAllFiles(){

        try {

            File firstDirectory = new File(System.getProperty("user.dir"), "PluginMC\\");
            if (!firstDirectory.exists()) {
                firstDirectory.mkdir();
                System.out.println("-----Dossier 1 crée avec succes-----");
            }

            File secondDirectory = new File(System.getProperty("user.dir"), "PluginMC\\economy\\");
            if (!secondDirectory.exists()) {
                secondDirectory.mkdir();
                System.out.println("-----Dossier 2 crée avec succes-----");
            }

            File otherDirectory = new File(System.getProperty("user.dir"), "PluginMC\\economy\\hdv");
            if (!otherDirectory.exists()) {
                otherDirectory.mkdir();
                System.out.println("-----Dossier 3 crée avec succes-----");
            }

            File fourDirectory = new File(System.getProperty("user.dir"), "PluginMC\\economy\\money");
            if (!fourDirectory.exists()) {
                fourDirectory.mkdir();
                System.out.println("-----Dossier 4 crée avec succes-----");
            }

            File fiveDirectory = new File(System.getProperty("user.dir"), "PluginMC\\homes\\");
            if (!fiveDirectory.exists()) {
                fiveDirectory.mkdir();
                System.out.println("-----Dossier 5 crée avec succes-----");
            }

            File homes = new File(System.getProperty("user.dir"), "PluginMC\\homes\\homes.txt");
            if (!homes.exists()) {
                homes.createNewFile();
                System.out.println("-----Fichier des homes crée avec succes-----");
            }

            File hdv = new File(System.getProperty("user.dir"), "PluginMC\\economy\\hdv\\announcements.txt");
            if (!hdv.exists()) {
                hdv.createNewFile();
                System.out.println("-----fichier hdv crée avec succes-----");
            }

            File buyCommand = new File(System.getProperty("user.dir"), "PluginMC\\economy\\buycommand\\");
            if (!buyCommand.exists()) {
                buyCommand.mkdir();
                System.out.println("-----dossier commandes achetables crée avec succes-----");
            }

            File filesFolder = new File(System.getProperty("user.dir"),"PluginMC\\files\\");
            if(!filesFolder.exists()){
                filesFolder.mkdir();
                System.out.println("-----Dossier 6 crée avec succes-----");
            }

            File folderSpawn = new File(System.getProperty("user.dir"),"PluginMC\\files\\spawn\\");
            if(!folderSpawn.exists()){
                folderSpawn.mkdir();
                System.out.println("-----Dossier 7 crée avec succes-----");
            }


            File sapwn = new File(System.getProperty("user.dir"), "PluginMC\\files\\spawn\\spawn.txt");
            if (!sapwn.exists()) {
                sapwn.createNewFile();

                FileWriter fw = new FileWriter(sapwn);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write("world,0,0,0");

                bw.close();
                fw.close();

                System.out.println("-----fichier spawn crée avec succes-----");
            }

            File entrprises = new File(System.getProperty("user.dir"),"PluginMC\\entreprises\\");
            if(!entrprises.exists()){
                entrprises.mkdir();
                System.out.println("-----Dossier entreprises crée avec succes-----");
            }

            File allEntr = new File(System.getProperty("user.dir"),"PluginMC\\entreprises\\allEntreprises.txt");
            if(!allEntr.exists()){
                allEntr.createNewFile();
                System.out.println("-----Fichier entreprises crée avec succes-----");
            }


        }catch (IOException e){
            System.out.println("-----Une erreur est survenue lors de la création d'un fichier,redémarrer le plugin-----");
        }

    }

}
