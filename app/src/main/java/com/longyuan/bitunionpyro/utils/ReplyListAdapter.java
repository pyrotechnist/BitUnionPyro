package com.longyuan.bitunionpyro.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longyuan.bitunionpyro.R;
import com.longyuan.bitunionpyro.pojo.action.Lastreply;
import com.longyuan.bitunionpyro.pojo.action.NewlistItem;
import com.longyuan.bitunionpyro.pojo.action.reply.ReplyItem;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.longyuan.bitunionpyro.utils.Constant.REPLY;
import static com.longyuan.bitunionpyro.utils.HtmlHelper.avatarUrlUpdate;

/**
 * Created by loxu on 11/01/2018.
 */

public class ReplyListAdapter extends RecyclerView.Adapter<ReplyListAdapter.ReplayListViewHolder> {

    private List<ReplyItem> mReplyList;

    private Context mContext;

    public ReplyListAdapter(List<ReplyItem> mPostList, Context mContext) {
        this.mReplyList = mPostList;
        this.mContext = mContext;
    }

    @Override
    public ReplayListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_item,parent,false);

        return new ReplayListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReplayListViewHolder holder, int position){

        final ReplyItem aReply = mReplyList.get(position);
      /*  holder.textView1.setText(HtmlHelper.urlDecode(aPost.getPname()));

        holder.textView2.setText(HtmlHelper.urlDecode(aPost.getFname()));

        holder.textView3.setText(HtmlHelper.urlDecode(aPost.getAuthor()));*/

        holder.aTextView_reply_author.setText(HtmlHelper.urlDecode(aReply.getAuthor()));

        String htmlString = HtmlHelper.urlDecode(aReply.getMessage());

        Pattern p = Pattern.compile("(.*<\\/table><\\/center><br\\/>)(.*)");
        Matcher m = p.matcher(htmlString);

        String stringContent = "";

        String stringQuote="";

        if(m.find())
        {
            stringQuote = m.group(1);

            updateTextview(stringQuote,holder.aTextView_reply_qoute);

            stringContent = m.group(2);

            updateTextview(stringContent,holder.aTextView_reply_content);
        }else {

            updateTextview(htmlString,holder.aTextView_reply_content);
        }

        //holder.aTextView_reply_content.setText(HtmlHelper.urlDecode(aReply.getMessage()));

        //holder.aTextView_replySum.setText(HtmlHelper.urlDecode(aPost.getFname() + " : " + aPost.getTidSum() + REPLY));

            Glide.with(mContext).load(avatarUrlUpdate(HtmlHelper.urlDecode(aReply.getAvatar()))).into(holder.aImageView_reply_avatar);
           // Log.d("before update ",HtmlHelper.urlDecode(aReply.getAvatar()));
          //  Log.d("after update",avatarUrlUpdate(HtmlHelper.urlDecode(aReply.getAvatar())));

    }


    private void updateTextview(String htmlString,TextView textView){

        GlideImageGetter imageGetter = new GlideImageGetter(mContext,textView);

        Spannable html;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            html = (Spannable) Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
        } else {
            html = (Spannable) Html.fromHtml(htmlString, imageGetter, null);
        }

        textView.setText(html);

    }

    private String getContent(Lastreply lastreply) {

        return lastreply.getWho()+ " : " + lastreply.getWhat();
    }

    @Override
    public int getItemCount() {
        if(mReplyList != null){
            return mReplyList.size();
        }else
        return 0;
    }

    public void updateData(List<ReplyItem> postList){

        mReplyList = postList;

        notifyDataSetChanged();
    }


    public static class ReplayListViewHolder extends RecyclerView.ViewHolder{

      /*  @BindView(R.id.textView)
        TextView textView1;

        @BindView(R.id.textView2)
        TextView textView2;

        @BindView(R.id.textView3)
        TextView textView3;*/

        /*@BindView(R.id.)
        TextView aTextView_title;*/

       /* @BindView(R.id.post_reply_sum)
        TextView aTextView_replySum;
*/
        @BindView(R.id.reply_author)
        TextView aTextView_reply_author;

        @BindView(R.id.reply_content)
        TextView aTextView_reply_content;

        @BindView(R.id.reply_quote)
        TextView aTextView_reply_qoute;

        @BindView(R.id.reply_avatar)
        ImageView aImageView_reply_avatar;

        public ReplayListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }

}
