package CONTROLER;

import MODEL.Annonce;
import MODEL.Logement;
import MODEL.Utilisateur;
import com.mysql.cj.log.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class CreateObject {

    public static Connexion connexion = null;

    public static Utilisateur CreateUtilisateur(String nom,String prenom,int age,String numeroTelephone,String motDePasse,String ville,String mail) throws SQLException {
        connexion.enableConnexion();

        Statement stmt = connexion.getConn().createStatement();

        String sql = "INSERT INTO utilisateur VALUES (NULL, '"+ nom +"', '"+ prenom +
                "', '"+ numeroTelephone +"', '"+ motDePasse.hashCode() +
                "', '"+ ville +"', '"+ mail +"');";
        stmt.executeUpdate(sql);
        ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID();");
        result.next();
        Utilisateur user = new Utilisateur(result.getInt(1),nom,prenom,numeroTelephone,motDePasse,ville,mail);

        stmt.close();

        connexion.stopConnexion();

        return user;
    }

    public Logement CreateLogement(String ville, int taille, String type, LocalDate anneeDeCreation, String adresse, String photo, String description) throws SQLException {
        connexion.enableConnexion();

        Statement stmt = connexion.getConn().createStatement();

        String sql = "INSERT INTO logement VALUES (NULL, '"+ ville +"', "+ taille +", '"+ type +"', '" +
                anneeDeCreation.getYear() +"-" + anneeDeCreation.getMonth().getValue() + "-"+
                anneeDeCreation.getDayOfMonth() +"', '"+ adresse +"', '"+ photo +"', '"+ description +"');";
        stmt.executeUpdate(sql);
        ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID();");
        result.next();
        Logement logement = new Logement(result.getInt(1),ville,taille,type,anneeDeCreation,adresse,photo,description);

        stmt.close();

        connexion.stopConnexion();

        return logement;
    }

    public Annonce CreateAnnonce(Logement logement, Utilisateur utilisateur, int prix) throws SQLException {
        connexion.enableConnexion();

        Statement stmt = connexion.getConn().createStatement();

        String sql = "INSERT INTO annonce VALUES (NULL, "+ logement.getId() +", "+ utilisateur.getId() +", "+ prix +");";
        stmt.executeUpdate(sql);
        ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID();");
        result.next();
        Annonce annonce = new Annonce(result.getInt(1),logement,utilisateur,prix);

        stmt.close();

        connexion.stopConnexion();

        return annonce;
    }
}
