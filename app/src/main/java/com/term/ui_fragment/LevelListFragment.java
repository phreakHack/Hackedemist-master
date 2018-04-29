package com.term.ui_fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.term.R;

public class LevelListFragment extends Fragment {

    RecyclerView questionRecyclerView;
    LevelListAdapter levelListAdapter;
    GridLayoutManager gridLayoutManager;
    TextView levelTextView;

    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.level_list_fragment,container,false);

        levelTextView = (TextView) v.findViewById(R.id.level_list_fragment_level_text_view);

        levelTextView.setText(LevelListActivity.levelChosen);

        final SharedPreferences sharedPreferences = context.getSharedPreferences("com.term",Context.MODE_PRIVATE);

        questionRecyclerView = (RecyclerView) v.findViewById(R.id.level_list_fragment_recycler_view);
        levelListAdapter = new LevelListAdapter(context, new LevelListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {

                int levelAttained = 0;

                if(LevelListActivity.levelChosen.matches("Easy")) {
                    levelAttained = sharedPreferences.getInt("easy_level", 0);

                    if (levelAttained + 1 >= Integer.parseInt(LevelListActivity.QuestionsIDs.get(position))) {
                        Intent detailIntent = new Intent(getContext(), LevelDetailActivity.class);
                        detailIntent.putExtra("ID", LevelListActivity.QuestionsIDs.get(position));
                        startActivity(detailIntent);
                    }else{
                        Toast.makeText(context, "Complete earlier levels to proceed", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(LevelListActivity.levelChosen.matches("Medium")){
                    levelAttained = sharedPreferences.getInt("medium_level",0);

                    if (levelAttained + 1 >= Integer.parseInt(LevelListActivity.QuestionsIDs.get(position))) {
                        Intent detailIntent = new Intent(getContext(), LevelDetailActivity.class);
                        detailIntent.putExtra("ID", LevelListActivity.QuestionsIDs.get(position));
                        startActivity(detailIntent);
                    }else{
                        Toast.makeText(context, "Complete earlier levels to proceed", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    levelAttained = sharedPreferences.getInt("hard_level",0);

                    if (levelAttained + 1 >= Integer.parseInt(LevelListActivity.QuestionsIDs.get(position))) {
                        Intent detailIntent = new Intent(getContext(), LevelDetailActivity.class);
                        detailIntent.putExtra("ID", LevelListActivity.QuestionsIDs.get(position));
                        startActivity(detailIntent);
                    }else{
                        Toast.makeText(context, "Complete earlier levels to proceed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        gridLayoutManager = new GridLayoutManager(context,3);

        questionRecyclerView.setAdapter(levelListAdapter);
        questionRecyclerView.setLayoutManager(gridLayoutManager);

        return v;
    }

    public void refreshRecyclerView(){
        levelListAdapter.notifyDataSetChanged();
    }

    public void refreshLevel(){levelTextView.setText(LevelListActivity.levelChosen);}
    //

}
