package com.jessedewild.seekinglight.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jessedewild.seekinglight.R;
import com.jessedewild.seekinglight.entities.characters.Seeker;
import com.jessedewild.seekinglight.lib.GameView;
import com.jessedewild.seekinglight.utils.Constants;

import java.io.IOException;
import java.io.InputStream;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class Activity extends AppCompatActivity {

    private Game game;
    private GameView gameView;
    private ProgressBar healthBar;
    private TextView coinsView;
    private JoystickView joystick;
    private Button attackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = findViewById(R.id.gameView);

        // If a running game has been serialized (because it has been paused for
        // a long time, or because of an orientation change), recreate the Game
        // object from the serialized bundle.
        if (savedInstanceState != null && savedInstanceState.containsKey("game")) {
            game = (Game) savedInstanceState.getSerializable("game");
        } else {
            game = new Game();
            game.setJson(readJSONFile(1));
            game.setAutoScroll(false);
            game.setShowCharactersOnMap(true);
        }

        /**
         * Health bar settings
         */
        healthBar = findViewById(R.id.healthBar);
        healthBar.setMax(200);

        /**
         * Coins settings
         */
        coinsView = findViewById(R.id.coins);
        coinsView.setText(String.valueOf(Constants.coins));

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
        },7);

        /**
         * Attack button settings
         */
        attackButton = findViewById(R.id.attack);
        attackButton.setBackgroundResource(R.drawable.attackbutton);
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
        String file = "maps/level" + levelNum + "/level" + levelNum + ".json";
        String json = null;
        try {
            InputStream is = getAssets().open(file);
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
