package com.example.ducph.musicplayer;

/**
 * Created by ducph on 11/04/2017.
 */

public class Application extends android.app.Application {
    private static Application instance;
    public Application() {
        instance = this;

    }
    public static Application getInstance() {
        if (instance == null)
            throw new IllegalStateException();
        return instance;
    }
}
