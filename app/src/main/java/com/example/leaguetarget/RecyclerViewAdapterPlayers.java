package com.example.leaguetarget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapterPlayers extends RecyclerView.Adapter<RecyclerViewAdapterPlayers.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private Context mContext;
    private ArrayList<String> playersImages;
    private ArrayList<String> playersFullnames;
    private ArrayList<String> playersNicknames;
    private ArrayList<String> playersNationalityFlag;
    private ArrayList<String> playersAges;
    private ArrayList<String> playersRoles;


    public RecyclerViewAdapterPlayers(Context mContext, ArrayList<String> playersImages, ArrayList<String> playersFullnames, ArrayList<String> playersNicknames, ArrayList<String> playersNationalityFlag, ArrayList<String> playersAges, ArrayList<String> playersRoles) {
        this.mContext = mContext;
        this.playersImages = playersImages;
        this.playersFullnames = playersFullnames;
        this.playersNicknames = playersNicknames;
        this.playersNationalityFlag = playersNationalityFlag;
        this.playersAges = playersAges;
        this.playersRoles = playersRoles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(mContext)
                .asBitmap()
                .load(playersImages.get(position))
                .into(holder.player_image);

        Glide.with(mContext)
                .asBitmap()
                .load("https://flagcdn.com/w20/" + playersNationalityFlag.get(position) + ".png")
                .into(holder.player_nationality);

        holder.player_fullname.setText(playersFullnames.get(position));
        holder.player_nickname.setText(playersNicknames.get(position));
        holder.player_age.setText(playersAges.get(position));
        holder.player_role.setText(playersRoles.get(position));

        holder.player_parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + playersImages.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return playersImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView player_image;
        TextView player_fullname;
        TextView player_nickname;
        ImageView player_nationality;
        TextView player_age;
        TextView player_role;
        RelativeLayout player_parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            player_image = itemView.findViewById(R.id.player_image);
            player_fullname = itemView.findViewById(R.id.player_fullname);
            player_nickname = itemView.findViewById(R.id.player_nickname);
            player_nationality= itemView.findViewById(R.id.player_nationality);
            player_age = itemView.findViewById(R.id.player_age);
            player_role = itemView.findViewById(R.id.player_role);
            player_parent_layout = itemView.findViewById(R.id.player_parent_layout);
        }
    }
}
