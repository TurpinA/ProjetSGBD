package CONTROLER;

import MODEL.Annonce;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

public class TableModelRechercheLogement extends AbstractTableModel {

    private String[] columnNames;
    private ArrayList<Annonce> donnee;

    public TableModelRechercheLogement(String[] columnNames, ArrayList<Annonce> donnee) {
        this.columnNames = columnNames;
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
            Iterator<Annonce> iterator = donnee.iterator();

            for (int i = 0; i < row; i++)
                iterator.next();

            Annonce annonceSelected = iterator.next();

            switch (col) {
                case 0:
                    return annonceSelected.getId();
                case 1:
                    return annonceSelected.getLogement().getVille();
                case 2:
                    return annonceSelected.getLogement().getType();
                case 3:
                    return annonceSelected.getLogement().getTaille();
                case 4:
                    return annonceSelected.getPrix();
                case 5:
                    return annonceSelected.gettypeTransaction();
            }
        }
        return null;
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
