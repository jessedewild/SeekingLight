package com.jessedewild.seekinglight.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;

import com.jessedewild.seekinglight.R;
import com.jessedewild.seekinglight.compounds.CoinsView;
import com.jessedewild.seekinglight.entities.characters.Seeker;
import com.jessedewild.seekinglight.lib.GameView;
import com.jessedewild.seekinglight.utils.Constants;
import com.jessedewild.seekinglight.utils.JSONHelper;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class Activity extends AppCompatActivity {

    private Game game;
    private GameView gameView;
    private ProgressBar healthBar;
    private static CoinsView coinsView;
    private JoystickView joystick;
    private Button attackButton;
    private JSONHelper jsonHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Load save
        jsonHelper = new JSONHelper(getApplicationContext());
        jsonHelper.load();

        /**
         * GameView settings
         */
        gameView = findViewById(R.id.gameView);

        /**
         * Health bar settings
         */
        healthBar = findViewById(R.id.healthBar);
        healthBar.setMax(200);

        /**
         * Coins settings
         */
        coinsView = findViewById(R.id.gameCoinsView);
        coinsView.setCoins(String.valueOf(Constants.coins));

        // If a running game has been serialized (because it has been paused for
        // a long time, or because of an orientation change), recreate the Game
        // object from the serialized bundle.
        if (savedInstanceState != null && savedInstanceState.containsKey("game")) {
            game = (Game) savedInstanceState.getSerializable("game");
        } else {
            game = new Game(getApplicationContext());
            game.setJson(jsonHelper.readLevel(Constants.level));
            game.setAutoScroll(false);
            game.setShowCharactersOnMap(true);
            game.setCoinsView(coinsView);
            game.setShowFog(true);
        }

        /**
         * Joystick settings
         */
        final float moveSpeed = 0.05f;
        joystick = findViewById(R.id.joystick);
        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                Seeker seeker = game.getEntity(Seeker.class);
                Map map = game.getEntity(Map.class);

                if (angle > 45 && angle <= 135 && strength > 0) {
                    seeker.setFacingPosition(Constants.FACING_POSITION.BACK);
                    map.move(0, -moveSpeed / 150 * strength); // seeker.move(0, -moveSpeed / 100 * strength);
                } else if (angle > 135 && angle <= 225 && strength > 0) {
                    seeker.setFacingPosition(Constants.FACING_POSITION.LEFT);
                    map.move(-moveSpeed / 150 * strength, 0); // seeker.move(-moveSpeed / 100 * strength, 0);
                } else if (angle > 225 && angle <= 315 && strength > 0) {
                    seeker.setFacingPosition(Constants.FACING_POSITION.FRONT);
                    map.move(0, moveSpeed / 150 * strength); // seeker.move(0, moveSpeed / 100 * strength);
                } else if (strength > 0) {
                    seeker.setFacingPosition(Constants.FACING_POSITION.RIGHT);
                    map.move(moveSpeed / 150 * strength, 0); // seeker.move(moveSpeed / 100 * strength, 0);
                }
            }
        }, 7);

        /**
         * Attack button settings
         */
        attackButton = findViewById(R.id.attack);
        attackButton.setBackgroundResource(R.drawable.attack_button);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("game", game);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load
        jsonHelper.load();

        gameView.setGame(game);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save
        jsonHelper.save();

        gameView.setGame(null);
    }
}
