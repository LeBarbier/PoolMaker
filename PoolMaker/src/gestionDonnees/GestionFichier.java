package gestionDonnees;

import joueur.Gardien;
import joueur.Joueur;
import joueur.Patineur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GestionFichier {
	public static ArrayList<Joueur> obtenirListeJoueurDeData() {
		File fichier = new File(Paths.get("data/playerData") + "\\18-19.csv");
		System.out.println(fichier.getAbsolutePath());
		BufferedReader br;
		ArrayList<Joueur> listeJoueurRetournee = new ArrayList<>();
		int ligneCount = 0;
		
		try {
			br = new BufferedReader(new FileReader(fichier));
			
			String ligne;
			while ((ligne = br.readLine()) != null) {
				if (ligneCount > 1) {
					String[] infoJoueur = ligne.split(",", 17);
					if (infoJoueur.length > 0)
						if (infoJoueur[2] == "g") {
							listeJoueurRetournee.add(new Gardien(infoJoueur[0],
									infoJoueur[1],
									infoJoueur[2],
									Integer.parseInt(infoJoueur[4]),
									Integer.parseInt(infoJoueur[5])));
						} else {
							listeJoueurRetournee.add(new Patineur(infoJoueur[0],
									infoJoueur[1],
									infoJoueur[2],
									Integer.parseInt(infoJoueur[4]),
									Integer.parseInt(infoJoueur[5])));
						}
				}
				ligneCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listeJoueurRetournee;
	}
}
