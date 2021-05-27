package fr.devthib.friendpluginmc.entreprises;

import com.mysql.fabric.xmlrpc.base.Array;
import fr.devthib.friendpluginmc.commands.StaticMethodCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class EntrepriseCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if(sender instanceof Player){

            Player player = (Player) sender;

            try {

                String action = strings[0];

                if (action.equalsIgnoreCase("help")) {
                    player.sendMessage("§2Aides entreprises : §9\n- /e create <nom> : Créer une entreprise\n- /e list : Liste des entreprises\n- /e help : Affiche cette aide\n- /e info <nom> : Informations sur une entreprise\n- /e deposit : déposer de l'argent dans la banque de votre entreprise\n- /e remove : retirer de l'argent a la banque de votre entreprise\n- /e add : ajouter un joueur à votre entreprise\n- /e mandate <entreprise> <prix> <demande> : mandater une entreprise\n- /e denied <joueur> : refuser le contrat d'un joueur\n- /e end <entreprise> termine le contrat avec l'entreprise\n- /e contrat <joueur> : obtenir les contrats en cour avec un joueur\n- /e contrats : obtenir les contrats en cour dans votre entreprise");
                }
                if (action.equalsIgnoreCase("list")) {
                    ArrayList<Entreprise> entreprises = StaticMethodCommands.getAllEntreprises();


                //pour la liste des entreprises,faire sous la forme d'un inventaire (a chaque fois ca ajoute une table de craft quand c'est build,un blé quand c'est farm etc... et faire un système de pages
                //pour cette liste,quand on clique sur l'icone de l'entreprise on arrive sur un menu pour mandater l'entreprise (a dev plus tard ca )




                }

                if (action.equalsIgnoreCase("create")) {

                    try {

                        String name = strings[1];
                        File entreprises = new File(System.getProperty("user.dir"), "PluginMC\\entreprises\\" + name + "\\");

                        if (!entreprises.exists()) {
                            StaticMethodCommands.showEntrepriseCreateMenu(player, name);
                        } else {
                            player.sendMessage("§4Ce nom d'entreprise existe déjà");
                        }

                    } catch (IndexOutOfBoundsException e) {
                        player.sendMessage("§4Veuillez mettre un nom d'entreprise");
                    }

                }
                if (action.equalsIgnoreCase("info")) {

                    try {

                        String name = strings[1];
                        EntrepriseProperties properties = new EntrepriseProperties(new Entreprise(name));

                        if (properties.exists()) {
                            StaticMethodCommands.showEntrepriseInfoMenu(properties, player);
                        } else {
                            player.sendMessage("§4Entreprise inconnue !");
                        }

                    } catch (IndexOutOfBoundsException | NullPointerException e) {

                        try {

                            Entreprise entreprise = StaticMethodCommands.getEntrepriseToAPlayer(player);
                            StaticMethodCommands.showEntrepriseInfoMenu(new EntrepriseProperties(entreprise), player);

                        }catch (NullPointerException a){
                            player.sendMessage("§4Veuillez mettre un nom d'entreprise");
                        }

                    }

                }
                if (action.equalsIgnoreCase("mandate")) {

                    try {

                        String entrepriseName = strings[1];
                        int price = Integer.parseInt(strings[2]);
                        String clause = "";

                        for (int i = 3; i < strings.length; i++) {
                            clause += strings[i] + " ";
                        }

                        Entreprise entreprise = StaticMethodCommands.getEntrepriseByName(entrepriseName);

                        if(entreprise != null){

                            if(!StaticMethodCommands.isInEntreprise(player) || !StaticMethodCommands.getEntrepriseToAPlayer(player).getName().equalsIgnoreCase(entreprise.getName())) {

                                if(!StaticMethodCommands.haveAContratWithEntreprise(player,entreprise)) {

                                    int money = StaticMethodCommands.getMoneyToAPlayer(player);

                                    if (money >= price) {

                                        EntrepriseProperties properties = new EntrepriseProperties(entreprise);

                                        properties.addContrat(price, player, clause);
                                        StaticMethodCommands.removeMoneyFromAPlayer(player, price);

                                        player.sendMessage("§5Contrat avec l'entreprise " + entrepriseName + " débuté !");
                                        player.sendMessage("§cVous avez été prélevé de " + price + " $ L'entreprise ne recevera cette argent que lorsque vous terminerez le contrat avec /e end "+entrepriseName);

                                        properties.getOwner().sendMessage("§aLe joueur "+player.getName()+" vient de démarrer un contrat avec votre entreprise ! Faites /e contrat "+player.getName()+" pour voir ce contrat");

                                    } else {
                                        player.sendMessage("§4Vous n'avez pas assez pour payer");
                                    }

                                }else{
                                    player.sendMessage("§4Vous avez déja un contrat en cour avec cette entreprise");
                                }
                            }else{
                                player.sendMessage("§4Vous ne pouvez pas mandater votre propre entreprise");
                            }

                        }else{
                            player.sendMessage("§4Entreprise "+entrepriseName+" inconnue");
                        }


                    }catch (IndexOutOfBoundsException  | NumberFormatException e){
                        player.sendMessage("§4Veuillez mettre sous la forme /e mandate <nom de l'entreprise> <prix> <clause>");
                    }

                }
                if (action.equalsIgnoreCase("deposit")) {
                    try {

                        Entreprise entreprise = StaticMethodCommands.getEntrepriseToAPlayer(player);

                            int amount = Integer.parseInt(strings[1]);
                            int money = StaticMethodCommands.getMoneyToAPlayer(player);

                            if (money >= amount) {


                                EntrepriseProperties properties = new EntrepriseProperties(entreprise);

                                properties.addToBank(amount);

                                StaticMethodCommands.removeMoneyFromAPlayer(player, amount);

                                player.sendMessage("§aVous avez ajouté " + amount + " $ à la banque de votre entreprise");

                            } else {
                                player.sendMessage("§4Vous n'avez pas assez d'argent pour déposer autant !");
                            }

                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        player.sendMessage("§4Veuillez mettre un nombre valide");
                    }catch (NullPointerException e){
                        player.sendMessage("§4Vous n'êtes pas dans une entreprise");
                    }
                }
                if (action.equalsIgnoreCase("remove")) {
                    try {

                        Entreprise entreprise = StaticMethodCommands.getEntrepriseToAPlayer(player);

                            EntrepriseProperties properties = new EntrepriseProperties(entreprise);

                            if (properties.getOwnerName().equalsIgnoreCase(player.getName())) {

                                int amount = Integer.parseInt(strings[1]);
                                int money = properties.getBank();

                                if (money >= amount) {

                                    properties.removeToBank(amount);
                                    StaticMethodCommands.addMoneyToAPlayer(player, amount);
                                    player.sendMessage("§aVous avez retiré " + amount + " $ à la banque de votre entreprise");

                                } else {
                                    player.sendMessage("§4Votre entreprise ne possède que "+properties.getBank()+" $");
                                }

                            } else {
                                player.sendMessage("§4Vous n'êtes pas le propriétaire de votre entreprise");
                            }

                    } catch (NumberFormatException e) {
                        player.sendMessage("§4Veuillez mettre un nombre valide");
                        //plus tard faire en sorte qu'on puisse faire "all" pour déposer tout
                    }catch (NullPointerException e){
                        player.sendMessage("§4Vous n'êtes pas dans une entreprise");
                    }
                }
                if (action.equalsIgnoreCase("add")) {
                    try {

                        Entreprise entreprise = StaticMethodCommands.getEntrepriseToAPlayer(player);

                        Entreprise entreprise1 = StaticMethodCommands.getEntrepriseToAPlayer(Bukkit.getPlayer(strings[1]));

                        if(entreprise1 == null) {

                            EntrepriseProperties properties = new EntrepriseProperties(entreprise);

                            if (properties.getOwnerName().equalsIgnoreCase(player.getName())) {
                                String playerAddName = strings[1];
                                Player toAdd = Bukkit.getPlayer(playerAddName);

                                StaticMethodCommands.addPlayerToEntreprise(toAdd, StaticMethodCommands.getEntrepriseToAPlayer(player));
                                Bukkit.getPlayer(playerAddName).sendMessage("§5" + player.getName() + " vous a ajouté a son entreprise");
                            } else {
                                player.sendMessage("§4Vous n'êtes pas le propriétaire de votre entreprise");
                            }

                        }else{
                            player.sendMessage("Ce joueur est déja dans une entreprise");
                        }

                    } catch (IndexOutOfBoundsException e) {
                        player.sendMessage("§4Veuillez mettre un nombre valide");
                    }catch (NullPointerException e){
                        player.sendMessage("§4Vous n'êtes pas dans une entreprise");
                    }
                }
                if (action.equalsIgnoreCase("leave")) {

                   Entreprise entreprise = StaticMethodCommands.getEntrepriseToAPlayer(player);

                    try {

                        EntrepriseProperties properties = new EntrepriseProperties(entreprise);
                        properties.removeContributor(player);
                        player.sendMessage("§cVous avez quitté l'entreprise");

                    }catch (NullPointerException e){
                        player.sendMessage("§4Vous n'êtes pas dans une entreprise");
                    }

                }
                if(action.equalsIgnoreCase("contrats")){

                    Entreprise entreprise = StaticMethodCommands.getEntrepriseToAPlayer(player);

                    try {

                        EntrepriseProperties properties = new EntrepriseProperties(entreprise);
                        String toSend = "";

                        for (Contrat contrat : properties.getContrats()) {
                            toSend += "Contrat : " + contrat.getWhoAsk().getName() + " : " + contrat.getToPaid() + " $\n";
                        }

                        player.sendMessage("§9Contrats en cours pour "+properties.getName()+" :\n"+toSend+"§5");

                    }catch (NullPointerException e){
                        player.sendMessage("§4Vous n'êtes pas dans une entreprise");
                    }

                }
                if(action.equalsIgnoreCase("contrat")){

                    Player player1 = Bukkit.getPlayer(strings[1]);

                    Entreprise entreprise = StaticMethodCommands.getEntrepriseToAPlayer(player1);

                    try {

                        if (StaticMethodCommands.haveAContratWithEntreprise(player1, entreprise)) {

                            Contrat contrat = StaticMethodCommands.getContrat(player1, entreprise);

                            player.sendMessage("§4Contrat avec " + contrat.getWhoAsk().getName() + " :§1\n- Entreprise : " + contrat.getEntreprise().getName() + "\n- Demandeur : " + contrat.getWhoAsk().getName() + "\n- Prix : " + contrat.getToPaid() + " $\n- Clause : " + contrat.getClause());

                        } else {
                            player.sendMessage("§5Ce joueur n'a aucun contrat en cour avec votre entreprise");
                        }

                    }catch (NullPointerException e){
                        player.sendMessage("§4Vous n'êtes pas dans une entreprise ou bien ce joueur n'existe pas");
                    }

                }
                if(action.equalsIgnoreCase("denied")){
                    String playerName = strings[1];
                    Player player1 = Bukkit.getPlayer(playerName);
                    Entreprise entreprise = StaticMethodCommands.getEntrepriseToAPlayer(player);

                    try {

                        if (StaticMethodCommands.haveAContratWithEntreprise(player1, entreprise)){

                            EntrepriseProperties properties = new EntrepriseProperties(entreprise);
                            properties.removeContrat(player1);

                            player.sendMessage("§2Vous avez annulé votre contrat avec "+playerName);
                            player1.sendMessage("§4L'entreprise "+properties.getName()+" a annulé le contrat avec vous");

                        }else{
                         player.sendMessage("§4Ce joueur n'a aucun contrat en cour avec votre entreprise");
                        }

                    }catch (NullPointerException e){
                        player.sendMessage("§4Vous n'êtes pas dans une entreprise ou bien ce joueur n'existe pas");
                    }catch (IndexOutOfBoundsException e){
                        player.sendMessage("§4Veuillez mettre un joueur");
                    }

                }
                if(action.equalsIgnoreCase("end")){
                    String entrepriseName = strings[1];

                    try {

                        Entreprise entreprise = new Entreprise(entrepriseName);

                        if(StaticMethodCommands.haveAContratWithEntreprise(player,entreprise)){

                            EntrepriseProperties properties = new EntrepriseProperties(entreprise);
                            Contrat contrat = StaticMethodCommands.getContrat(player,entreprise);

                            properties.removeContrat(player);
                            properties.addToBank(contrat.getToPaid());
                            properties.addWritedContrat();
                            properties.getOwner().sendMessage("§2Le joueur "+player.getName()+" vient de mettre fin au contrat avec votre entreprise ! Votre entreprise a recu "+contrat.getToPaid()+" $");
                            player.sendMessage("§2Vous venez de mettre fin au contrat avec "+entreprise.getName()+" ! Celle ci a recu les "+contrat.getToPaid()+" $");

                        }else{
                            player.sendMessage("§4Vous n'avez aucun contrat en cour avec cette entreprise");
                        }

                    }catch (NullPointerException e){
                        player.sendMessage("§4Cette entreprise n'existe pas");
                    }

                }
                //un end qui permet de finir et l'entreprise recoit ses sous



                //attention ban pas fait
            }catch (IndexOutOfBoundsException e){
                player.sendMessage("§4Un argument a été manquant");
            }

        }

        return true;
    }
}
