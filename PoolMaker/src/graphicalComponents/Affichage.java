package graphicalComponents;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import gestionDonnees.*;
import joueur.Joueur;

import java.awt.*;
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
	JMenuBar barreMenu;
    JMenu menuParamUtilisateur;
    JMenu menuMenu;
	JMenuItem creerNouveauPool;
	JPanel jPanelAllPooler;
	String[] listeEnteteTableauJoueur;

	/**
	 * Constructeur de la classe d'affichage
	 */
	public Affichage() {
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    champRechercheMot = new JTextField();
	    libelleRecherche = new JLabel("Rechercher un joueur");
	    
	    listeEnteteTableauJoueur = new String[]{"Nom", "Équipe", "Pos.", "But", "Ass."};
		model = new DefaultTableModel(creationMatriceJoueur(), listeEnteteTableauJoueur);

		tableJoueurs = new JTable();
		tableJoueurs.setModel(model);
		for (int i = 0; i < 5; i++){
			if (i == 0) { tableJoueurs.getColumnModel().getColumn(i).setMaxWidth(125); }
			else { tableJoueurs.getColumnModel().getColumn(i).setMaxWidth(50); }
		}

		tableJoueurs.setAutoCreateRowSorter(true);
		tableJoueurs.setDefaultEditor(Object.class, null);
		tableJoueurs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		CreationAffichage();
	}

	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		Affichage ex = new Affichage();

		ex.setVisible(true);
	}

	/**
	 * Création de l'affichage de l'application
	 */
	public void CreationAffichage() {
		JPanel joueurRechercheJPanel = new JPanel();

		setTitle("Pool maker");
		setLayout(new BorderLayout());

		barreMenu = new JMenuBar();
		menuParamUtilisateur = new JMenu("Paramètres Utilisateur");
		menuMenu = new JMenu("Menu");
		creerNouveauPool = new JMenuItem("Créer un nouveau Pool");

		creerNouveauPool.addActionListener(e -> {
			initDataPool();
			refreshAffichagePool();
		});

		menuMenu.add(creerNouveauPool);
		barreMenu.add(menuMenu);
		barreMenu.add(menuParamUtilisateur);

		tableJoueurs.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pool instancePool = Pool.getInstance();
				JPopupMenu popupMenu = new JPopupMenu();
				Joueur joueurSelectionner = instancePool.getJoueur(tableJoueurs.getSelectedRow());

				if (e.getButton() == 3){
					JMenu anItem = new JMenu("Ajouter à ...");

					if (instancePool != null) {
						for (int i = 0; i <= 7; i++){
							final int poolerId = i;
							JMenuItem jMenuItem = new JMenuItem(instancePool.getPooler(poolerId).getPoolerPrenom());

							jMenuItem.addActionListener(menuItemPoolerUnActionListener -> {
								instancePool.getPooler(poolerId).ajoutJoueurAuPool(joueurSelectionner);

								refreshAffichagePool();
								listeJoueursUpdate();
							});
							anItem.add(jMenuItem);
						}
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
		refreshAffichagePool();
		this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

	/**
	 * Mise à jour de la liste des joueurs du pool en cours
	 */
	public void listeJoueursUpdate(){
		tableJoueurs = new JTable(rechercheListeJoueur(champRechercheMot.getText()), listeEnteteTableauJoueur);
		rafraichissementAffichageListeJoueur();
	}

	/**
	 * Création de la matrice des joueurs présentement dans le pool
	 * @return Retourne une matrice en string des joueurs dans le pool
	 */
	public String[][] creationMatriceJoueur(){
		ArrayList<Joueur> listeJoueur = Pool.getInstance().arrayListeJoueur;
	    String[][] matriceInfoJoueur = new String[listeJoueur.size()][5];

	    for(int row = 2; row < listeJoueur.size(); row++){
	    	if (listeJoueur.get(row) != null) {
		    	for (int col = 0; col <= 4; col++) {
		    		switch(col) {
		    		  case 0: // Nom
		    			  matriceInfoJoueur[row-2][col] = listeJoueur.get(row).getNom();
		    			  	break;
		    		  case 1: // Équipe
		    			  matriceInfoJoueur[row-2][col] = listeJoueur.get(row).getEquipe();
		    			  	break;
		    		  case 2: // Position
		    			  matriceInfoJoueur[row-2][col] = listeJoueur.get(row).getPosition();
			    		    break;
		    		  case 3: // Buts
		    			  matriceInfoJoueur[row-2][col] = Integer.toString(listeJoueur.get(row).getButs());
			    		    break;
		    		  case 4: // Assistances
		    			  matriceInfoJoueur[row-2][col] = Integer.toString(listeJoueur.get(row).getAssistances());
			    		    break;
		    		  default:
		    		}
		    	}
	    	}
	    }	    
	    return matriceInfoJoueur;
	}

	/**
	 * Rechercher des joueurs dans la liste des joueurs du pool en cours
	 * @param _stringRecherche Chaîne correspondant au nom du joueur recherché
	 * @return Retourne une matrice en string de la liste des joueurs qui correspondent à la recherche fournit
	 */
	public String[][] rechercheListeJoueur(String _stringRecherche){
		Pool poolInstance = Pool.getInstance();
		String[][] listeJoueurFiltre = new String[poolInstance.arrayListeJoueur.size()][];
		int iterateur = 0;

		for (Joueur joueurFiltre : poolInstance.arrayListeJoueur){
			if (joueurFiltre != null && joueurFiltre.getChoisit() == false)
			{
				if (joueurFiltre.getNom().toLowerCase().contains(_stringRecherche)) {
					listeJoueurFiltre[iterateur] = joueurToStringTableau(joueurFiltre);
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

	/**
	 * Initialisation des données utiles pour le pool
	 */
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
			ArrayList<Pooler> listePoolerCreation = new ArrayList<>();
			listePoolerCreation.add(new Pooler(poolerUnTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerDeuxTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerTroisTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerQuatreTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerCinqTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerSixTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerSeptTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerHuitTextField.getText()));

			Pool.getInstance().initialiserPool(listePoolerCreation);
		}
	}

	/**
	 * Mise à jour de l'affiche du pool
	 */
	public void refreshAffichagePool(){
		Pool instancePool = Pool.getInstance();
		if (jPanelAllPooler != null) remove(jPanelAllPooler);
		jPanelAllPooler = new JPanel();
		ArrayList<JPanel> arrayListeJPanel = new ArrayList<>();

		if (instancePool == null || instancePool.isPoolEmpty()) {
			repaint();
			pack();
			return;
		}

		for (int i = 0; i < 8; i++){
			JPanel jPanelUnPooler = new JPanel();
			JScrollPane jScrollPaneJoueurDuPooler = new JScrollPane(
					new JTable(Pooler.arrayListJoueurToStringMatrice(instancePool.getPooler(i).getPool()),
							new String[]{"Nom", "Équipe", "Position", "But", "Assist."}));
			jScrollPaneJoueurDuPooler.setPreferredSize(new Dimension(500, 50));

			jPanelUnPooler.add(new JLabel(instancePool.getPooler(i).getPoolerPrenom()));
			jPanelUnPooler.add(Box.createVerticalStrut(10));
			jPanelUnPooler.add(jScrollPaneJoueurDuPooler);

			arrayListeJPanel.add(jPanelUnPooler);
		}

		for (JPanel jPanel : arrayListeJPanel) {
			jPanelAllPooler.add(jPanel);
		}
		jPanelAllPooler.setLayout(new BoxLayout(jPanelAllPooler, BoxLayout.Y_AXIS));

		add(jPanelAllPooler, BorderLayout.EAST);
		validate();
		repaint();
		pack();
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

	/**
	 * Permet de convertir les données d'un joueur en tableau pour l'afficher
 	 * @param _joueur Joueur que l'on désire convertir
	 * @return Retourne les informations du joueur sous forme de table string
	 */
	public String[] joueurToStringTableau(Joueur _joueur){
		String[] matriceRetourne = new String[5];

		matriceRetourne[0] = _joueur.getNom();
		matriceRetourne[1] = _joueur.getEquipe();
		matriceRetourne[2] = _joueur.getPosition();
		matriceRetourne[3] = Integer.toString(_joueur.getButs());
		matriceRetourne[4] = Integer.toString(_joueur.getAssistances());

		return matriceRetourne;
	}

	/**
	 * Mise à jour de l'affiche de la liste des joueurs
	 */
	public void rafraichissementAffichageListeJoueur(){
		tableJoueurs.setAutoCreateRowSorter(true);
		tableJoueurs.setDefaultEditor(Object.class, null);
		tableJoueurs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		remove(listeJoueurScroll);
		listeJoueurScroll = new JScrollPane(tableJoueurs);
		add(listeJoueurScroll, BorderLayout.CENTER);
		validate();
	}
}
