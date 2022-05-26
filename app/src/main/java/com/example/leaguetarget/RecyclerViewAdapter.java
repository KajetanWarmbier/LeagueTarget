package com.example.leaguetarget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private String seriesApiLink;
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mImageNames, ArrayList<String> mImages, String seriesApiLink) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
        this.seriesApiLink = seriesApiLink;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.team_image);

        holder.team_name.setText(mImageNames.get(position));

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, TeamActivity.class);
                intent.putExtra("teamId", position);
                intent.putExtra("seriesApiLink", seriesApiLink);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView team_image;
        TextView team_name;
        RelativeLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            team_image = itemView.findViewById(R.id.team_image);
            team_name = itemView.findViewById(R.id.player_fullname);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
