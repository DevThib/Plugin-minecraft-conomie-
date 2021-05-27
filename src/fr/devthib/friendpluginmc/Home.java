package fr.devthib.friendpluginmc;

public class Home {

    String ownerName;
    String name;
    double X;
    double Y;
    double Z;

    public Home(String ownerName,String name,double X,double Y,double Z){
        this.name = name;
        this.ownerName = ownerName;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getZ() {
        return Z;
    }
}
