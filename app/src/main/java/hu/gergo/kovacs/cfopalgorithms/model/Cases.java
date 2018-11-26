package hu.gergo.kovacs.cfopalgorithms.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cases implements Serializable {
    @SerializedName("cases")
    public ArrayList<Algorithm> algList;

    public Cases(ArrayList<Algorithm> algList) {
        this.algList = algList;
    }

    public ArrayList<String> getTitles() {
        Iterator itr = algList.iterator();
        ArrayList<String> titles = new ArrayList<String>();

        while (itr.hasNext()) {
            Algorithm alg = (Algorithm) itr.next();
            titles.add(alg.getName());
        }

        return titles;
    }

    public ArrayList<String> getFirstAlg() {
        Iterator itr = algList.iterator();
        ArrayList<String> firstAlg = new ArrayList<String>();
        List<String> tempList;

        while (itr.hasNext()) {
            Algorithm alg = (Algorithm) itr.next();
            tempList = alg.getAlgList();
            firstAlg.add(tempList.get(0));
        }

        return firstAlg;
    }

    public ArrayList<String> getAlgorithmsOfCase(int caseNumber){
        Algorithm algorithm = algList.get(caseNumber);
        return algorithm.getAlgList();
    }

    public String getSingleAlgOfCase(int caseNumber, int algNumber){
        Algorithm algorithm = algList.get(caseNumber);
        return algorithm.getAlgList().get(algNumber);
    }


}
