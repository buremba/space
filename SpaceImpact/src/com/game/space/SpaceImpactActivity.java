package com.game.space;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.game.Game;

public class SpaceImpactActivity extends AndroidApplication {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(new Game(),false);
    }
}