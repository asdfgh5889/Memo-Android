package lab.humanz.memo;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataController {
    private static volatile DataController dataController = new DataController();
    private DatabaseReference databaseReference;

    private DataController() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public void initDatabaseWith(FirebaseUser user) {
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid());
    }

    public static DataController getInstance() {
        return dataController;
    }

    /**
     * Writes given memo to DB
     * @param memo
     */
    public void writeMemoToDB(Memo memo) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth == null) {
            Log.d("DB", "writeMemoToDB: Null Auth");
        } else {
            if (mAuth.getCurrentUser() == null) {
                Log.d("DB", "writeMemoToDB: Null User");
            } else {
                if (mAuth.getCurrentUser().getUid() == null) {
                    Log.d("DB", "writeMemoToDB: Null User Uid");
                }
            }
            if (mAuth.getUid() == null) {
                Log.d("DB", "writeMemoToDB: Null Uid");
            } else {
                Log.d("DB", "writeMemoToDB: Writing");
                memo.id = this.databaseReference.push().getKey();
                this.databaseReference.child(memo.id).setValue(memo);
            }
        }
    }

    public void readAllMemos() {

    }
}
