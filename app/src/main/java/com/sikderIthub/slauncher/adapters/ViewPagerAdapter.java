package com.sikderIthub.slauncher.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sikderIthub.slauncher.R;
import com.sikderIthub.slauncher.models.Pager;

import java.util.ArrayList;


public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    Context context;
    ArrayList<Pager> pagerList;
    int cellHeight;
    int numColumn;
    ArrayList<AppAdapter> appAdapterList = new ArrayList<>();

    public ViewPagerAdapter(Context context, ArrayList<Pager> pagerAppList, int cellHeight, int numColumn) {
        this.context = context;
        this.pagerList = pagerAppList;
        this.cellHeight = cellHeight;
        this.numColumn = numColumn;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.pager_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mGridView.setNumColumns(numColumn);

        AppAdapter mGridAdapter = new AppAdapter(context, pagerList.get(position).getAppsList(), cellHeight);
        holder.mGridView.setAdapter(mGridAdapter);
    }

    @Override
    public int getItemCount() {
        if (pagerList == null)
            return 0;
        return pagerList.size();
    }

    public void notifyGridChanged() {
        for (int i = 0; i < appAdapterList.size(); i++) {
            appAdapterList.get(i).notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        GridView mGridView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mGridView = itemView.findViewById(R.id.grid);
        }
    }
}
