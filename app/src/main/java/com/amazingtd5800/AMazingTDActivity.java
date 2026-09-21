package com.amazingtd5800;

import android.app.Activity;
import android.os.Bundle;

/** Application entry point and lifecycle. Original: AMazingTDMidlet. */
public class AMazingTDActivity extends Activity {

    private final Game game = new Game(this);

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        game.start();
    }
}
