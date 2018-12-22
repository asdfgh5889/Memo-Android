package lab.humanz.memo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_in extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button login;
    private EditText emailTxt;
    private EditText pwdTExt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();


     }
    /**
     * UI items initialization function
     */
    private void init() {
        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(this);
        emailTxt = findViewById(R.id.loginEmail);
        pwdTExt = findViewById(R.id.loginPwd);
    }

    /**
     *
     * @param view checking user credentials to sign in (Email and Password)
     */
    @Override
    public void onClick(View view) {
        String email = emailTxt.getText().toString();
        String password = pwdTExt.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (mAuth.getCurrentUser() != null) {
                                startActivity(new Intent(Sign_in.this, MainActivity.class));
                            }
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Sign_in.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
