package com.example.dean.bigbangmk2;

/**
 * Created by Dean on 25/03/2015.
 */
public class SicssorComparisionStratergy implements ComparisonStrategy{
    public int compare(int selected) {
        switch (selected) {
            case GameHub.SICSSOR:
                return GameHub.TIE;

            case GameHub.PAPER:
                return GameHub.WIN;

            case GameHub.LIZARD:
                return GameHub.WIN;

            default:
                return GameHub.LOSS;

        }
    }
}
