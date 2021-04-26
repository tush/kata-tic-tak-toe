package com.tush.kata.game.model.enums;

/**
 * PlayerType Enum
 * The class defines enum for Player Type X or O
 */
public enum PlayerType {
    X(1), O(2);

    private final Integer value;

    PlayerType(int i) {
        this.value = i;
    }

    public Integer getValue() {
        return value;
    }
}
