package com.example.aron.maththermind.model;

public class Items {

    private String name, score, solved, rank;

    public Items() {
    }

    public Items(String name, String score, String solved) {

        this.name = name;
        this.score = score;
        this.solved = solved;
        this.rank = rank;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSolved()
    {
        return solved;
    }

    public void setSolved (String solved)
    {
        this.solved = solved;
    }

    public String getRank()
    {
        return rank;
    }

    public void setRank (String rank)
    {
        this.rank = rank;
    }
}
