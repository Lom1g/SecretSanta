package com.lomig.secretsanta;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private ArrayList<Player> playerArrayList;
    private OnMenuItemClickListener listener;

    public interface OnMenuItemClickListener{
        void OnMenuItemClick(int position, MenuItem item);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener){
        this.listener = listener;
    }

    public PlayerAdapter(ArrayList<Player> arrayList){
        this.playerArrayList = arrayList;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_cardview, parent, false);
        return new PlayerViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = playerArrayList.get(position);
        holder.textPseudo.setText(player.getPseudo());
    }

    @Override
    public int getItemCount() {
        return playerArrayList.size();
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder{

        public TextView textPseudo;
        public Toolbar subMenuPlayer;

        public PlayerViewHolder(@NonNull View itemView, OnMenuItemClickListener listener) {
            super(itemView);
            textPseudo = itemView.findViewById(R.id.textPseudo);
            subMenuPlayer = itemView.findViewById(R.id.subMenuPlayer);
            subMenuPlayer.setOnMenuItemClickListener(item -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.OnMenuItemClick(position, item);
                    }
                }
                return true;
            });
        }
    }
}
