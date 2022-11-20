package com.igor.blackjack;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.igor.blackjack.controllers.GameControler;
import com.igor.blackjack.models.Card;
import com.igor.blackjack.models.Game;
import com.igor.blackjack.models.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Igor Guilherme Almeida Rocha
 */

public class MainActivity extends AppCompatActivity {

    private GameControler gameControler;
    private Game game;

    private TextView tvPlayerTurn;

    private TextView tvPlayerPartialScore;
    private TextView tvPlayerTotalScore;

    private TextView tvCPU1PartialScore;
    private TextView tvCPU2PartialScore;
    private TextView tvCPU3PartialScore;
    private TextView tvCPU1LastCard;
    private TextView tvCPU2LastCard;
    private TextView tvCPU3LastCard;

    private ImageView cardPlayer;
    private Button btContinue;
    private Button btStop;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUiComponents();
        setEvents();
        initializeGame();
        Log.i("ON","create");


    }

    /**
     * Método utilizado para inicializar os componentes visuais.
     */
    private void initializeUiComponents(){
        tvPlayerTurn = findViewById(R.id.player_turn);

        tvPlayerTotalScore = findViewById(R.id.player_total);
        tvPlayerPartialScore = findViewById(R.id.player_partial);
        cardPlayer = findViewById(R.id.player_last_card);
        btContinue = findViewById(R.id.bt_continue);
        btStop = findViewById(R.id.bt_stop);

        tvCPU1PartialScore = findViewById(R.id.cpu_1_partial_score);
        tvCPU2PartialScore = findViewById(R.id.cpu_2_partial_score);
        tvCPU3PartialScore = findViewById(R.id.cpu_3_partial_score);
        tvCPU1LastCard = findViewById(R.id.cpu_1_last_card);
        tvCPU2LastCard = findViewById(R.id.cpu_2_last_card);
        tvCPU3LastCard = findViewById(R.id.cpu_3_last_card);

    }

    /**
     * Método utilizado para fazer as configurações iniciais do jogo.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initializeGame(){
        this.game = new Game((Player[]) Arrays.asList(new Player("Igor",true), new Player("CPU-1",false)
                , new Player("CPU-2",false),  new Player("CPU-3",false)).toArray());
        this.gameControler = new GameControler(this.game);
        configInitialHands();
        setImage();
        configInitialPlayer();

    }

    /**
     * Método que sorteia e cofigura o primeiro jogador.
     */
    private void configInitialPlayer(){
        this.gameControler.sortPositions();
        Player player = this.game.getPlayerTurn();
        this.tvPlayerTurn.setText(getString(R.string.turn) + " " + player.getName());

        if(!player.isHuman()){
            changeButtonState(Arrays.asList(this.btContinue,this.btStop), false, Color.BLACK);
            startCpu(player);
        }else enableOrDisable(player.getTotalScore());

    }


    /**
     * Método responsável por sortear as duas primeiras cartas para cada jogador.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void configInitialHands(){
        this.gameControler.sortInitialHands();
        Arrays.stream(this.game.getPlayers()).forEach(player -> {
            updateScores(player, true);
        });

    }

    /**
     * Método utilizado para comprar mais uma carta do baralho e passar a vez caso a soma seja maior que 21.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void drawMoreOne(){
        int sum =  this.gameControler.addMoreOne();
        Player player = this.game.getPlayerTurn();
        handleResult(player);
        if(player.isHuman()) {
            enableOrDisable(sum);
            setImage();
            if(!btContinue.isEnabled()) stopDraw();
        }

    }

    /**
     * Método utilizado para parar de comprar e passar a vez.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void stopDraw(){
        Player player = this.game.getPlayerTurn();
        if(player.isHuman()) changeButtonState(Arrays.asList(this.btStop, this.btContinue), false, Color.BLACK);
        boolean isFinished = this.game.changeTurn();
        player = this.game.getPlayerTurn();

        if(!isFinished){
            ModifyUITurn(player);
            if(!player.isHuman()) startCpu(player);
            else enableOrDisable(player.getTotalScore());

        }else{
            this.gameControler.organizeWinners();
            Intent intent =  new Intent(this, ResultsActivity.class);
            intent.putExtra("players", this.gameControler.organizeWinners());
            intent.putExtra("winner", this.game.getWinner());
            startActivity(intent);
        }
    }

    /**
     * Este método pega a imagem da última carta comprada pelo jogador humano.
     */
    private void setImage(){
        int imageId = this.game.getHuman().getLastCard().cardImage;
        this.cardPlayer.setImageResource(imageId);
    }

    /**
     * Configura as ações que serão feitas após o click do usuário.
     */
    private void setEvents(){
        this.btContinue.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                drawMoreOne();
            }
        });
        this.btStop.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                stopDraw();
            }
        });
    }

    /**
     * Tem a função de iniciar as jogadas da CPU.
     * @param player Instância de Player, este player representa o jogador atual que seja uma CPU.
     */
    private void startCpu(Player player)  {
        CpuPlay cpuPlay = new CpuPlay(player);
        cpuPlay.start();
    }

    /**
     * Este método representa a decisão da CPU de continuar comprando ou parar com 50% de probabilidade.
     * @param player Instância de Player, este player representa o jogador atual que seja uma CPU.
     * @return Boolean true para continuar comprando, false para parar.
     */
    private Boolean continueOrStop(Player player){
        if(player.getTotalScore() < 16) return true;
        return new Random().nextInt(2) == 0 ? true : false;
    }

    /**
     * Método responsável por atualizar os placares, e anunciar um 21 ou um estouro.
     * @param player Instância de Player, este player representa o jogador atual.
     */
    private void handleResult(Player player){
        updateScores(player,false);
        isGreaterThan21(player, this);
    }

    /**
     * Atualiza o score do jogador humano
     * @param player Instância de Player, este player representa o jogador atual.
     */
    private void updateScores(Player player, boolean isFirstHand)  {

        if(player.isHuman()){
            try {
                changeTvValue(player, this.tvPlayerPartialScore, this.tvPlayerTotalScore);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                Log.d("CLONE","Erro ao clonar player");
            }
        }else{
            updateNotHumanScore(player, isFirstHand);
        }
    }

    /**
     * Atualiza os placares das máquinas
     * @param player Player atual
     * @param isFirstHand se é true não atualiza a última carta
     */
    private void updateNotHumanScore(Player player, Boolean isFirstHand){
        String playerName = player.getName();
        Card lastCard = player.getLastCard();

        if(playerName.equals("CPU-1")) {
            changeTvValue(player.getPartialScore(), this.tvCPU1PartialScore);
            if(!isFirstHand) attLastCard(this.tvCPU1LastCard, lastCard);
        }
        else if(playerName.equals("CPU-2")){
            changeTvValue(player.getPartialScore(), this.tvCPU2PartialScore);
            if(!isFirstHand) attLastCard(this.tvCPU2LastCard, lastCard);
        }
        else if(playerName.equals("CPU-3")) {
            changeTvValue(player.getPartialScore(), this.tvCPU3PartialScore);
            if(!isFirstHand) attLastCard(this.tvCPU3LastCard, lastCard);
        }
    }

    /**
     * Atualiza os placares do jogador.
     * @param player Instância de Player, este player representa o jogador atual.
     * @param tvPartialScore TextView referente ao placar parcial do jogador.
     * @param tvTotalScore TextView visual referente placar total do jogador.
     * @throws CloneNotSupportedException caso a operação de clonagem de errado.
     */
    private void changeTvValue(Player player, TextView tvPartialScore, TextView tvTotalScore) throws CloneNotSupportedException {
        Player playerCopy = player.clone();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvPartialScore.setText(getString(R.string.partial_score) + " " + playerCopy.getPartialScore());
                tvTotalScore.setText(getString(R.string.total_score) + " " + playerCopy.getTotalScore());
            }
        });
    }

    /**
     * Atualiza placar parcial.
     * @param partialScore valor parcial do jogador.
     * @param tvPartialScore TextView referente ao placar parcial do jogador.
      */
    private void changeTvValue(int partialScore, TextView tvPartialScore){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvPartialScore.setText(getString(R.string.partial_score) + " " + partialScore);
            }
        });

    }

    /**
     * Atualiza a última carta jogada
     * @param tvLastCard TextView referente a última carta jogada
     * @param lastCard última carta jogada
     */
    private void attLastCard(TextView tvLastCard, Card lastCard){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvLastCard.setText(getString(R.string.cpu_last_card) + " " + lastCard.getName() + " de " + lastCard.getSuit().getName());
            }
        });
    }


    /**
     * Decide se o botão continuar e parar estarão abilitados ou não.
     * @param sum Um inteiro que representa a soma dos valores de todas as cartas do jogador.
     */
    private void enableOrDisable(int sum){
        if(sum < 16) {
            changeButtonState(this.btStop, false, Color.BLACK);
            changeButtonState(this.btContinue, true, Color.blue(R.color.blue));
        }
        else if(sum < 21) {
            changeButtonState(this.btStop, true, Color.blue(R.color.blue));
            changeButtonState(this.btContinue, true, Color.blue(R.color.blue));
        }
        else changeButtonState(Arrays.asList(this.btContinue,this.btStop), false, Color.BLACK);

    }

    /**
     * Habilita ou desabilita várias views, e muda as cores delas.
     * @param views Uma Lista de Views.
     * @param enable Um boolean, true para habilitar as views e false para desabilitar.
     * @param color Um Inteiro que representa a proxima cor de fundo dessas views.
     */
    private void changeButtonState(List<View> views, Boolean enable, Integer  color){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(View view : views){
                    view.setEnabled(enable);
                    view.setBackgroundColor(color);
                }
            }
        });
    }

    /**
     * Habilita ou desabilita uma view, e muda a cor dela.
     * @param view Uma view.
     * @param enable Um boolean, true para habilitar as views e false para desabilitar.
     * @param color Um Inteiro que representa a proxima cor de fundo dessa view.
     */
    private void changeButtonState(View view, Boolean enable, Integer  color){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(enable);
                view.setBackgroundColor(color);
            }
        });
    }


    /**
     * Verifica se a soma das cartas do Jogador ultrapassa 21.
     * @param player Uma Instância de Player, representa o jogador atual.
     */
    private void isGreaterThan21(Player player, Context context){
        int sum = player.getTotalScore();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(sum > 21) Toast.makeText(context, player.getName() +": Estourou", Toast.LENGTH_SHORT).show();
                else if(sum == 21) Toast.makeText(context, player.getName() +": 21!!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * Classe interna responsável pelas jogadas da CPU.
     */
    private class CpuPlay extends Thread{
        Player player;

        /**
         * @param player Um Player ,jogador atual, que seja uma CPU.
         */
        public CpuPlay(Player player){
            this.player = player;
        }

        /**
         * Responsável por fazer as jogadas da CPU.
         */
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run(){
            try{
                while(continueOrStop(player) && player.getTotalScore() < 21) {
                    Thread.sleep(2000);
                    drawMoreOne();
                    updateScores(player, false);
                }
                Thread.sleep(2000);
                stopDraw();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Solicita  a mudança do texto do componente visual responsável por dizer a vez de cada jogador.
     * @param player Um player, jogador Atual.
     */
    private void ModifyUITurn(Player player){
        try {
            Player playerCopy  = player.clone();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvPlayerTurn.setText(getString(R.string.turn) + " " + player.getName());
                }
            });
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }



}