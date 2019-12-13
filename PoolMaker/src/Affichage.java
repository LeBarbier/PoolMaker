import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Affichage extends JFrame{
    JTextField champRechercheMot;
    JLabel libelleRecherche;
    
	public Affichage() {
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    champRechercheMot = new JTextField();
	    libelleRecherche = new JLabel("Rechercher un joueur");

		CreationAffichage();
	}
	
	public static void main(String[] args) {
		Affichage ex = new Affichage();

		ex.setVisible(true);
	}
	
	public void CreationAffichage() {
		setTitle("Dictio");
        setSize(900, 500);
        
        libelleRecherche.setBounds(10, 1, 270, 25);
        champRechercheMot.setBounds(5, 25, 270, 30);

        getContentPane().setLayout(null);
        getContentPane().add(libelleRecherche);
        getContentPane().add(champRechercheMot);
        
        this.setResizable(false);
    }

}
