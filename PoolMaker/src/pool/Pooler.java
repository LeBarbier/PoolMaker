package pool;

import joueurHockey.Joueur;

import joueur.Joueur;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Pooler {
    private static final AtomicInteger count = new AtomicInteger(-1);
    private final int poolerID;
    private final String prenom;
    private ArrayList<Joueur> listeArrayJoueur;

    public Pooler(String _prenom){
        listeArrayJoueur = new ArrayList<>();
        prenom = _prenom;
        poolerID = count.incrementAndGet();
    }

    public String getPoolerPrenom(){
        return prenom;
    }

    public int getPoolerID(){
        return poolerID;
    }

    public ArrayList<Joueur> getPool(){
        return listeArrayJoueur;
    }

    public void ajoutJoueurAuPool(Joueur _joueur){
        listeArrayJoueur.add(_joueur);
        Pool.getInstance().choisirJoueur(_joueur.getJoueurID());
    }

    public static String[][] arrayListJoueurToStringMatrice(ArrayList<Joueur> _arrayListAModifier){
        String[][] matriceRetournee = new String[_arrayListAModifier.size()][5];
        int row = 0;

        for (Joueur joueur : _arrayListAModifier) {
            if (joueur != null) {
                for (int col = 0; col <= 5; col++){
                    switch(col) {
                        case 0: // Nom
                            matriceRetournee[row][col] = joueur.getNom();
                            break;
                        case 1: // Équipe
                            matriceRetournee[row][col] = joueur.getEquipe();
                            break;
                        case 2: // Position
                            matriceRetournee[row][col] = joueur.getPosition();
                            break;
                        case 3: // Buts
                            matriceRetournee[row][col] = Integer.toString(joueur.getButs());
                            break;
                        case 4: // Assistances
                            matriceRetournee[row][col] = Integer.toString(joueur.getAssistances());
                            break;
                        default:
                    }
                }
            }
            row++;
        }

        return matriceRetournee;
    }
}
