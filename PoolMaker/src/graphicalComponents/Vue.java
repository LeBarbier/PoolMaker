package graphicalComponents;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import gestionDonnees.*;
import joueurHockey.Joueur;
import pool.Pool;
import pool.Pooler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Vue extends JFrame{
    JTextField champRechercheMot;
    JScrollPane listeJoueurScroll;
    JLabel libelleRecherche;
    JTable tableJoueurs;
	DefaultTableModel model;
	JMenuBar barreMenu;
    JMenu menuParamUtilisateur;
    JMenu menuMenu;
	JMenuItem creerNouveauPool;
	Pool pool;
	JPanel jPanelAllPooler;

	public Vue() {
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    champRechercheMot = new JTextField();
	    libelleRecherche = new JLabel("Rechercher un joueur");
	    
	    String[] listeColonne = new String[]{"Nom", "Équipe", "Pos.", "But", "Ass."};
		model = new DefaultTableModel(creationMatriceJoueur(), listeColonne);

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
				JPopupMenu popupMenu = new JPopupMenu();
				Joueur joueurSelectionner = GestionJoueur.findJoueurByName(
												tableJoueurs.getValueAt(tableJoueurs.getSelectedRow(),
												0).toString(), GestionFichier.obtenirListeJoueurDeData());

				if (e.getButton() == 3){
					JMenu anItem = new JMenu("Ajouter à ...");

					if (pool != null) {
						for (int i = 0; i <= 7; i++){
							final int poolerId = i;
							JMenuItem jMenuItem = new JMenuItem(pool.getPooler(poolerId).getPoolerPrenom());

							jMenuItem.addActionListener(menuItemPoolerUnActionListener -> {
								pool.getPooler(poolerId).ajoutJoueurAuPool(joueurSelectionner);
								String[][] stringMatriceJoueurPooler = Pooler.arrayListJoueurToStringMatrice(pool.getPooler(0).getPool());

								refreshAffichagePool();
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

    public void listeJoueursUpdate(){
		tableJoueurs = new JTable(rechercheListeJoueur(champRechercheMot.getText()),
				new String[]{"Nom", "Équipe", "Pos.", "But", "Ass."});
		rafraichissementAffichageListeJoueur();
	}

	public String[][] creationMatriceJoueur(){
		ArrayList<Joueur> listeJoueur = GestionFichier.obtenirListeJoueurDeData();
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

	public String[][] rechercheListeJoueur(String charRecherche){
		String[][] matriceJoueur = creationMatriceJoueur();
		String[][] listeJoueurFiltre = new String[matriceJoueur.length][];
		int iterateur = 0;

		for (String[] joueurFiltre : matriceJoueur){
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
			ArrayList<Pooler> listePoolerCreation = new ArrayList<>();
			listePoolerCreation.add(new Pooler(poolerUnTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerDeuxTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerTroisTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerQuatreTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerCinqTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerSixTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerSeptTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerHuitTextField.getText()));

			pool = new Pool(listePoolerCreation);
		}
	}

	public void refreshAffichagePool(){
		if (jPanelAllPooler != null) remove(jPanelAllPooler);
		jPanelAllPooler = new JPanel();
		ArrayList<JPanel> arrayListeJPanel = new ArrayList<>();
		if (arrayListeJPanel.size() > 0) {
			for (JPanel jPanel : arrayListeJPanel) {
				jPanelAllPooler.remove(jPanel);
			}
		}

		if (pool == null) {
			repaint();
			pack();
			return;
		}

		JTable tableJoueurPooler;
		String[] listePoolerNom = pool.getListeNomPooler();

		for (int i = 0; i < 8; i++){
			JPanel jPanelUnPooler = new JPanel();

			tableJoueurPooler = new JTable(Pooler.arrayListJoueurToStringMatrice(pool.getPooler(i).getPool()),
					new String[]{"Nom", "Équipe", "Pos.", "But", "Ass."});

			for (int j = 0; j < 5; j++){
				if (j == 0) { tableJoueurPooler.getColumnModel().getColumn(j).setMaxWidth(125); }
				else { tableJoueurPooler.getColumnModel().getColumn(j).setMaxWidth(50); }
			}

			JScrollPane jScrollPaneJoueurDuPooler = new JScrollPane(tableJoueurPooler);
			jScrollPaneJoueurDuPooler.setPreferredSize(new Dimension(250, 50));

			jPanelUnPooler.add(new JLabel(listePoolerNom[i]));
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

		// "Nom", "Équipe", "Pos.", "But", "Ass."
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

	public void rafraichissementAffichageListeJoueur(){
		tableJoueurs.setAutoCreateRowSorter(true);
		tableJoueurs.setDefaultEditor(Object.class, null);
		tableJoueurs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		remove(listeJoueurScroll);
		listeJoueurScroll = new JScrollPane(tableJoueurs);
		add(listeJoueurScroll, BorderLayout.CENTER);
		listeJoueurScroll.setPreferredSize(new Dimension(250, 500));
		validate();
	}
}
