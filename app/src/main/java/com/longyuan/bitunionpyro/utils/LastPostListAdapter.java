package com.longyuan.bitunionpyro.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longyuan.bitunionpyro.R;
import com.longyuan.bitunionpyro.pojo.action.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.POST;

/**
 * Created by loxu on 11/01/2018.
 */

public class LastPostListAdapter extends RecyclerView.Adapter<LastPostListAdapter.LastPostListViewHolder> {

    private List<Post> mPostList;

    private Context mContext;

    public LastPostListAdapter(List<Post> mPostList, Context mContext) {
        this.mPostList = mPostList;
        this.mContext = mContext;
    }

    @Override
    public LastPostListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);

        return new LastPostListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LastPostListViewHolder holder, int position) {

        final Post aPost = mPostList.get(position);

        holder.textView1.setText(aPost.getMessage());

        holder.textView2.setText(aPost.getAuthor());

        holder.textView3.setText(aPost.getDateline());


    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    public static class LastPostListViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.textView)
        TextView textView1;

        @BindView(R.id.textView2)
        TextView textView2;

        @BindView(R.id.textView3)
        TextView textView3;

        public LastPostListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }

}
