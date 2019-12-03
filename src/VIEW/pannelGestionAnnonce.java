package VIEW;

import CONTROLER.CreateObject;
import CONTROLER.TableModelGererAnnonce;
import MODEL.Annonce;
import MODEL.Logement;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class pannelGestionAnnonce extends JPanel implements ActionListener {

    private View frame = null;

    private JButton buttonSupprimerAnnonce = new JButton();
    private JButton buttonAjouterAnnonce = new JButton();
    private JButton photo = new JButton("...");
    private JTable tableGestionAnnonce = new JTable();
    private JLabel gestionAnnonceTitre = new JLabel();
    private TableModelGererAnnonce model2;
    private ArrayList<Annonce> dataGestionAnnonce = new ArrayList<>();
    private JTextField photoField = new JTextField();

    public pannelGestionAnnonce(View view){
        this.frame = view;
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
        this.setLayout(null);

        photo.addActionListener(this);

        buttonSupprimerAnnonce.setText("Supprimer l'annonce");
        buttonSupprimerAnnonce.setBounds(460,150,200,40);
        this.add(buttonSupprimerAnnonce);
        buttonSupprimerAnnonce.addActionListener(this);

        buttonAjouterAnnonce.setText("Ajouter une annonce");
        buttonAjouterAnnonce.setBounds(100,150,200,40);
        this.add(buttonAjouterAnnonce);
        buttonAjouterAnnonce.addActionListener(this);

        gestionAnnonceTitre.setText("Gestion de mes annonces");
        gestionAnnonceTitre.setFont(new Font("Arial", Font.BOLD, 30));
        gestionAnnonceTitre.setBounds(200,20,600,35);
        this.add(gestionAnnonceTitre);

        String[] entetes2 = {"ID", "VILLE", "TYPE", "TAILLE (M²)","PRIX","Type Transaction"};

        model2 = new TableModelGererAnnonce(entetes2, dataGestionAnnonce);
        tableGestionAnnonce.setModel(model2);

        JScrollPane tableContainer2 = new JScrollPane(tableGestionAnnonce);
        tableGestionAnnonce.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableGestionAnnonce.getTableHeader().setForeground(Color.white);
        tableGestionAnnonce.getTableHeader().setBackground(new Color(51, 153, 255));
        tableGestionAnnonce.setFont(new Font("Arial", Font.BOLD, 13));
        tableGestionAnnonce.setAutoCreateRowSorter(true);
        tableGestionAnnonce.getTableHeader().setReorderingAllowed(false);
        tableContainer2.setBounds(50, 200, 650, 400);

        this.add(tableContainer2,BorderLayout.CENTER);

    }

    public TableModelGererAnnonce getModel2() {
        return model2;
    }

    public void setModel2(TableModelGererAnnonce model2) {
        this.model2 = model2;
    }

    public void setDataGestionAnnonce(ArrayList<Annonce> dataGestionAnnonce) {
        this.dataGestionAnnonce = dataGestionAnnonce;
    }

    public ArrayList<Annonce> getDataGestionAnnonce() {
        return dataGestionAnnonce;
    }


    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonSupprimerAnnonce) {
            if (tableGestionAnnonce.getSelectedRow() != -1) {

                int retour = JOptionPane.showConfirmDialog(this,
                        " Voulez-vous supprimer l'annonce selectionnée ? (ID = " + model2.getValueAt(tableGestionAnnonce.getSelectedRow(),0) +")",
                        "TITRE", JOptionPane.YES_NO_OPTION);

                if (retour == JOptionPane.OK_OPTION) {
                    try {
                        int id = (int)model2.getValueAt(tableGestionAnnonce.getSelectedRow(),0);
                        frame.getPanelRechercheLogement().getModel().removeRowByID(id);
                        frame.getPanelRechercheLogement().getModel().fireTableDataChanged();
                        model2.removeRow(tableGestionAnnonce.getSelectedRow());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        if (e.getSource() == buttonAjouterAnnonce) {

            JPanel p = new JPanel(new BorderLayout(5,5));

            JPanel labels = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;


            c.ipady = 0;
            c.gridx = 0;
            c.gridy = 0;
            labels.add(new JLabel("Ville :  ", SwingConstants.RIGHT),c);

            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 1;
            labels.add(new JLabel("Taille :  ", SwingConstants.RIGHT),c);

            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 2;
            labels.add(new JLabel("Type de Logement :  ", SwingConstants.RIGHT),c);

            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 3;
            labels.add(new JLabel("Type de Transaction :  ", SwingConstants.RIGHT),c);

            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 4;
            labels.add(new JLabel("Annee de création :  ", SwingConstants.RIGHT),c);

            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 5;
            labels.add(new JLabel("Adresse :  ", SwingConstants.RIGHT),c);

            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 6;
            labels.add(new JLabel("Prix :  ", SwingConstants.RIGHT),c);

            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 7;
            labels.add(new JLabel("Photo :  ", SwingConstants.RIGHT),c);

            c.ipady = 100;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 8;
            labels.add(new JLabel("Description :  ", SwingConstants.RIGHT),c);

            c.ipadx = 100;
            c.ipady = 0;
            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 0;
            JTextField ville = new JTextField();
            labels.add(ville,c);

            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 1;
            JTextField taille = new JTextField();
            labels.add(taille,c);

            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 2;
            JTextField typeLogement = new JTextField();
            labels.add(typeLogement,c);

            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 3;
            JTextField typeTransaction = new JTextField();
            labels.add(typeTransaction,c);

            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 4;
            JTextField anneeDeCreation = new JTextField("DD/MM/YYYY");
            labels.add(anneeDeCreation,c);

            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 5;
            JTextField adresse = new JTextField();
            labels.add(adresse,c);

            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 6;
            JTextField prix = new JTextField();
            labels.add(prix,c);

            c.ipadx = 5;
            c.weightx = 0.5;
            c.gridx = 2;
            c.gridy = 6;
            labels.add(new JLabel("€", SwingConstants.LEFT),c);

            c.ipadx = 5;
            c.weightx = 0.5;
            c.gridx = 2;
            c.gridy = 1;
            labels.add(new JLabel("m²", SwingConstants.LEFT),c);

            c.ipadx = 100;
            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 7;
            labels.add(photoField,c);

            c.ipadx = 5;
            c.weightx = 0.5;
            c.gridx = 2;
            c.gridy = 7;
            photo.setPreferredSize(new Dimension(20,20));
            labels.add(photo,c);

            c.ipadx = 250;
            c.ipady = 100;
            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 8;
            JTextArea description = new JTextArea();
            JScrollPane sp = new JScrollPane(description);
            labels.add(sp, c);

            p.add(labels);

            boolean valid = false;
            boolean ready = false;
            while(!valid) {
                valid = true;
                int result = JOptionPane.showConfirmDialog(null, p, "Create Atomic Action", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    boolean photoGood = false;

                    if(!photo.getText().equals("")){
                        File f = new File(photoField.getText());
                        if(f.exists()) {
                            photoGood = true;
                            System.out.println("FILE IS GOOD");

                            double tailleFichier =  f.length() / 1024;

                            if(tailleFichier > 4194303.999023438)
                            {
                                JOptionPane.showMessageDialog(null,"L'image est trop volumineuse ","Alerte",JOptionPane.OK_OPTION);
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"Le chemin vers l'image n'est pas bon","Alerte",JOptionPane.OK_OPTION);
                        }
                    }

                    if (ville.getText().equals("")
                            || taille.getText().equals("")
                            || typeLogement.getText().equals("")
                            || anneeDeCreation.getText().equals("")
                            || adresse.getText().equals("")
                            || prix.getText().equals("")
                            || !photoGood
                            || typeTransaction.getText().equals("")) {

                        valid = false;
                    } else {ready = true;}
                }
            }

            if(ready)
            {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String date = anneeDeCreation.getText();
                LocalDate localDate = LocalDate.parse(date, formatter);
                BufferedImage bufferedImage = null;

                InputStream data = null;
                try {
                    data = new FileInputStream(photoField.getText());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

                try {
                    Logement logement = CreateObject.CreateLogement(ville.getText(),Integer.parseInt(taille.getText()),typeLogement.getText(),localDate,adresse.getText(),data,description.getText());
                    Annonce annonce = CreateObject.CreateAnnonce(logement,View.utilisateurConnecte,Integer.parseInt(prix.getText()),typeTransaction.getText());

                    model2.getDonnee().add(annonce);
                    model2.fireTableDataChanged();

                    frame.getPanelRechercheLogement().getModel().getDonnee().add(annonce);
                    frame.getPanelRechercheLogement().getModel().fireTableDataChanged();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if(e.getSource() == photo){
            JFileChooser choix = new JFileChooser();
            int retour = choix.showOpenDialog(this);
            if(retour==JFileChooser.APPROVE_OPTION){
                photoField.setText(choix.getSelectedFile().getAbsolutePath());
            }
        }
    }
}
