package com.longyuan.bitunionpyro.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.longyuan.bitunionpyro.R;
import com.longyuan.bitunionpyro.pojo.action.Lastreply;
import com.longyuan.bitunionpyro.pojo.action.NewlistItem;
import com.longyuan.bitunionpyro.userdetails.UserdetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.longyuan.bitunionpyro.userdetails.UserdetailsActivity.EXTRA_USER_AVATAR;
import static com.longyuan.bitunionpyro.userdetails.UserdetailsActivity.EXTRA_USER_NAME;
import static com.longyuan.bitunionpyro.utils.Constant.REPLY;
import static com.longyuan.bitunionpyro.utils.HtmlHelper.avatarUrlUpdate;

/**
 * Created by loxu on 11/01/2018.
 */

public class LastPostListAdapter extends RecyclerView.Adapter<LastPostListAdapter.LastPostListViewHolder> {

    private List<NewlistItem> mPostList;

    private Context mContext;

    private OnItemClickListener.OnPostItemClickListener mOnItemClickListener;

    public LastPostListAdapter(List<NewlistItem> mPostList, Context mContext) {
        this.mPostList = mPostList;
        this.mContext = mContext;
    }

    @Override
    public LastPostListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_2,parent,false);

        return new LastPostListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LastPostListViewHolder holder, int position){

        final NewlistItem aPost = mPostList.get(position);
      /*  holder.textView1.setText(HtmlHelper.urlDecode(aPost.getPname()));

        holder.textView2.setText(HtmlHelper.urlDecode(aPost.getFname()));

        holder.textView3.setText(HtmlHelper.urlDecode(aPost.getAuthor()));*/

        holder.aTextView_title.setText(HtmlHelper.urlDecode(aPost.getPname()));

        holder.aTextView_lastReply.setText(HtmlHelper.urlDecode(getContent(aPost.getLastreply())));

        holder.aTextView_author_name.setText(Constant.AUTHOR+HtmlHelper.urlDecode(aPost.getAuthor()));

        holder.aTextView_forum.setText(HtmlHelper.urlDecode(aPost.getFname() + " : " + aPost.getTidSum() + REPLY));

        String logoUrl = avatarUrlUpdate(HtmlHelper.urlDecode(aPost.getAvatar()));

            Glide.with(mContext).load(logoUrl).diskCacheStrategy( DiskCacheStrategy.SOURCE ).into(holder.aImageView);
            Log.d("IMage",HtmlHelper.urlDecode(aPost.getAvatar()));
            Log.d("IMage",avatarUrlUpdate(HtmlHelper.urlDecode(aPost.getAvatar())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(aPost);
            }
        });

        holder.aImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,UserdetailsActivity.class);

                intent.putExtra(EXTRA_USER_NAME,aPost.getAuthor());

                intent.putExtra(EXTRA_USER_AVATAR,logoUrl);

                mContext.startActivity(intent);
            }
        });

    }

    private String getContent(Lastreply lastreply) {

        return lastreply.getWho()+ " : " + lastreply.getWhat();
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    public void updateData(List<NewlistItem> postList){

        mPostList = postList;

        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener.OnPostItemClickListener tOnItemClickListener) {
        this.mOnItemClickListener = tOnItemClickListener;
    }

    public static class LastPostListViewHolder extends RecyclerView.ViewHolder{

      /*  @BindView(R.id.textView)
        TextView textView1;

        @BindView(R.id.textView2)
        TextView textView2;

        @BindView(R.id.textView3)
        TextView textView3;*/

        @BindView(R.id.post_title)
        TextView aTextView_title;

        @BindView(R.id.post_author_name)
        TextView aTextView_author_name;

        @BindView(R.id.post_last_reply)
        TextView aTextView_lastReply;

       @BindView(R.id.post_forum)
        TextView aTextView_forum;

        @BindView(R.id.post_author_avatar)
        ImageView aImageView;

        public LastPostListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }

}
