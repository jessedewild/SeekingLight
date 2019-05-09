package com.jessedewild.seekinglight.platformer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jessedewild.seekinglight.R;
import com.jessedewild.seekinglight.lib.GameView;

public class Activity extends AppCompatActivity implements Game.Listener {

    Game game;
    GameView gameView;
    TextView scrollText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // In this example, we're overlaying a TextView over our canvas. We could add
        // any Android Views this way.
        setContentView(R.layout.activity_platformer);

        gameView = findViewById(R.id.gameView);
        scrollText = findViewById(R.id.scrollText);

        // If a running game has been serialized (because it has been paused for
        // a long time, or because of an orientation change), recreate the Game
        // object from the serialized bundle.
        if (savedInstanceState != null && savedInstanceState.containsKey("game")) {
            game = (Game) savedInstanceState.getSerializable("game");
        } else {
            game = new Game();
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
        game.listeners.add(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.setGame(null);
        game.listeners.remove(this);
    }

    @Override
    public void scrollChanged() {
        scrollText.setText(String.format("%.2f", game.scroller.x));
    }
}
