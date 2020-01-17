package gestionDonnees;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GestionFichier {
	public static ArrayList<Joueur> obtenirListeJoueurDeData() {
		File fichier = new File(Paths.get("PoolMaker/data/playerData") + "\\18-19.csv");
		BufferedReader br;
		ArrayList<Joueur> listeJoueurRetournee = new ArrayList<Joueur>();
		int ligneCount = 0;
		
		try {
			br = new BufferedReader(new FileReader(fichier));
			
			String ligne;
			while ((ligne = br.readLine()) != null) {
				if (ligneCount > 1) {
					String[] infoJoueur = ligne.split(",", 17);
					if (infoJoueur.length > 0)
						listeJoueurRetournee.add(new Joueur(infoJoueur[0],
															infoJoueur[1],
															infoJoueur[2],
															Integer.parseInt(infoJoueur[4]),
															Integer.parseInt(infoJoueur[5])));
				}
				ligneCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listeJoueurRetournee;
	}
}
