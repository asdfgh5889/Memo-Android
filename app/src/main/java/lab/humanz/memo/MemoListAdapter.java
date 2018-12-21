package lab.humanz.memo;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MemoListAdapter extends RecyclerView.Adapter<MemoListAdapter.MemoViewHolder>{

    private List<MemoModel> memos;

    public MemoListAdapter(List<MemoModel> memos) {
        this.memos = memos;
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_holder_activity, parent, false);
        return new MemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder holder, int position) {
        MemoModel memo = memos.get(position);
        holder.setUpData(memo);
    }

    @Override
    public int getItemCount() {
        return memos.size();
    }

    public class MemoViewHolder extends RecyclerView.ViewHolder{

        TextView memo_title;
        TextView memo_body;

        public MemoViewHolder(View itemView) {
            super(itemView);
            memo_title = itemView.findViewById(R.id.memo_title);
            memo_body = itemView.findViewById(R.id.memo_body);

        }

        public void setUpData(MemoModel memo) {
            memo_title.setText(memo.getTitle());
            memo_body.setText(memo.getContent());
        }
    }
}
