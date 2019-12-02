package CONTROLER;

import MODEL.Annonce;
import MODEL.Logement;
import MODEL.Utilisateur;
import VIEW.View;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws SQLException, IOException {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connexion connexion = new Connexion();
		connexion.setConnexion("127.0.0.1","3306","mydb", "root", "");

		View test  = new View();
		//test.utilisateurConnecte(new Utilisateur(13,"ADMIN","","","","",""));
	}

}
