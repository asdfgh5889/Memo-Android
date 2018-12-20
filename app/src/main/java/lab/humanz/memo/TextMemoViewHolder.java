package lab.humanz.memo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

public class TextMemoViewHolder extends RecyclerView.ViewHolder {
    public EditText textMemo;
    public int position = 0;
    public String memoText;
    public TextMemoViewHolder(View itemView) {
        super(itemView);
        this.textMemo = (EditText) itemView;
        this.textMemo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    TextMemoViewHolder.this.memoText = TextMemoViewHolder.this.textMemo.getText().toString();
            }
        });
    }
}
