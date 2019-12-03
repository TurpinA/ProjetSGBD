package VIEW;

import CONTROLER.TableModelGererAnnonce;
import CONTROLER.TableModelGestionOffre;
import MODEL.Annonce;
import MODEL.Offre;
import MODEL.RechercheBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class pannelGestionOffre extends JPanel implements ActionListener {

    private View frame = null;

    private JTable tableGestionOffre = new JTable();
    private JTable tableGestionOffreProprio = new JTable();
    private JLabel GestionOffre = new JLabel();
    private JButton SwitchProfilclient = new JButton();
    private JButton SwitchProfilProprio = new JButton();
    private TableModelGestionOffre model2;
    private TableModelGestionOffre model3;
    private ArrayList<Offre> dataGestionOffreClient = new ArrayList<Offre>();
    private ArrayList<Offre> dataGestionOffreProprio = new ArrayList<Offre>();


    public pannelGestionOffre(View view) throws SQLException {
        this.frame = view;
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
        this.setLayout(null);

        GestionOffre.setText("Gestion des offres");
        GestionOffre.setFont(new Font("Arial", Font.BOLD, 30));
        GestionOffre.setBounds(225,20,600,35);
        this.add(GestionOffre);

        SwitchProfilclient.setText("Afficher en tant que proprietaire");
        SwitchProfilclient.setBounds(230,100,250,40);
        this.add(SwitchProfilclient);
        SwitchProfilclient.addActionListener(this);

        SwitchProfilProprio.setText("Afficher en tant que client");
        SwitchProfilProprio.setBounds(240,100,230,40);
        this.add(SwitchProfilProprio);
        SwitchProfilProprio.addActionListener(this);
        SwitchProfilProprio.setVisible(false);

        dataGestionOffreClient = RechercheBD.ExtractionOffreUtilisateur(View.utilisateurConnecte);
        for(Offre offre : dataGestionOffreClient){
            if(offre.getAcheteur().getNom().equals(View.utilisateurConnecte.getNom())){
                dataGestionOffreProprio.add(offre);
            }
        }

        for(Offre offre : dataGestionOffreProprio){
            if(dataGestionOffreClient.contains(offre)){
                dataGestionOffreClient.remove(offre);
            }
        }

        String[] entetes2 = {"ID", "Annonce", "Acheteur", "Date de creation","PRIX","Status"};

        model2 = new TableModelGestionOffre(entetes2, dataGestionOffreClient);
        model3 = new TableModelGestionOffre(entetes2, dataGestionOffreProprio);

        tableGestionOffre.setModel(model2);

        JScrollPane tableContainer2 = new JScrollPane(tableGestionOffre);
        tableGestionOffre.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableGestionOffre.getTableHeader().setForeground(Color.white);
        tableGestionOffre.getTableHeader().setBackground(new Color(51, 153, 255));
        tableGestionOffre.setFont(new Font("Arial", Font.BOLD, 13));
        tableGestionOffre.setAutoCreateRowSorter(true);
        tableGestionOffre.getTableHeader().setReorderingAllowed(false);
        tableContainer2.setBounds(50, 200, 650, 400);

        this.add(tableContainer2,BorderLayout.CENTER);



    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == SwitchProfilclient){
            SwitchProfilclient.setVisible(!SwitchProfilclient.isVisible());
            SwitchProfilProprio.setVisible(!SwitchProfilProprio.isVisible());
            tableGestionOffre.setModel(model3);
        }

        if(e.getSource() == SwitchProfilProprio){
            SwitchProfilclient.setVisible(!SwitchProfilclient.isVisible());
            SwitchProfilProprio.setVisible(!SwitchProfilProprio.isVisible());
            tableGestionOffre.setModel(model2);
        }
    }


}
