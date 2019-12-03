package CONTROLER;

import MODEL.Annonce;
import MODEL.Offre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

public class TableModelGestionOffre extends AbstractTableModel {

    private String[] columnNames;
    private ArrayList<Offre> donnee;

    public TableModelGestionOffre(String[] columnNames, ArrayList<Offre> donnee) {
        this.columnNames = columnNames;
        this.donnee = donnee;
    }

    public void setDonne(ArrayList<Offre> donnee){
        this.donnee = donnee;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return donnee.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {

        if (donnee.size() != 0) {
            Iterator<Offre> iterator = donnee.iterator();

            for (int i = 0; i < row; i++)
                iterator.next();

            Offre annonceSelected = iterator.next();

            switch (col) {
                case 0:
                    return annonceSelected.getId();
                case 1:
                    return annonceSelected.getAnnonce().getId();
                case 2:
                    return annonceSelected.getAcheteur().getNom() + " " + annonceSelected.getAcheteur().getPrenom();
                case 3:
                    return annonceSelected.getDateCreation().toString();
                case 4:
                    return annonceSelected.getPrix();
                case 5:
                    return annonceSelected.getStatus();
            }
        }
        return null;
    }

    public void removeRow(int row) throws SQLException {
        int idOffre = (int)getValueAt(row,0);
        DelecteObject.SupprimerAnnonceByID(idOffre);

        Iterator<Offre> iterator = donnee.iterator();
        Offre offre = iterator.next();
        while(offre.getId() != idOffre){
            offre = iterator.next();
        }

        donnee.remove(offre);
        this.fireTableDataChanged();
    }

    public Class<? extends Object> getColumnClass(int c) {
        if (getValueAt(0, c) != null)
            return getValueAt(0, c).getClass();
        else
            return null;
    }

    public boolean isCellEditable(int row, int col) {
            return false;
    }

}
