package hu.gergo.kovacs.cfopalgorithms.util;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import hu.gergo.kovacs.cfopalgorithms.model.Cases;

public class JSONManager {
    public static String loadDataFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    return json;

    }

    public static Cases GSONDeserialzer(Context context, String fileName){
        String json = loadDataFromAsset(context, fileName);
        Gson gson = new Gson();
        Cases cases = gson.fromJson(json, Cases.class);
        return cases;

    }
}
