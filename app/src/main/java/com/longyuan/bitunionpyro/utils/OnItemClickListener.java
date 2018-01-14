package com.longyuan.bitunionpyro.utils;

import com.longyuan.bitunionpyro.pojo.action.NewlistItem;

/**
 * Created by LONGYUAN on 2018/1/12.
 */


public interface OnItemClickListener {

    interface OnPostItemClickListener{

        void onItemClick(NewlistItem item);

       // void onItemLongClick(NewlistItem item, int position);

    }

   /* interface OnCommentItemClickListener{

        void onItemClick(CommentItem item);

        void onItemLongClick(CommentItem item, int position);
    }*/
}