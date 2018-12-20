package lab.humanz.memo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        essentialInit();
        setupToolbar();
        setupFab();
    }

    private void essentialInit() {
        Memo.imageDir = this.getDir("images", MODE_PRIVATE);
    }

    private void setupToolbar() {
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Notes");
    }

    private void setupFab() {
        FloatingActionButton fab = findViewById(R.id.add_memo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditMemo.class);
                startActivity(intent);
            }
        });

        init();
    }

    private void init() {
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(MainActivity.this, Sign_in.class);
                startActivity(intent);
                break;
        }
    }
}
