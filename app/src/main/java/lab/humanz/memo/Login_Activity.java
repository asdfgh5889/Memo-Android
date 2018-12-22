package lab.humanz.memo;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {

    /**
     * UI items initialization
     */
    private FirebaseAuth mAuth;
    private SignInButton button;
    private TextView registration;
    private Button sing_in;
    private int RC_SIGN_IN = 12345;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
/**
 * sign in with google firebase option implementation
 * here you can get user information by using  mGoogleSignInClient object
 */
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();// check user credentials
/**
 * function for automatic sign in if user already signed in
 */
        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
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
                signIn();
                break;

        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // ...
                Log.d("GOOGLE", "onActivityResult: ERROR " + e.getLocalizedMessage());
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("GOOGLE", "onActivityResult: firebase");
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("GOOGLE", "onActivityResult: completion");
                        if (task.isSuccessful()) {
                            Log.d("GOOGLE", "onActivityResult: success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                            Login_Activity.this.startActivity(intent);
                        }else {
                            // If sign in fails, display a message to the user.
                            Log.w("GOOGLE", "signInWithCredential:failure", task.getException());
                        }

                        // ...
                    }
                });
    }
}
