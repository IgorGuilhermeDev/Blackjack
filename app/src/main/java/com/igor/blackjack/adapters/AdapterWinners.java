package com.igor.blackjack.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igor.blackjack.R;
import com.igor.blackjack.models.Player;

public class AdapterWinners extends RecyclerView.Adapter<AdapterWinners.MyViewHolder> {

    private Player[] players;

    public AdapterWinners(Player [] players){
        this.players = players;
    }

    /**
     * Método responsável por criar os itens da lista
     * @param parent View parant dessa, ou seja view do xml que possui esta recycler view
     * @param viewType tipo da view
     * @return Uma instancia de MyViewHolder, ou seja, um item da lista
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adpter_winners, parent, false);

        return new MyViewHolder(item);
    }

    /**
     * Método designado a preencher o conteudo dos itens da lista.
     * @param holder Um item da lista
     * @param position Posição deste item da lista
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvPlayerName.setText(this.players[position].getName());
        holder.tvTotalScore.setText(this.players[position].getTotalScore()+"");
    }

    /**
     * Retorna o  tamanho da lista
     * @return Um inteiro
     */
    @Override
    public int getItemCount() {
        return this.players.length;
    }

    /**
     * Classe responsável por representar um item da lista
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvPlayerName;
        private TextView tvTotalScore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlayerName = itemView.findViewById(R.id.tvPlayerName);
            tvTotalScore = itemView.findViewById(R.id.tvPlayerScore);
        }
    }
}
