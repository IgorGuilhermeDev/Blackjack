package com.igor.blackjack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.igor.blackjack.adapters.AdapterWinners;
import com.igor.blackjack.listeners.MyRecyclerClickListener;
import com.igor.blackjack.models.Player;

public class ResultsActivity extends AppCompatActivity {

    private RecyclerView rvWinners;
    private TextView tvWinner;
    private Player[] players;

    private Button btPLayAgain;
    private Button btFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        initializeComponents();
        setEvents();

        Bundle bundle = getIntent().getExtras();

        this.players = (Player[]) bundle.get("players");
        setWinner((Player) bundle.get("winner"));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        dividerItemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.lines));
        AdapterWinners adapterWinners = new AdapterWinners(players);


        this.rvWinners.setLayoutManager(layoutManager);
        this.rvWinners.setHasFixedSize(true);
        this.rvWinners.addItemDecoration(dividerItemDecoration);
        setListEvents();
        this.rvWinners.setAdapter(adapterWinners);

    }

    /**
     * Inicializa os componentes
     */
    private void initializeComponents(){
        this.tvWinner = findViewById(R.id.tv_winner);
        this.rvWinners = findViewById(R.id.rv_winners);
        this.btPLayAgain = findViewById(R.id.bt_play_again);
        this.btFinish = findViewById(R.id.bt_finish);

    }

    /**
     * seta os eventos dos botões
     */
    private void setEvents(){
        this.btPLayAgain.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gameRestart();
                    }
                }
        );
        this.btFinish.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishAffinity();
                    }
                }
        );
    }

    /**
     * Reinicia o jogo
     */
    private void gameRestart(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    /**
     * Mostra o resultado da partida em um TextView
     * @param winner O vencedor
     */
    private void setWinner(Player winner){
        if(winner == null) this.tvWinner.setText("EMPATE");
        else this.tvWinner.setText("Vencedor: " + winner.getName());
    }

    /**
     * Seta os eventos da lista
     */
    private void setListEvents(){
        rvWinners.addOnItemTouchListener(

                new MyRecyclerClickListener(
                        getApplicationContext(), rvWinners, new MyRecyclerClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getApplicationContext(),players[position].listCards() , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
                )
        );
    }
}