package com.igor.blackjack.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Classe modelo para o jogador.
 */
public class Player implements Cloneable, Serializable, Comparable<Player> {

    private String name;
    private List<Card> hand;
    private Integer partialScore;
    private Integer totalScore;
    private Boolean human;

    public Player(String name, Boolean isHuman) {
        this.name = name;
        hand = new ArrayList<>();
        this.partialScore = 0;
        this.totalScore = 0;
        this.human = isHuman;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand.addAll(hand);
    }

    public void addCard(Card card){
        this.hand.add(card);
    }

    public Card getLastCard(){
       return this.hand.get(this.hand.size() - 1);
    }

    public Integer getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(Integer partialScore) {
        this.partialScore = partialScore;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Boolean isHuman() {
        return human;
    }

    public void setHuman(Boolean human) {
        this.human = human;
    }

    public void updatePartialScore(){
        this.partialScore += this.hand.get(this.hand.size() - 1).getCardValue();
    }

    public void updateTotalScore(){
        this.totalScore += this.hand.get(this.hand.size() - 1).getCardValue();
    }

    /**
     * Retorna a soma de todas as cartas possuidas.
     * @return Um Inteiro.
     */
    public Integer getSum(){
        int sum = 0;
        for(Card card : hand){
            sum += card.getCardValue();
        }
        return sum;
    }

    @Override
    public Player clone() throws CloneNotSupportedException {
        return (Player) super.clone();
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", hand=" + hand +
                ", partialScore=" + partialScore +
                ", totalScore=" + totalScore +
                ", human=" + human +
                '}';
    }


    @Override
    public int compareTo(Player o) {
        if(o.getTotalScore() > this.getTotalScore()) return 1;
        if(o.getTotalScore() == this.getTotalScore()) return 0;
        return - 1;
    }


    public String listCards(){
        String response = "";
        for(Card card : this.hand){
            response += card.getName() + " de " + card.getSuit().getName() + System.lineSeparator();
        }
        return response.trim();
    }
}
