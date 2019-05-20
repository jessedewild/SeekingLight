package com.jessedewild.seekinglight.compounds;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.jessedewild.seekinglight.R;

public class CoinsView extends ConstraintLayout {

    private TextView coins;
    private ImageView coin;

    public CoinsView(Context context) {
        super(context);
        init();
    }

    public CoinsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoinsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.coins_view, this);

        coins = findViewById(R.id.coins);
        coin = findViewById(R.id.coin);
    }

    public TextView getCoins() {
        return coins;
    }

    public void setCoins(String text) {
        this.coins.setText(text);
    }
}
