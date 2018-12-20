package lab.humanz.memo;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MemoContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Memo memo;

    public MemoContentAdapter(Memo memo) {
        super();
        this.memo = memo;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case Memo.TEXT:
                return new TextMemoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.text_memo_layout, parent, false));
            case Memo.IMAGE:
                return new ImageMemoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_memo_layout, parent, false));
            case Memo.AUDIO:
                break;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case Memo.TEXT:
                ((TextMemoViewHolder) holder).textMemo.setText(this.memo.textContents.get(this.memo.getPositionFor(position)));
                ((TextMemoViewHolder) holder).memoText = this.memo.textContents.get(position);
                break;
            case Memo.IMAGE:
                ((ImageMemoViewHolder) holder).memoImage.setImageBitmap(this.memo.imageBitmapCache.get(this.memo.getPositionFor(position)));
                break;
            case Memo.AUDIO:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return memo.contentTypes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.memo.contentTypes.get(position);
    }
}
