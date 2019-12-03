package VIEW;

import CONTROLER.CreateObject;
import CONTROLER.TableModelGererAnnonce;
import CONTROLER.TableModelRechercheLogement;
import MODEL.Annonce;
import MODEL.RechercheBD;
import MODEL.Utilisateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static MODEL.RechercheBD.ExtractionAnnonceAvecID;

public class View extends JFrame {

    public static Utilisateur utilisateurConnecte = null;

    private pannelAccueil panelAccueil = new pannelAccueil();
    private pannelBandeau panelBandeau = new pannelBandeau(this);
    private pannelAffichageAnnonce panelAnnonce = new pannelAffichageAnnonce(this);
    private pannelRechercheLogement panelRechercheLogement = new pannelRechercheLogement(this);
    private pannelGestionAnnonce panelGestionAnnonce = new pannelGestionAnnonce(this);
    private pannelGestionOffre panelGestionOffre;

    public View() throws SQLException {
        this.add(panelBandeau,BorderLayout.WEST);
        this.add(panelAccueil,BorderLayout.CENTER);

        this.setSize(1000,700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("Annonce Manager");

        panelBandeau.utilisateurConnecte(new Utilisateur(13,"ADMIN","","","","",""));
    }

    public pannelAccueil getPanelAccueil() {
        return panelAccueil;
    }

    public void setPanelAccueil(pannelAccueil panelAccueil) {
        this.panelAccueil = panelAccueil;
    }

    public pannelBandeau getPanelBandeau() {
        return panelBandeau;
    }

    public void setPanelBandeau(pannelBandeau panelBandeau) {
        this.panelBandeau = panelBandeau;
    }

    public pannelAffichageAnnonce getPanelAnnonce() {
        return panelAnnonce;
    }

    public void setPanelAnnonce(pannelAffichageAnnonce panelAnnonce) {
        this.panelAnnonce = panelAnnonce;
    }

    public pannelRechercheLogement getPanelRechercheLogement() {
        return panelRechercheLogement;
    }

    public void setPanelRechercheLogement(pannelRechercheLogement panelRechercheLogement) {
        this.panelRechercheLogement = panelRechercheLogement;
    }

    public pannelGestionAnnonce getPanelGestionAnnonce() {
        return panelGestionAnnonce;
    }

    public void setPanelGestionAnnonce(pannelGestionAnnonce panelGestionAnnonce) {
        this.panelGestionAnnonce = panelGestionAnnonce;
    }

    public pannelGestionOffre getPanelGestionOffre() {
        return panelGestionOffre;
    }

    public void setPanelGestionOffre(pannelGestionOffre panelGestionOffre) {
        this.panelGestionOffre = panelGestionOffre;
    }

    public void actionPerformed(ActionEvent e) {

    }
}
