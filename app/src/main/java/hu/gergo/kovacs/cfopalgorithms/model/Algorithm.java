package hu.gergo.kovacs.cfopalgorithms.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Algorithm implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("algs")
    private ArrayList<String> algList;

    public String getName() {
        return name;
    }

    public ArrayList<String> getAlgList() {
        return algList;
    }

    public Algorithm(String name, ArrayList<String> algList) {
        this.name = name;
        this.algList = algList;
    }





}
