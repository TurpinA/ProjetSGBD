package MODEL;

public class Annonce {

    int id;
    String typeTransaction;
    Logement logement;
    Utilisateur utilisateur;
    int prix;

    public Annonce(int id, Logement logement, Utilisateur utilisateur, int prix,String typeTransaction) {
        this.id = id;
        this.logement = logement;
        this.utilisateur = utilisateur;
        this.prix = prix;
        this.typeTransaction = typeTransaction;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public Logement getLogement() {return logement;}

    public void setLogement(Logement logement) {this.logement = logement;}

    public Utilisateur getUtilisateur() {return utilisateur;}

    public void setUtilisateur(Utilisateur utilisateur) {this.utilisateur = utilisateur;}

    public int getPrix() {return prix;}

    public void setPrix(int prix) {this.prix = prix;}

    public String gettypeTransaction() {return typeTransaction;}

    public void settypeTransaction(String typeTransaction) {this.typeTransaction = typeTransaction;}
}
