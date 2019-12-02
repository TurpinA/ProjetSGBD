package VIEW;

import CONTROLER.CreateObject;
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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static MODEL.RechercheBD.ExtractionAnnonceAvecID;

public class View implements ActionListener {

    private Utilisateur utilisateurConnecte = null;

    // Fenetre
    private JFrame frame = new JFrame();

    // Panel Acceuil + bandeau
    private JPanel panelAccueil = new JPanel();
    private JPanel panelBandeau = new JPanel();
    private JLabel connexionLabel = new JLabel();
    private JLabel texteBienvenue = new JLabel();

    // Panel affichage annonce
    private Annonce annonce = new Annonce(-1,null,null,-1,"");
    private JPanel panelAffichageAnnonce = new JPanel();
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
    private JTextArea AFATextArea = new JTextArea();
    private Blob AFAPhoto = null;


    // Panel Recherche de logement
    private JPanel panelRechercheLogement = new JPanel();
    private JButton buttonRechercheLogement = new JButton();
    private JButton buttonRechercher1 = new JButton();
    private JTable tableRechercheLogement = new JTable();
    private JLabel labelTailleMin = new JLabel();
    private JLabel labelTailleMax = new JLabel();
    private JLabel labelPrixMin = new JLabel();
    private JLabel labelPrixMax = new JLabel();
    private JLabel labelVille = new JLabel();
    private JLabel labelTypeLogement = new JLabel();
    private JLabel labelTypeTransaction = new JLabel();
    private JLabel rechercheLogementTitre = new JLabel();
    private JTextField textFieldTailleMin = new JTextField();
    private JTextField textFieldTailleMax = new JTextField();
    private JTextField textFieldPrixMin = new JTextField();
    private JTextField textFieldPrixMax = new JTextField();
    private JTextField textFieldVille = new JTextField();
    private JComboBox<String> listypeLogement = new JComboBox<String>();
    private JComboBox<String> listTransaction = new JComboBox<String>();

    private ArrayList<Annonce> dataRechercheLogement = new ArrayList<Annonce>();
    private TableRowSorter<TableModelRechercheLogement> sorter;

    // Panel Gestion des ses annonces
    private JPanel panelGestionLogement = new JPanel();
    private JButton buttonGestionLogement = new JButton();

    // Panel Gestion des offres
    private JPanel panelGestionOffre = new JPanel();
    private JButton buttonGestionOffre = new JButton();

    public View() throws SQLException, IOException {
        // Panel de gauche
        panelBandeau.setBackground(Color.gray);
        panelBandeau.setLayout(null);
        panelBandeau.setPreferredSize(new Dimension(250, 700));
        panelBandeau.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));

        JLabel image = new JLabel(new ImageIcon( "ressources/user2.png"));
        image.setBounds(84,5,82,82);
        panelBandeau.add(image);

        connexionLabel.setText("Non connecté");
        connexionLabel.setForeground(Color.red.darker());
        connexionLabel.setBounds(83,90,200,20);
        panelBandeau.add(connexionLabel);
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
        panelBandeau.add(buttonRechercheLogement);
        buttonRechercheLogement.addActionListener(this);

        buttonGestionLogement.setText("Gestion de mes logements");
        buttonGestionLogement.setBounds(0,390,250,40);
        panelBandeau.add(buttonGestionLogement);
        buttonGestionLogement.setVisible(false);
        buttonGestionLogement.addActionListener(this);

        buttonGestionOffre.setText("Gestion de mes offres");
        buttonGestionOffre.setBounds(0,430,250,40);
        panelBandeau.add(buttonGestionOffre);
        buttonGestionOffre.setVisible(false);
        buttonGestionOffre.addActionListener(this);

        // Panel de droite - Accueil w:750 h:700
        panelAccueil.setBackground(Color.white);
        panelAccueil.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
        panelAccueil.setLayout(null);

        texteBienvenue.setText("BIENVENUE DANS ANNONCE MANAGER !");
        texteBienvenue.setFont(new Font("Arial", Font.BOLD, 20));
        texteBienvenue.setBounds(180,300,texteBienvenue.getText().length()*20,20);
        panelAccueil.add(texteBienvenue);

        // Panel de recherche de logements
        panelRechercheLogement.setBackground(Color.white);
        panelRechercheLogement.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
        panelRechercheLogement.setLayout(null);

        rechercheLogementTitre.setText("Rechercher un logement");
        rechercheLogementTitre.setFont(new Font("Arial", Font.BOLD, 30));
        rechercheLogementTitre.setBounds(200,20,600,30);
        panelRechercheLogement.add(rechercheLogementTitre);

        int [] positionDepart = {130,80};
        int gapX = 275;
        int gapY = 30;

        labelTailleMin.setText("Taille min:");
        labelTailleMin.setBounds(positionDepart[0],positionDepart[1],labelTailleMin.getText().length()*6,20);
        panelRechercheLogement.add(labelTailleMin);
        textFieldTailleMin.setBounds(positionDepart[0] + labelTailleMin.getText().length()*6,positionDepart[1],150,20);
        panelRechercheLogement.add(textFieldTailleMin);

        labelTailleMax.setText("Taille max:");
        labelTailleMax.setBounds(positionDepart[0] + gapX,positionDepart[1],labelTailleMax.getText().length()*6,20);
        panelRechercheLogement.add(labelTailleMax);
        textFieldTailleMax.setBounds(positionDepart[0] + gapX + labelTailleMax.getText().length()*6,positionDepart[1],150,20);
        panelRechercheLogement.add(textFieldTailleMax);

        labelPrixMin.setText("Prix min :");
        labelPrixMin.setBounds(positionDepart[0],positionDepart[1] + gapY*1,labelPrixMin.getText().length()*6,20);
        panelRechercheLogement.add(labelPrixMin);
        textFieldPrixMin.setBounds(positionDepart[0] + labelPrixMin.getText().length()*6,positionDepart[1] + gapY*1,150,20);
        panelRechercheLogement.add(textFieldPrixMin);

        labelPrixMax.setText("Prix max :");
        labelPrixMax.setBounds(positionDepart[0]+gapX,positionDepart[1] + gapY*1,labelPrixMax.getText().length()*6,20);
        panelRechercheLogement.add(labelPrixMax);
        textFieldPrixMax.setBounds(positionDepart[0]+gapX + labelPrixMin.getText().length()*6,positionDepart[1] + gapY*1,150,20);
        panelRechercheLogement.add(textFieldPrixMax);


        labelTypeLogement.setText("Type de Logement :");
        labelTypeLogement.setBounds(positionDepart[0]+gapX,positionDepart[1] + gapY*2,labelTypeLogement.getText().length()*6+10,20);
        panelRechercheLogement.add(labelTypeLogement);
        listypeLogement.addItem(null);
        listypeLogement.addItem("Maison");
        listypeLogement.addItem("Appartement");
        listypeLogement.setBounds(positionDepart[0]+gapX + labelTypeLogement.getText().length()*6 + 7,positionDepart[1] + gapY*2,100,20);
        panelRechercheLogement.add(listypeLogement);

        labelTypeTransaction.setText("Type de Transaction : ");
        labelTypeTransaction.setBounds(positionDepart[0],positionDepart[1] + gapY*3,labelTypeTransaction.getText().length()*6,20);
        panelRechercheLogement.add(labelTypeTransaction);
        listTransaction.addItem(null);
        listTransaction.addItem("Location");
        listTransaction.addItem("Vente");
        listTransaction.setBounds(positionDepart[0]+ labelTypeTransaction.getText().length()*6,positionDepart[1] + gapY*3,80,20);
        panelRechercheLogement.add(listTransaction);

        labelVille.setText("Ville :");
        labelVille.setBounds(positionDepart[0],positionDepart[1] + gapY*2,labelVille.getText().length()*6,20);
        panelRechercheLogement.add(labelVille);
        textFieldVille.setBounds(positionDepart[0] + labelPrixMin.getText().length()*6,positionDepart[1] + gapY*2,150,20);
        panelRechercheLogement.add(textFieldVille);

        buttonRechercher1.setText("Rechercher");
        buttonRechercher1.setBounds(positionDepart[0]+gapX,positionDepart[1] + gapY*3,200,20);
        panelRechercheLogement.add(buttonRechercher1);
        buttonRechercher1.addActionListener(this);

        String[] entetes = {"ID", "VILLE", "TYPE", "TAILLE (M²)","PRIX","Type Transaction"};
        dataRechercheLogement = RechercheBD.ExtractionAllAnnonce();

        TableModelRechercheLogement model = new TableModelRechercheLogement(entetes, dataRechercheLogement);
        tableRechercheLogement.setModel(model);

        sorter = new TableRowSorter<TableModelRechercheLogement>(model);

        tableRechercheLogement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable table = (JTable) e.getSource();
                    int idAnnonce = (int) table.getValueAt(table.getSelectedRow(),0);
                    try {
                        annonce = ExtractionAnnonceAvecID(idAnnonce);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

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
                    AFAPhoto = annonce.getLogement().getPhoto();

                    if(AFAPhoto != null)
                    {
                        InputStream in = null;
                        try {
                            in = AFAPhoto.getBinaryStream();
                            AFAImageLogement.setIcon(new ImageIcon(ImageIO.read(in)));
                            AFAImageLogement.setBounds(50,50,300,300);
                            panelAffichageAnnonce.add(AFAImageLogement);
                        } catch (IOException | SQLException ex) {
                            ex.printStackTrace();
                        }
                    }

                    Component[] allPanel = frame.getContentPane().getComponents();
                    frame.remove(allPanel[1]);
                    frame.add(panelAffichageAnnonce,BorderLayout.CENTER);
                    frame.repaint();
                    frame.revalidate();
                }
            }
        });

        JScrollPane tableContainer = new JScrollPane(tableRechercheLogement);
        tableRechercheLogement.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableRechercheLogement.getTableHeader().setForeground(Color.white);
        tableRechercheLogement.getTableHeader().setBackground(new Color(51, 153, 255));
        tableRechercheLogement.setFont(new Font("Arial", Font.BOLD, 13));
        tableRechercheLogement.setAutoCreateRowSorter(true);
        tableRechercheLogement.getTableHeader().setReorderingAllowed(false);
        tableContainer.setBounds(50, 200, 650, 400);

        panelRechercheLogement.add(tableContainer,BorderLayout.CENTER);

        // Panel de gestion de logements

        panelGestionLogement.setBackground(Color.blue);
        panelGestionLogement.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
        panelGestionLogement.setLayout(null);

        // Panel de gestion des offres

        panelGestionOffre.setBackground(Color.red);
        panelGestionOffre.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
        panelGestionOffre.setLayout(null);

        // Panel affichage annonce

        panelAffichageAnnonce.setBackground(Color.white);
        panelAffichageAnnonce.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
        panelAffichageAnnonce.setLayout(null);

        buttonRetourAffichage.setText("Retour");
        buttonRetourAffichage.setBounds(100,550,100,40);
        panelAffichageAnnonce.add(buttonRetourAffichage);
        buttonRetourAffichage.addActionListener(this);

        buttonFaireOffre.setText("Faire une offre");
        buttonFaireOffre.setBounds(500,550,150,40);
        panelAffichageAnnonce.add(buttonFaireOffre);
        buttonFaireOffre.addActionListener(this);
        buttonFaireOffre.setVisible(false);

        AFALabelTypeTransaction.setFont(new Font("Arial", Font.BOLD, 30));
        AFALabelTypeTransaction.setBounds(500,40,200,30);
        panelAffichageAnnonce.add(AFALabelTypeTransaction);

        AFALabelTypeLogement.setFont(new Font("Arial", Font.BOLD, 25));
        AFALabelTypeLogement.setBounds(500,80,200,30);
        panelAffichageAnnonce.add(AFALabelTypeLogement);

        AFALabelVille.setFont(new Font("Arial", Font.BOLD, 15));
        AFALabelVille.setBounds(450,130,200,30);
        panelAffichageAnnonce.add(AFALabelVille);

        AFALabelTaille.setFont(new Font("Arial", Font.BOLD, 15));
        AFALabelTaille.setBounds(450,150,200,30);
        panelAffichageAnnonce.add(AFALabelTaille);

        AFALabelAnneCreation.setFont(new Font("Arial", Font.BOLD, 15));
        AFALabelAnneCreation.setBounds(450,170,400,30);
        panelAffichageAnnonce.add(AFALabelAnneCreation);

        AFALabelAdresse.setFont(new Font("Arial", Font.BOLD, 15));
        AFALabelAdresse.setBounds(450,190,400,30);
        panelAffichageAnnonce.add(AFALabelAdresse);

        AFATextArea.setFont(new Font("Arial", Font.BOLD, 12));
        AFATextArea.setBounds(420,250,300,260);
        AFATextArea.setLineWrap(true);
        panelAffichageAnnonce.add(AFATextArea);

        AFALabelContact.setFont(new Font("Arial", Font.BOLD, 15));
        AFALabelContact.setBounds(50,440,400,30);
        panelAffichageAnnonce.add(AFALabelContact);

        AFALabelNumero.setFont(new Font("Arial", Font.BOLD, 15));
        AFALabelNumero.setBounds(50,460,400,30);
        panelAffichageAnnonce.add(AFALabelNumero);

        AFALabelMail.setFont(new Font("Arial", Font.BOLD, 15));
        AFALabelMail.setBounds(50,480,400,30);
        panelAffichageAnnonce.add(AFALabelMail);


        // Fenetre
        frame.add(panelBandeau,BorderLayout.WEST);
        frame.add(panelAccueil,BorderLayout.CENTER);

        frame.setSize(1000,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Annonce Manager");
    }

    public void utilisateurConnecte(Utilisateur utilisateur){
        connexionLabel.setText(utilisateur.getPrenom() + " " + utilisateur.getNom());
        connexionLabel.setForeground(Color.green.darker());

        utilisateurConnecte = utilisateur;

        buttonGestionLogement.setVisible(true);
        buttonGestionOffre.setVisible(true);
        buttonFaireOffre.setVisible(true);
    }

    public void utilisateurDeconnecte(){
        connexionLabel.setText("Non connecté");
        connexionLabel.setForeground(Color.red.darker());
        utilisateurConnecte = null;

        buttonGestionLogement.setVisible(false);
        buttonGestionOffre.setVisible(false);
        buttonFaireOffre.setVisible(false);

        Component[] allPanel = frame.getContentPane().getComponents();
        frame.remove(allPanel[1]);
        frame.add(panelAccueil,BorderLayout.CENTER);
        frame.repaint();
        frame.revalidate();
    }

    public void mouseClickedActionConnexionLabel(){
        if(utilisateurConnecte == null)
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
                    utilisateurConnecte = RechercheBD.ConnexionUtilisateur(mail.getText(),Integer.toString(motDePasse.getText().hashCode()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if(utilisateurConnecte != null) {
                    utilisateurConnecte(utilisateurConnecte);
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

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == buttonRechercher1) {

            if (!textFieldTailleMin.getText().equals("") && !textFieldTailleMax.getText().equals("")) {

                RowFilter<TableModelRechercheLogement, Integer> filter = new RowFilter<>() {
                    public boolean include(Entry entry) {
                        int taille = (int) entry.getValue(3);
                        return (taille >= Integer.parseInt(textFieldTailleMin.getText()) || taille <= Integer.parseInt(textFieldTailleMax.getText()));
                    }
                };

                sorter.setRowFilter(filter);
                tableRechercheLogement.setRowSorter(sorter);
            }

            if (!textFieldTailleMin.getText().equals("") && textFieldTailleMax.getText().equals("")) {

                sorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, Integer.parseInt(textFieldTailleMin.getText()),3));
                tableRechercheLogement.setRowSorter(sorter);
            }

            if (!textFieldTailleMax.getText().equals("") && textFieldTailleMin.getText().equals(""))
            {

                RowFilter<TableModelRechercheLogement, Integer> filter = new RowFilter<>() {
                    public boolean include(Entry entry) {
                        int taille = (int) entry.getValue(3);
                        boolean bool = taille <= Integer.parseInt(textFieldTailleMax.getText());
                        return (bool);
                    }
                };

                sorter.setRowFilter(filter);
                tableRechercheLogement.setRowSorter(sorter);
            }

            if (!textFieldPrixMin.getText().equals("") && !textFieldPrixMax.getText().equals("")) {

                RowFilter<TableModelRechercheLogement, Integer> filter = new RowFilter<TableModelRechercheLogement, Integer>() {
                    public boolean include(Entry entry) {
                        int prix = (int) entry.getValue(4);
                        return (prix >= Integer.parseInt(textFieldPrixMin.getText()) || prix <= Integer.parseInt(textFieldPrixMax.getText()));
                    }
                };

                sorter.setRowFilter(filter);
                tableRechercheLogement.setRowSorter(sorter);
            }

            if (!textFieldPrixMin.getText().equals("") && textFieldPrixMax.getText().equals("")) {

                RowFilter<TableModelRechercheLogement, Integer> filter = new RowFilter<>() {
                    public boolean include(Entry entry) {
                        int prix = (int) entry.getValue(4);
                        return(prix >= Integer.parseInt(textFieldPrixMin.getText()));
                    }
                };

                sorter.setRowFilter(filter);
                tableRechercheLogement.setRowSorter(sorter);
            }

            if (!textFieldPrixMax.getText().equals("") && textFieldPrixMin.getText().equals(""))
            {

                RowFilter<Object, Object> filter = new RowFilter<Object, Object>(){
                    public boolean include(Entry entry) {
                        Integer prix = (Integer) entry.getValue(4);
                        Integer prixMax = Integer.parseInt(textFieldPrixMax.getText());
                        return (prix.intValue() <= prixMax.intValue());
                    }
                };

                sorter.setRowFilter(filter);
                tableRechercheLogement.setRowSorter(sorter);
            }

            if (textFieldVille.getText() != null) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textFieldVille.getText(), 1));
                tableRechercheLogement.setRowSorter(sorter);
            }

            if (listypeLogement.getSelectedItem() != null && listypeLogement.getSelectedItem().equals("Maison")) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + "Maison", 2));
                tableRechercheLogement.setRowSorter(sorter);
            }

            if (listypeLogement.getSelectedItem() != null && listypeLogement.getSelectedItem().equals("Appartement")) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + "Appartement", 2));
                tableRechercheLogement.setRowSorter(sorter);
            }

            if (listTransaction.getSelectedItem() != null && listTransaction.getSelectedItem().equals("Location")) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + "Location", 5));
                tableRechercheLogement.setRowSorter(sorter);
            }

            if (listTransaction.getSelectedItem() != null && listTransaction.getSelectedItem().equals("Vente")) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + "Vente", 5));
                tableRechercheLogement.setRowSorter(sorter);
            }

        }

        if (e.getSource() == buttonRechercheLogement) {
            Component[] allPanel = frame.getContentPane().getComponents();
            frame.remove(allPanel[1]);
            frame.add(panelRechercheLogement,BorderLayout.CENTER);
            frame.repaint();
            frame.revalidate();
        }

        if (e.getSource() == buttonGestionLogement) {
            Component[] allPanel = frame.getContentPane().getComponents();
            frame.remove(allPanel[1]);
            frame.add(panelGestionLogement,BorderLayout.CENTER);
            frame.repaint();
            frame.revalidate();
        }

        if (e.getSource() == buttonGestionOffre) {
            Component[] allPanel = frame.getContentPane().getComponents();
            frame.remove(allPanel[1]);
            frame.add(panelGestionOffre,BorderLayout.CENTER);
            frame.repaint();
            frame.revalidate();
        }

        if(e.getSource() == buttonRetourAffichage){
            Component[] allPanel = frame.getContentPane().getComponents();
            frame.remove(allPanel[1]);
            frame.add(panelRechercheLogement,BorderLayout.CENTER);
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
                    CreateObject.CreateOffre(annonce,utilisateurConnecte, LocalDate.now(),Integer.parseInt(prix.getText()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                new JOptionPane().showMessageDialog( null, "Offre envoyée au Vendeur","Offre envoyée", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
