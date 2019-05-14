package com.jessedewild.seekinglight.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jessedewild.seekinglight.lib.GameView;

import java.io.IOException;
import java.io.InputStream;

public class Activity extends AppCompatActivity {

    private Game game;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // In this example, we don't require a Layout or any other Android Views than
        // are custom GameCanvas.
        gameView = new GameView(this);
        setContentView(gameView);

        // If a running game has been serialized (because it has been paused for
        // a long time, or because of an orientation change), recreate the Game
        // object from the serialized bundle.
        if (savedInstanceState != null && savedInstanceState.containsKey("game")) {
            game = (Game) savedInstanceState.getSerializable("game");
        } else {
            game = new Game();
            game.setJson(readJSONFile(1));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("game", game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.setGame(game);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.setGame(null);
    }

    private String readJSONFile(int levelNum) {
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

        System.out.println(json);
        return json;
    }
}
