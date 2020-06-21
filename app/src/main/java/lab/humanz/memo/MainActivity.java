package lab.humanz.memo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton logout;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        essentialInit();
        setupToolbar();
        setupFab();
        setupMemoList();
    }

    private void setupMemoList() {
        recyclerView = findViewById(R.id.memo_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        /**
         * segue from memo list to selected memo edit page
         */
        MemoListAdapter adapter = new MemoListAdapter(dummydata(), new MemoListAdapter.OnItemClick() {
            @Override
            public void OnClickchange(int position) {
                Intent intent = new Intent(MainActivity.this, EditMemo.class);
                intent.putExtra(EditMemo.MEMO_TO_EDIT, dummydata().get(position));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
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

    /**
     *
     * @param viewO onClick functions are implememted here
     *              Logout
     *              sellected MemoCard
     *              Delete
     */

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(MainActivity.this, Login_Activity.class);
                startActivity(intent);
                break;
        }
    }

    public List<Memo> dummydata(){
        List<Memo> memos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Memo temp = new Memo();
            temp.insertTextContent(i + " Something test");
            memos.add(temp);
        }
        return memos;
    }
}
