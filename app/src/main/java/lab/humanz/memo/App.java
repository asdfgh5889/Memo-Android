package lab.humanz.memo;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * firebase initialization for app  settings
         */
        FirebaseApp.initializeApp(this);
    }
}
