package VIEW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class pannelGestionOffre extends JPanel implements ActionListener {

    private View frame = null;

    public pannelGestionOffre(View view){
        this.frame = view;
        this.setBackground(Color.red);
        this.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
        this.setLayout(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
