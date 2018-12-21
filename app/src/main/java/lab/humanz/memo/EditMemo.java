package lab.humanz.memo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

//To create new memo or to edit existing memo
public class EditMemo extends AppCompatActivity {

    public static String MEMO_TO_EDIT = "memotoedit";
    public static int RESULT_LOAD_IMG = 100;
    private RecyclerView memoContent;
    private ImageButton pickImageButton;
    private Memo memo;
    private MemoContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_memo_activity);

        Memo tempMemo = (Memo) getIntent().getSerializableExtra(MEMO_TO_EDIT);
        if (tempMemo != null)
            this.memo = tempMemo;
        else
            this.memo = new Memo();

        this.memo.insertTextContent("Some test note");
        this.memoContent = findViewById(R.id.memo_content);
        this.pickImageButton = findViewById(R.id.pick_image_button);
        this.pickImageButton.setOnClickListener(this.pickMemoImage);
        this.adapter = new MemoContentAdapter(this.memo);
        this.memoContent.setAdapter(this.adapter);
        this.memoContent.setLayoutManager(new LinearLayoutManager(this));
        this.setupToolbar();
        findViewById(R.id.place_holder).setOnTouchListener(this.newMemoContent);
        this.adapter.notifyDataSetChanged();
    }

    //Sets up toolbar
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //To show back button on toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Picks image from gallery
     */
    private View.OnClickListener pickMemoImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
    };

    private View.OnTouchListener newMemoContent = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (EditMemo.this.memo.contentTypes.get(EditMemo.this.memo.contentTypes.size() - 1) != Memo.TEXT
                    && motionEvent.getAction() == MotionEvent.ACTION_UP){
                EditMemo.this.insertText("");
            }
            return true;
        }
    };

    public void insertText(String text) {
        Log.d("MEMO", "insertText: " + text);
        this.memo.insertTextContent(text);
        this.adapter.notifyDataSetChanged();
        findViewById(R.id.place_holder).setVisibility(View.GONE);
    }

    /**
     * Inserts imageBitmap after compression to memo.
     * It also writes image to file and assigns id to it.
     * @param imageBitmap
     */
    public void insertImage(Bitmap imageBitmap) {
        Log.d("MEMO", "Image");
        if (this.memo.textContents.get(this.memo.textContents.size() - 1).isEmpty()) {
            Log.d("MEMO", "Removed last element");
            this.memo.removeLastContent();
        }
        this.memo.insertImageContent(imageBitmap);
        this.adapter.notifyDataSetChanged();
        findViewById(R.id.place_holder).setVisibility(View.VISIBLE);
    }

    /**
     * Removes image from data set at given position
     * @param position
     */
    public void removeImageAt(int position) {
        if (this.memo.contentTypes.size() > position) {
            this.memo.removeContentAt(position);
            this.adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getGroupId() == Memo.IMAGE)
            this.removeImageAt(item.getOrder());
        return false;
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                this.insertImage(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
