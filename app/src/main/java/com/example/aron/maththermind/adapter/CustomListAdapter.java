package com.example.aron.maththermind.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import com.example.aron.maththermind.R;
import com.example.aron.maththermind.model.Items;

public class CustomListAdapter extends BaseAdapter {

    private  Context mContext;
    private LayoutInflater inflater;
    private List<Items> itemsItems;



    public CustomListAdapter(Context context, List<Items> itemsItems) {
        this.mContext = context;
        this.itemsItems = itemsItems;
    }

    @Override
    public int getCount() {
        return itemsItems.size();
    }

    @Override
    public Object getItem(int location) {
        return itemsItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View scoreView, ViewGroup parent) {
        ViewHolder holder;
        if (inflater == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (scoreView == null) {

            scoreView = inflater.inflate(R.layout.list_row, parent, false);
            holder = new ViewHolder();
            holder.rank = (TextView) scoreView.findViewById(R.id.rank);
            holder.name = (TextView) scoreView.findViewById(R.id.name);
            holder.solved = (TextView) scoreView.findViewById(R.id.solved);
            holder.score = (TextView) scoreView.findViewById(R.id.score);

            scoreView.setTag(holder);

        } else {
            holder = (ViewHolder) scoreView.getTag();
        }

        final Items m = itemsItems.get(position);
        holder.rank.setText(m.getRank());
        holder.name.setText(m.getName());
        holder.solved.setText(m.getSolved());
        holder.score.setText(m.getScore());

        return scoreView;
    }

    static class ViewHolder {

        TextView name;
        TextView score;
        TextView rank;
        TextView solved;
    }

}
