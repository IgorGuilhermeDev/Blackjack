package com.igor.blackjack.models;


import com.igor.blackjack.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe modelo para o Jogo.
 */
public class Game {

    private Player[] players;
    private Integer turn;
    private List<Card> deck;
    private Player winner;

    public Game (Player[] players){
        this.players = players;
        this.deck = new ArrayList<>();
        this.turn = 0;
        initDeck(this.deck);
    }

    private void initDeck(List<Card> deck){
        this.deck.addAll(loadCards());

    }

    /**
     * Inicializa as cartas do deck.
     * @return Uma lista de cartas.
     */
    private List<Card> loadCards(){
        List<Card> allCards = new ArrayList<>(Arrays.asList(
                new Card("A",CardSuit.HEARTS,1, R.drawable.ca), new Card("2",CardSuit.HEARTS,2,R.drawable.c2),
                new Card("3",CardSuit.HEARTS,3,R.drawable.c3), new Card("4",CardSuit.HEARTS,4,R.drawable.c4),
                new Card("5",CardSuit.HEARTS,5,R.drawable.c5), new Card("6",CardSuit.HEARTS,6,R.drawable.c6),
                new Card("7",CardSuit.HEARTS,7,R.drawable.c7), new Card("8",CardSuit.HEARTS,8,R.drawable.c8),
                new Card("9",CardSuit.HEARTS,9,R.drawable.c9), new Card("10",CardSuit.HEARTS,10,R.drawable.c10),
                new Card("Q",CardSuit.HEARTS,10,R.drawable.cq), new Card("J",CardSuit.HEARTS,10,R.drawable.cj),
                new Card("R",CardSuit.HEARTS,10,R.drawable.cr),new Card("A",CardSuit.SPADES,1,R.drawable.ea), new Card("2",CardSuit.SPADES,2,R.drawable.e2),
                new Card("3",CardSuit.SPADES,3,R.drawable.e3), new Card("4",CardSuit.SPADES,4,R.drawable.e4),
                new Card("5",CardSuit.SPADES,5,R.drawable.e5), new Card("6",CardSuit.SPADES,6,R.drawable.e6),
                new Card("7",CardSuit.SPADES,7,R.drawable.e7), new Card("8",CardSuit.SPADES,8,R.drawable.e8),
                new Card("9",CardSuit.SPADES,9,R.drawable.e9), new Card("10",CardSuit.SPADES,10,R.drawable.e10),
                new Card("Q",CardSuit.SPADES,10,R.drawable.eq), new Card("J",CardSuit.SPADES,10,R.drawable.ej),
                new Card("R",CardSuit.SPADES,10,R.drawable.er),  new Card("A",CardSuit.DIAMONDS,1,R.drawable.oa), new Card("2",CardSuit.DIAMONDS,2,R.drawable.o2),
                new Card("3",CardSuit.DIAMONDS,3,R.drawable.o3), new Card("4",CardSuit.DIAMONDS,4,R.drawable.o4),
                new Card("5",CardSuit.DIAMONDS,5,R.drawable.o5), new Card("6",CardSuit.DIAMONDS,6,R.drawable.o6),
                new Card("7",CardSuit.DIAMONDS,7,R.drawable.o7), new Card("8",CardSuit.DIAMONDS,8,R.drawable.o8),
                new Card("9",CardSuit.DIAMONDS,9,R.drawable.o9), new Card("10",CardSuit.DIAMONDS,10,R.drawable.o10),
                new Card("Q",CardSuit.DIAMONDS,10,R.drawable.oq), new Card("J",CardSuit.DIAMONDS,10,R.drawable.oj),
                new Card("R",CardSuit.DIAMONDS,10,R.drawable.or),new Card("A",CardSuit.CLUBS,1,R.drawable.pa), new Card("2",CardSuit.CLUBS,2,R.drawable.o2),
                new Card("3",CardSuit.CLUBS,3,R.drawable.p3), new Card("4",CardSuit.CLUBS,4,R.drawable.p4),
                new Card("5",CardSuit.CLUBS,5,R.drawable.p5), new Card("6",CardSuit.CLUBS,6,R.drawable.p6),
                new Card("7",CardSuit.CLUBS,7,R.drawable.p7), new Card("8",CardSuit.CLUBS,8,R.drawable.p8),
                new Card("9",CardSuit.CLUBS,9,R.drawable.p9), new Card("10",CardSuit.CLUBS,10,R.drawable.p10),
                new Card("Q",CardSuit.CLUBS,10,R.drawable.pq), new Card("J",CardSuit.CLUBS,10,R.drawable.pj),
                new Card("R",CardSuit.CLUBS,10,R.drawable.pr)));
        return allCards;
    }

    /**
     * Muda de turno.
     * @return Um Boolean, caso true o jogo acabou, false o jogo continua.
     */
    public Boolean changeTurn(){
        this.turn = (turn == players.length - 1) ? 0 : turn + 1;
        return (this.turn == 0);
    }
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Retorna uma carta do deck, e a remove.
     * @param cardIndex Um Inteiro que representa a carta.
     * @return Uma carta.
     */
    public Card getCard (int cardIndex){
        if(this.deck.get(cardIndex) != null){
            Card cardAux = this.deck.get(cardIndex);
            this.deck.remove(cardAux);
            return cardAux;
        }
        return null;
    }

    public Integer getDeckSize(){
        return this.deck.size();
    }

    public Player getPlayerTurn(){
        return this.players[turn];
    }

    /**
     * Retorna o único player humano do jogo.
     * @return Um player.
     */
    public Player getHuman() {
        for (Player player : this.players){
            if(player.isHuman()) return player;
        }
        return null;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
