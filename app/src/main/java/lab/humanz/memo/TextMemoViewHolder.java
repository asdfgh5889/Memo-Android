package lab.humanz.memo;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class TextMemoViewHolder extends RecyclerView.ViewHolder {
    public EditText textMemo;
    public int position = 0;
    public MemoContentAdapterDelegate delegate;
    public TextMemoViewHolder(View itemView, final MemoContentAdapterDelegate delegate) {
        super(itemView);
        this.delegate = delegate;
        this.textMemo = (EditText) itemView;
        this.textMemo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("MEMO", "afterTextChanged: \"" + editable.toString() + "\" at position " + position);
                if(!editable.toString().isEmpty()) {
                    Log.d("MEMO", "afterTextChanged: Changed at position " + position);
                    delegate.alterTextMemoAt(position, editable.toString());
                }
            }
        });
    }
}
