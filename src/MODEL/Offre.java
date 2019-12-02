package MODEL;

import java.time.LocalDate;

public class Offre {

    int id;
    Annonce annonce;
    Utilisateur acheteur;
    LocalDate dateCreation;
    int prix;

    public Offre(int id, Annonce annonce, Utilisateur acheteur, LocalDate dateCreation,int prix) {
        this.id = id;
        this.annonce = annonce;
        this.acheteur = acheteur;
        this.dateCreation = dateCreation;
        this.prix = prix;
    }

    public int getId() {return id;}

    public int getPrix() {return prix;}

    public void setPrix(int prix) {this.prix = prix;}

    public void setId(int id) {this.id = id;}

    public Annonce getAnnonce() {return annonce;}

    public void setAnnonce(Annonce annonce) {this.annonce = annonce;}

    public Utilisateur getAcheteur() {return acheteur;}

    public void setAcheteur(Utilisateur acheteur) {this.acheteur = acheteur;}

    public LocalDate getDateCreation() {return dateCreation;}

    public void setDateCreation(LocalDate dateCreation) {this.dateCreation = dateCreation;}

}
