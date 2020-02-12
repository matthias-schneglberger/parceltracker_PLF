package at.htlgkr.parceltracker;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationParser {
    static final String TAG = "ConfigurationParser";


    public static List<Category> getCategories(InputStream is) throws IOException {
        List<Category> categories = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

        String content = bufferedReader.lines().reduce((a,b) -> a+b).get();

        bufferedReader.close();
        Log.d(TAG, "getCategories: content: " + content);
        try {
            JSONObject json = new JSONObject(content);

            int categoryCount = json.getInt("categoryCount");

            for(int i = 1; i < categoryCount+1; i++){
                categories.add(new Category(i, json.getString("category" + i)));
                Log.d(TAG, "getCategories: current Cat.: " + json.getString("category" + i));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        // implement this method
        return categories;
    }

    public static List<Category> getCategoriesReal(InputStream is) throws IOException {
        List<Category> categories = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

        String content = bufferedReader.lines().reduce((a,b) -> a+b).get();

        bufferedReader.close();
        Log.d(TAG, "getCategories: content: " + content);


        content = content.replaceAll("\n", "");
        String count = content.split("\"categoryCount\":")[1].trim().split("\"")[1];



        return categories;
    }
}
