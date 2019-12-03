package VIEW;

import CONTROLER.CreateObject;
import MODEL.RechercheBD;
import MODEL.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class pannelBandeau extends JPanel implements ActionListener{

    private View frame = null;

    private JLabel connexionLabel = new JLabel();
    private JButton buttonRechercheLogement = new JButton();
    private JButton buttonGestionLogement = new JButton();
    private JButton buttonGestionOffre = new JButton();
    private JButton ConnexionButton = new JButton();
    private JButton DeconnexionButton = new JButton();
    private JButton EnregistrementButton = new JButton();
    private JLabel iconUtilisateur = new JLabel();

    public pannelBandeau(View view){
        this.frame = view;
        this.setBackground(Color.gray);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(250, 700));
        this.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));

        iconUtilisateur.setIcon(new ImageIcon( "ressources/user2.png"));
        iconUtilisateur.setBounds(84,5,82,82);
        this.add(iconUtilisateur);

        connexionLabel.setText("Non connecté");
        connexionLabel.setForeground(Color.red.darker());
        connexionLabel.setBounds(83,90,200,20);
        this.add(connexionLabel);

        buttonRechercheLogement.setText("Rechercher un logement");
        buttonRechercheLogement.setBounds(0,350,250,40);
        this.add(buttonRechercheLogement);
        buttonRechercheLogement.addActionListener(this);

        buttonGestionLogement.setText("Gestion de mes annonces");
        buttonGestionLogement.setBounds(0,390,250,40);
        this.add(buttonGestionLogement);
        buttonGestionLogement.setVisible(false);
        buttonGestionLogement.addActionListener(this);

        buttonGestionOffre.setText("Gestion de mes offres");
        buttonGestionOffre.setBounds(0,430,250,40);
        this.add(buttonGestionOffre);
        buttonGestionOffre.setVisible(false);
        buttonGestionOffre.addActionListener(this);

        ConnexionButton.setBounds(0,125,125,40);
        ConnexionButton.setText("Connexion");
        this.add(ConnexionButton);
        ConnexionButton.addActionListener(this);

        DeconnexionButton.setBounds(0,125,250,40);
        DeconnexionButton.setText("Deconnexion");
        this.add(DeconnexionButton);
        DeconnexionButton.addActionListener(this);
        DeconnexionButton.setVisible(false);

        EnregistrementButton.setBounds(125,125, 125,40);
        EnregistrementButton.setText("Enregistrement");
        this.add(EnregistrementButton);
        EnregistrementButton.addActionListener(this);

    }

    public void utilisateurConnecte(Utilisateur utilisateur) throws SQLException {
        connexionLabel.setText(utilisateur.getPrenom() + " " + utilisateur.getNom());
        connexionLabel.setForeground(Color.green.darker());

        View.utilisateurConnecte = utilisateur;

        buttonGestionLogement.setVisible(true);
        buttonGestionOffre.setVisible(true);
        frame.getPanelAnnonce().getButtonFaireOffre().setVisible(true);

        frame.getPanelGestionAnnonce().setDataGestionAnnonce(RechercheBD.ExtractionAllAnnonceUtilisateur(View.utilisateurConnecte));
        frame.getPanelGestionAnnonce().getModel2().setDonne(frame.getPanelGestionAnnonce().getDataGestionAnnonce());
    }

    public void utilisateurDeconnecte(){
        connexionLabel.setText("Non connecté");
        connexionLabel.setForeground(Color.red.darker());
        View.utilisateurConnecte = null;

        buttonGestionLogement.setVisible(false);
        buttonGestionOffre.setVisible(false);
        frame.getPanelAnnonce().getButtonFaireOffre().setVisible(false);

        Component[] allPanel = frame.getContentPane().getComponents();
        frame.remove(allPanel[1]);
        frame.add(frame.getPanelAccueil(),BorderLayout.CENTER);
        frame.repaint();
        frame.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonRechercheLogement) {
            Component[] allPanel = frame.getContentPane().getComponents();
            frame.remove(allPanel[1]);
            frame.add(frame.getPanelRechercheLogement(),BorderLayout.CENTER);
            frame.repaint();
            frame.revalidate();
        }

        if (e.getSource() == buttonGestionLogement) {
            Component[] allPanel = frame.getContentPane().getComponents();
            frame.remove(allPanel[1]);
            frame.add(frame.getPanelGestionAnnonce(),BorderLayout.CENTER);
            frame.repaint();
            frame.revalidate();
        }

        if (e.getSource() == buttonGestionOffre) {
            try {
                frame.setPanelGestionOffre(new pannelGestionOffre(frame));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            Component[] allPanel = frame.getContentPane().getComponents();
            frame.remove(allPanel[1]);
            frame.add(frame.getPanelGestionOffre(),BorderLayout.CENTER);
            frame.repaint();
            frame.revalidate();
        }

        if(e.getSource() == ConnexionButton){
            if(View.utilisateurConnecte == null)
            {
                JTextField mail = new JTextField();
                JTextField motDePasse = new JPasswordField();
                Object[] message = {
                        "Mail:", mail,
                        "Mot de passe:", motDePasse
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        View.utilisateurConnecte = RechercheBD.ConnexionUtilisateur(mail.getText(),Integer.toString(motDePasse.getText().hashCode()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    if(View.utilisateurConnecte != null) {
                        try {
                            utilisateurConnecte(View.utilisateurConnecte);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        new JOptionPane().showMessageDialog( null, "Connexion reussit !","Connexion", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        new JOptionPane().showMessageDialog( null, "mail ou mot de passe incorrect","Connexion", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if(View.utilisateurConnecte != null){
                    ConnexionButton.setVisible(false);
                    EnregistrementButton.setVisible(false);
                    DeconnexionButton.setVisible(true);
                }
            }
        }

        if(e.getSource() == DeconnexionButton){
            JOptionPane d = new JOptionPane();
            int retour = d.showConfirmDialog(null, "Etes vous sûr de vouloir vous deconnecter ?","Deconnexion", JOptionPane.YES_NO_OPTION);
            if(retour == 0)
            {
                utilisateurDeconnecte();
                new JOptionPane().showMessageDialog( null, "Vous etes a present deconnecte","Deconnexion", JOptionPane.INFORMATION_MESSAGE);
            }
            if(View.utilisateurConnecte == null){
                DeconnexionButton.setVisible(false);
                ConnexionButton.setVisible(true);
                EnregistrementButton.setVisible(true);
            }
        }

        if(e.getSource() == EnregistrementButton){
            JTextField nom = new JTextField();
            JTextField prenom = new JTextField();
            JTextField numeroTelephone = new JTextField();
            JTextField motDePasse = new JPasswordField();
            JTextField ville = new JTextField();
            JTextField mail = new JTextField();
            Object[] message = {
                    "Nom", nom,
                    "Prenom", prenom,
                    "Numero de telephone", numeroTelephone,
                    "Mot de passe " ,  motDePasse,
                    "Ville", ville,
                    "mail" , mail
            };

            boolean valid = false;
            boolean readytosign = false;
            while(!valid){
                valid = true;
                int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
                if(option == JOptionPane.OK_OPTION) {
                    if (nom.getText().isEmpty()
                            || prenom.getText().equals("")
                            || numeroTelephone.getText().equals("")
                            || motDePasse.getText().equals("")
                            || ville.getText().equals("")
                            || mail.getText().equals("")) {
                        valid = false;
                    }
                    else{
                        readytosign = true;
                    }
                }
            }


            if(readytosign){
                try {
                    CreateObject.CreateUtilisateur(nom.getText(),
                            prenom.getText(),
                            numeroTelephone.getText(),
                            motDePasse.getText(),
                            ville.getText(),
                            mail.getText());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }



        }
    }
}
