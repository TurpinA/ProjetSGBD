package MODEL;

import CONTROLER.Connexion;
import MODEL.Annonce;
import MODEL.Logement;
import MODEL.Utilisateur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class RechercheBD {

    public static Connexion connexion = null;

    public static Utilisateur ConnexionUtilisateur(String mail, String motdepasse) throws SQLException {

        Utilisateur utilisateur = null;
        connexion.enableConnexion();
        Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result = stmt.executeQuery("SELECT * FROM utilisateur WHERE mail = '" + mail + "' AND MotDePasse = '" + motdepasse + "'");

        if(result.next()){
            utilisateur = new Utilisateur(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),null,result.getString(6),result.getString(7));
        }

        stmt.close();
        connexion.stopConnexion();

        return utilisateur;
    }

    public static ArrayList<Annonce> ExtractionAllAnnonce() throws SQLException {

        ArrayList<Annonce> retour = new ArrayList<Annonce>();

        connexion.enableConnexion();
        Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet result = stmt.executeQuery("SELECT idLogement,logement.Ville,Taille,Type,AnneeDeCreation,Adresse," +
                "Photo,Description,idUtilisateur,Nom,Prénom,NumeroTelephone,utilisateur.ville,mail,idAnnonce,Prix,typeTransaction " +
                "FROM annonce INNER JOIN utilisateur ON annonce.Utilisateur = utilisateur.idUtilisateur " +
                "INNER JOIN logement ON annonce.Logement = logement.idLogement");

        while(result.next()){
            Logement logement = new Logement(result.getInt(1),result.getString(2),result.getInt(3),result.getString(4),result.getDate(5).toLocalDate(),result.getString(6),result.getBlob(7),result.getString(8));
            Utilisateur utilisateur = new Utilisateur(result.getInt(9),result.getString(10),result.getString(11),result.getString(12),null,result.getString(13),result.getString(14));
            Annonce annonce = new Annonce(result.getInt(15),logement,utilisateur,result.getInt(16),result.getString(17));

            retour.add(annonce);
        }

        stmt.close();
        connexion.stopConnexion();

        return retour;
    }

    public static ArrayList<Annonce> ExtractionAllAnnonceUtilisateur(Utilisateur utilisateur2) throws SQLException {

        int idUtilisateur;
        if(utilisateur2 != null){
            idUtilisateur = utilisateur2.getId();
        }else{
            idUtilisateur = -1;
        }
        ArrayList<Annonce> retour = new ArrayList<Annonce>();

        connexion.enableConnexion();
        Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet result = stmt.executeQuery("SELECT idLogement,logement.Ville,Taille,Type,AnneeDeCreation,Adresse," +
                "Photo,Description,idUtilisateur,Nom,Prénom,NumeroTelephone,utilisateur.ville,mail,idAnnonce,Prix,typeTransaction " +
                "FROM annonce INNER JOIN utilisateur ON annonce.Utilisateur = utilisateur.idUtilisateur " +
                "INNER JOIN logement ON annonce.Logement = logement.idLogement WHERE utilisateur.idUtilisateur = " + idUtilisateur);

        while(result.next()){
            Logement logement = new Logement(result.getInt(1),result.getString(2),result.getInt(3),result.getString(4),result.getDate(5).toLocalDate(),result.getString(6),result.getBlob(7),result.getString(8));
            Utilisateur utilisateur = new Utilisateur(result.getInt(9),result.getString(10),result.getString(11),result.getString(12),null,result.getString(13),result.getString(14));
            Annonce annonce = new Annonce(result.getInt(15),logement,utilisateur,result.getInt(16),result.getString(17));

            retour.add(annonce);
        }

        stmt.close();
        connexion.stopConnexion();

        return retour;
    }

    public static Annonce ExtractionAnnonceAvecID(int id) throws SQLException {
        Annonce annonce = null;
        connexion.enableConnexion();
        Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet result = stmt.executeQuery("SELECT idLogement,logement.Ville,Taille,Type,AnneeDeCreation,Adresse," +
                "Photo,Description,idUtilisateur,Nom,Prénom,NumeroTelephone,utilisateur.ville,mail,idAnnonce,Prix,typeTransaction " +
                "FROM annonce INNER JOIN utilisateur ON annonce.Utilisateur = utilisateur.idUtilisateur " +
                "INNER JOIN logement ON annonce.Logement = logement.idLogement WHERE idAnnonce = " + id);

        if(result.next()){
            Logement logement = new Logement(result.getInt(1),result.getString(2),result.getInt(3),result.getString(4),result.getDate(5).toLocalDate(),result.getString(6),result.getBlob(7),result.getString(8));
            Utilisateur utilisateur = new Utilisateur(result.getInt(9),result.getString(10),result.getString(11),result.getString(12),null,result.getString(13),result.getString(14));
            annonce = new Annonce(result.getInt(15),logement,utilisateur,result.getInt(16),result.getString(17));
        }

        stmt.close();
        connexion.stopConnexion();

        return annonce;
    }

    public static ArrayList<Offre> ExtractionOffreUtilisateur(Utilisateur utilisateur) throws SQLException {

        ArrayList<Offre> retour = new ArrayList<Offre>();

        connexion.enableConnexion();
        Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet result = stmt.executeQuery("SELECT idUtilisateur,Nom,Prénom,NumeroTelephone,utilisateur.ville," +
                "mail,Annonce,idOffre,DateCreation,prix,status FROM offre INNER JOIN utilisateur ON offre.Acheteur = " +
                "utilisateur.idUtilisateur");

        while(result.next()){
            Utilisateur acheteur = new Utilisateur(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),null,result.getString(5),result.getString(6));
            Annonce annonce = new Annonce(result.getInt(7),null,null,0,null);
            Offre offre = new Offre(result.getInt(8),annonce,acheteur,result.getDate(9).toLocalDate(),result.getInt(10),result.getString(11));
            retour.add(offre);
        }

        stmt.close();
        connexion.stopConnexion();

        return retour;
    }
}
