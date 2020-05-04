package joueur;

import java.util.concurrent.atomic.AtomicInteger;

public class Gardien implements Joueur {
    private static final AtomicInteger count = new AtomicInteger(-1);
    private final int joueurID;
    private final String nom;
    private final String equipe;
    private final String position;
    private final int buts;
    private final int assistances;
    private boolean choisit;

    public Gardien(String _nom,
                  String _equipe,
                  String _position,
                  int _buts,
                  int _assistances) {
        nom = _nom;
        equipe = _equipe;
        position = _position;
        buts = _buts;
        assistances = _assistances;
        joueurID = count.incrementAndGet();
        choisit = false;
    }

    public int getButs() {
        return buts;
    }
    public int getAssistances() {
        return assistances;
    }
    public String getNom() {
        return nom;
    }
    public String getEquipe() {
        return equipe;
    }
    public String getPosition() {
        return position;
    }
    public int getJoueurID() {
        return joueurID;
    }
    public boolean getChoisit() {
        return choisit;
    }
    public void setChoisit(boolean _choisit) {
        choisit = _choisit;
    }
}