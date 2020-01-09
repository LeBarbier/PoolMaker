package graphicalComponents;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import gestionDonnees.GestionFichier;
import gestionDonnees.Joueur;

@SuppressWarnings("serial")
public class Affichage extends JFrame{
    JTextField champRechercheMot;
    JScrollPane listeJoueurScroll;
    JLabel libelleRecherche;
    JTable tableJoueurs;
    
	public Affichage() {
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    champRechercheMot = new JTextField();
	    libelleRecherche = new JLabel("Rechercher un joueur");
	    
	    String[] listeColonne = new String[]{"Nom", "Équipe", "Position", "But", "Assistance"};
	    
	    tableJoueurs = new JTable(creationListeJoueur(), listeColonne);
	    
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
	    listeJoueurScroll = new JScrollPane(tableJoueurs);
        listeJoueurScroll.setLayout(null);
        listeJoueurScroll.setBounds(5, 65, 880, 400);
        // listeJoueurScroll.add();
        // listeJoueurScroll.showFrame();
        // listeJoueurScroll.setViewportView(tableJoueurs);

        getContentPane().setLayout(null);
        getContentPane().add(libelleRecherche);
        getContentPane().add(champRechercheMot);
        getContentPane().add(listeJoueurScroll);
        
        this.setResizable(false);
    }

	public String[][] creationListeJoueur(){
	    Joueur[] listeJoueur = GestionFichier.obtenirListeJoueur();
	    String[][] matriceInfoJoueur = new String[listeJoueur.length][5];
	    
	    for(int row = 2; row < listeJoueur.length; row++){
	    	if (listeJoueur[row] != null) {
		    	for (int col = 0; col <= 4; col++) {
		    		switch(col) {
		    		  case 0: // Nom
		    			  matriceInfoJoueur[row-2][col] = listeJoueur[row].getNom();
		    			  	break;
		    		  case 1: // Équipe
		    			  matriceInfoJoueur[row-2][col] = listeJoueur[row].getEquipe();
		    			  	break;
		    		  case 2: // Position
		    			  matriceInfoJoueur[row-2][col] = listeJoueur[row].getPosition();
			    		    break;
		    		  case 3: // Buts
		    			  matriceInfoJoueur[row-2][col] = Integer.toString(listeJoueur[row].getButs());
			    		    break;
		    		  case 4: // Assistances
		    			  matriceInfoJoueur[row-2][col] = Integer.toString(listeJoueur[row].getAssistances());
			    		    break;
		    		  default:
		    		}
		    	}
	    	}
	    }	    
	    return matriceInfoJoueur;
	}	
}
