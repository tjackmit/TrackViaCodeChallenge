package com.example.thecoloradophotosappv2;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView.Adapter;


import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private ArrayList<ContactModel> contactModels;
    Context mContext;
    private LayoutInflater mInflater;

    // CustomAdapter constructor
    public CustomAdapter(Context context, ArrayList<ContactModel> models) {
        this.mInflater = LayoutInflater.from(context);
        contactModels = models;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.metadata, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContactModel contactModel = contactModels.get(position);
        holder.textTitle.setText(contactModel.getTitle());
        holder.textLink.setText(contactModel.getLink());
        holder.textThumbnail.setText(contactModel.getThumbnail());
        holder.textDateTaken.setText(contactModel.getDateTaken());
        holder.textDescription.setText(contactModel.getDescription());
        holder.textPublished.setText(contactModel.getPublished());
        holder.textAuthor.setText(contactModel.getAuthor());
        holder.textAuthorID.setText(contactModel.getAuthorID());
        holder.textTags.setText(contactModel.getTags());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return contactModels.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textLink;
        TextView textThumbnail;
        TextView textDateTaken;
        TextView textDescription;
        TextView textPublished;
        TextView textAuthor;
        TextView textAuthorID;
        TextView textTags;

        ViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.title);
            textLink = itemView.findViewById(R.id.link);
            textThumbnail = itemView.findViewById(R.id.media);
            textDateTaken = itemView.findViewById(R.id.date_taken);
            textDescription = itemView.findViewById(R.id.description);
            textPublished = itemView.findViewById(R.id.published);
            textAuthor = itemView.findViewById(R.id.author);
            textAuthorID = itemView.findViewById(R.id.author_id);
            textTags = itemView.findViewById(R.id.tags);

        }
    }

}
