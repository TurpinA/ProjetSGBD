package VIEW;

import CONTROLER.CreateObject;
import CONTROLER.TableModelRechercheLogement;
import MODEL.Annonce;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;

import static MODEL.RechercheBD.ExtractionAnnonceAvecID;

public class pannelAffichageAnnonce extends JPanel implements ActionListener {

    private View frame = null;

    private Annonce annonce = new Annonce(-1,null,null,-1,"");

    private JButton buttonRetourAffichage = new JButton();
    private JButton buttonFaireOffre = new JButton();
    private JLabel AFALabelTypeTransaction = new JLabel();
    private JLabel AFALabelTypeLogement = new JLabel();
    private JLabel AFALabelVille = new JLabel();
    private JLabel AFALabelTaille = new JLabel();
    private JLabel AFALabelAnneCreation = new JLabel();
    private JLabel AFALabelAdresse = new JLabel();
    private JLabel AFALabelContact = new JLabel();
    private JLabel AFALabelNumero = new JLabel();
    private JLabel AFALabelMail = new JLabel();
    private JLabel AFAImageLogement = new JLabel();
    private JLabel AFAPrix = new JLabel();
    private JTextArea AFATextArea = new JTextArea();
    private InputStream AFAPhoto = null;

    public pannelAffichageAnnonce(View view){

        this.frame = view;
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
        this.setLayout(null);

        buttonRetourAffichage.setText("Retour");
        buttonRetourAffichage.setBounds(100,550,100,40);
        this.add(buttonRetourAffichage);
        buttonRetourAffichage.addActionListener(this);

        buttonFaireOffre.setText("Faire une offre");
        buttonFaireOffre.setBounds(500,550,150,40);
        this.add(buttonFaireOffre);
        buttonFaireOffre.addActionListener(this);
        buttonFaireOffre.setVisible(false);

        AFALabelTypeTransaction.setFont(new Font("Arial", Font.BOLD, 30));
        AFALabelTypeTransaction.setBounds(500,40,200,30);
        this.add(AFALabelTypeTransaction);

        AFALabelTypeLogement.setFont(new Font("Arial", Font.BOLD, 25));
        AFALabelTypeLogement.setBounds(500,80,200,30);
        this.add(AFALabelTypeLogement);

        AFALabelVille.setFont(new Font("Arial", Font.BOLD, 15));
        AFALabelVille.setBounds(450,130,200,30);
        this.add(AFALabelVille);

        AFALabelTaille.setFont(new Font("Arial", Font.BOLD, 15));
        AFALabelTaille.setBounds(450,150,200,30);
        this.add(AFALabelTaille);

        AFALabelAnneCreation.setFont(new Font("Arial", Font.BOLD, 15));
        AFALabelAnneCreation.setBounds(450,170,400,30);
        this.add(AFALabelAnneCreation);

        AFALabelAdresse.setFont(new Font("Arial", Font.BOLD, 15));
        AFALabelAdresse.setBounds(450,190,400,30);
        this.add(AFALabelAdresse);

        AFAPrix.setFont(new Font("Arial", Font.BOLD, 15));
        AFAPrix.setBounds(450,210,400,30);
        this.add(AFAPrix);

        AFATextArea.setFont(new Font("Arial", Font.BOLD, 12));
        AFATextArea.setBounds(420,250,300,260);
        AFATextArea.setLineWrap(true);
        AFATextArea.setEditable(false);
        this.add(AFATextArea);

        AFALabelContact.setFont(new Font("Arial", Font.BOLD, 15));
        AFALabelContact.setBounds(50,440,400,30);
        this.add(AFALabelContact);

        AFALabelNumero.setFont(new Font("Arial", Font.BOLD, 15));
        AFALabelNumero.setBounds(50,460,400,30);
        this.add(AFALabelNumero);

        AFALabelMail.setFont(new Font("Arial", Font.BOLD, 15));
        AFALabelMail.setBounds(50,480,400,30);
        this.add(AFALabelMail);


    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void changerAnnonce(Annonce annonce) throws IOException {
        this.annonce = annonce;

        AFALabelTypeTransaction.setText(annonce.gettypeTransaction());
        AFALabelTypeLogement.setText(annonce.getLogement().getType());
        AFALabelVille.setText("Localisation : " + annonce.getLogement().getVille());
        AFALabelTaille.setText("Taille : " + annonce.getLogement().getTaille() + " m²");
        AFALabelAnneCreation.setText("Annee de creation : " + annonce.getLogement().getAnneeDeCreation());
        AFALabelAdresse.setText("Adresse : " + annonce.getLogement().getAdresse());
        AFATextArea.setText(" Description : " + annonce.getLogement().getDescription());
        AFALabelContact.setText("Contact : " + annonce.getUtilisateur().getNom() + " " + annonce.getUtilisateur().getPrenom());
        AFALabelNumero.setText("Numero de Telephone : " + annonce.getUtilisateur().getNumeroTelephone());
        AFALabelMail.setText("Mail : " + annonce.getUtilisateur().getMail());
        AFAPrix.setText("Prix : " + annonce.getPrix() + " €");
        AFAPhoto = annonce.getLogement().getPhoto();

        if(AFAPhoto != null)
        {
                AFAImageLogement.setIcon(new ImageIcon(ImageIO.read(AFAPhoto)));
                AFAImageLogement.setBounds(50,50,300,300);
                this.add(AFAImageLogement);
        }
    }

    public JButton getButtonFaireOffre() {
        return buttonFaireOffre;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == buttonRetourAffichage){
            Component[] allPanel = frame.getContentPane().getComponents();
            frame.remove(allPanel[1]);
            frame.add(frame.getPanelRechercheLogement(),BorderLayout.CENTER);
            frame.repaint();
            frame.revalidate();
        }

        if(e.getSource() == buttonFaireOffre)
        {
            JTextField prix = new JTextField(Integer.toString(annonce.getPrix()));
            Object[] message = {
                    "Offre : (en €)", prix,
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Offre", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION)
            {
                try {
                    CreateObject.CreateOffre(annonce,View.utilisateurConnecte, LocalDate.now(),Integer.parseInt(prix.getText()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                new JOptionPane().showMessageDialog( null, "Offre envoyée au Vendeur","Offre envoyée", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
