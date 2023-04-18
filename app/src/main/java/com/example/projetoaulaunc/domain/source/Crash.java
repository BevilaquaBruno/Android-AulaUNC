package com.example.projetoaulaunc.domain.source;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class Crash {
    FirebaseCrashlytics crash = FirebaseCrashlytics.getInstance();

    public void exceptions(Throwable throwable ) {
        crash.recordException(throwable);
    }

    public void log(String message) {
        crash.log(message);
    }
}
