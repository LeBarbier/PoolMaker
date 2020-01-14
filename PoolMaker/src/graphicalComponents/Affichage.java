package graphicalComponents;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import gestionDonnees.GestionFichier;
import gestionDonnees.Joueur;

import java.awt.*;

@SuppressWarnings("serial")
public class Affichage extends JFrame{
    JTextField champRechercheMot;
    JScrollPane listeJoueurScroll;
    JLabel libelleRecherche;
    JTable tableJoueurs;
	final String[][] listeJoueurTest;
    
	public Affichage() {
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    champRechercheMot = new JTextField();
	    libelleRecherche = new JLabel("Rechercher un joueur");
	    
	    String[] listeColonne = new String[]{"Nom", "Équipe", "Position", "But", "Assist."};
		listeJoueurTest = creationListeJoueur();
	    tableJoueurs = new JTable(listeJoueurTest, listeColonne);
	    
		CreationAffichage();
	}
	
	public static void main(String[] args) {
		Affichage ex = new Affichage();

		ex.setVisible(true);
	}
	
	public void CreationAffichage() {
		JPanel joueurRechercheJPanel = new JPanel();

		setTitle("Pool maker");
		setLayout(new BorderLayout());

	    listeJoueurScroll = new JScrollPane(tableJoueurs);
		tableJoueurs.setFillsViewportHeight(true);
		joueurRechercheJPanel.add(libelleRecherche);
		joueurRechercheJPanel.add(champRechercheMot);
		champRechercheMot.setPreferredSize(new Dimension(150, 25));

		add(joueurRechercheJPanel, BorderLayout.PAGE_START);
		add(listeJoueurScroll, BorderLayout.PAGE_END);

		champRechercheMot.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				listeJoueursUpdate();
			}
			public void removeUpdate(DocumentEvent e) {
				listeJoueursUpdate();
			}
			public void insertUpdate(DocumentEvent e) {
				listeJoueursUpdate();
			}
		});

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public void listeJoueursUpdate(){
		tableJoueurs = new JTable(rechercheListeJoueur(champRechercheMot.getText()),
				new String[]{"Nom", "Équipe", "Position", "But", "Assist."});
		remove(listeJoueurScroll);
		listeJoueurScroll = new JScrollPane(tableJoueurs);
		add(listeJoueurScroll, BorderLayout.PAGE_END);
		validate();
		pack();
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

	public String[][] rechercheListeJoueur(String charRecherche){
		String[][] listeJoueurFiltre = new String[listeJoueurTest.length][];
		int iterateur = 0;

		for (String[] joueurFiltre : listeJoueurTest){
			if (joueurFiltre[0] != null)
			{
				if (joueurFiltre[0].toLowerCase().contains(charRecherche)) {
					System.out.println(joueurFiltre[0]);
					listeJoueurFiltre[iterateur] = joueurFiltre;
					iterateur++;
				}
			}
		}

		return listeJoueurFiltre;
	}
}
