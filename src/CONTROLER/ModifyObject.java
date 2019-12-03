package CONTROLER;

import MODEL.Annonce;
import MODEL.Logement;

import java.sql.*;
public class ModifyObject {

    public static Connexion connexion = null;

    public static void modifierLogement(Logement logement) throws SQLException {

        connexion.enableConnexion();

        Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet rs = stmt.executeQuery("SELECT * FROM logement  WHERE idLogement = " + logement.getId());

        while (rs.next()) {
            rs.updateString("Ville",logement.getVille());
            rs.updateInt("Taille",logement.getTaille());
            rs.updateString("Type",logement.getType());
            rs.updateDate("AnneeDeCreation",Date.valueOf(logement.getAnneeDeCreation()));
            rs.updateString("Adresse",logement.getAdresse());
            rs.updateBlob("Photo",logement.getPhoto());
            rs.updateString("Description",logement.getDescription());
            rs.updateRow();
        }

        stmt.close();

        connexion.stopConnexion();
    }

    public static void modifierAnnonce(Annonce annonce) throws SQLException {

        connexion.enableConnexion();

        Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet rs = stmt.executeQuery("SELECT * FROM annonce  WHERE idAnnonce = " + annonce.getId());

        while (rs.next()) {
            rs.updateInt("Prix",annonce.getPrix());
            rs.updateString("typeTransaction",annonce.gettypeTransaction());
            rs.updateRow();
        }

        stmt.close();

        connexion.stopConnexion();

        modifierLogement(annonce.getLogement());
    }
}
