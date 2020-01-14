package gestionDonnees;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

public class GestionFichier {
	public static Joueur[] obtenirListeJoueur() {
		File fichier = new File(Paths.get("PoolMaker/data/playerData") + "\\18-19.csv");
		BufferedReader br;
		Joueur[] listeJoueur = new Joueur[1000];
		int ligneCount = 0;
		
		try {
			br = new BufferedReader(new FileReader(fichier));
			
			String ligne;
			while ((ligne = br.readLine()) != null) {
				if (ligneCount > 1) {
					String[] infoJoueur = ligne.split(",", 17);
					if (infoJoueur.length > 0)
						listeJoueur[ligneCount] = new Joueur(infoJoueur[0],
																infoJoueur[1],
																infoJoueur[2],
																Integer.parseInt(infoJoueur[4]),
																Integer.parseInt(infoJoueur[5]));
				}
				ligneCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		int iterateur = 0;
		Joueur[] listeJoueurRetournee = new Joueur[ligneCount];

		for (Joueur joueur : listeJoueur){
			if (joueur != null){
				listeJoueurRetournee[iterateur] = joueur;
			}
			iterateur++;
		}

		return listeJoueurRetournee;
	}
}
