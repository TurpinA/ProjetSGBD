package VIEW;

import CONTROLER.TableModelRechercheLogement;
import MODEL.Annonce;
import MODEL.RechercheBD;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import static MODEL.RechercheBD.ExtractionAnnonceAvecID;

public class pannelRechercheLogement extends JPanel implements ActionListener {

    private View frame = null;

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
    private TableModelRechercheLogement model;
    private TableRowSorter<TableModelRechercheLogement> sorterRechercheLogement;

    public pannelRechercheLogement(View view) throws SQLException {
        this.frame = view;
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
        this.setLayout(null);

        rechercheLogementTitre.setText("Rechercher un logement");
        rechercheLogementTitre.setFont(new Font("Arial", Font.BOLD, 30));
        rechercheLogementTitre.setBounds(200,20,600,35);
        this.add(rechercheLogementTitre);

        int [] positionDepart = {130,80};
        int gapX = 275;
        int gapY = 30;

        labelTailleMin.setText("Taille min:");
        labelTailleMin.setBounds(positionDepart[0],positionDepart[1],labelTailleMin.getText().length()*6,20);
        this.add(labelTailleMin);
        textFieldTailleMin.setBounds(positionDepart[0] + labelTailleMin.getText().length()*6,positionDepart[1],150,20);
        this.add(textFieldTailleMin);

        labelTailleMax.setText("Taille max:");
        labelTailleMax.setBounds(positionDepart[0] + gapX,positionDepart[1],labelTailleMax.getText().length()*6,20);
        this.add(labelTailleMax);
        textFieldTailleMax.setBounds(positionDepart[0] + gapX + labelTailleMax.getText().length()*6,positionDepart[1],150,20);
        this.add(textFieldTailleMax);

        labelPrixMin.setText("Prix min :");
        labelPrixMin.setBounds(positionDepart[0],positionDepart[1] + gapY*1,labelPrixMin.getText().length()*6,20);
        this.add(labelPrixMin);
        textFieldPrixMin.setBounds(positionDepart[0] + labelPrixMin.getText().length()*6,positionDepart[1] + gapY*1,150,20);
        this.add(textFieldPrixMin);

        labelPrixMax.setText("Prix max :");
        labelPrixMax.setBounds(positionDepart[0]+gapX,positionDepart[1] + gapY*1,labelPrixMax.getText().length()*6,20);
        this.add(labelPrixMax);
        textFieldPrixMax.setBounds(positionDepart[0]+gapX + labelPrixMin.getText().length()*6,positionDepart[1] + gapY*1,150,20);
        this.add(textFieldPrixMax);


        labelTypeLogement.setText("Type de Logement :");
        labelTypeLogement.setBounds(positionDepart[0]+gapX,positionDepart[1] + gapY*2,labelTypeLogement.getText().length()*6+10,20);
        this.add(labelTypeLogement);
        listypeLogement.addItem(null);
        listypeLogement.addItem("Maison");
        listypeLogement.addItem("Appartement");
        listypeLogement.setBounds(positionDepart[0]+gapX + labelTypeLogement.getText().length()*6 + 7,positionDepart[1] + gapY*2,100,20);
        this.add(listypeLogement);

        labelTypeTransaction.setText("Type de Transaction : ");
        labelTypeTransaction.setBounds(positionDepart[0],positionDepart[1] + gapY*3,labelTypeTransaction.getText().length()*6,20);
        this.add(labelTypeTransaction);
        listTransaction.addItem(null);
        listTransaction.addItem("Location");
        listTransaction.addItem("Vente");
        listTransaction.setBounds(positionDepart[0]+ labelTypeTransaction.getText().length()*6,positionDepart[1] + gapY*3,80,20);
        this.add(listTransaction);

        labelVille.setText("Ville :");
        labelVille.setBounds(positionDepart[0],positionDepart[1] + gapY*2,labelVille.getText().length()*6,20);
        this.add(labelVille);
        textFieldVille.setBounds(positionDepart[0] + labelPrixMin.getText().length()*6,positionDepart[1] + gapY*2,150,20);
        this.add(textFieldVille);

        buttonRechercher1.setText("Rechercher");
        buttonRechercher1.setBounds(positionDepart[0]+gapX,positionDepart[1] + gapY*3,200,20);
        this.add(buttonRechercher1);
        buttonRechercher1.addActionListener(this);

        String[] entetes = {"ID", "VILLE", "TYPE", "TAILLE (M²)","PRIX","Type Transaction"};
        dataRechercheLogement = RechercheBD.ExtractionAllAnnonce();

        model = new TableModelRechercheLogement(entetes, dataRechercheLogement);
        tableRechercheLogement.setModel(model);

        sorterRechercheLogement = new TableRowSorter<TableModelRechercheLogement>(model);

        tableRechercheLogement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable table = (JTable) e.getSource();
                    int idAnnonce = (int) table.getValueAt(table.getSelectedRow(),0);
                    try {
                        frame.getPanelAnnonce().changerAnnonce(ExtractionAnnonceAvecID(idAnnonce));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    Component[] allPanel = frame.getContentPane().getComponents();
                    frame.remove(allPanel[1]);
                    frame.add(frame.getPanelAnnonce(),BorderLayout.CENTER);
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

        this.add(tableContainer,BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if(e.getSource() == buttonRechercher1) {

            RowFilter<TableModelRechercheLogement, Object> filterVilleRechercheLogement = null;
            RowFilter<TableModelRechercheLogement, Object> filterlistypeLogement1RechercheLogement = null;
            RowFilter<TableModelRechercheLogement, Object> filterlistypeLogement2RechercheLogement = null;
            RowFilter<TableModelRechercheLogement, Object> filterlistTransaction1RechercheLogement = null;
            RowFilter<TableModelRechercheLogement, Object> filterlistTransaction2RechercheLogement = null;
            RowFilter<TableModelRechercheLogement, Object> filterTailleMinRechercheLogement = null;
            RowFilter<TableModelRechercheLogement, Object> filterTailleMaxRechercheLogement = null;
            RowFilter<TableModelRechercheLogement, Object> filterPrixMaxRechercheLogement = null;
            RowFilter<TableModelRechercheLogement, Object> filterPrixMinRechercheLogement = null;
            RowFilter<TableModelRechercheLogement, Object> filterPrixRechercheLogement = null;
            RowFilter<TableModelRechercheLogement, Object> filterTailleRechercheLogement = null;

            ArrayList<RowFilter<TableModelRechercheLogement,Object>> filtersRechercheLogement = new ArrayList<RowFilter<TableModelRechercheLogement,Object>>();
            RowFilter<TableModelRechercheLogement, Object> compoundRowFilter = null;

            if (!textFieldPrixMin.getText().equals("") && !textFieldPrixMax.getText().equals("")) {

                filterPrixRechercheLogement = new RowFilter<>() {
                    @Override
                    public boolean include(Entry entry) {
                        int prix = (int) entry.getValue(4);
                        return (prix >= Integer.parseInt(textFieldPrixMin.getText()) && prix <= Integer.parseInt(textFieldPrixMax.getText()));
                    }
                };
                filtersRechercheLogement.add(filterPrixRechercheLogement);
            }

            if (!textFieldTailleMin.getText().equals("") && !textFieldTailleMax.getText().equals("")) {

                filterTailleRechercheLogement = new RowFilter<>() {
                    @Override
                    public boolean include(Entry entry) {
                        int taille = (int) entry.getValue(3);
                        return (taille >= Integer.parseInt(textFieldTailleMin.getText()) && taille <= Integer.parseInt(textFieldTailleMax.getText()));
                    }
                };
                filtersRechercheLogement.add(filterTailleRechercheLogement);
            }

            if (!textFieldPrixMin.getText().equals("") && textFieldPrixMax.getText().equals("")) {

                filterPrixMinRechercheLogement = new RowFilter<>() {
                    @Override
                    public boolean include(Entry entry) {
                        int prix = (int) entry.getValue(4);
                        return (prix >= Integer.parseInt(textFieldPrixMin.getText()));
                    }
                };
                filtersRechercheLogement.add(filterPrixMinRechercheLogement);
            }

            if (!textFieldPrixMax.getText().equals("") && textFieldPrixMin.getText().equals("")) {

                filterPrixMaxRechercheLogement = new RowFilter<>() {
                    @Override
                    public boolean include(Entry entry) {
                        int prix = (int) entry.getValue(4);
                        return (prix <= Integer.parseInt(textFieldPrixMax.getText()));
                    }
                };
                filtersRechercheLogement.add(filterPrixMaxRechercheLogement);
            }

            if (!textFieldTailleMax.getText().equals("") && textFieldTailleMin.getText().equals("")) {

                filterTailleMaxRechercheLogement = new RowFilter<>() {
                    @Override
                    public boolean include(Entry entry) {
                        int taille = (int) entry.getValue(3);
                        return (taille <= Integer.parseInt(textFieldTailleMax.getText()));
                    }
                };
                filtersRechercheLogement.add(filterTailleMaxRechercheLogement);
            }

            if (!textFieldTailleMin.getText().equals("") && textFieldTailleMax.getText().equals("")) {

                filterTailleMinRechercheLogement = new RowFilter<>() {
                    @Override
                    public boolean include(Entry entry) {
                        int taille = (int) entry.getValue(3);
                        return (taille >= Integer.parseInt(textFieldTailleMin.getText()));
                    }
                };
                filtersRechercheLogement.add(filterTailleMinRechercheLogement);
            }

            if (textFieldVille.getText() != null) {
                filterVilleRechercheLogement = RowFilter.regexFilter("(?i)" + textFieldVille.getText(), 1);
                filtersRechercheLogement.add(filterVilleRechercheLogement);
            }

            if (listypeLogement.getSelectedItem() != null && listypeLogement.getSelectedItem().equals("Maison")) {
                filterlistypeLogement1RechercheLogement = RowFilter.regexFilter("(?i)" + "Maison", 2);
                filtersRechercheLogement.add(filterlistypeLogement1RechercheLogement);
            }

            if (listypeLogement.getSelectedItem() != null && listypeLogement.getSelectedItem().equals("Appartement")) {
                filterlistypeLogement2RechercheLogement = RowFilter.regexFilter("(?i)" + "Appartement", 2);
                filtersRechercheLogement.add(filterlistypeLogement2RechercheLogement);
            }

            if (listTransaction.getSelectedItem() != null && listTransaction.getSelectedItem().equals("Location")) {
                filterlistTransaction1RechercheLogement = RowFilter.regexFilter("(?i)" + "Location", 5);
                filtersRechercheLogement.add(filterlistTransaction1RechercheLogement);
            }

            if (listTransaction.getSelectedItem() != null && listTransaction.getSelectedItem().equals("Vente")) {
                filterlistTransaction2RechercheLogement = RowFilter.regexFilter("(?i)" + "Vente", 5);
                filtersRechercheLogement.add(filterlistTransaction2RechercheLogement);
            }

            compoundRowFilter = RowFilter.andFilter(filtersRechercheLogement);
            sorterRechercheLogement.setRowFilter(compoundRowFilter);
            tableRechercheLogement.setRowSorter(sorterRechercheLogement);
        }
    }
}
