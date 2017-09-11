package com.example.admin.karsol_ano_1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by Admin on 27-04-2017.
 */
public class SubTitleViewHolder extends ChildViewHolder {

    private TextView subTitleTextView;
    private ImageView icon;

    public SubTitleViewHolder(View itemView) {
        super(itemView);
        subTitleTextView = (TextView) itemView.findViewById(R.id.subtitle);
        icon=(ImageView)itemView.findViewById(R.id.list_item_genre_icon);


    }

    public void setSubTitletName(String name)
    {
        subTitleTextView.setText(name);
    }
}

