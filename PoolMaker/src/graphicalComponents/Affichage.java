package graphicalComponents;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import gestionDonnees.GestionFichier;
import gestionDonnees.GestionPool;
import gestionDonnees.Joueur;
import gestionDonnees.Pooler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Affichage extends JFrame{
    JTextField champRechercheMot;
    JScrollPane listeJoueurScroll;
    JLabel libelleRecherche;
    JTable tableJoueurs;
	DefaultTableModel model;
	final String[][] listeJoueurTest;
	JMenuBar barreMenu;
    JMenu menuParamUtilisateur;
    JMenu menuMenu;
	JMenuItem creerNouveauPool;
	GestionPool pool;

	public Affichage() {
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    champRechercheMot = new JTextField();
	    libelleRecherche = new JLabel("Rechercher un joueur");
	    
	    String[] listeColonne = new String[]{"Nom", "Équipe", "Position", "But", "Assist."};
		listeJoueurTest = creationListeJoueur();
		model = new DefaultTableModel(listeJoueurTest, listeColonne);

		tableJoueurs = new JTable();
		tableJoueurs.setModel(model);
		tableJoueurs.setAutoCreateRowSorter(true);
		tableJoueurs.setDefaultEditor(Object.class, null);
		tableJoueurs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

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

		barreMenu = new JMenuBar();
		menuParamUtilisateur = new JMenu("Paramètres Utilisateur");
		menuMenu = new JMenu("Menu");
		creerNouveauPool = new JMenuItem("Créer un nouveau Pool");

		creerNouveauPool.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initDataPool();
				initAffichagePool();
				repaint();
				pack();
			}
		});

		menuMenu.add(creerNouveauPool);
		barreMenu.add(menuMenu);
		barreMenu.add(menuParamUtilisateur);

		tableJoueurs.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPopupMenu popupMenu = new JPopupMenu();
				if (e.getButton() == 3){
					JMenu anItem = new JMenu("Ajouter à ...");

					if (pool != null) {
						System.out.println(pool.getPooler(0).getPoolerPrenom());
						JMenuItem menuItemPoolerUn = new JMenuItem(pool.getPooler(0).getPoolerPrenom());
						JMenuItem menuItemPoolerDeux = new JMenuItem(pool.getPooler(1).getPoolerPrenom());
						JMenuItem menuItemPoolerTrois = new JMenuItem(pool.getPooler(2).getPoolerPrenom());
						JMenuItem menuItemPoolerQuatre = new JMenuItem(pool.getPooler(3).getPoolerPrenom());
						JMenuItem menuItemPoolerCinq = new JMenuItem(pool.getPooler(4).getPoolerPrenom());
						JMenuItem menuItemPoolerSix = new JMenuItem(pool.getPooler(5).getPoolerPrenom());
						JMenuItem menuItemPoolerSept = new JMenuItem(pool.getPooler(6).getPoolerPrenom());
						JMenuItem menuItemPoolerHuit = new JMenuItem(pool.getPooler(7).getPoolerPrenom());

						menuItemPoolerUn.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// pool.getPooler(0).ajoutJoueurAuPool();
							}
						});

						anItem.add(menuItemPoolerUn);
						anItem.add(menuItemPoolerDeux);
						anItem.add(menuItemPoolerTrois);
						anItem.add(menuItemPoolerQuatre);
						anItem.add(menuItemPoolerCinq);
						anItem.add(menuItemPoolerSix);
						anItem.add(menuItemPoolerSept);
						anItem.add(menuItemPoolerHuit);
					}

					popupMenu.add(anItem);
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		listeJoueurScroll = new JScrollPane(tableJoueurs);
		tableJoueurs.setFillsViewportHeight(true);
		joueurRechercheJPanel.add(libelleRecherche);
		joueurRechercheJPanel.add(champRechercheMot);
		champRechercheMot.setPreferredSize(new Dimension(150, 25));

		setJMenuBar(barreMenu);
		add(joueurRechercheJPanel, BorderLayout.PAGE_START);
		add(listeJoueurScroll, BorderLayout.CENTER);

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
		this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public void listeJoueursUpdate(){
		tableJoueurs = new JTable(rechercheListeJoueur(champRechercheMot.getText()),
				new String[]{"Nom", "Équipe", "Position", "But", "Assist."});

		remove(listeJoueurScroll);
		listeJoueurScroll = new JScrollPane(tableJoueurs);
		add(listeJoueurScroll, BorderLayout.CENTER);
		validate();
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
					listeJoueurFiltre[iterateur] = joueurFiltre;
					iterateur++;
				}
			}
		}

		String[][] listeJoueurRetournee = new String[iterateur][];

		iterateur = 0;
		for (String[] joueur : listeJoueurFiltre){
			if (joueur != null){
				listeJoueurRetournee[iterateur] = joueur;
			}
			iterateur++;
		}

		return listeJoueurRetournee;
	}

	public void initDataPool(){
		JTextField poolerUnTextField = new JTextField(5);
		JTextField poolerDeuxTextField = new JTextField(5);
		JTextField poolerTroisTextField = new JTextField(5);
		JTextField poolerQuatreTextField = new JTextField(5);
		JTextField poolerCinqTextField = new JTextField(5);
		JTextField poolerSixTextField = new JTextField(5);
		JTextField poolerSeptTextField = new JTextField(5);
		JTextField poolerHuitTextField = new JTextField(5);
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

		myPanel.add(new JLabel("Pooler #1"));
		myPanel.add(poolerUnTextField);
		myPanel.add(Box.createVerticalStrut(10)); // a spacer

		myPanel.add(new JLabel("Pooler #2"));
		myPanel.add(poolerDeuxTextField);
		myPanel.add(Box.createVerticalStrut(10)); // a spacer

		myPanel.add(new JLabel("Pooler #3"));
		myPanel.add(poolerTroisTextField);
		myPanel.add(Box.createVerticalStrut(10)); // a spacer

		myPanel.add(new JLabel("Pooler #4"));
		myPanel.add(poolerQuatreTextField);
		myPanel.add(Box.createVerticalStrut(10)); // a spacer

		myPanel.add(new JLabel("Pooler #5"));
		myPanel.add(poolerCinqTextField);
		myPanel.add(Box.createVerticalStrut(10)); // a spacer

		myPanel.add(new JLabel("Pooler #6"));
		myPanel.add(poolerSixTextField);
		myPanel.add(Box.createVerticalStrut(10)); // a spacer

		myPanel.add(new JLabel("Pooler #7"));
		myPanel.add(poolerSeptTextField);
		myPanel.add(Box.createVerticalStrut(10)); // a spacer

		myPanel.add(new JLabel("Pooler #8"));
		myPanel.add(poolerHuitTextField);
		myPanel.add(Box.createVerticalStrut(10)); // a spacer

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Entrée le nom de chacun des poolers", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null);
		if (result == JOptionPane.OK_OPTION) {
			ArrayList<Pooler> listePooler = new ArrayList<Pooler>();
			listePooler.add(new Pooler(poolerUnTextField.getText()));
			listePooler.add(new Pooler(poolerDeuxTextField.getText()));
			listePooler.add(new Pooler(poolerTroisTextField.getText()));
			listePooler.add(new Pooler(poolerQuatreTextField.getText()));
			listePooler.add(new Pooler(poolerCinqTextField.getText()));
			listePooler.add(new Pooler(poolerSixTextField.getText()));
			listePooler.add(new Pooler(poolerSeptTextField.getText()));
			listePooler.add(new Pooler(poolerHuitTextField.getText()));

			pool = new GestionPool(listePooler);
		}
	}

	public void initAffichagePool(){
		if (pool != null) {
			String[] listePoolerNom = pool.getListeNomPooler();
			JPanel jPanelAllPooler = new JPanel();
			JPanel jPanelUnPooler = new JPanel();
			jPanelUnPooler.setLayout(new BoxLayout(jPanelUnPooler, BoxLayout.Y_AXIS));

			for (int i = 0; i < 8; i++){
				JScrollPane jScrollPaneJoueurDuPooler = new JScrollPane(
						new JTable(arrayListJoueurToMatriceString(pool.getPooler(i).getPool()),
								new String[]{"Nom", "Équipe", "Position", "But", "Assist."}));
				jScrollPaneJoueurDuPooler.setPreferredSize(new Dimension(300, 50));

				jPanelUnPooler.add(new JLabel(listePoolerNom[i]));
				jPanelUnPooler.add(Box.createVerticalStrut(10));
				jPanelUnPooler.add(jScrollPaneJoueurDuPooler);

				jPanelAllPooler.add(jPanelUnPooler);
			}

			add(jPanelAllPooler, BorderLayout.EAST);
		}
	}

	public String[][] arrayListJoueurToMatriceString(ArrayList<Joueur> _array){
		int longueurArray = 0;
		if (_array != null) longueurArray = _array.size();
		String[][] matriceStringRetournee = new String[longueurArray][];

		// "Nom", "Équipe", "Position", "But", "Assist."
		for (int i = 0; i < matriceStringRetournee.length; i++){
			for (int j = 0; j < 5; j++){
				switch (j){
					case 0 :
						matriceStringRetournee[i][j] = _array.get(i).getNom();
						break;
					case 1 :
						matriceStringRetournee[i][j] = _array.get(i).getEquipe();
						break;
					case 2 :
						matriceStringRetournee[i][j] = _array.get(i).getPosition();
						break;
					case 3 :
						matriceStringRetournee[i][j] = Integer.toString(_array.get(i).getButs());
						break;
					case 4 :
						matriceStringRetournee[i][j] = Integer.toString(_array.get(i).getAssistances());
						break;
				}
			}
		}
		return matriceStringRetournee;
	}
}
