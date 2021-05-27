package fr.devthib.friendpluginmc.entreprises;

import fr.devthib.friendpluginmc.commands.StaticMethodCommands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Entreprise {

    String name;

    public Entreprise(String name){
        this.name = name;
    }

    public void create(EntrepriseType type,String ownerName){

        try {

            File entrprises = new File(System.getProperty("user.dir"), "PluginMC\\entreprises\\" + name + "\\");
            File properties = new File(System.getProperty("user.dir"),"PluginMC\\entreprises\\"+name+"\\properties.txt");
            File contributors = new File(System.getProperty("user.dir"),"PluginMC\\entreprises\\"+name+"\\contributors.txt");
            File contrats = new File(System.getProperty("user.dir"),"PluginMC\\entreprises\\"+name+"\\contrats.txt");

            if (!entrprises.exists()) {
                entrprises.mkdir();
            }

            if(!properties.exists()){

                properties.createNewFile();

                FileWriter fw = new FileWriter(properties);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(name + "/" + type + "/" + "0/" + "0/"+ownerName);

                bw.close();
                fw.close();

            }

            if(!contributors.exists()){
                contributors.createNewFile();

                FileWriter fw = new FileWriter(contributors);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(ownerName + "/");

                bw.close();
                fw.close();
            }

            if(!contrats.exists()){
                contrats.createNewFile();
            }

            ArrayList<Entreprise> all = StaticMethodCommands.getAllEntreprises();
            all.add(this);
            StaticMethodCommands.writeListOfEntreprises(all);

        }catch (IOException e){
            System.out.println("Une erreur est survenue");
        }

    }

    public String getName() {
        return name;
    }


}
