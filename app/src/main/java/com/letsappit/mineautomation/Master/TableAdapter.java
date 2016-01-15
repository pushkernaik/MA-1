package com.letsappit.mineautomation.Master;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.letsappit.mineautomation.BO.ListItem;
import com.letsappit.mineautomation.R;

import java.util.ArrayList;

/**
 * Created by radhaprasadborkar on 28/12/15.
 */
public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {
    public ArrayList<ListItem> mContent;
    private final int rowLayout;
    private View faButton;
    private final Activity mContext;



    public TableAdapter(ArrayList<ListItem> mContent,int rowLayout, Activity context)
    {

        this.mContent = mContent;
        this.rowLayout = rowLayout;
        this.mContext = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitle,mDescription;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view);
            this.mTitle = (TextView)  v.findViewById(R.id.title_text_view);
            this.mDescription = (TextView) v.findViewById(R.id.description_text_view);

        }
    }

    @Override
    public int getItemCount() {
        return this.mContent == null ? 0 : this.mContent.size();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(this.rowLayout, parent, false);

        return new TableAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TableAdapter.ViewHolder holder,final int position) {
         ListItem table = this.mContent.get(position);
        holder.mTitle.setText(table.getTitle());
        holder.mDescription.setText(table.getDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,TableDetailActivity.class);
                intent.putExtra("TABLE_ID",position);
                mContext.startActivity(intent);
            }
        });
    }




}
