package MODEL;

import java.sql.Blob;
import java.time.LocalDate;

public class Logement {

    int id;
    String ville;
    int taille;
    String type;
    LocalDate anneeDeCreation;
    String adresse;
    Blob photo;
    String description;

    public Logement(int id, String ville, int taille, String type, LocalDate anneeDeCreation, String adresse, Blob photo, String description) {
        this.id = id;
        this.ville = ville;
        this.taille = taille;
        this.type = type;
        this.anneeDeCreation = anneeDeCreation;
        this.adresse = adresse;
        this.photo = photo;
        this.description = description;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getVille() {return ville;}

    public void setVille(String ville) {this.ville = ville;}

    public int getTaille() {return taille;}

    public void setTaille(int taille) {this.taille = taille;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public LocalDate getAnneeDeCreation() {return anneeDeCreation;}

    public void setAnneeDeCreation(LocalDate anneeDeCreation) {this.anneeDeCreation = anneeDeCreation;}

    public String getAdresse() {return adresse;}

    public void setAdresse(String adresse) {this.adresse = adresse;}

    public Blob getPhoto() {return photo;}

    public void setPhoto(Blob photo) {this.photo = photo;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}
