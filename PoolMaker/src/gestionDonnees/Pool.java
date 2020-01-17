package gestionDonnees;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Pool {
    ArrayList<Pooler> arrayListePooler;

    public Pool(ArrayList<Pooler> _arrayListePooler){
        arrayListePooler = new ArrayList<>();

        for (Pooler pooler : _arrayListePooler) {
            if (pooler != null) { ajouterPooler(pooler); }
        }
    }

    public void ajouterPooler(Pooler _pooler){
        arrayListePooler.add(_pooler);
    }

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

    public Pooler getPooler(int _idPooler){
        return arrayListePooler.get(_idPooler);
    }
}
