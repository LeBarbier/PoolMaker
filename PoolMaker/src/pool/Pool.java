package gestionDonnees;

import joueur.Joueur;

import java.util.ArrayList;

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
     * @return Retourne le pool unique pr�sentement en cours
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
     * @param _pooler Le pooler que l'on d�sire ajouter � la liste
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
     * Obtenir un pooler que l'on recherche � partir de son ID
     * @param _idPooler ID du pooler que l'on recherche
     * @return Retourne le pooler retrouv�
     */
    public Pooler getPooler(int _idPooler){
        if (isPoolEmpty()) return null;
        if (arrayListePooler.size() - 1 < _idPooler) return null;

        return arrayListePooler.get(_idPooler);
    }

    /**
     * V�rifier si le pool contient aucun participant
     * @return
     */
    public boolean isPoolEmpty(){
        return arrayListePooler.isEmpty();
    }

    /**
     * Obtenir les informations d'un joueur � partir de son ID
     * @param _idJoueur Id du joueur que l'on recherche
     * @return Retourne les informations du joueur retrouv�es
     */
    public Joueur getJoueur(int _idJoueur){
        return arrayListeJoueur.get(_idJoueur);
    }

    /**
     * Action qu'un pooler commet pour s�lectionner un joueur non s�lectionn�
     * @param _idJoueur Id du joueur que le pooler d�sire s�lectionner
     * @return Retourne vrai si le joueur � �t� associ� au pooler, faux sinon
     */
    public boolean choisirJoueur(int _idJoueur) {
        if (arrayListeJoueur.get(_idJoueur).getChoisit() == false)
            arrayListeJoueur.get(_idJoueur).setChoisit(true);
        else
            return false;

        return true;
    }
}
