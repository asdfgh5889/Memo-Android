package lab.humanz.memo;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class DataController {
    private static volatile DataController dataController = new DataController();
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private DataController() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public void initDatabaseWith(FirebaseUser user) {
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid());
        storageReference = FirebaseStorage.getInstance().getReference(user.getUid() + "/");
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
                if (memo.imageContents != null) {
                    for (final String image : memo.imageContents) {
                        UploadTask uploadTask = this.storageReference.putFile(Uri.fromFile(new File(Memo.imageDir + "/" + image)));

                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.d("DB", "writeMemoToDB: Failed uploaded " + image);
                                Log.d("DB", "writeMemoToDB: " + exception.getLocalizedMessage());
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Log.d("DB", "writeMemoToDB: Success uploaded " + image);
                            }
                        });
                    }
                }
            }
        }
    }

    public void readAllMemos() {

    }
}
