package lab.humanz.memo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.FirebaseApp;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {

    /**
     * UI items initialization
     */
    private SignInButton button;
    private TextView registration;
    private Button sing_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
/**
 * sign in with google firebase option implementation
 * here you can get user information by using  mGoogleSignInClient object
 */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    /**
     * initialization of items
     */
    private void init() {
        button = findViewById(R.id.sign_in_button);
        button.setOnClickListener(this);
        registration = findViewById(R.id.registration);
        registration.setOnClickListener(this);
        sing_in = findViewById(R.id.singn_in);
        sing_in.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        /**
         * segue implementation from login page to required pages
         */
        Intent intent;
        switch (view.getId()){
            case R.id.singn_in:
                 intent = new Intent(Login_Activity.this, Sign_in.class);
                startActivity(intent);
                break;
            case R.id.registration:
                 intent = new Intent(Login_Activity.this, Registration.class);
                startActivity(intent);
                break;
            case R.id.sign_in_button:
                intent = new Intent(Login_Activity.this, MainActivity.class);
                startActivity(intent);
                break;

        }
    }
}
