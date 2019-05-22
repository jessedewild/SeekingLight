package com.jessedewild.seekinglight.utils;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class JSONHelper {

    private Context context;

    public JSONHelper(Context context) {
        this.context = context;
    }

    public String readLevel(int levelNum) {
        String file = "maps/level" + levelNum + "/level" + levelNum + ".json";
        String json = null;
        try {
            InputStream is = context.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    public String readJSON(String fileName) {
        FileReader reader = null;
        File file = new File(context.getFilesDir() + "/" + fileName);
        try {
            reader = new FileReader(file);
            Gson gson = new Gson();
            Constants constants = gson.fromJson(reader, Constants.class);

            return constants.toString();
        } catch (IOException e) {
//            Toast.makeText(context, "File Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
//                Toast.makeText(context, "File Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    public void save() {
        Save save = new Save();
        String jsonString = new Gson().toJson(save, Save.class);
        FileOutputStream outputStream = null;
        File file = new File(context.getFilesDir() + "/save.json");
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(jsonString.getBytes());
        } catch (Exception e) {
//            Toast.makeText(context, "File Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                outputStream.close();
            } catch (NullPointerException | IOException e) {
//                Toast.makeText(context, "File Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void load() {
        FileReader reader = null;
        File file = new File(context.getFilesDir() + "/save.json");
        try {
            reader = new FileReader(file);
            Gson gson = new Gson();
            Save save = gson.fromJson(reader, Save.class);

            // Load Save into Constants
            Constants.coins = save.getCoins();
            Constants.level = save.getLevel();
        } catch (Exception e) {
//            Toast.makeText(context, "File Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                reader.close();
            } catch (NullPointerException | IOException e) {
//                Toast.makeText(context, "File Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
