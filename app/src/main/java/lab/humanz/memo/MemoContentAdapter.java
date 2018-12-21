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

interface MemoContentAdapterDelegate {
    public void alterTextMemoAt(int position, String text);
}

public class MemoContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MemoContentAdapterDelegate {
    public Memo memo;

    public MemoContentAdapter(Memo memo) {
        super();
        this.memo = memo;
    }

    /**
     * Alters text content at given position
     * @param position at recycler view
     * @param text new text
     */
    public void alterTextMemoAt(int position, String text) {
        if (this.memo.contentTypes.size() > position)
            this.memo.textContents.set(this.memo.getPositionFor(position), text);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case Memo.TEXT:
                return new TextMemoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.text_memo_layout, parent, false), this);
            case Memo.IMAGE:
                return new ImageMemoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_memo_layout, parent, false), this);
            case Memo.AUDIO:
                break;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("MEMO", "Adapter: Binding on position " + position + " type of: " + (holder.getItemViewType() == Memo.TEXT ? "Text" : "Image"));
        switch (holder.getItemViewType()) {
            case Memo.TEXT:
                ((TextMemoViewHolder) holder).position = position;
                ((TextMemoViewHolder) holder).textMemo.setText(this.memo.textContents.get(this.memo.getPositionFor(position)));
                break;
            case Memo.IMAGE:
                ((ImageMemoViewHolder) holder).position = position;
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
