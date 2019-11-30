package MODEL;

import java.time.LocalDate;

public class Offre {

    int id;
    Annonce annonce;
    Utilisateur acheteur;
    LocalDate dateCreation;

    public Offre(int id, Annonce annonce, Utilisateur acheteur, LocalDate dateCreation) {
        this.id = id;
        this.annonce = annonce;
        this.acheteur = acheteur;
        this.dateCreation = dateCreation;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public Annonce getAnnonce() {return annonce;}

    public void setAnnonce(Annonce annonce) {this.annonce = annonce;}

    public Utilisateur getAcheteur() {return acheteur;}

    public void setAcheteur(Utilisateur acheteur) {this.acheteur = acheteur;}

    public LocalDate getDateCreation() {return dateCreation;}

    public void setDateCreation(LocalDate dateCreation) {this.dateCreation = dateCreation;}

}
