package fr.devthib.friendpluginmc.entreprises;

import org.bukkit.entity.Player;

public class Contrat {

    Entreprise entreprise;
    Player whoAsk;
    int toPaid;
    String clause;

    public Contrat(Entreprise entreprise,Player whoAsk,int toPaid,String clause){
        this.entreprise = entreprise;
        this.whoAsk = whoAsk;
        this.toPaid = toPaid;
        this.clause = clause;
    }

    public int getToPaid() {
        return toPaid;
    }

    public Player getWhoAsk() {
        return whoAsk;
    }

    public String getClause() {
        return clause;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }
}
