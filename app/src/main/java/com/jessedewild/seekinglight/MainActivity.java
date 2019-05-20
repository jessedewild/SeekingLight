package com.jessedewild.seekinglight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jessedewild.seekinglight.compounds.CoinsView;
import com.jessedewild.seekinglight.lib.GameView;
import com.jessedewild.seekinglight.lib.GameModel;
import com.jessedewild.seekinglight.game.Game;
import com.jessedewild.seekinglight.game.Activity;
import com.jessedewild.seekinglight.utils.Constants;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private GameView gameCanvas;
    private GameModel game;
    private CoinsView coinsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameCanvas = findViewById(R.id.mainGameView);
        game = new Game();
        ((Game) game).setJson(readJSONFile());
        ((Game) game).setAutoScroll(true);
        ((Game) game).setShowCharactersOnMap(false);

        // Start button
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activity.class));
            }
        });

        // Upgrade button
        findViewById(R.id.upgradeButton).setBackgroundResource(R.drawable.upgrade_button);

        // CoinsView for coins
        coinsView = findViewById(R.id.mainCoinsView);
        coinsView.setCoins(String.valueOf(Constants.coins));
    }

    private void onRepeat() {
        coinsView.setCoins(String.valueOf(Constants.coins));
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRepeat();
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
