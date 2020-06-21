package lab.humanz.memo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button registerBtn;
    private TextView emailTxt;
    private TextView passwordTxt;
    private TextView fullNameTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        FirebaseApp.initializeApp(this);
        init();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    /**
     * UI items initialization
     */
    private void init() {
        registerBtn = findViewById(R.id.btnRegister);
        registerBtn.setOnClickListener(this);
        emailTxt = findViewById(R.id.txtEmail);
        passwordTxt = findViewById(R.id.txtPwd);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    }


    /**
     * firebase registration function implementation
     * @param view
     */
    public void onClick(View view) {
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent  intent = new Intent(Registration.this, Sign_in.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Registration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });




        }
}




