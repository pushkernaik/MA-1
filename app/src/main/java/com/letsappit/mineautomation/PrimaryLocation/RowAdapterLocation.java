package com.letsappit.mineautomation.PrimaryLocation;

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
 * Created by radhaprasadborkar on 01/01/16.
 */
public class RowAdapterLocation  extends RecyclerView.Adapter<RowAdapterLocation.ViewHolder>
{
    public ArrayList<ListItem> mContent;
    private final int rowLayout;
    private View faButton;
    private final Activity mContext;


    public RowAdapterLocation(ArrayList<ListItem> mContent,int rowLayout, Activity context)
    {
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.mContent = mContent; // DBOps.getAllRows(mContext, MAContract.Location.TABLE_NAME, MAContract.Location.COLUMN_CODE, MAContract.Location.COLUMN_DESCRIPTION);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitle, mDescription;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view);
            this.mTitle = (TextView) v.findViewById(R.id.title_text_view);
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
        return new RowAdapterLocation.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RowAdapterLocation.ViewHolder holder,final int i) {
        final ListItem table = this.mContent.get(i);
        holder.mTitle.setText(table.getTitle());
        holder.mDescription.setText(table.getDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,RowEditorLocation.class);
                intent.putExtra("ROW_CODE",table.getTitle());
                intent.putExtra("OP_TYPE",2);
                mContext.startActivity(intent);
            }
        });
    }

}

