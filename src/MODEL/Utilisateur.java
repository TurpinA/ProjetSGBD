package MODEL;

public class Utilisateur {

    int id;
    String nom;
    String prenom;
    String numeroTelephone;
    String motDePasse;
    String ville;
    String mail;

    public Utilisateur(int id,String nom,String prenom,String numeroTelephone,String motDePasse,String ville,String mail){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTelephone = numeroTelephone;
        this.motDePasse = motDePasse;
        this.ville = ville;
        this.mail = mail;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getNom() {return nom;}

    public void setNom(String nom) {this.nom = nom;}

    public String getPrenom() {return prenom;}

    public void setPrenom(String prenom) {this.prenom = prenom;}

    public String getNumeroTelephone() {return numeroTelephone;}

    public void setNumeroTelephone(String numeroTelephone) {this.numeroTelephone = numeroTelephone;}

    public String getMotDePasse() {return motDePasse;}

    public void setMotDePasse(String motDePasse) {this.motDePasse = motDePasse;}

    public String getVille() {return ville;}

    public void setVille(String ville) {this.ville = ville;}

    public String getMail() {return mail;}

    public void setMail(String mail) {this.mail = mail;}
}
