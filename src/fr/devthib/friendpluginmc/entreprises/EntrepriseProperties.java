package fr.devthib.friendpluginmc.entreprises;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;

public class EntrepriseProperties {

    Entreprise entreprise;
    String name;
    int bank;
    EntrepriseType type;
    int writedContrats;
    boolean exists;
    String ownerName;
    Player owner;
    ArrayList<Player> contributors = new ArrayList<>();
    ArrayList<Contrat> contrats = new ArrayList<>();

    public EntrepriseProperties(Entreprise entreprise){
        this.entreprise = entreprise;
        name = entreprise.getName();

        try {

            File entreprises = new File(System.getProperty("user.dir"), "PluginMC\\entreprises\\" + name + "\\properties.txt");

            if (entreprises.exists()) {
                exists = true;

                FileReader fr = new FileReader(entreprises);
                BufferedReader br = new BufferedReader(fr);

                String[] prop = br.readLine().split("/");
                this.type = EntrepriseType.valueOf(prop[1]);
                this.bank = Integer.parseInt(prop[2]);
                this.writedContrats = Integer.parseInt(prop[3]);
                this.ownerName = prop[4];
                this.owner = Bukkit.getPlayer(ownerName);

                br.close();
                fr.close();

            } else {
                exists = false;
            }

            File contributors = new File(System.getProperty("user.dir"),"PluginMC\\entreprises\\"+name+"\\contributors.txt");
            if(contributors.exists()){

                FileReader fr = new FileReader(contributors);
                BufferedReader br = new BufferedReader(fr);

                String[] prop = br.readLine().split("/");

                for (String s : prop) {
                    this.contributors.add(Bukkit.getPlayer(s));
                }

                br.close();
                fr.close();

            }

            File contrats = new File(System.getProperty("user.dir"),"PluginMC\\entreprises\\"+name+"\\contrats.txt");
            if(contrats.exists()){

                try {

                    FileReader fr = new FileReader(contrats);
                    BufferedReader br = new BufferedReader(fr);

                    String str = br.readLine();

                    while(str != null){
                        String[] proper = str.split(",");
                        int toPaid = Integer.parseInt(proper[1]);

                        this.contrats.add(new Contrat(entreprise, Bukkit.getPlayer(proper[0]), toPaid, proper[2]));
                        str = br.readLine();
                    }

                    br.close();
                    fr.close();

                }catch (NullPointerException e){}

            }

        }catch (IOException e){}

    }

    public boolean exists(){
        return exists;
    }

    public String getName() {
        return name;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public EntrepriseType getType() {
        return type;
    }

    public int getBank() {
        return bank;
    }

    public int getWritedContrats() {
        return writedContrats;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Player getOwner() {
        return owner;
    }

    public ArrayList<Player> getContributors() {
        return contributors;
    }

    public ArrayList<Contrat> getContrats() {
        return contrats;
    }

    public void addContributor(Player player){

        ArrayList<Player> contributors = getContributors();

        try {

            File contri = new File(System.getProperty("user.dir"), "PluginMC\\entreprises\\" + name + "\\contributors.txt");
            if (contri.exists()) {

                contributors.add(player);

                FileWriter fw = new FileWriter(contri);
                BufferedWriter bw = new BufferedWriter(fw);
                String toWrite = "";

                for(Player player1 : contributors){
                    toWrite += player1.getName()+"/";
                }

                bw.write(toWrite);

                bw.close();
                fw.close();

            }

        }catch (IOException e){
            System.out.println("Une IOException est survenue !");
        }

    }

    public void removeContributor(Player player){

        ArrayList<Player> contributors = getContributors();

        try {

            File contri = new File(System.getProperty("user.dir"), "PluginMC\\entreprises\\" + name + "\\contributors.txt");
            if (contri.exists()) {

                contributors.remove(player);

                FileWriter fw = new FileWriter(contri);
                BufferedWriter bw = new BufferedWriter(fw);
                String toWrite = "";

                for(Player player1 : contributors){
                    toWrite += player1.getName()+"/";
                }

                bw.write(toWrite);

                bw.close();
                fw.close();

                ArrayList<Player> players = getContributors();

                if(players.size() == 0){
                    File entrprises = new File(System.getProperty("user.dir"), "PluginMC\\entreprises\\" + name + "\\");
                    if(entrprises.exists()){
                        entrprises.delete();
                        System.out.println("ui");
                    }else{
                        System.out.println("no");
                    }
                }

            }

        }catch (IOException e){
            System.out.println("Une IOException est survenue !");
        }

    }

    public void addToBank(int toAdd){

        try {

            File entreprises = new File(System.getProperty("user.dir"), "PluginMC\\entreprises\\" + name + "\\properties.txt");

            if (entreprises.exists()) {

                FileReader fr = new FileReader(entreprises);
                BufferedReader br = new BufferedReader(fr);

                String[] prop = br.readLine().split("/");

                br.close();
                fr.close();

                int money = Integer.parseInt(prop[2]);
                money += toAdd;
                prop[2] = String.valueOf(money);

                FileWriter fw = new FileWriter(entreprises);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(prop[0] + "/" + prop[1] + "/" + prop[2] +"/" +prop[3] +"/"+prop[4]);

                bw.close();
                fw.close();

            }
        }catch (IOException e){
            System.out.println("Une IoException est survenue !");
        }

    }

    public void removeToBank(int toRemove){

        try {

            File entreprises = new File(System.getProperty("user.dir"), "PluginMC\\entreprises\\" + name + "\\properties.txt");

            if (entreprises.exists()) {

                FileReader fr = new FileReader(entreprises);
                BufferedReader br = new BufferedReader(fr);

                String[] prop = br.readLine().split("/");

                br.close();
                fr.close();

                int money = Integer.parseInt(prop[2]);
                money -= toRemove;
                prop[2] = String.valueOf(money);

                FileWriter fw = new FileWriter(entreprises);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(prop[0] + "/" + prop[1] + "/" + prop[2] +"/" +prop[3] +"/"+prop[4]);

                bw.close();
                fw.close();

            }
        }catch (IOException e){
            System.out.println("Une IoException est survenue !");
        }

    }

    public void addContrat(int paid,Player whoAsk,String clause){

        ArrayList<Contrat> contrats = getContrats();
        contrats.add(new Contrat(entreprise,whoAsk,paid,clause));

        File con = new File(System.getProperty("user.dir"),"PluginMC\\entreprises\\"+name+"\\contrats.txt");
        if(con.exists()){

            try {

                FileWriter fw = new FileWriter(con);
                BufferedWriter bw = new BufferedWriter(fw);

                for(Contrat contrat : contrats){
                    bw.write(contrat.getWhoAsk().getName()+","+ contrat.getToPaid()+","+contrat.getClause());
                    bw.newLine();
                }

                bw.close();
                fw.close();

            }catch (IOException e){}

        }

    }

    public void removeContrat(Player whoAsk) {

        ArrayList<Contrat> contrats = getContrats();

        for(int i = 0; i < contrats.size(); i++){
            if(contrats.get(i).getWhoAsk().getName().equals(whoAsk.getName())){
                contrats.remove(i);
            }
        }

        File con = new File(System.getProperty("user.dir"), "PluginMC\\entreprises\\" + name + "\\contrats.txt");
        if (con.exists()) {

            try {

                FileWriter fw = new FileWriter(con);
                BufferedWriter bw = new BufferedWriter(fw);

                for (Contrat c : contrats) {
                    bw.write(c.getWhoAsk().getName() + "," + c.getToPaid() + "," + c.getClause());
                    bw.newLine();
                }

                bw.close();
                fw.close();

            } catch (IOException e) {
            }

        }
    }

    public void addWritedContrat(){
        try {

            File entreprises = new File(System.getProperty("user.dir"), "PluginMC\\entreprises\\" + name + "\\properties.txt");

            if (entreprises.exists()) {

                FileReader fr = new FileReader(entreprises);
                BufferedReader br = new BufferedReader(fr);

                String[] prop = br.readLine().split("/");

                br.close();
                fr.close();

                int writed = Integer.parseInt(prop[3]);
                writed += 1;
                prop[3] = String.valueOf(writed);

                FileWriter fw = new FileWriter(entreprises);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(prop[0] + "/" + prop[1] + "/" + prop[2] +"/" +prop[3] +"/"+prop[4]);

                bw.close();
                fw.close();

            }
        }catch (IOException e){
            System.out.println("Une IoException est survenue !");
        }
    }
}
