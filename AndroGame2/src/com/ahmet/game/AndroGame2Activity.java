package com.ahmet.game;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.game.main.Game;

import android.app.Activity;
import android.os.Bundle;

public class AndroGame2Activity extends AndroidApplication {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        initialize(new Game(),false);
    }
}