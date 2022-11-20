package com.igor.blackjack.controllers;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.igor.blackjack.models.Game;
import com.igor.blackjack.models.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Classe controladora do jogo.
 */
public class GameControler {

    private Random random = new Random();
    private Game game;
    private Player winner;

    public GameControler(Game game){
        this.game = game;
    }

    /**
     * Sorteia as duas cartas iniciais para cada jogador.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortInitialHands(){
        for(Player player : this.game.getPlayers()){
            player.setHand(Arrays.asList(this.game.getCard(randomCardNumber()), this.game.getCard(randomCardNumber())));
            player.setPartialScore(0);
            player.setTotalScore(player.getSum());
        }
    }

    /**
     * Define a ordem de jogada aleatoriamente.
     */
    public void sortPositions(){
        Collections.shuffle(Arrays.asList(this.game.getPlayers()));
    }

    /**
     * Compra cartas, atualiza os pontos do jogador e retorna os pontos totais.
     * @return Um inteiro referente a soma das cartas do jogador.
     */
    public Integer addMoreOne() {
        Player player = this.game.getPlayerTurn();
        draw(player);
        updateScores(player);
        return player.getTotalScore();
    }

    /**
     * Adiciona mais uma carta para o jogador.
     * @param player Um player, jogador atual.
     */
    private void draw(Player player){
        int randomNumber = randomCardNumber();
        player.addCard(this.game.getCard(randomNumber));
    }

    /**
     * Atualiza os pontos do jogador
     * @param player Um player, jogador atual.
     */
    private void updateScores(Player player){
        player.updatePartialScore();
        player.updateTotalScore();

    }

    /**
     * Retorna número aleatorio com base nas cartas restantes do deck.
     * @return Um Inteiro.
     */
    private Integer randomCardNumber(){
        return (game.getDeckSize() > 1) ? random.nextInt(game.getDeckSize() - 1) : - 1;
    }

    /**
     * Retorna uma lista ordenada dos vencedores e escolhe um vencedor ou empate
     * @return Uma lista
     */

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Player[] organizeWinners(){
        Player [] organizedPlayers = organizelist();
        return organizedPlayers;
    }

    /**
     * Retorna uma lista ordenada dos vencedores e escolhe um vencedor ou empate
     * @return Um array de players
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private Player[] organizelist(){
        List<Player> greater21 = organizeGreater21();
        List<Player> lesser21 = organizeLesser21();
        chooseWinner(lesser21);
        List<Player> organized = new ArrayList<>();
        organized.addAll(lesser21);
        organized.addAll(greater21);
        return organized.toArray(new Player[0]);
    }

    /**
     * Organiza os players que tiveram o resultado maior que 21 em ordem crescente.
     * @return Uma lista de player organizada
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Player> organizeGreater21(){
        List<Player> greater21 = Arrays.stream(this.game.getPlayers()).filter((player -> {
            return player.getTotalScore() > 21;
        })).collect(Collectors.toList());
        Collections.sort(greater21, Collections.reverseOrder());

        return greater21;
    }

    /**
     * Organiza os players que tiveram o resultado menor que 21 em ordem decrescente.
     * @return Uma lista de player organizada
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Player> organizeLesser21(){
        List<Player> lesser21 = Arrays.stream(this.game.getPlayers()).filter((player -> {
            return player.getTotalScore() <= 21;
        }
        )).collect(Collectors.toList());
        Collections.sort(lesser21);
        return lesser21;
    }

    /**
     * Escolhe um vencedor ou empate
     * @param lesser21 uma lista dos jogadores que não estouraram
     */
    private void chooseWinner(List<Player> lesser21){
        if(lesser21.size() < 1) this.game.setWinner(null);
        else if(lesser21.size() == 1) this.game.setWinner(lesser21.get(0));
        else if(lesser21.get(0).getTotalScore() == lesser21.get(1).getTotalScore())this.game.setWinner(null);
        else this.game.setWinner(lesser21.get(0));
    }

}
