package pool;

import java.util.ArrayList;
import gestionDonnees.GestionFichier;
import joueurHockey.Joueur;

public class Pool {
    private static Pool single_instance = null;
    public ArrayList<Pooler> arrayListePooler;
    public ArrayList<Joueur> arrayListeJoueur;

    /**
     * Constructeur de l'instance Singleton de Pool
     */
    private Pool(){
        arrayListePooler = new ArrayList<>();
        arrayListeJoueur = GestionFichier.obtenirListeJoueurDeData();
    }

    /**
     * Obtenir l'instance unique du Pool
     * @return Retourne le pool unique présentement en cours
     */
    public static Pool getInstance(){
        if (single_instance == null){
            single_instance = new Pool();
        }
        return single_instance;
    }

    /**
     * Initialiser le pool si aucun n'est initialiser encore
     * @param _arrayListePooler Liste des poolers qui participeront au pool
     */
    public void initialiserPool(ArrayList<Pooler> _arrayListePooler){
        for (Pooler pooler : _arrayListePooler) {
            if (pooler != null) { ajouterPooler(pooler); }
        }
    }

    /**
     * Ajouter un pooler au pool en cours
     * @param _pooler Le pooler que l'on désire ajouter à la liste
     */
    public void ajouterPooler(Pooler _pooler){
        arrayListePooler.add(_pooler);
    }

    /**
     * Obtenir la liste des poolers qui participent au pool
     * @return Retourne la liste des participants du pool
     */
    public String[] getListeNomPooler(){
        String[] listeNomPooler = new String[8];

        for (int i = 0; i < arrayListePooler.size(); i++){
            Pooler pooler = arrayListePooler.get(i);
            if (pooler != null){
                listeNomPooler[i] = pooler.getPoolerPrenom();
            }
        }

        return listeNomPooler;
    }

    /**
     * Obtenir un pooler que l'on recherche à partir de son ID
     * @param _idPooler ID du pooler que l'on recherche
     * @return Retourne le pooler retrouvé
     */
    public Pooler getPooler(int _idPooler){
        if (isPoolEmpty()) return null;
        if (arrayListePooler.size() - 1 < _idPooler) return null;

        return arrayListePooler.get(_idPooler);
    }

    /**
     * Vérifier si le pool contient aucun participant
     * @return
     */
    public boolean isPoolEmpty(){
        return arrayListePooler.isEmpty();
    }

    /**
     * Permet de retrouver un joueur dans la liste de joueur selon son nom
     * @param _nom Nom du joueur que l'on désire retrouver
     * @return Retourne le joueur retrouvé ou null sinon
     */
    public Joueur findJoueurByName(String _nom){
        for (Joueur joueur : arrayListeJoueur) {
            if (joueur != null && joueur.getNom().contains(_nom)){
                return joueur;
            }
        }
        return null;
    }

    /**
     * Obtenir les informations d'un joueur à partir de son ID
     * @param _idJoueur Id du joueur que l'on recherche
     * @return Retourne les informations du joueur retrouvées, sinon null
     */
    public Joueur getJoueur(int _idJoueur) {
        for (Joueur joueur : arrayListeJoueur) {
            if (joueur.getJoueurID() == _idJoueur)
                return joueur;
        }
        return null;
    }

    /**
     * Permet d'obtenir l'id du joueur dans la liste arrayListeJoueur
     * @param _idJoueur Numéro d'id du joueur
     * @return Retourne le position du joueur dans la liste arrayListeJoueur
     */
    public int getJoueurListeId(int _idJoueur){
        int indiceRetour = 0;
        for (Joueur joueur : arrayListeJoueur) {
            if (joueur.getJoueurID() == _idJoueur)
                return indiceRetour;
            indiceRetour++;
        }
        return indiceRetour;
    }

    /**
     * Action qu'un pooler commet pour sélectionner un joueur non sélectionné
     * @param _idJoueur Id du joueur que le pooler désire sélectionner
     * @return Retourne vrai si le joueur à été associé au pooler, faux sinon
     */
    public boolean choisirJoueur(int _idJoueur) {
        if (!arrayListeJoueur.get(getJoueurListeId(_idJoueur)).getChoisit())
            arrayListeJoueur.get(getJoueurListeId(_idJoueur)).setChoisit(true);
        else
            return false;

        return true;
    }
}
