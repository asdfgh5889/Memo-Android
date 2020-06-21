package lab.humanz.memo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageMemoViewHolder extends RecyclerView.ViewHolder {
    public ImageView memoImage;
    public MemoContentAdapterDelegate delegate;
    public int position = 0;
    public ImageMemoViewHolder(View itemView, MemoContentAdapter delegate) {
        super(itemView);
        this.memoImage = (ImageView) itemView;
        this.delegate = delegate;

        this.memoImage.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                Log.d("MENU", "onCreateContextMenu: Created Menu");
                contextMenu.setHeaderTitle("Delete Image?");
                contextMenu.add(Memo.IMAGE, Menu.NONE, position, "Delete");
            }
        });
    }
}
