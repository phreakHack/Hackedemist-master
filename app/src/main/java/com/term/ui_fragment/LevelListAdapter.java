package com.term.ui_fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.term.R;

public class LevelListAdapter extends RecyclerView.Adapter<LevelListAdapter.LevelListViewHolder> {

    private Context context;
    private ItemClickListener itemClickListener;

    public LevelListAdapter(Context context, ItemClickListener itemClickListener){

        this.context = context;
        this.itemClickListener = itemClickListener;

    }

    public interface ItemClickListener{

        void onItemClick(int position);

    }

    @Override
    public LevelListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        return new LevelListViewHolder(LayoutInflater.from(context).inflate(R.layout.level_list_recycler_view_item,parent,false));
    }

    @Override
    public void onBindViewHolder(LevelListViewHolder holder, int position) {

        if(LevelListActivity.levelChosen.matches("Easy"))
            if(Integer.parseInt(LevelListActivity.QuestionsIDs.get(position)) <= context.getSharedPreferences("com.term",Context.MODE_PRIVATE).getInt("easy_level",0) + 1){
                holder.backgroundImageView.setBackground(context.getResources().getDrawable(R.drawable.easykey));
            }

        if(LevelListActivity.levelChosen.matches("Medium"))
            if(Integer.parseInt(LevelListActivity.QuestionsIDs.get(position)) <= context.getSharedPreferences("com.term",Context.MODE_PRIVATE).getInt("medium_level",0) + 1){
                holder.backgroundImageView.setBackground(context.getResources().getDrawable(R.drawable.mediumkey));
            }else
            {
                holder.backgroundImageView.setBackground(context.getResources().getDrawable(R.drawable.mediumlock));
            }

        if(LevelListActivity.levelChosen.matches("Hard"))
            if(Integer.parseInt(LevelListActivity.QuestionsIDs.get(position)) <= context.getSharedPreferences("com.term",Context.MODE_PRIVATE).getInt("hard_level",0) + 1){
                holder.backgroundImageView.setBackground(context.getResources().getDrawable(R.drawable.hardkey));
            }else
            {
                holder.backgroundImageView.setBackground(context.getResources().getDrawable(R.drawable.hardlock));
            }
    }

    @Override
    public int getItemCount() {
        return LevelListActivity.totalQuestions;
    }

    class LevelListViewHolder extends RecyclerView.ViewHolder{

        ImageView backgroundImageView;

        public LevelListViewHolder(View itemView) {

            super(itemView);
            backgroundImageView = itemView.findViewById(R.id.level_list_recycler_view_item_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    itemClickListener.onItemClick(getAdapterPosition());

                }
            });

        }

    }

}
