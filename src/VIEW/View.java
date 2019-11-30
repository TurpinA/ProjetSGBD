package VIEW;

import CONTROLER.RechercheBD;
import MODEL.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class View {

    private boolean connected = false;

    private JFrame frame = new JFrame();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JButton buttonRechercheLogement = new JButton();
    private JButton buttonGestionLogement = new JButton();
    private JButton buttonGestionOffre = new JButton();
    private JLabel connexionLabel = new JLabel();
    private JLabel texteBienvenue = new JLabel();

    public View(){
        // Panel de gauche
        panel2.setBackground(Color.gray);
        panel2.setLayout(null);
        panel2.setPreferredSize(new Dimension(250, 700));
        panel2.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));

        JLabel image = new JLabel(new ImageIcon( "ressources/user2.png"));
        image.setBounds(84,5,82,82);
        panel2.add(image);

        connexionLabel.setText("Non connecté");
        connexionLabel.setForeground(Color.red.darker());
        connexionLabel.setBounds(83,90,200,20);
        panel2.add(connexionLabel);
        connexionLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        connexionLabel.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                mouseClickedActionConnexionLabel();
            }
        });

        buttonRechercheLogement.setText("Rechercher un logement");
        buttonRechercheLogement.setBounds(0,350,250,40);
        panel2.add(buttonRechercheLogement);

        buttonGestionLogement.setText("Gestion de mes logements");
        buttonGestionLogement.setBounds(0,390,250,40);
        panel2.add(buttonGestionLogement);
        buttonGestionLogement.setVisible(false);

        buttonGestionOffre.setText("Gestion de mes offres");
        buttonGestionOffre.setBounds(0,430,250,40);
        panel2.add(buttonGestionOffre);
        buttonGestionOffre.setVisible(false);

        // Panel de droite w:750 h:700
        panel1.setBackground(Color.white);
        panel1.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
        panel1.setLayout(null);

        texteBienvenue.setText("BIENVENUE DANS ANNONCE MANAGER !");
        texteBienvenue.setFont(new Font("Arial", Font.BOLD, 20));
        texteBienvenue.setBounds(180,300,texteBienvenue.getText().length()*20,20);
        panel1.add(texteBienvenue);

        // Fenetre
        frame.add(panel1,BorderLayout.CENTER);
        frame.add(panel2,BorderLayout.WEST);

        frame.setSize(1000,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Annonce Manager");
    }

    public void utilisateurConnecte(Utilisateur utilisateur){
        connexionLabel.setText(utilisateur.getPrenom() + " " + utilisateur.getNom());
        connexionLabel.setForeground(Color.green.darker());

        connected = true;

        buttonGestionLogement.setVisible(true);
        buttonGestionOffre.setVisible(true);
    }

    public void utilisateurDeconnecte(){
        connexionLabel.setText("Non connecté");
        connexionLabel.setForeground(Color.red.darker());
        connected = false;

        buttonGestionLogement.setVisible(false);
        buttonGestionOffre.setVisible(false);
    }

    public void mouseClickedActionConnexionLabel(){
        if(connected == false)
        {
            JTextField mail = new JTextField();
            JTextField motDePasse = new JPasswordField();
            Object[] message = {
                    "Mail:", mail,
                    "Mot de passe:", motDePasse
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Utilisateur utilisateur = null;
                try {
                    utilisateur = RechercheBD.ConnexionUtilisateur(mail.getText(),Integer.toString(motDePasse.getText().hashCode()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if(utilisateur != null) {
                    System.out.println("Login successful");
                    utilisateurConnecte(utilisateur);
                    new JOptionPane().showMessageDialog( null, "Connexion reussit !","Connexion", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    new JOptionPane().showMessageDialog( null, "mail ou mot de passe incorrect","Connexion", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else
        {
            JOptionPane d = new JOptionPane();
            int retour = d.showConfirmDialog(null, "Etes vous sûr de vouloir vous deconnecter ?","Deconnexion", JOptionPane.YES_NO_OPTION);
            if(retour == 0)
            {
                utilisateurDeconnecte();
                new JOptionPane().showMessageDialog( null, "Vous etes a present deconnecte","Deconnexion", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
