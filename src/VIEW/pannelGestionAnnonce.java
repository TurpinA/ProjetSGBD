package VIEW;

import CONTROLER.CreateObject;
import CONTROLER.TableModelGererAnnonce;
import MODEL.Annonce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class pannelGestionAnnonce extends JPanel implements ActionListener {

    private View frame = null;

    private JButton buttonSupprimerAnnonce = new JButton();
    private JButton buttonAjouterAnnonce = new JButton();
    private JTable tableGestionAnnonce = new JTable();
    private JLabel gestionAnnonceTitre = new JLabel();
    private TableModelGererAnnonce model2;
    private ArrayList<Annonce> dataGestionAnnonce = new ArrayList<>();

    public pannelGestionAnnonce(View view){
        this.frame = view;
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
        this.setLayout(null);

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

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonSupprimerAnnonce) {
            if (tableGestionAnnonce.getSelectedRow() != -1) {

                int retour = JOptionPane.showConfirmDialog(this,
                        " Voulez-vous supprimer l'annonce selectionnée ?",
                        "TITRE", JOptionPane.YES_NO_OPTION);

                if (retour == JOptionPane.OK_OPTION) {
                    try {
                        model2.removeRow(tableGestionAnnonce.getSelectedRow());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        if (e.getSource() == buttonAjouterAnnonce) {

            // https://stackoverflow.com/questions/43050653/setsize-on-joptionpane-box

            JTextField ville = new JTextField();
            JTextField taille = new JTextField();
            JTextField type = new JTextField();
            JTextField AnneeDeCreation = new JPasswordField();
            JTextField adresse = new JTextField();
            JFileChooser fc = new JFileChooser();
            JButton description = new JButton();
            description.setBounds(5,5,100,100);
            Object[] message = {
                    "Ville :", ville,
                    "Taille (m²) :", taille,
                    "Type de Logement :", type,
                    "Annee de création :" ,  AnneeDeCreation,
                    "Adresse :", adresse,
                    "Photo :" , fc,
                    "Description :", description
            };

            boolean valid = false;
            boolean readytoadd = false;
            while(!valid){
                valid = true;
                int option = JOptionPane.showConfirmDialog(null, message, "Ajouter une annonce", JOptionPane.OK_CANCEL_OPTION);
                if(option == JOptionPane.OK_OPTION) {
                    if (ville.getText().isEmpty()
                            || taille.getText().equals("")
                            || type.getText().equals("")
                            || AnneeDeCreation.getText().equals("")
                            || adresse.getText().equals("")
                           // || photo.getText().equals("")
                            || description.getText().equals("")){
                        valid = false;
                    }
                    else{
                        readytoadd = true;
                    }
                }
            }
            if(readytoadd){

            }
        }
    }
}
