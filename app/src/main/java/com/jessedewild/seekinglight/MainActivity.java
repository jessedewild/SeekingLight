package com.jessedewild.seekinglight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jessedewild.seekinglight.entities.characters.Seeker;
import com.jessedewild.seekinglight.lib.GameView;
import com.jessedewild.seekinglight.lib.GameModel;
import com.jessedewild.seekinglight.game.Game;
import com.jessedewild.seekinglight.game.Activity;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private GameView gameCanvas;
    private GameModel game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameCanvas = findViewById(R.id.spaceShooter);
        game = new Game();
        ((Game) game).setJson(readJSONFile());
        ((Game) game).setAutoScroll(true);
        ((Game) game).setShowCharactersOnMap(false);

        findViewById(R.id.spaceShooterText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameCanvas.setGame(game);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameCanvas.setGame(null);
    }

    private String readJSONFile() {
        String json = null;
        try {
            InputStream is = getAssets().open("maps/level1/level1.json");
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
}
