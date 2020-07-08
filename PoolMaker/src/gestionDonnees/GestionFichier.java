package gestionDonnees;

import joueurHockey.Gardien;
import joueurHockey.Joueur;
import joueurHockey.Patineur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GestionFichier {
	public static ArrayList<joueurHockey.Joueur> obtenirListeJoueurDeData(String fichierSelectionne) {
		File fichier = new File(Paths.get("data/playerData") + "\\" + fichierSelectionne);
		BufferedReader br;
		ArrayList<Joueur> listeJoueurRetournee = new ArrayList<Joueur>();
		int ligneCount = 0;
		
		try {
			br = new BufferedReader(new FileReader(fichier));
			
			String ligne;
			while ((ligne = br.readLine()) != null) {
				if (ligneCount > 0) {
					String[] infoJoueur = ligne.split(",", 17);
					if (infoJoueur.length > 0)
						if (infoJoueur[2].equalsIgnoreCase("G"))
							listeJoueurRetournee.add(new Gardien(infoJoueur[0], 						// _nom
																	infoJoueur[1], 						// _equipe
																	infoJoueur[2], 						// _position
																	Integer.parseInt(infoJoueur[4]), 	// _buts
																	Integer.parseInt(infoJoueur[5]))); 	// _assistances
						else
							listeJoueurRetournee.add(new Patineur(infoJoueur[0], 						// _nom
																	infoJoueur[1], 						// _equipe
																	infoJoueur[2], 						// _position
																	Integer.parseInt(infoJoueur[4]), 	// _buts
																	Integer.parseInt(infoJoueur[5]))); 	// _assistances
				}
				ligneCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listeJoueurRetournee;
	}

	public static String[] obtenirListeFichierStats() {
		File fichiers = new File(Paths.get("data/playerData").toString());

		return fichiers.list();
	}
}
