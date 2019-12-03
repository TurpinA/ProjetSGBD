package VIEW;

import CONTROLER.ModifyObject;
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
    private JButton AccepterOffre = new JButton();
    private JButton refuserOffre = new JButton();



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

        refuserOffre.setText("Refuser");
        refuserOffre.setBounds(100,150,200,40);
        this.add(refuserOffre);
        refuserOffre.addActionListener(this);
        refuserOffre.setVisible(false);

        AccepterOffre.setText("Accepter");
        AccepterOffre.setBounds(300,150,200,40);
        this.add(AccepterOffre);
        AccepterOffre.addActionListener(this);
        AccepterOffre.setVisible(false);

        SwitchProfilProprio.setText("Afficher en tant que client");
        SwitchProfilProprio.setBounds(240,100,230,40);
        this.add(SwitchProfilProprio);
        SwitchProfilProprio.addActionListener(this);
        SwitchProfilProprio.setVisible(false);

        extractdata();

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
            refuserOffre.setVisible(true);
            AccepterOffre.setVisible(true);
        }

        if(e.getSource() == SwitchProfilProprio){
            SwitchProfilclient.setVisible(!SwitchProfilclient.isVisible());
            SwitchProfilProprio.setVisible(!SwitchProfilProprio.isVisible());
            tableGestionOffre.setModel(model2);
            refuserOffre.setVisible(false);
            AccepterOffre.setVisible(false);
        }

        if(e.getSource() == AccepterOffre){
            int id = (int) tableGestionOffre.getValueAt(tableGestionOffre.getSelectedRow(),0);
            System.out.print(id);
            Offre offre = new Offre(id,null,null,null,0,"accepter");
            try {
                ModifyObject.modifierOffre(offre);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            extractdata();

            model3.setDonne(dataGestionOffreProprio);
            model3.fireTableDataChanged();

        }

        if(e.getSource() == refuserOffre){
            int id = (int) tableGestionOffre.getValueAt(tableGestionOffre.getSelectedRow(),0);
            System.out.print(id);
            Offre offre = new Offre(id,null,null,null,0,"refuser");
            try {
                ModifyObject.modifierOffre(offre);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            extractdata();

            model3.setDonne(dataGestionOffreProprio);
            model3.fireTableDataChanged();
        }
    }

    public void extractdata(){

        dataGestionOffreClient = new ArrayList<Offre>();
        dataGestionOffreProprio = new ArrayList<Offre>();

        try {
            dataGestionOffreProprio = RechercheBD.ExtractionOffreUtilisateur(View.utilisateurConnecte);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Offre offre : dataGestionOffreProprio){
            if(View.utilisateurConnecte.getId() == offre.getAcheteur().getId()){
                dataGestionOffreClient.add(offre);
            }
        }

        for(Offre offre : dataGestionOffreClient){
            if(dataGestionOffreProprio.contains(offre)){
                dataGestionOffreProprio.remove(offre);
            }
        }
    }


}
