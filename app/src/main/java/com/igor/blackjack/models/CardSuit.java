package com.igor.blackjack.models;

import java.io.Serializable;

/**
 * Classe que presenta os tipos de cada carta.
 */
public enum CardSuit implements Serializable {
    CLUBS("Paus"),
    DIAMONDS("Ouros"),
    HEARTS("Copas"),
    SPADES("Espadas");

    private String name;

    private CardSuit(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return "CardSuit{" +
                "name='" + name + '\'' +
                '}';
    }
}
