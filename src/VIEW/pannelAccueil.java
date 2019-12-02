package VIEW;

import javax.swing.*;
import java.awt.*;

public class pannelAccueil extends JPanel {

    private JLabel texteBienvenue = new JLabel();

    public pannelAccueil(){
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
        this.setLayout(null);

        texteBienvenue.setText("BIENVENUE DANS ANNONCE MANAGER !");
        texteBienvenue.setFont(new Font("Arial", Font.BOLD, 20));
        texteBienvenue.setBounds(180,300,texteBienvenue.getText().length()*20,20);
        this.add(texteBienvenue);
    }
}
