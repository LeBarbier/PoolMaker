package gestionDonnees;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Pooler {
    private static final AtomicInteger count = new AtomicInteger(-1);
    private final int poolerID;
    private final String prenom;
    private ArrayList<Joueur> listePool;

    public Pooler(String _prenom){
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
        return listePool;
    }

    public void ajoutJoueurAuPool(Joueur _joueur){
        listePool.add(_joueur);
    }
}
