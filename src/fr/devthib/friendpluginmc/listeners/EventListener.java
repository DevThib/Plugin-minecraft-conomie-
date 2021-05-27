package fr.devthib.friendpluginmc.listeners;

import fr.devthib.friendpluginmc.HDVAnnouncement;
import fr.devthib.friendpluginmc.commands.StaticMethodCommands;
import fr.devthib.friendpluginmc.entreprises.Entreprise;
import fr.devthib.friendpluginmc.entreprises.EntrepriseProperties;
import fr.devthib.friendpluginmc.entreprises.EntrepriseType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class EventListener implements Listener {

    public static HashMap<Player,String> name = new HashMap<>();
    public static HashMap<Player, EntrepriseType> type = new HashMap<>();

    @EventHandler
    public void onInvClick(InventoryClickEvent event){

        try {

            Player player = (Player) event.getWhoClicked();
            ItemStack itemStack = event.getCurrentItem();
            Inventory inventory = event.getClickedInventory();

            if(itemStack != null) {

                if (name.get(player).equalsIgnoreCase("HDV Menu")) {
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§cAdminshop")) {
                        StaticMethodCommands.showMenuAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§bHôtel de vente")) {
                        StaticMethodCommands.showHotelMenu(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§bCommandes achetables")) {
                        StaticMethodCommands.showBuyCommandMenu(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                }

                if (name.get(player).equalsIgnoreCase("AdminShop menu")) {
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§4Acheter")) {
                        StaticMethodCommands.showBuyAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§2Vendre")) {
                        StaticMethodCommands.showSellAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getType() == Material.ARROW) {
                        StaticMethodCommands.showHdvMenu(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                }

                if (name.get(player).equalsIgnoreCase("AdminShop Achat")) {

                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§bAcheter 1 Diamant")) {
                        if (StaticMethodCommands.getMoneyToAPlayer(player) >= 75) {
                            player.getInventory().addItem(new ItemStack(Material.DIAMOND));
                            StaticMethodCommands.removeMoneyFromAPlayer(player, 75);
                        }
                        StaticMethodCommands.showBuyAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§eAcheter 1 Or")) {
                        if (StaticMethodCommands.getMoneyToAPlayer(player) >= 40) {
                            player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                            StaticMethodCommands.removeMoneyFromAPlayer(player, 40);
                        }
                        StaticMethodCommands.showBuyAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§7Acheter 1 Fer")) {
                        if (StaticMethodCommands.getMoneyToAPlayer(player) >= 25) {
                            player.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
                            StaticMethodCommands.removeMoneyFromAPlayer(player, 25);
                        }
                        StaticMethodCommands.showBuyAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§aAcheter 1 Emeraude")) {
                        if (StaticMethodCommands.getMoneyToAPlayer(player) >= 75) {
                            player.getInventory().addItem(new ItemStack(Material.EMERALD));
                            StaticMethodCommands.removeMoneyFromAPlayer(player, 75);
                        }
                        StaticMethodCommands.showBuyAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§6Acheter 1 Bois")) {
                        if (StaticMethodCommands.getMoneyToAPlayer(player) >= 2) {
                            player.getInventory().addItem(new ItemStack(Material.OAK_WOOD));
                            StaticMethodCommands.removeMoneyFromAPlayer(player, 2);
                        }
                        StaticMethodCommands.showBuyAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§fAcheter 1 Laine")) {
                        if (StaticMethodCommands.getMoneyToAPlayer(player) >= 10) {
                            player.getInventory().addItem(new ItemStack(Material.WHITE_WOOL));
                            StaticMethodCommands.removeMoneyFromAPlayer(player, 10);
                        }
                        StaticMethodCommands.showBuyAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getType() == Material.YELLOW_SHULKER_BOX) {
                        StaticMethodCommands.showBuyAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getType() == Material.ARROW) {
                        StaticMethodCommands.showMenuAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }

                }

                if (name.get(player).equalsIgnoreCase("AdminShop Vente")) {

                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§bVendre 1 Diamant")) {

                        if (player.getInventory().contains(new ItemStack(Material.DIAMOND))) {
                            player.getInventory().removeItem(new ItemStack(Material.DIAMOND));
                            StaticMethodCommands.addMoneyToAPlayer(player, 35);
                        } else {
                            player.sendMessage("§4Vous ne possédez pas l'item demandé");
                        }
                        StaticMethodCommands.showSellAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§eVendre 1 Or")) {
                        if (player.getInventory().contains(new ItemStack(Material.GOLD_INGOT))) {
                            player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                            StaticMethodCommands.addMoneyToAPlayer(player, 20);
                        } else {
                            player.sendMessage("§4Vous ne possédez pas l'item demandé");
                        }
                        StaticMethodCommands.showSellAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§7Vendre 1 Fer")) {
                        if (player.getInventory().contains(new ItemStack(Material.IRON_INGOT))) {
                            player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                            StaticMethodCommands.addMoneyToAPlayer(player, 12);
                        } else {
                            player.sendMessage("§4Vous ne possédez pas l'item demandé");
                        }
                        StaticMethodCommands.showSellAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§aVendre 1 Emeraude")) {
                        if (player.getInventory().contains(new ItemStack(Material.EMERALD))) {
                            player.getInventory().remove(new ItemStack(Material.EMERALD));
                            StaticMethodCommands.addMoneyToAPlayer(player, 35);
                        } else {
                            player.sendMessage("§4Vous ne possédez pas l'item demandé");
                        }
                        StaticMethodCommands.showSellAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§fVendre 1 Laine")) {
                        if (player.getInventory().contains(new ItemStack(Material.WHITE_WOOL))) {
                            player.getInventory().remove(new ItemStack(Material.WHITE_WOOL));
                            StaticMethodCommands.addMoneyToAPlayer(player, 2);
                        } else {
                            player.sendMessage("§4Vous ne possédez pas l'item demandé");
                        }
                        StaticMethodCommands.showSellAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getType() == Material.YELLOW_SHULKER_BOX) {
                        StaticMethodCommands.showSellAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                    if (itemStack.getType() == Material.ARROW) {
                        StaticMethodCommands.showMenuAdminShop(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }


                }

                if (name.get(player).equalsIgnoreCase("§bHôtel de vente")) {

                    if (itemStack != null && itemStack.getItemMeta().hasDisplayName()) {

                        char[] chars = itemStack.getItemMeta().getDisplayName().toCharArray();

                        String pri = "";

                        for (int i = 0; i < chars.length - 2; i++) {
                            pri += chars[i];
                        }

                        if (!pri.equals("")) {

                            int price = Integer.parseInt(pri);

                            if (StaticMethodCommands.getMoneyToAPlayer(player) >= price) {

                                StaticMethodCommands.removeMoneyFromAPlayer(player, price);

                                Player seller = Bukkit.getPlayer(StaticMethodCommands.getAnnoncementsWithNumber(event.getSlot()).getOwnerName());
                                seller.sendMessage("§b[VENTE] §2 Vous avez gagné " + price + " $");

                                StaticMethodCommands.addMoneyToAPlayer(seller, price);

                                player.getInventory().addItem(new ItemStack(itemStack.getType()));

                                if (StaticMethodCommands.getAnnoncementsWithNumber(event.getSlot()).getQuantity() > 1) {

                                    ArrayList<HDVAnnouncement> announcements = StaticMethodCommands.getAllAnnouncements();
                                    HDVAnnouncement announcement = announcements.get(event.getSlot());
                                    announcement.setQuantity(announcement.getQuantity() - 1);
                                    announcements.set(event.getSlot(), announcement);
                                    StaticMethodCommands.writeListOfAnnouncements(announcements);
                                    StaticMethodCommands.showHotelMenu(player);
                                    player.getInventory().remove(player.getItemOnCursor());

                                } else {
                                    StaticMethodCommands.removeHDVAnnoncementWithNumber(event.getSlot());
                                    StaticMethodCommands.showHotelMenu(player);
                                    player.getInventory().remove(player.getItemOnCursor());
                                }

                            } else {
                                player.sendMessage("§4Vous n'avez pas assez d'argent pour acheter cet item");
                                StaticMethodCommands.showHotelMenu(player);
                                player.getInventory().remove(player.getItemOnCursor());
                            }

                        }

                    }

                }

                if (name.get(player).equalsIgnoreCase("Commandes Achetables")) {
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§4Homes")) {

                        int money = StaticMethodCommands.getMoneyToAPlayer(player);

                        if (money >= 1500) {

                            ArrayList<String> commands = StaticMethodCommands.getAvailableCommands(player);
                            commands.add("homes");
                            StaticMethodCommands.writeListOfCommands(commands, player);
                            StaticMethodCommands.removeMoneyFromAPlayer(player, 1500);
                            player.sendMessage("§1Achat effectué ! Vous avez maintenant accès aux homes");
                            StaticMethodCommands.showBuyCommandMenu(player);
                            player.getInventory().remove(player.getItemOnCursor());

                        } else {
                            player.sendMessage("§4L'achat de cette commande côute 1500 $ ! Vous n'avez pas assez d'argent");
                            StaticMethodCommands.showBuyCommandMenu(player);
                            player.getInventory().remove(player.getItemOnCursor());
                        }

                    }
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§2Homes")) {
                        player.sendMessage("§4Vous avez déja acheté cette commande");
                        StaticMethodCommands.showBuyCommandMenu(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }

                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§4Sauvegarde du stuff")) {

                        int money = StaticMethodCommands.getMoneyToAPlayer(player);

                        if (money >= 500) {

                            ArrayList<String> commands = StaticMethodCommands.getAvailableCommands(player);
                            commands.add("chest");
                            StaticMethodCommands.writeListOfCommands(commands, player);
                            StaticMethodCommands.removeMoneyFromAPlayer(player, 500);
                            player.sendMessage("§1Achat effectué ! Votre stuff est maintenant sauvegardé quand vous mourez !");
                            StaticMethodCommands.showBuyCommandMenu(player);
                            player.getInventory().remove(player.getItemOnCursor());

                        } else {
                            player.sendMessage("§4L'achat de cette commande côute 500 $ ! Vous n'avez pas assez d'argent");
                            StaticMethodCommands.showBuyCommandMenu(player);
                            player.getInventory().remove(player.getItemOnCursor());
                        }

                    }
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§2Sauvegarde du stuff")) {
                        player.sendMessage("§4Vous avez déja acheté cette fonctionnalité");
                        StaticMethodCommands.showBuyCommandMenu(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }


                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§5Retour")) {
                        StaticMethodCommands.showHdvMenu(player);
                        player.getInventory().remove(player.getItemOnCursor());
                    }
                }

                //entreprises

                if (name.get(player).equalsIgnoreCase("Créer une entreprise")) {

                    String name = inventory.getItem(11).getItemMeta().getLore().get(0);
                    Entreprise entreprise = new Entreprise(name);

                    if (itemStack.getItemMeta().getLore().get(0).equalsIgnoreCase("FARM : cliquez pour changer")) {
                        type.replace(player, EntrepriseType.REDSTONE);
                        StaticMethodCommands.showEntrepriseCreateMenu(player, entreprise.getName());
                    }
                    if (itemStack.getItemMeta().getLore().get(0).equalsIgnoreCase("REDSTONE : cliquez pour changer")) {
                        type.replace(player, EntrepriseType.BUILD);
                        StaticMethodCommands.showEntrepriseCreateMenu(player, entreprise.getName());
                    }
                    if (itemStack.getItemMeta().getLore().get(0).equalsIgnoreCase("BUILD : cliquez pour changer")) {
                        type.replace(player, EntrepriseType.FARM);
                        StaticMethodCommands.showEntrepriseCreateMenu(player, entreprise.getName());
                    }

                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§2Créer")) {

                        int money = StaticMethodCommands.getMoneyToAPlayer(player);

                        if (money >= 1500) {
                            entreprise.create(type.get(player), player.getName());
                            StaticMethodCommands.removeMoneyFromAPlayer(player, 1500);
                            player.closeInventory();
                            player.sendMessage("§2Entreprise créée avec succès");
                        } else {
                            player.sendMessage("§4La création côute 1500$, vous n'avez pas assez d'argent !");
                        }

                    }

                    StaticMethodCommands.showEntrepriseCreateMenu(player, entreprise.getName());

                }

                if (name.get(player).equalsIgnoreCase("Info entreprises")) {

                    String name = inventory.getItem(11).getItemMeta().getLore().get(0);
                    Entreprise entreprise = new Entreprise(name);

                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§4Fermer")) {
                        player.closeInventory();
                    }
                    StaticMethodCommands.showEntrepriseInfoMenu(new EntrepriseProperties(entreprise), player);
                }

            }

        }catch (NullPointerException e){
            System.out.println("Une NullPointerException est survenue !!");
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        StaticMethodCommands.createAccountToAPlayer(event.getPlayer());
        StaticMethodCommands.createCommandAccount(event.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){

        Player player = event.getEntity();

        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();

        if(StaticMethodCommands.getAvailableCommands(player).contains("chest")){

            Inventory inventory = player.getInventory();

            Location deathLocation = new Location(player.getWorld(), x,y,z);

            deathLocation.getBlock().setType(Material.CHEST);

            Chest chest = (Chest) deathLocation.getBlock().getState();

            for(int i = 0; i < inventory.getSize(); i++){
                if(inventory.getItem(i) != null) {
                    chest.getInventory().addItem(inventory.getItem(i));
                }
            }

            try{
                Bukkit.getWorld("world").getEntities().stream().filter(Item.class::isInstance).forEach(Entity::remove);
            }catch (NullPointerException e){}



        }

        player.sendMessage("§2Dernière mort : \n§9X : "+x+"\nY : "+y+"\nZ : "+z);

    }
   
}
