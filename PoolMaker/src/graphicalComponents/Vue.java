package graphicalComponents;
import gestionDonnees.GestionFichier;
import joueurHockey.Joueur;
import pool.Pool;
import pool.Pooler;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

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
	JMenuBar barreMenuPrincipal;
	JMenu menuSelectionAnnee;
    JMenu menuParamUtilisateur;
    JMenu menuMenu;
	JMenuItem creerNouveauPool;
	ArrayList<JMenuItem> listeMenuAnnee;
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
			if (i == 0) { tableJoueurs.getColumnModel().getColumn(i).setMinWidth(125); }
			else { tableJoueurs.getColumnModel().getColumn(i).setMinWidth(50); }
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

		barreMenuPrincipal = new JMenuBar();
		menuParamUtilisateur = new JMenu("Paramètres Utilisateur");
		menuMenu = new JMenu("Menu");
		creerNouveauPool = new JMenuItem("Créer un nouveau Pool");
		menuSelectionAnnee = new JMenu("Année...");
		listeMenuAnnee = new ArrayList<>();
		listeMenuAnnee.add(new JMenuItem("Moyenne"));
		for (String fichier : GestionFichier.obtenirListeFichierStats()){
			JMenuItem menuItem = new JMenuItem("20" + fichier.substring(0, fichier.length() - 4));
			menuItem.addActionListener(e -> {
				pool.arrayListeJoueur = GestionFichier.obtenirListeJoueurDeData(fichier);
				listeJoueursUpdate();
			});
			listeMenuAnnee.add(menuItem);
		}

		creerNouveauPool.addActionListener(e -> {
			initDataPool();
			refreshAffichagePool();
		});

		menuMenu.add(creerNouveauPool);
		menuMenu.add(menuSelectionAnnee);
		for (JMenuItem menuAnnee : listeMenuAnnee){
			menuSelectionAnnee.add(menuAnnee);
		}
		barreMenuPrincipal.add(menuMenu);
		barreMenuPrincipal.add(menuParamUtilisateur);


		ajoutListenerRightClickListeJoueur();

		champRechercheMot.setSize(new Dimension(175, 25));
		listeJoueurScroll = new JScrollPane(tableJoueurs);
		tableJoueurs.setFillsViewportHeight(true);
		joueurRechercheJPanel.add(libelleRecherche);
		joueurRechercheJPanel.add(champRechercheMot);

		setJMenuBar(barreMenuPrincipal);
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

		setMinimumSize(new Dimension(500, 650));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		refreshAffichagePool();
		this.setLocationRelativeTo(null);
    }

    private void listeJoueursUpdate(){
		tableJoueurs = new JTable(rechercheListeJoueur(champRechercheMot.getText()),
				new String[]{"Nom", "Équipe", "Pos.", "But", "Ass."});
		rafraichissementAffichageListeJoueur();
		ajoutListenerRightClickListeJoueur();
	}

	private String[][] creationMatriceJoueur(){
		ArrayList<Joueur> listeJoueur = Pool.getInstance().arrayListeJoueur;
	    String[][] matriceInfoJoueur = new String[listeJoueur.size()][5];

	    for(int row = 2; row < listeJoueur.size(); row++){
	    	Joueur joueurSelectionne = listeJoueur.get(row);
			if (joueurSelectionne != null && !joueurSelectionne.getChoisit()) {
		    	for (int col = 0; col <= 4; col++) {
		    		switch(col) {
		    		  case 0: // Nom
		    			  matriceInfoJoueur[row-2][col] = joueurSelectionne.getNom();
		    			  	break;
		    		  case 1: // Équipe
		    			  matriceInfoJoueur[row-2][col] = joueurSelectionne.getEquipe();
		    			  	break;
		    		  case 2: // Position
		    			  matriceInfoJoueur[row-2][col] = joueurSelectionne.getPosition();
			    		    break;
		    		  case 3: // Buts
		    			  matriceInfoJoueur[row-2][col] = String.format("%02d", (joueurSelectionne.getButs()));
			    		    break;
		    		  case 4: // Assistances
		    			  matriceInfoJoueur[row-2][col] = String.format("%02d", (joueurSelectionne.getAssistances()));
			    		    break;
		    		  default:
		    		}
		    	}
	    	}
	    }	    
	    return matriceInfoJoueur;
	}

	private String[][] rechercheListeJoueur(String charRecherche){
		String[][] matriceJoueur = creationMatriceJoueur();
		String[][] listeJoueurFiltre = new String[matriceJoueur.length][];
		int iterateur = 0;

		for (String[] joueurFiltre : matriceJoueur){
			if (joueurFiltre[0] != null)
			{
				if (joueurFiltre[0].toLowerCase().contains(charRecherche.toLowerCase())) {
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
		myPanel.add(Box.createVerticalStrut(10));

		myPanel.add(new JLabel("Pooler #2"));
		myPanel.add(poolerDeuxTextField);
		myPanel.add(Box.createVerticalStrut(10));

		myPanel.add(new JLabel("Pooler #3"));
		myPanel.add(poolerTroisTextField);
		myPanel.add(Box.createVerticalStrut(10));

		myPanel.add(new JLabel("Pooler #4"));
		myPanel.add(poolerQuatreTextField);
		myPanel.add(Box.createVerticalStrut(10));

		myPanel.add(new JLabel("Pooler #5"));
		myPanel.add(poolerCinqTextField);
		myPanel.add(Box.createVerticalStrut(10));

		myPanel.add(new JLabel("Pooler #6"));
		myPanel.add(poolerSixTextField);
		myPanel.add(Box.createVerticalStrut(10));

		myPanel.add(new JLabel("Pooler #7"));
		myPanel.add(poolerSeptTextField);
		myPanel.add(Box.createVerticalStrut(10));

		myPanel.add(new JLabel("Pooler #8"));
		myPanel.add(poolerHuitTextField);
		myPanel.add(Box.createVerticalStrut(10));

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Entrée le nom de chacun des poolers", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null);
		if (result == JOptionPane.OK_OPTION) {
			ArrayList<Pooler> listePoolerCreation = Pool.getInstance().arrayListePooler;
			listePoolerCreation.add(new Pooler(poolerUnTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerDeuxTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerTroisTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerQuatreTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerCinqTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerSixTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerSeptTextField.getText()));
			listePoolerCreation.add(new Pooler(poolerHuitTextField.getText()));

			pool = Pool.getInstance();
		}
	}

	public void refreshAffichagePool(){
		if (jPanelAllPooler != null) remove(jPanelAllPooler);
		jPanelAllPooler = new JPanel();
		ArrayList<JPanel> arrayListeJPanel = new ArrayList<>();
		for (JPanel jPanel : arrayListeJPanel) jPanelAllPooler.remove(jPanel);

		if (pool == null) {
			pool = Pool.getInstance();
			repaint();
			return;
		}

		JTable tableJoueurPooler;
		String[] listePoolerNom = pool.getListeNomPooler();

		for (int i = 0; i < pool.arrayListePooler.size(); i++){
			JPanel jPanelUnPooler = new JPanel();

			tableJoueurPooler = new JTable(Pooler.arrayListJoueurToStringMatrice(pool.getPooler(i).getPool()),
					new String[]{"Nom", "Équipe", "Pos.", "But", "Ass."});

			for (int j = 0; j < 5; j++){
				if (j == 0) { tableJoueurPooler.getColumnModel().getColumn(j).setMinWidth(125); }
				else { tableJoueurPooler.getColumnModel().getColumn(j).setMinWidth(50); }
			}

			JScrollPane jScrollPaneJoueurDuPooler = new JScrollPane(tableJoueurPooler);
			jPanelUnPooler.add(new JLabel(listePoolerNom[i]));
			jPanelUnPooler.add(Box.createVerticalStrut(10));
			jPanelUnPooler.add(jScrollPaneJoueurDuPooler);
			arrayListeJPanel.add(jPanelUnPooler);
		}

		for (JPanel jPanel : arrayListeJPanel) {
			jPanelAllPooler.add(jPanel);
		}
		jPanelAllPooler.setLayout(new BoxLayout(jPanelAllPooler, BoxLayout.Y_AXIS));
		jPanelAllPooler.setMinimumSize(new Dimension(475, 500));

		JSplitPane splitPanel = new JSplitPane();
		splitPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPanel.add(jPanelAllPooler, JSplitPane.RIGHT);
		splitPanel.add(listeJoueurScroll, JSplitPane.LEFT);

		add(splitPanel, BorderLayout.CENTER);
		jPanelAllPooler.validate();
		jPanelAllPooler.repaint();

		validate();
		repaint();

		setMinimumSize(new Dimension(950, 650));
	}

	public void rafraichissementAffichageListeJoueur(){
		for (int i = 0; i < 5; i++){
			if (i == 0) { tableJoueurs.getColumnModel().getColumn(i).setMinWidth(125); }
			else { tableJoueurs.getColumnModel().getColumn(i).setMinWidth(50); }
		}
		tableJoueurs.setAutoCreateRowSorter(true);
		tableJoueurs.setDefaultEditor(Object.class, null);
		tableJoueurs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		listeJoueurScroll.setViewportView(tableJoueurs);
		listeJoueurScroll.revalidate();
	}

	private void ajoutListenerRightClickListeJoueur(){
		tableJoueurs.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPopupMenu popupMenu = new JPopupMenu();
				Joueur joueurSelectionner = Pool.getInstance().findJoueurByName(tableJoueurs.getValueAt(tableJoueurs.getSelectedRow(),0).toString());

				if (e.getButton() == 3){
					JMenu anItem = new JMenu("Ajouter à ...");

					if (pool != null) {
						for (int i = 0; i <= 7; i++){
							final int poolerId = i;
							JMenuItem jMenuItem = new JMenuItem(pool.getPooler(poolerId).getPoolerPrenom());

							jMenuItem.addActionListener(menuItemPoolerUnActionListener -> {
								pool.getPooler(poolerId).ajoutJoueurAuPool(joueurSelectionner);
								pool.choisirJoueur(joueurSelectionner.getJoueurID());
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
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});
	}
}
