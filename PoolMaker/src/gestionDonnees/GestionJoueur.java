package gestionDonnees;

import joueurHockey.Joueur;

import java.util.ArrayList;

public class GestionJoueur {

    public static Joueur findJoueurByName(String _nom, ArrayList<Joueur> _listeJoueur){
        for (Joueur joueur : _listeJoueur) {
            if (joueur != null && joueur.getNom().contains(_nom)){
                return joueur;
            }
        }
        return null;
    }
}
