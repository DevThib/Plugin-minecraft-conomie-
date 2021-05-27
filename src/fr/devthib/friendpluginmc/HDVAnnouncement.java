package fr.devthib.friendpluginmc;

import org.bukkit.Material;

public class HDVAnnouncement {

    String ownerName;
    Material material;
    int price;
    int quantity;

    public HDVAnnouncement(Material material, int price,String ownerName,int quantity){
        this.price = price;
        this.material = material;
        this.ownerName = ownerName;
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public Material getMaterial() {
        return material;
    }

    public String getOwnerName(){
        return ownerName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
