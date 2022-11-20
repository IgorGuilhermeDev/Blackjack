package com.igor.blackjack.models;

import java.io.Serializable;

/**
 * Classe modelo que representa uma carta.
 */
public class Card implements Serializable {
    private String name;
    private CardSuit suit;
    private Integer cardValue;
    public Integer cardImage;

    public Card(String name, CardSuit suit, int cardValue, int cardImage){
        this.name = name;
        this.suit = suit;
        this.cardValue = cardValue;
        this.cardImage = cardImage;
    }

    public Card(String name, CardSuit suit, int cardValue){
        this.name = name;
        this.suit = suit;
        this.cardValue = cardValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }

    public Integer getCardValue() {
        return cardValue;
    }

    public void setCardValue(Integer cardValue) {
        this.cardValue = cardValue;
    }

    public Integer getCardImage() {
        return cardImage;
    }

    public void setCardImage(Integer cardImage) {
        this.cardImage = cardImage;
    }


    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", suit=" + suit +
                ", cardValue=" + cardValue +
                '}';
    }
}
