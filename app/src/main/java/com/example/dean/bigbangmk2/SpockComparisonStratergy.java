package com.example.dean.bigbangmk2;

/**
 * Created by Dean on 25/03/2015.
 */
public class SpockComparisonStratergy implements ComparisonStrategy{

    public int compare(int selected) {
        switch (selected) {
            case GameHub.SPOCK:
                return GameHub.TIE;

            case GameHub.SICSSOR:
                return GameHub.WIN;

            case GameHub.ROCK:
                return GameHub.WIN;

            default:
                return GameHub.LOSS;

        }

    }
}