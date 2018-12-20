package lab.humanz.memo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageMemoViewHolder extends RecyclerView.ViewHolder {
    public ImageView memoImage;
    public int position = 0;
    public ImageMemoViewHolder(View itemView) {
        super(itemView);
        this.memoImage = (ImageView) itemView;
    }
}
