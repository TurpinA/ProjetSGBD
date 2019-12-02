package CONTROLER;

import MODEL.Annonce;
import MODEL.Utilisateur;

import java.sql.SQLException;
import java.sql.Statement;

public class DelecteObject {
    public static Connexion connexion = null;

    public static void SupprimerAnnonceByID(int idAnnonce) throws SQLException {
        connexion.enableConnexion();
        Statement stmt = connexion.getConn().createStatement();
        String sql = "DELETE FROM annonce WHERE idAnnonce = " + idAnnonce;
        stmt.executeUpdate(sql);
        stmt.close();
        connexion.stopConnexion();
    }
}
