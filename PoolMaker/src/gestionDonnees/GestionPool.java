package gestionDonnees;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GestionPool {
    ArrayList<Pooler> arrayListePooler;

    public GestionPool(ArrayList<Pooler> _arrayListePooler){
        arrayListePooler = _arrayListePooler;
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
