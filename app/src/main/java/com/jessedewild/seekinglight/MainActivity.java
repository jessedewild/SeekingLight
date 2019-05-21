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
import com.jessedewild.seekinglight.utils.JSONHelper;

public class MainActivity extends AppCompatActivity {

    private GameView gameCanvas;
    private GameModel game;
    private CoinsView coinsView;
    private JSONHelper jsonHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load save
        jsonHelper = new JSONHelper(getApplicationContext());
        jsonHelper.load();

        gameCanvas = findViewById(R.id.mainGameView);
        game = new Game(getApplicationContext());
        ((Game) game).setJson(jsonHelper.readLevel(Constants.level));
        ((Game) game).setAutoScroll(true);
        ((Game) game).setShowCharactersOnMap(false);
        ((Game) game).setShowFog(false);

        // Start button
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activity.class));
            }
        });

        // Upgrade button
        Button upgradeButton = findViewById(R.id.upgradeButton);
        upgradeButton.setBackgroundResource(R.drawable.upgrade_button);

        // CoinsView for coins
        coinsView = findViewById(R.id.mainCoinsView);
        coinsView.setCoins(String.valueOf(Constants.coins));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load
        coinsView.setCoins(String.valueOf(Constants.coins));
        jsonHelper.load();

        gameCanvas.setGame(game);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save
        jsonHelper.save();

        gameCanvas.setGame(null);
    }
}
