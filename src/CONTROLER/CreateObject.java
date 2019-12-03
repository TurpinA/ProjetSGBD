package CONTROLER;

import MODEL.Annonce;
import MODEL.Logement;
import MODEL.Offre;
import MODEL.Utilisateur;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;

public class CreateObject {

    public static Connexion connexion = null;

    public static Utilisateur CreateUtilisateur(String nom,String prenom,String numeroTelephone,String motDePasse,String ville,String mail) throws SQLException {
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

    public static Logement CreateLogement(String ville, int taille, String type, LocalDate anneeDeCreation, String adresse, InputStream photo, String description) throws SQLException {
        connexion.enableConnexion();

        String sql = "INSERT INTO logement VALUES (NULL, '"+ ville +"', "+ taille +", '"+ type +"', '" +
                anneeDeCreation.getYear() +"-" + anneeDeCreation.getMonth().getValue() + "-"+
                anneeDeCreation.getDayOfMonth() +"', '"+ adresse +"',?, '"+ description +"');";

        PreparedStatement pstmt = connexion.getConn().prepareStatement(sql);

        pstmt .setBinaryStream(1,photo);

        pstmt .execute();

        Statement stmt = connexion.getConn().createStatement();
        ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID();");
        result.next();
        Logement logement = new Logement(result.getInt(1),ville,taille,type,anneeDeCreation,adresse,photo,description);

        pstmt.close();
        stmt.close();

        connexion.stopConnexion();

        return logement;
    }

    public static Annonce CreateAnnonce(Logement logement, Utilisateur utilisateur, int prix, String typeTransaction) throws SQLException {
        connexion.enableConnexion();

        Statement stmt = connexion.getConn().createStatement();

        String sql = "INSERT INTO annonce VALUES (NULL, "+ logement.getId() +", "+ utilisateur.getId() +", "+ prix +" , '" + typeTransaction +"');";
        stmt.executeUpdate(sql);
        ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID();");
        result.next();
        Annonce annonce = new Annonce(result.getInt(1),logement,utilisateur,prix,typeTransaction);

        stmt.close();

        connexion.stopConnexion();

        return annonce;
    }

    public static Offre CreateOffre(Annonce annonce, Utilisateur acheteur, LocalDate dateCreation,int prix) throws SQLException {

        connexion.enableConnexion();
        Statement stmt = connexion.getConn().createStatement();

        String sql = "INSERT INTO offre VALUES (NULL, "+ annonce.getId() +", "+ acheteur.getId() +",'" + dateCreation.getYear()
                +"-" + dateCreation.getMonth().getValue() + "-"+ dateCreation.getDayOfMonth()
                +"', " + prix +";" + "en attente" + ");";
        stmt.executeUpdate(sql);
        ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID();");
        result.next();
        Offre offre = new Offre(result.getInt(1),annonce,acheteur,dateCreation,prix,"en attente");

        stmt.close();

        connexion.stopConnexion();

        return offre;
    }
}
