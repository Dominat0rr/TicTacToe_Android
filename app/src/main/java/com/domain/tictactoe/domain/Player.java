package com.domain.tictactoe.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable, Parcelable {
    private static final long serialVersionUID = 1L;
    private int score = 0;
    private String name;

    /**
     * Constructor for Player object without parameters
     */
    public Player() { }

    /**
     * Constructor for Player object with name as parameter
     * @param name
     */
    public Player(String name) {
        setName(name);
    }

    // Getters
    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setScore(int score) throws IllegalArgumentException {
        if (isValidScore(score)) this.score = score;
        else throw new IllegalArgumentException("score cannot be negative");
    }

    public void setName(String name) throws IllegalArgumentException {
        if (isValidName(name)) this.name = name;
        else throw new IllegalArgumentException("Name cannot be empty");
    }

    // isValid
    private static boolean isValidScore(int score) {
        return score >= 0;
    }

    private static boolean isValidName(String name) {
        return name != null && !name.isEmpty() && name.trim().length() > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Player{" +
                "score=" + score +
                ", name='" + name + '\'' +
                '}';
    }


    /**
     * For saving object
     * using the onSaveInstanceState
     * and onRestoreInstanceState
     * in MainActivity class
     */
        private int mData;

        public int describeContents() {
            return 0;
        }

        /** save object in parcel */
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(mData);
        }

        public static final Parcelable.Creator<Player> CREATOR
                = new Parcelable.Creator<Player>() {
            public Player createFromParcel(Parcel in) {
                return new Player(in);
            }

            public Player[] newArray(int size) {
                return new Player[size];
            }
        };

        /** recreate object from parcel */
        private Player(Parcel in) {
            mData = in.readInt();
        }
}
