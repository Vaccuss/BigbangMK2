package com.example.dean.bigbangmk2;

/**
 * Created by Dean on 25/03/2015.
 */
public class PaperComparisionStratergy implements ComparisonStrategy {
    public int compare(int selected) {
        switch (selected) {
            case GameHub.PAPER:
                return GameHub.TIE;

            case GameHub.ROCK:
            case GameHub.SPOCK:
                return GameHub.WIN;

            default:
                return GameHub.LOSS;

        }

    }
}
