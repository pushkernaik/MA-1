package com.letsappit.mineautomation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.letsappit.mineautomation.BO.GridMenuItem;

import java.util.ArrayList;

/**
 * Created by radhaprasadborkar on 09/01/16.
 */
public class MenuAdapter  extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    public ArrayList<GridMenuItem> mContent;
    private final int rowLayout;
    private View faButton;
    private final Activity mContext;



    public MenuAdapter(ArrayList<GridMenuItem> mContent,int rowLayout, Activity context)
    {

        this.mContent = mContent;
        this.rowLayout = rowLayout;
        this.mContext = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitle;
        public ImageView icon;
        RelativeLayout background;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_item);
            mTitle = (TextView)  v.findViewById(R.id.menu_title);
            icon =(ImageView)v.findViewById(R.id.menu_icon);
            background = (RelativeLayout) v.findViewById(R.id.background);

        }
    }

    @Override
    public int getItemCount() {
        return this.mContent == null ? 0 : this.mContent.size();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(this.rowLayout, parent, false);

        return new MenuAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder,final int position) {
        final GridMenuItem item = this.mContent.get(position);
        holder.mTitle.setText(item.getName());
        holder.icon.setImageDrawable(mContext.getDrawable(item.getResId()));
        holder.background.setBackground(mContext.getDrawable(item.getColorId()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,item.getActivity());

                mContext.startActivity(intent);
            }
        });
    }




}

