package CONTROLER;

import MODEL.Utilisateur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
